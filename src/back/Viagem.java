package back;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.Duration;

public class Viagem implements Lancamento {

    private Usuario usuario;
    private Veiculo veiculo;
    private Estacao estacaoOrigem;
    private Estacao estacaoDestino;

    private LocalDateTime inicio;
    private LocalDateTime termino;

    private BigDecimal valorPago;
    private int duracaoMinutos;

    private static final BigDecimal SALDO_MINIMO = new BigDecimal("5.00");

    public Viagem(Usuario usuario, Veiculo veiculo, LocalDateTime inicio) {
        if (usuario == null) {
            throw new RegraDeNegocioException("Informe o usuário.");
        }
        if (veiculo == null) {
            throw new RegraDeNegocioException("Informe o veículo.");
        }
        if (inicio == null) {
            throw new RegraDeNegocioException("Informe a data e a hora do início.");
        }
        if (usuario.temViagemEmAndamento()) {
            throw new RegraDeNegocioException("O usuário já possui uma viagem em andamento.");
        }
        if (usuario.getSaldo().compareTo(SALDO_MINIMO) < 0) {
            throw new RegraDeNegocioException("Saldo insuficiente para iniciar a viagem.");
        }

        this.estacaoOrigem = veiculo.getEstacao();
        this.usuario = usuario;
        this.veiculo = veiculo;
        this.inicio = inicio;
        veiculo.liberar();
        this.usuario.definirViagemAtual(this);
    }

    public Usuario getUsuario() { return usuario; }
    public Veiculo getVeiculo() { return veiculo; }
    public LocalDateTime getInicio() { return inicio; }
    public LocalDateTime getTermino() { return termino; }
    public int getDuracaoMinutos() { return duracaoMinutos; }
    public BigDecimal getValorPago() { return valorPago; }

    public boolean isEncerrada() {
        return termino != null;
    }

    public void encerrar(Estacao destino, LocalDateTime termino) {
        if (termino == null) {
            throw new RegraDeNegocioException("Informe a data e a hora do término.");
        }
        if (destino == null) {
            throw new RegraDeNegocioException("Informe a estação de destino.");
        }
        if (this.termino != null) {
            throw new RegraDeNegocioException("Esta viagem já foi encerrada.");
        }
        if (!termino.isAfter(inicio)) {
            throw new RegraDeNegocioException("O término deve ser posterior ao início.");
        }

        int minutos = (int) Duration.between(inicio, termino).toMinutes();
        veiculo.devolver(destino, minutos);
        BigDecimal valor = usuario.getPlano().calcularValor(veiculo, minutos);
        valor = valor.setScale(2, RoundingMode.HALF_UP);

        this.termino = termino;
        this.estacaoDestino = destino;
        this.duracaoMinutos = minutos;
        this.valorPago = valor;

        usuario.concluirViagem(this);
    }

    @Override
    public LocalDateTime getDataHora() {
        return termino;
    }

    @Override
    public BigDecimal getValor() {
        return valorPago.negate();
    }

    @Override
    public String getDescricao() {
        return "back.Viagem - " + veiculo.getModelo() + " (" + duracaoMinutos + "min)";
    }

}
