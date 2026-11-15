package front;

import back.Persistencia;
import back.RegraDeNegocioException;
import back.Sistema;

import javax.swing.*;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class PainelBase extends JPanel {

    protected static final DateTimeFormatter FORMATO_DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    protected final Sistema sistema;
    private final JTextArea saida;

    protected PainelBase(Sistema sistema, JTextArea saida) {
        this.sistema = sistema;
        this.saida = saida;
    }

    protected void mostrar(String texto) {
        saida.setText(texto);
    }

    protected void mostrarErro(RegraDeNegocioException e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
    }

    protected void salvar() {
        Persistencia.salvar(sistema);
    }

    protected LocalDateTime lerDataHora(String texto) {
        try {
            return LocalDateTime.parse(texto.trim(), FORMATO_DATA_HORA);
        } catch (DateTimeException e) {
            throw new RegraDeNegocioException("Data e hora inválidas. Use o formato dd/MM/yyyy HH:mm.");
        }
    }

    protected int lerInteiro(String texto) {
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            throw new RegraDeNegocioException("Informe um número inteiro válido.");
        }
    }

    protected static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    protected LocalDate lerData(String texto) {
        try {
            return LocalDate.parse(texto.trim(), FORMATO_DATA);
        } catch (DateTimeException e) {
            throw new RegraDeNegocioException("Data inválida. Use o formato dd/MM/yyyy.");
        }
    }

    protected BigDecimal lerValor(String texto) {
        try {
            return new BigDecimal(texto.trim().replace(",", "."));
        } catch (NumberFormatException e) {
            throw new RegraDeNegocioException("Informe um valor válido, por exemplo 50,00.");
        }
    }

}
