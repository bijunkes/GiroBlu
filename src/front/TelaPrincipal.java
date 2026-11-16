package front;

import back.Sistema;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal(Sistema sistema) {
        super("GiroBlu | Painel de Operação");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);

        JTextArea saida = new JTextArea();
        saida.setEditable(false);
        saida.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane rolagem = new JScrollPane(saida);
        rolagem.setPreferredSize(new Dimension(0, 220));

        JTabbedPane abas = new JTabbedPane();

        setLayout(new BorderLayout());
        add(abas, BorderLayout.CENTER);
        add(rolagem, BorderLayout.SOUTH);

        abas.addTab("Cadastros", new PainelCadastros(sistema, saida));
        abas.addTab("Carteira", new PainelCarteira(sistema, saida));
        abas.addTab("Pesquisa", new PainelPesquisa(sistema, saida));
    }

}
