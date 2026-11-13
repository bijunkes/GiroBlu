package Planos;

public interface Plano {

    double calcularValor(
            double taxaDesbloqueio,
            double valorMinuto,
            int duracao,
            boolean patinete
    );
}
