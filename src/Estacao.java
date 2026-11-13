import java.util.ArrayList;

public class Estacao {
    private final String nome;
    private final int vagas;
    private final ArrayList<Veiculo> veiculos = new ArrayList<>();

    public Estacao(String nome, int vagas) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome obrigatório.");
        if (vagas <= 0) throw new IllegalArgumentException("Vagas devem ser positivas.");
        this.nome = nome;
        this.vagas = vagas;
    }

    public boolean temVaga() {
        return veiculos.size() < vagas;
    }

    public void receber(Veiculo v) {
        if (!temVaga()) throw new IllegalStateException("Estação sem vagas.");
        veiculos.add(v);
    }

    public void retirar(Veiculo v) {
        if (!veiculos.remove(v)) throw new IllegalStateException("Veículo não está na estação.");
        veiculos.remove(v);
    }

}
