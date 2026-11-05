import java.util.ArrayList;

public class Estacao {
    private final String nome;
    private final int vagas;
    private final ArrayList<Veiculo> veiculos = new ArrayList<>();

    public Estacao(String nome, int vagas) {
        if (vagas <= 0) throw new IllegalArgumentException("Vagas devem ser positivas.");
        this.nome = nome;
        this.vagas = vagas;
    }

    public boolean temVagaLivre() {
        return veiculos.size() < vagas;
    }

    public void receber(Veiculo v) {
        if (!temVagaLivre()) throw new IllegalArgumentException("Estacao sem vaga.");
        veiculos.add(v);
    }

    public void liberar(Veiculo v) {
        veiculos.remove(v);
    }

}
