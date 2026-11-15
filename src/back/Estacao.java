package back;

import java.io.Serializable;
import java.util.ArrayList;

public class Estacao implements Serializable {

    private final String nome;
    private final int vagas;
    private final ArrayList<Veiculo> veiculos = new ArrayList<>();

    public Estacao(String nome, int vagas) {
        if (nome == null || nome.isBlank()) {
            throw new RegraDeNegocioException("Nome obrigatório.");
        }
        if (vagas <= 0) {
            throw new RegraDeNegocioException("Vagas devem ser positivas.");
        }
        this.nome = nome;
        this.vagas = vagas;
    }

    public String getNome() {
        return nome;
    }

    public ArrayList<Veiculo> getDisponiveis() {
        ArrayList<Veiculo> disponiveis = new ArrayList<>();
        for (Veiculo v : veiculos) {
            if (v.getEstado() == EstadoVeiculo.DISPONIVEL) {
                disponiveis.add(v);
            }
        }
        return disponiveis;
    }

    public boolean temVaga() {
        return veiculos.size() < vagas;
    }

    public void receber(Veiculo v) {
        if (!temVaga()) {
            throw new RegraDeNegocioException("Estação sem vagas.");
        }
        veiculos.add(v);
    }

    public void retirar(Veiculo v) {
        if (!veiculos.remove(v)) {
            throw new RegraDeNegocioException("Veículo não está na estação.");
        }
    }

}
