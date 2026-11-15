package back;

import java.math.BigDecimal;

public class Mensal implements Plano {

    private static final int MINUTOS_GRATIS = 15;

    @Override
    public BigDecimal calcularValor(Veiculo veiculo, int duracao) {
        int minutosCobrados = Math.max(0, duracao - MINUTOS_GRATIS);
        BigDecimal minutos = BigDecimal.valueOf(minutosCobrados);
        BigDecimal total = veiculo.valorPorMinuto().multiply(minutos);
        return total;
    }
}
