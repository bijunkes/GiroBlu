public abstract class Veiculo {

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

    public abstract double taxaDesbloqueio();
    public abstract double valorPorMinuto();

    public double calcularTarifa(int minutos) {
        return taxaDesbloqueio() + valorPorMinuto() * minutos;
    }

    public void liberar() {
        if (estado != EstadoVeiculo.DISPONIVEL) {
            throw new IllegalStateException("Somente veículos disponíveis podem ser liberados.");
        }
        estacao.retirar(this);
        estacao = null;
        estado = EstadoVeiculo.EM_USO;
    }

    public double devolver(Estacao estacao, int minutos) {
        if (estado != EstadoVeiculo.EM_USO) {
            throw new IllegalStateException("Veículo não está em uso");
        }
        if (minutos < 0) {
            throw new IllegalArgumentException("Minutos não podem ser negativos");
        }
        estacao.receber(this);
        this.estacao = estacao;
        estado = EstadoVeiculo.DISPONIVEL;
        return calcularTarifa(minutos);
    }

    public void enviarParaManutencao() {
        if (estado == EstadoVeiculo.EM_USO) {
            throw new IllegalStateException("Veículos em uso não podem ir para manutenção.");
        }
        estado = EstadoVeiculo.EM_MANUTENCAO;
    }

    public void concluirManutencao() {
        if (estado != EstadoVeiculo.EM_MANUTENCAO) {
            throw new IllegalStateException("Veíuculo não está em manutenção");
        }
        estado = EstadoVeiculo.DISPONIVEL;
    }

    public EstadoVeiculo getEstado() { return estado; }

}
