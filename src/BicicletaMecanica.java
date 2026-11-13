public class BicicletaMecanica extends Veiculo {
    public BicicletaMecanica(String codigo, String modelo, Estacao estacao) {
        super(codigo, modelo, estacao);
    }

    @Override
    public double taxaDesbloqueio() {
        return 1.0;
    }

    @Override
    public double valorPorMinuto() {
        return 0.15;
    }
}
