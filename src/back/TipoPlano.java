package back;

public enum TipoPlano {

    AVULSO("Avulso") {
        @Override
        public Plano criar() {
            return new Avulso();
        }
    },
    MENSAL("Mensal") {
        @Override
        public Plano criar() {
            return new Mensal();
        }
    },
    UNIVERSITARIO("Universitário") {
        @Override
        public Plano criar() {
            return new Universitario();
        }
    };

    private final String nome;

    TipoPlano(String nome) {
        this.nome = nome;
    }

    public abstract Plano criar();

    @Override
    public String toString() {
        return nome;
    }

}
