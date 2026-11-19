package front;

import back.RegraDeNegocioException;
import back.Sistema;

import javax.swing.*;
import java.awt.*;

public class PainelManutencao extends PainelBase {

    private final JTextField campoCodigo = new JTextField(10);

    public PainelManutencao(Sistema sistema, JTextArea saida) {
        super(sistema, saida);
        setLayout(new FlowLayout(FlowLayout.LEFT));

        add(new JLabel("Código do veículo:"));
        add(campoCodigo);

        JButton botaoEnviarManutencao = new JButton("Enviar para manutenção");
        botaoEnviarManutencao.addActionListener(e -> enviarParaManutencao());
        add(botaoEnviarManutencao);

        JButton botaoConcluirManutencao = new JButton("Concluir manutenção");
        botaoConcluirManutencao.addActionListener(e -> concluirManutencao());
        add(botaoConcluirManutencao);

        JButton botaoRecarregarBateria = new JButton("Recarregar bateria");
        botaoRecarregarBateria.addActionListener(e -> recarregarBateria());
        add(botaoRecarregarBateria);
    }

    private void enviarParaManutencao() {
        try {
            sistema.enviarParaManutencao(campoCodigo.getText());
            salvar();
            mostrar("Veículo " + campoCodigo.getText() + " enviado para manuteção.");
        } catch (RegraDeNegocioException e) {
            mostrarErro(e);
        }
    }

    private void concluirManutencao() {
        try {
            sistema.concluirManutencao(campoCodigo.getText());
            salvar();
            mostrar("Manutenção do veículo " + campoCodigo.getText() + " concluída.");
        } catch (RegraDeNegocioException e) {
            mostrarErro(e);
        }
    }

    private void recarregarBateria() {
        try {
            sistema.recarregarBateria(campoCodigo.getText());
            salvar();
            mostrar("Bateria do veículo " + campoCodigo.getText() + " carregada em 100%.");
        } catch (RegraDeNegocioException e) {
            mostrarErro(e);
        }
    }

}
