public abstract class VeiculoEletrico extends Veiculo {

    public static final int CARGA_MINIMA = 20;

    private int bateria = 100;

    protected VeiculoEletrico(String codigo, String modelo, Estacao estacaoInicial) {
        super(codigo, modelo, estacaoInicial);
    }

    @Override
    public void liberar() {
        if(getEstado() == EstadoVeiculo.DISPONIVEL && bateria < CARGA_MINIMA) {
            throw new IllegalStateException("Bateria abaixo da carga mínima.");
        }
        super.liberar();
    }

    @Override
    public double devolver(Estacao estacao, int minutos) {
        double tarifa = super.devolver(estacao, minutos);
        bateria = Math.max(0, bateria - minutos * consumoPorMinuto());
        return tarifa;
    }

    public void recarregar() {
        if(getEstado() == EstadoVeiculo.EM_USO) {
            throw new IllegalStateException("Não é possível recarregar veículo em uso.");
        }
        bateria = 100;
    }

    protected abstract int consumoPorMinuto();

    public int getBateria() { return bateria; }
}
