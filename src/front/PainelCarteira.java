package front;

import back.RegraDeNegocioException;
import back.Sistema;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class PainelCarteira extends PainelBase {

    private final JTextField campoEmail = new JTextField(20);
    private final JTextField campoValor = new JTextField(8);
    private final JTextField campoDataHora = new JTextField(LocalDateTime.now().format(FORMATO_DATA_HORA), 12);

    public PainelCarteira(Sistema sistema, JTextArea saida) {
        super(sistema, saida);
        setLayout(new FlowLayout(FlowLayout.LEFT));

        add(new JLabel("E-mail do usuário:"));
        add(campoEmail);
        add(new JLabel("Valor (R$):"));
        add(campoValor);
        add(new JLabel("Data e hora (dd/MM/yyyy HH:mm):"));
        add(campoDataHora);
        JButton botaoRecarga = new JButton("Registrar recarga");
        botaoRecarga.addActionListener(e -> registrarRecarga());
        add(botaoRecarga);
    }

    private void registrarRecarga() {
        try {
            sistema.registrarRecarga(campoEmail.getText(), lerValor(campoValor.getText()), lerDataHora(campoDataHora.getText()));
            salvar();
            mostrar("Recarga registrada para " + campoEmail.getText());
            campoValor.setText("");
        } catch (RegraDeNegocioException e) {
            mostrarErro(e);
        }
    }

}
