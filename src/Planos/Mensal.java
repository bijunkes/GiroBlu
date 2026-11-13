package Planos;

public class Mensal implements Plano {

    @Override
    public double calcularValor(
            double taxaDesbloqueio,
            double valorMinuto,
            int duracao,
            boolean patinete
    ) {
        return valorMinuto * Math.max(0, duracao - 15);
    }
}
