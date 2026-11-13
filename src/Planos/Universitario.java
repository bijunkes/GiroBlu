package Planos;

public class Universitario implements Plano {

    @Override
    public double calcularValor(
            double taxaDesbloqueio,
            double valorMinuto,
            int duracao,
            boolean patinete
    ) {
        if (!patinete) {
            return (taxaDesbloqueio + (valorMinuto * duracao)) * 0.50;
        }
        return taxaDesbloqueio + (valorMinuto * duracao);
    }
}
