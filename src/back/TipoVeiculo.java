package back;

public enum TipoVeiculo {

    BICICLETA_MECANICA("Bicicleta mecânica") {
        @Override
        public Veiculo criar(String codigo, String modelo, Estacao estacao, int bateria) {
            return new BicicletaMecanica(codigo, modelo, estacao);
        }
    },
    BICICLETA_ELETRICA("Bicicleta elétrica") {
        @Override
        public Veiculo criar(String codigo, String modelo, Estacao estacao, int bateria) {
            return new BicicletaEletrica(codigo, modelo, estacao, bateria);
        }
    },
    PATINETE_ELETRICO("Patinete elétrico") {
        @Override
        public Veiculo criar(String codigo, String modelo, Estacao estacao, int bateria) {
            return new PatineteEletrico(codigo, modelo, estacao, bateria);
        }
    };

    private final String nome;

    TipoVeiculo(String nome) {
        this.nome = nome;
    }

    public abstract Veiculo criar(String codigo, String modelo, Estacao estacao, int bateria);

    @Override
    public String toString() {
        return nome;
    }
}