public abstract class VeiculoEletrico extends Veiculo {

    private int bateria;

    public VeiculoEletrico(String codigo, String tipo, Estacao estacao, EstadoVeiculo estadoVeiculo) {
        super(codigo, tipo, estacao, estadoVeiculo);
    }
}
