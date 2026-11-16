package back;

import java.math.BigDecimal;

public class Universitario implements Plano {

    private static final long serialVersionUID = 1L;

    private static final BigDecimal FATOR_DESCONTO = BigDecimal.valueOf(0.5);

    @Override
    public BigDecimal calcularValor(Veiculo veiculo, int duracao) {
        BigDecimal minutos = BigDecimal.valueOf(duracao);
        BigDecimal custoMinutos = veiculo.valorPorMinuto().multiply(minutos);
        BigDecimal valorCheio = veiculo.taxaDesbloqueio().add(custoMinutos);

        if (veiculo.concedeDescontoUniversitario()) {
            return valorCheio.multiply(FATOR_DESCONTO);
        }
        return valorCheio;
    }
}
