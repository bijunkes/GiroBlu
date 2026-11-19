package back;

import java.math.BigDecimal;

public class BicicletaEletrica extends VeiculoEletrico {

    private static final long serialVersionUID = 1L;

    public BicicletaEletrica(String codigo, String modelo, Estacao estacao, int bateria) {
        super(codigo, modelo, estacao, bateria);
    }

    @Override
    protected int consumoPorMinuto() {
        return 1;
    }

    @Override
    public BigDecimal taxaDesbloqueio() {
        return new BigDecimal("2.00");
    }

    @Override
    public BigDecimal valorPorMinuto() {
        return new BigDecimal("0.30");
    }

    @Override
    public TipoVeiculo tipo() {
        return TipoVeiculo.BICICLETA_ELETRICA;
    }

}
