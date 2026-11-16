package back;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nome;
    private String email;
    private BigDecimal saldo;
    private Plano plano;
    private Viagem viagemAtual;
    private final ArrayList<Lancamento> lancamentos = new ArrayList<>();

    public Usuario(String nome, String email, Plano plano) {
        if (nome == null || nome.isBlank()) {
            throw new RegraDeNegocioException("Nome obrigatório.");
        }
        if (email == null || email.isBlank()) {
            throw new RegraDeNegocioException("Email obrigatório.");
        }
        if (plano == null) {
            throw new RegraDeNegocioException("back.Plano obrigatório.");
        }
        this.nome = nome;
        this.email = email;
        this.saldo = BigDecimal.ZERO;
        this.plano = plano;
    }

    public String getNome() { return nome; }

    public String getEmail() { return email; }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public Plano getPlano() {
        return plano;
    }

    public Viagem getViagemAtual() {
        return viagemAtual;
    }

    private void lancar(Lancamento lancamento) {
        saldo = saldo.add(lancamento.getValor());
        lancamentos.add(lancamento);
    }

    public void recarregar(BigDecimal valor, LocalDateTime dataHora) {
        Recarga recarga = new Recarga(valor, dataHora);
        lancar(recarga);
    }

    public void concluirViagem(Viagem viagem) {
        if (viagem != viagemAtual) {
            throw new RegraDeNegocioException("Esta não é a viagem em andamento do usuário.");
        }
        lancar(viagem);
        viagemAtual = null;
    }

    public boolean temViagemEmAndamento() {
        return viagemAtual != null;
    }

    public void definirViagemAtual(Viagem viagem) {
        this.viagemAtual = viagem;
    }

    public String emitirExtrato() {
        if (lancamentos.isEmpty()) {
            return "Nenhum lançamento";
        }

        ArrayList<Lancamento> ordenados = new ArrayList<>(lancamentos);
        ordenados.sort(Comparator.comparing(Lancamento::getDataHora));

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        StringBuilder extrato = new StringBuilder();
        BigDecimal saldoAtual = BigDecimal.ZERO;

        for (Lancamento l : ordenados) {
            saldoAtual = saldoAtual.add(l.getValor());

            String tipo;
            if (l.getValor().signum() >= 0) {
                tipo = "C";
            } else {
                tipo = "D";
            }

            extrato.append(l.getDataHora().format(formato));
            extrato.append(" | ");
            extrato.append(l.getDescricao());
            extrato.append(" | ");
            extrato.append(tipo);
            extrato.append(" R$ ");
            extrato.append(String.format("%.2f", l.getValor().abs()));
            extrato.append(" | Saldo: R$ ");
            extrato.append(String.format("%.2f", saldoAtual));
            extrato.append("\n");
        }

        return extrato.toString();
    }

}
