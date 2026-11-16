package back;

import java.io.Serializable;
import java.math.BigDecimal;

public abstract class Veiculo implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String codigo;
    private final String modelo;
    private Estacao estacao;
    private EstadoVeiculo estado = EstadoVeiculo.DISPONIVEL;

    protected Veiculo(String codigo, String modelo, Estacao estacaoInicial) {
        this.codigo = codigo;
        this.modelo = modelo;
        estacaoInicial.receber(this);
        this.estacao = estacaoInicial;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getModelo() {
        return modelo;
    }

    public Estacao getEstacao() {
        return estacao;
    }

    public EstadoVeiculo getEstado() {
        return estado;
    }

    public abstract BigDecimal taxaDesbloqueio();
    public abstract BigDecimal valorPorMinuto();

    public void liberar() {
        if (estado != EstadoVeiculo.DISPONIVEL) {
            throw new RegraDeNegocioException("Somente veículos disponíveis podem ser liberados.");
        }
        estacao.retirar(this);
        estacao = null;
        estado = EstadoVeiculo.EM_USO;
    }

    public void devolver(Estacao estacao, int minutos) {
        if (estado != EstadoVeiculo.EM_USO) {
            throw new RegraDeNegocioException("Veículo não está em uso");
        }
        if (minutos < 0) {
            throw new RegraDeNegocioException("Minutos não podem ser negativos");
        }
        estacao.receber(this);
        this.estacao = estacao;
        estado = EstadoVeiculo.DISPONIVEL;
    }

    public void enviarParaManutencao() {
        if (estado == EstadoVeiculo.EM_USO) {
            throw new RegraDeNegocioException("Veículos em uso não podem ir para manutenção.");
        }
        estado = EstadoVeiculo.EM_MANUTENCAO;
    }

    public void concluirManutencao() {
        if (estado != EstadoVeiculo.EM_MANUTENCAO) {
            throw new RegraDeNegocioException("Veículo não está em manutenção");
        }
        estado = EstadoVeiculo.DISPONIVEL;
    }

    public boolean concedeDescontoUniversitario() {
        return true;
    }

    public abstract TipoVeiculo tipo();

    public String descricao() {
        return tipo() + " " + codigo + " - " + modelo;
    }

    public void recarregar() {
        throw new RegraDeNegocioException("Este veículo não possui bateria.");
    }

}
