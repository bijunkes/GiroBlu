package front;

import back.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.HierarchyEvent;
import javax.swing.table.DefaultTableCellRenderer;

public class PainelPesquisa extends PainelBase {

    private static final Color AZUL_CAIXA = new Color(166, 216, 228);
    private static final Color AZUL_LINHAS = new Color(179, 212, 247);
    private static final Color AZUL_SELECAO = new Color(120, 170, 230);
    private static final Color VERDE_AGUA = new Color(0, 168, 181);

    private enum Categoria {
        VEICULOS("Veículos"),
        ESTACOES("Estações"),
        USUARIOS("Usuários"),
        FATURAMENTO("Faturamento");

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

    public PainelPesquisa(Sistema sistema, JTextArea saida) {
        super(sistema, saida);
        setLayout(new BorderLayout());

        add(criarCaixaBusca(), BorderLayout.NORTH);
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
        caixa.setBackground(AZUL_CAIXA);
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
        botaoLimpar.setBackground(VERDE_AGUA);
        botaoLimpar.setForeground(Color.WHITE);
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
        tabela.setBackground(AZUL_LINHAS);
        tabela.setSelectionBackground(AZUL_SELECAO);
        tabela.setSelectionForeground(Color.BLACK);
        tabela.setFillsViewportHeight(true);
        tabela.setAutoCreateRowSorter(true);

        tabela.getTableHeader().setBackground(Color.WHITE);
        tabela.getTableHeader().setFont(tabela.getTableHeader().getFont().deriveFont(Font.BOLD, 14f));
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
            case FATURAMENTO -> sistema.pesquisarFaturamento(filtro);
        };
        modelo.setDataVector(resultado.linhas().toArray(new String[0][]), resultado.colunas());
        rotuloResumo.setText(resultado.resumo());
    }

}