package back;

import java.math.BigDecimal;

public class BicicletaMecanica extends Veiculo {

    private static final long serialVersionUID = 1L;

    public BicicletaMecanica(String codigo, String modelo, Estacao estacao) {
        super(codigo, modelo, estacao);
    }

    @Override
    public BigDecimal taxaDesbloqueio() {
        return new BigDecimal("1.00");
    }

    @Override
    public BigDecimal valorPorMinuto() {
        return new BigDecimal("0.15");
    }

    @Override
    public TipoVeiculo tipo() {
        return TipoVeiculo.BICICLETA_MECANICA;
    }

}
