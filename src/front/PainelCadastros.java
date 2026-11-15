package front;

import back.*;

import javax.swing.*;
import java.awt.*;

public class PainelCadastros extends PainelBase {

    private final JTextField campoNomeEstacao = new JTextField(15);
    private final JTextField campoVagas = new JTextField(4);

    private final JComboBox<TipoVeiculo> comboTipoVeiculo = new JComboBox<>(TipoVeiculo.values());
    private final JTextField campoCodigo = new JTextField(8);
    private final JTextField campoModelo = new JTextField(12);
    private final JTextField campoEstacaoVeiculo = new JTextField(12);
    private final JTextField campoBateria = new JTextField("100", 4);

    private final JTextField campoNomeUsuario = new JTextField(20);
    private  final JTextField campoEmail = new JTextField(20);
    private final JComboBox<TipoPlano> comboTipoPlano = new JComboBox<>(TipoPlano.values());

    public PainelCadastros(Sistema sistema, JTextArea saida) {
        super(sistema, saida);
        setLayout(new GridLayout(0, 1));

        JPanel linhaEstacao = new JPanel(new FlowLayout(FlowLayout.LEFT));
        linhaEstacao.add(new JLabel("Estação - Nome:"));
        linhaEstacao.add(campoNomeEstacao);
        linhaEstacao.add(new JLabel("Vagas:"));
        linhaEstacao.add(campoVagas);
        JButton botaoEstacao = new JButton("Cadastrar estação");
        botaoEstacao.addActionListener(e -> cadastrarEstacao());
        linhaEstacao.add(botaoEstacao);
        add(linhaEstacao);

        JPanel linhaVeiculo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        linhaVeiculo.add(new JLabel("Tipo:"));
        linhaVeiculo.add(comboTipoVeiculo);
        linhaVeiculo.add(new JLabel("Código:"));
        linhaVeiculo.add(campoCodigo);
        linhaVeiculo.add(new JLabel("Modelo:"));
        linhaVeiculo.add(campoModelo);
        linhaVeiculo.add(new JLabel("Nome da estação:"));
        linhaVeiculo.add(campoEstacaoVeiculo);
        linhaVeiculo.add(new JLabel("Bateria: "));
        linhaVeiculo.add(campoBateria);
        JButton botaoVeiculo = new JButton("Cadastrar veículo");
        botaoVeiculo.addActionListener(e -> cadastrarVeiculo());
        linhaVeiculo.add(botaoVeiculo);
        add(linhaVeiculo);

        JPanel linhaUsuario = new JPanel(new FlowLayout(FlowLayout.LEFT));
        linhaUsuario.add(new JLabel("Usuário - Nome:"));
        linhaUsuario.add(campoNomeUsuario);
        linhaUsuario.add(new JLabel("E-mail:"));
        linhaUsuario.add(campoEmail);
        linhaUsuario.add(new JLabel("Plano:"));
        linhaUsuario.add(comboTipoPlano);
        JButton botaoUsuario = new JButton("Cadastrar usuário");
        botaoUsuario.addActionListener(e -> cadastrarUsuario());
        linhaUsuario.add(botaoUsuario);
        add(linhaUsuario);

    }

    private void cadastrarEstacao() {
        try {
            sistema.cadastrarEstacao(campoNomeEstacao.getText(), lerInteiro(campoVagas.getText()));
            salvar();
            mostrar("Estação cadastrada: " + campoNomeEstacao.getText());
            campoNomeEstacao.setText("");
            campoVagas.setText("");
        } catch (RegraDeNegocioException e) {
            mostrarErro(e);
        }
    }

    private void cadastrarVeiculo() {
        try {
            TipoVeiculo tipo = (TipoVeiculo) comboTipoVeiculo.getSelectedItem();
            sistema.cadastrarVeiculo(tipo, campoCodigo.getText(), campoModelo.getText(), campoEstacaoVeiculo.getText(), lerInteiro(campoBateria.getText()));
            salvar();
            mostrar("Veículo cadastrado: " + campoCodigo.getText());
            campoCodigo.setText("");
            campoModelo.setText("");
        } catch (RegraDeNegocioException e) {
            mostrarErro(e);
        }
    }

    private void cadastrarUsuario() {
        try {
            TipoPlano tipoPlano = (TipoPlano) comboTipoPlano.getSelectedItem();
            sistema.cadastrarUsuario(campoNomeUsuario.getText(), campoEmail.getText(), tipoPlano.criar());
            salvar();
            mostrar("Usuário cadastrado: " + campoNomeUsuario.getText());
            campoNomeUsuario.setText("");
            campoEmail.setText("");
        } catch (RegraDeNegocioException e) {
            mostrarErro(e);
        }
    }

}
