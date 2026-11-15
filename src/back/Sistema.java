package back;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Sistema implements Serializable {

    private final ArrayList<Estacao> estacoes = new ArrayList<>();
    private final ArrayList<Veiculo> veiculos = new ArrayList<>();
    private final ArrayList<Usuario> usuarios = new ArrayList<>();
    private final ArrayList<Viagem> viagens = new ArrayList<>();

    public Estacao buscarEstacao(String nome) {
        for (Estacao e : estacoes) {
            if (e.getNome().equalsIgnoreCase(nome)) {
                return e;
            }
        }
        throw new RegraDeNegocioException("Estação não encontrada.");
    }

    public Usuario buscarUsuario(String email) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return u;
            }
        }
        throw new RegraDeNegocioException("Usuário não encontrado.");
    }

    public Veiculo buscarVeiculo(String codigo) {
        for (Veiculo v : veiculos) {
            if (v.getCodigo().equalsIgnoreCase(codigo)) {
                return v;
            }
        }
        throw new RegraDeNegocioException("Veículo não encontrado.");
    }

    public void cadastrarEstacao(String nome, int vagas) {
        for (Estacao e : estacoes) {
            if (e.getNome().equalsIgnoreCase(nome)) {
                throw new RegraDeNegocioException("Já existe uma estação com esse nome.");
            }
        }

        Estacao novaEstacao = new Estacao(nome, vagas);
        estacoes.add(novaEstacao);
    }

    public void cadastrarUsuario(String nome, String email, Plano plano) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                throw new RegraDeNegocioException("Já existe um usuário com esse e-mail.");
            }
        }

        Usuario novoUsuario = new Usuario(nome, email, plano);
        usuarios.add(novoUsuario);
    }

    public void cadastrarVeiculo(TipoVeiculo tipo, String codigo, String modelo, String nomeEstacao, int bateria) {
        if (tipo == null) {
            throw new RegraDeNegocioException("Tipo obrigatório.");
        }
        if (codigo == null || codigo.isBlank()) {
            throw new RegraDeNegocioException("Código obrigatório.");
        }
        if (modelo == null || modelo.isBlank()) {
            throw new RegraDeNegocioException("Modelo obrigatório.");
        }

        for (Veiculo v : veiculos) {
            if (v.getCodigo().equalsIgnoreCase(codigo)) {
                throw new RegraDeNegocioException("Já existe um veículo com esse código.");
            }
        }

        Estacao estacao = buscarEstacao(nomeEstacao);
        Veiculo novoVeiculo = tipo.criar(codigo, modelo, estacao, bateria);
        veiculos.add(novoVeiculo);
    }

    public void registrarRecarga(String email, BigDecimal valor, LocalDateTime dataHora) {
        Usuario usuario = buscarUsuario(email);
        usuario.recarregar(valor, dataHora);
    }

    public Viagem iniciarViagem(String email, String codigoVeiculo, LocalDateTime inicio) {
        Usuario usuario = buscarUsuario(email);
        Veiculo veiculo = buscarVeiculo(codigoVeiculo);

        Viagem viagem = new Viagem(usuario, veiculo, inicio);
        viagens.add(viagem);
        return viagem;
    }

    public Viagem encerrarViagem(String email, String nomeEstacaoDestino, LocalDateTime termino) {
        Usuario usuario = buscarUsuario(email);

        Viagem viagem = usuario.getViagemAtual();
        if (viagem == null) {
            throw new RegraDeNegocioException("O usuário não possui viagem em andamento.");
        }

        Estacao destino = buscarEstacao(nomeEstacaoDestino);
        viagem.encerrar(destino, termino);
        return viagem;
    }

    public void enviarParaManutencao(String codigo) {
        buscarVeiculo(codigo).enviarParaManutencao();
    }

    public void concluirManutencao(String codigo) {
        buscarVeiculo(codigo).concluirManutencao();
    }

    public void recarregarBateria(String codigo) {
        buscarVeiculo(codigo).recarregar();
    }

    public String consultarDisponiveis(String nomeEstacao) {
        Estacao estacao = buscarEstacao(nomeEstacao);
        ArrayList<Veiculo> disponiveis = estacao.getDisponiveis();

        if (disponiveis.isEmpty()) {
            return "Nenhum veículo disponível nesta estação";
        }

        StringBuilder texto = new StringBuilder();
        for (Veiculo v : disponiveis) {
            texto.append(v.descricao());
            texto.append("\n");
        }

        return texto.toString();
    }

    public String emitirExtrato(String email) {
        Usuario usuario = buscarUsuario(email);
        return usuario.emitirExtrato();
    }

    public String consultarFaturamento(LocalDate inicial, LocalDate fim) {
        if (inicial == null || fim == null) {
            throw new RegraDeNegocioException("Informe a data inicial e a data fim.");
        }
        if (fim.isBefore(inicial)) {
            throw new RegraDeNegocioException("A data final não pode ser anterior à inicial.");
        }

        StringBuilder texto = new StringBuilder();
        int quantidadeGeral = 0;
        BigDecimal totalGeral = BigDecimal.ZERO;

        for (TipoVeiculo tipo : TipoVeiculo.values()) {
            int quantidade = 0;
            BigDecimal total = BigDecimal.ZERO;

            for (Viagem v : viagens) {
                if (!v.isEncerrada()) {
                    continue;
                }
                if (v.getVeiculo().tipo() != tipo) {
                    continue;
                }
                LocalDate dia = v.getTermino().toLocalDate();
                if (dia.isBefore(inicial) || dia.isAfter(fim)) {
                    continue;
                }

                quantidade++;
                total = total.add(v.getValorPago());
            }

            texto.append(tipo);
            texto.append(": ");
            texto.append(quantidade);
            texto.append(" viagem(ns) - R$ ");
            texto.append(String.format("%.2f", total));
            texto.append("\n");

            quantidadeGeral += quantidade;
            totalGeral = totalGeral.add(total);
        }

        texto.append("TOTAL GERAL: ");
        texto.append(quantidadeGeral);
        texto.append(" viagem(ns) - R$ ");
        texto.append(String.format("%.2f", totalGeral));
        texto.append("\n");

        return texto.toString();
    }

}
