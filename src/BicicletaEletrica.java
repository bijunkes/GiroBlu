public class BicicletaEletrica extends VeiculoEletrico {

    public BicicletaEletrica(String codigo, String modelo, Estacao estacao) {
        super(codigo, modelo, estacao);
    }

    @Override
    protected int consumoPorMinuto() {
        return 1;
    }

    @Override
    public double taxaDesbloqueio() {
        return 2.0;
    }

    @Override
    public double valorPorMinuto() {
        return 0.3;
    }
}
