package back;

import java.math.BigDecimal;

public class PatineteEletrico extends VeiculoEletrico {

    private static final long serialVersionUID = 1L;

    public PatineteEletrico(String codigo, String modelo, Estacao estacaoInicial, int bateria) {
        super(codigo, modelo, estacaoInicial, bateria);
    }

    @Override
    protected int consumoPorMinuto() {
        return 2;
    }

    @Override
    public BigDecimal taxaDesbloqueio() {
        return new BigDecimal("3.00");
    }

    @Override
    public BigDecimal valorPorMinuto() {
        return new BigDecimal("0.40");
    }

    @Override
    public boolean concedeDescontoUniversitario() {
        return false;
    }

    @Override
    public TipoVeiculo tipo() {
        return TipoVeiculo.PATINETE_ELETRICO;
    }

}
