public abstract class Veiculo {

    private String codigo;
    private String tipo;
    private Estacao estacao;
    private EstadoVeiculo estadoVeiculo;

    public Veiculo(String codigo, String tipo, Estacao estacao, EstadoVeiculo estadoVeiculo) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.estacao = estacao;
        this.estadoVeiculo = EstadoVeiculo.DISPONIVEL;
    }

    public abstract double getTaxaDesbloqueio();
    public abstract double getValorPorMinuto();

    public double calcularTarifa(int minutos) {
        return getTaxaDesbloqueio() + getValorPorMinuto() * minutos;
    }

    public void liberar() {
        if (estadoVeiculo != EstadoVeiculo.DISPONIVEL) {
            throw new IllegalStateException("Somente veículos disponíveis podem ser liberados.");
        }
        estadoVeiculo = EstadoVeiculo.EM_USO;
    }

    public void enviarParaManutencao() {
        if (estadoVeiculo == EstadoVeiculo.EM_USO) {
            throw new IllegalStateException("Veículos em uso não podem ir para manutenção.");
        }
        estadoVeiculo = EstadoVeiculo.EM_MANUTENCAO;
    }

    public void concluirManutencao() {
        if (estadoVeiculo != EstadoVeiculo.EM_MANUTENCAO) {
            throw new IllegalStateException("Veíuculo não está em manutenção");
        }
        estadoVeiculo = EstadoVeiculo.DISPONIVEL;
    }

}
