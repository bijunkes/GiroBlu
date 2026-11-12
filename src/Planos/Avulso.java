package Planos;

public class Avulso implements Plano {

    @Override
    public double calcularValor(
            double taxaDesbloqueio,
            double valorMinuto,
            int duracao,
            boolean patinete
    ) {
        return taxaDesbloqueio + (valorMinuto * duracao);
    }
}
