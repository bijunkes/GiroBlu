package back;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Recarga implements Lancamento {

    private static final long serialVersionUID = 1L;
    private final LocalDateTime dataHora;
    private final BigDecimal valor;

    public Recarga(BigDecimal valor, LocalDateTime dataHora) {
        if (valor == null || dataHora == null) {
            throw new RegraDeNegocioException("Valor, data e horário não podem ser nulos.");
        }
        if (valor.signum() <= 0) {
            throw new RegraDeNegocioException("O valor da recarga deve ser positivo.");
        }
        this.dataHora = dataHora;
        this.valor = valor;
    }

    @Override
    public LocalDateTime getDataHora() {

        return dataHora;
    }

    @Override
    public BigDecimal getValor() {
        return valor;
    }

    @Override
    public String getDescricao() {
        return "Recarga";
    }

}