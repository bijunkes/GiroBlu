package front;

import back.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.HierarchyEvent;
import javax.swing.table.DefaultTableCellRenderer;

public class PainelConsultas extends PainelBase {

    private final JTextField campoEstacao = new JTextField(14);
    private final JTextField campoEmailExtrato = new JTextField(20);
    private final JTextField campoDataInicial = new JTextField(10);
    private final JTextField campoDataFinal = new JTextField(10);

    private enum Categoria {
        VEICULOS("Veículos"),
        ESTACOES("Estações"),
        USUARIOS("Usuários");

        private final String rotulo;

        Categoria(String rotulo) {
            this.rotulo = rotulo;
        }
    }

    private final JTextField campoBusca = new JTextField(20);
    private final DefaultTableModel modelo = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int linha, int coluna) {
            return false;
        }
    };
    private final JLabel rotuloResumo = new JLabel(" ");
    private Categoria categoriaAtual = Categoria.VEICULOS;

    public PainelConsultas(Sistema sistema, JTextArea saida) {
        super(sistema, saida);
        setLayout(new BorderLayout());

        JPanel topo = new JPanel(new BorderLayout());
        topo.add(criarLinhasConsultas(), BorderLayout.NORTH);
        topo.add(criarCaixaBusca(), BorderLayout.CENTER);
        add(topo, BorderLayout.NORTH);

        add(new JScrollPane(criarTabela()), BorderLayout.CENTER);

        rotuloResumo.setFont(rotuloResumo.getFont().deriveFont(Font.BOLD, 13f));
        rotuloResumo.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        add(rotuloResumo, BorderLayout.SOUTH);

        campoBusca.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                atualizar();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                atualizar();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                atualizar();
            }
        });

        addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0 && isShowing()) {
                atualizar();
            }
        });

        atualizar();
    }

    private JPanel criarLinhasConsultas() {
        JPanel linhas = new JPanel(new GridLayout(0, 1));

        JPanel linhaDisponiveis = new JPanel(new FlowLayout(FlowLayout.LEFT));
        linhaDisponiveis.add(new JLabel("Veículos disponíveis - Estação:"));
        linhaDisponiveis.add(campoEstacao);
        JButton botaoConsultarDisponiveis = new JButton("Consultar");
        botaoConsultarDisponiveis.addActionListener(e -> consultarDisponiveis());
        linhaDisponiveis.add(botaoConsultarDisponiveis);
        linhas.add(linhaDisponiveis);

        JPanel linhaExtrato = new JPanel(new FlowLayout(FlowLayout.LEFT));
        linhaExtrato.add(new JLabel("Extrato - E-mail:"));
        linhaExtrato.add(campoEmailExtrato);
        JButton botaoEmitirExtrato = new JButton("Emitir extrato");
        botaoEmitirExtrato.addActionListener(e -> emitirExtrato());
        linhaExtrato.add(botaoEmitirExtrato);
        linhas.add(linhaExtrato);

        JPanel linhaFaturamento = new JPanel(new FlowLayout(FlowLayout.LEFT));
        linhaFaturamento.add(new JLabel("Faturamento de: "));
        linhaFaturamento.add(campoDataInicial);
        linhaFaturamento.add(new JLabel("até:"));
        linhaFaturamento.add(campoDataFinal);
        JButton botaoConsultarFaturamento = new JButton("Consultar");
        botaoConsultarFaturamento.addActionListener(e -> consultarFaturamento());
        linhaFaturamento.add(botaoConsultarFaturamento);
        linhas.add(linhaFaturamento);

        return linhas;
    }

    private void consultarDisponiveis() {
        try {
            mostrar(sistema.consultarDisponiveis(campoEstacao.getText()));
        } catch (RegraDeNegocioException e) {
            mostrarErro(e);
        }
    }

    private void emitirExtrato() {
        try {
            mostrar(sistema.emitirExtrato(campoEmailExtrato.getText()));
        } catch (RegraDeNegocioException e) {
            mostrarErro(e);
        }
    }

    private void consultarFaturamento() {
        try {
            mostrar(sistema.consultarFaturamento(lerData(campoDataInicial.getText()), lerData(campoDataFinal.getText())));
        } catch (RegraDeNegocioException e) {
            mostrarErro(e);
        }
    }

    private JPanel criarCaixaBusca() {
        JPanel caixa = new JPanel(new GridLayout(0, 1, 0, 6)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
            }
        };
        caixa.setOpaque(false);
        caixa.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));

        JPanel linhaCategorias = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
        linhaCategorias.setOpaque(false);
        ButtonGroup grupo = new ButtonGroup();
        for (Categoria categoria : Categoria.values()) {
            JToggleButton botao = new JToggleButton(categoria.rotulo, categoria == categoriaAtual);
            botao.addActionListener(e -> {
                categoriaAtual = categoria;
                atualizar();
            });
            grupo.add(botao);
            linhaCategorias.add(botao);
        }
        caixa.add(linhaCategorias);

        JPanel linhaBusca = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
        linhaBusca.setOpaque(false);
        linhaBusca.add(new JLabel("Buscar:"));
        linhaBusca.add(campoBusca);
        JButton botaoLimpar = new JButton("Limpar");
        botaoLimpar.setFocusPainted(false);
        botaoLimpar.addActionListener(e -> campoBusca.setText(""));
        linhaBusca.add(botaoLimpar);
        caixa.add(linhaBusca);

        JPanel centro = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        centro.add(caixa);
        return centro;
    }

    private JTable criarTabela() {
        JTable tabela = new JTable(modelo);
        tabela.setRowHeight(36);
        tabela.setShowGrid(false);
        tabela.setIntercellSpacing(new Dimension(0, 0));
        tabela.setSelectionForeground(Color.BLACK);
        tabela.setFillsViewportHeight(true);
        tabela.setAutoCreateRowSorter(true);

        tabela.getTableHeader().setFont(tabela.getTableHeader().getFont().deriveFont(Font.BOLD));
        tabela.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
        direita.setHorizontalAlignment(SwingConstants.RIGHT);
        tabela.setDefaultRenderer(Object.class, direita);

        if (tabela.getTableHeader().getDefaultRenderer() instanceof DefaultTableCellRenderer cabecalho) {
            cabecalho.setHorizontalAlignment(SwingConstants.RIGHT);
        }

        return tabela;
    }

    private void atualizar() {
        String filtro = campoBusca.getText();
        ResultadoPesquisa resultado = switch (categoriaAtual) {
            case VEICULOS -> sistema.pesquisarVeiculos(filtro);
            case ESTACOES -> sistema.pesquisarEstacoes(filtro);
            case USUARIOS -> sistema.pesquisarUsuarios(filtro);
        };
        modelo.setDataVector(resultado.linhas().toArray(new String[0][]), resultado.colunas());
        rotuloResumo.setText(resultado.resumo());
    }

}