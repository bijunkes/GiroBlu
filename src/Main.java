import back.Persistencia;
import back.Sistema;
import front.TelaPrincipal;

import javax.swing.*;

public class Main {

    public static final String CODIGO_HOMOLOGACAO = "GB-POO-26-0B7";

    public static void main(String[] args) {
        Sistema sistema = Persistencia.carregar();
        SwingUtilities.invokeLater(() -> new TelaPrincipal(sistema).setVisible(true));
    }

}
