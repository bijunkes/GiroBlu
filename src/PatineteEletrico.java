public class PatineteEletrico extends VeiculoEletrico {

    protected PatineteEletrico(String codigo, String modelo, Estacao estacaoInicial) {
        super(codigo, modelo, estacaoInicial);
    }

    @Override
    protected int consumoPorMinuto() {
        return 2;
    }

    @Override
    public double taxaDesbloqueio() {
        return 3.0;
    }

    @Override
    public double valorPorMinuto() {
        return 0.4;
    }
}
