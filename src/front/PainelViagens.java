package front;

import back.RegraDeNegocioException;
import back.Sistema;
import back.Viagem;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class PainelViagens extends PainelBase {

    private final JTextField campoEmailInicio = new JTextField(20);
    private final JTextField campoCodigoVeiculo = new JTextField(8);
    private final JTextField campoDataHoraInicio = new JTextField(LocalDateTime.now().format(FORMATO_DATA_HORA), 12);

    private final JTextField campoEmailFim = new JTextField(20);
    private final JTextField campoEstacaoDestino = new JTextField(20);
    private final JTextField campoDataHoraFim = new JTextField(LocalDateTime.now().format(FORMATO_DATA_HORA), 12);

    protected PainelViagens(Sistema sistema, JTextArea saida) {
        super(sistema, saida);
        setLayout(new GridLayout(0, 1));

        JPanel linhaInicio = new JPanel(new FlowLayout(FlowLayout.LEFT));
        linhaInicio.add(new JLabel("Iniciar - E-mail:"));
        linhaInicio.add(campoEmailInicio);
        linhaInicio.add(new JLabel("Código de veículo:"));
        linhaInicio.add(campoCodigoVeiculo);
        linhaInicio.add(new JLabel("Data e hora (dd/MM/yyyy HH:mm):"));
        linhaInicio.add(campoDataHoraInicio);
        add(linhaInicio);
        JButton botaoIniciarViagem = new JButton("Iniciar viagem");
        botaoIniciarViagem.addActionListener(e -> iniciarViagem());
        add(botaoIniciarViagem);

        JPanel linhaFim = new JPanel(new FlowLayout(FlowLayout.LEFT));
        linhaFim.add(new JLabel("Encerrar - E-mail:"));
        linhaFim.add(campoEmailFim);
        linhaFim.add(new JLabel("Estação de destino:"));
        linhaFim.add(campoEstacaoDestino);
        linhaFim.add(new JLabel("Data e hora (dd/MM/yyyy HH:mm):"));
        linhaFim.add(campoDataHoraFim);
        add(linhaFim);
        JButton botaoEncerrarViagem = new JButton("Encerrar viagem");
        botaoEncerrarViagem.addActionListener(e -> encerrarViagem());
        add(botaoEncerrarViagem);

    }

    public void iniciarViagem() {
        try {
            sistema.iniciarViagem(campoEmailInicio.getText(), campoCodigoVeiculo.getText(), lerDataHora(campoDataHoraInicio.getText()));
            salvar();
            mostrar("Viagem iniciada: Veículo " + campoCodigoVeiculo.getText() + " para " + campoEmailInicio.getText());
            campoEmailInicio.setText("");
            campoCodigoVeiculo.setText("");
        } catch (RegraDeNegocioException e) {
            mostrarErro(e);
        }
    }

    public void encerrarViagem() {
        try {
            Viagem viagem = sistema.encerrarViagem(campoEmailFim.getText(), campoEstacaoDestino.getText(), lerDataHora(campoDataHoraFim.getText()));
            salvar();
            mostrar("Duração (min): " + viagem.getDuracaoMinutos() + " | Valor (R$): " + String.format("%.2f", viagem.getValorPago()));
            campoEmailFim.setText("");
            campoEstacaoDestino.setText("");
        } catch (RegraDeNegocioException e) {
            mostrarErro(e);
        }
    }

}
