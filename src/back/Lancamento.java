package back;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface Lancamento extends Serializable {
    LocalDateTime getDataHora();
    BigDecimal getValor();
    String getDescricao();
}