package back;

import java.math.BigDecimal;

public class Avulso implements Plano {

    private static final long serialVersionUID = 1L;

    @Override
    public BigDecimal calcularValor(Veiculo veiculo, int duracao) {
        BigDecimal minutos = BigDecimal.valueOf(duracao);
        BigDecimal custoMinutos = veiculo.valorPorMinuto().multiply(minutos);
        BigDecimal total = veiculo.taxaDesbloqueio().add(custoMinutos);
        return total;
    }

}
