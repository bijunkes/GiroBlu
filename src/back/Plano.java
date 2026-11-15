package back;

import java.io.Serializable;
import java.math.BigDecimal;

public interface Plano extends Serializable {

    BigDecimal calcularValor(
            Veiculo veiculo,
            int duracao
    );
}
