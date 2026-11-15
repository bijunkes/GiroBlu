package back;

public abstract class VeiculoEletrico extends Veiculo {

    public static final int CARGA_MINIMA = 20;

    private int bateria;

    protected VeiculoEletrico(String codigo, String modelo, Estacao estacaoInicial, int bateria) {
        super(codigo, modelo, estacaoInicial);
        if (bateria < 0 || bateria > 100) {
            estacaoInicial.retirar(this);
            throw new RegraDeNegocioException("A bateria deve estar entre 0 e 100.");
        }
        this.bateria = bateria;
    }

    public int getBateria() {
        return bateria;
    }

    @Override
    public void liberar() {
        if(getEstado() == EstadoVeiculo.DISPONIVEL && bateria < CARGA_MINIMA) {
            throw new RegraDeNegocioException("Bateria abaixo da carga mínima.");
        }
        super.liberar();
    }

    @Override
    public void devolver(Estacao estacao, int minutos) {
        super.devolver(estacao, minutos);
        bateria = Math.max(0, bateria - minutos * consumoPorMinuto());
    }

    @Override
    public void recarregar() {
        if(getEstado() == EstadoVeiculo.EM_USO) {
            throw new RegraDeNegocioException("Não é possível recarregar veículo em uso.");
        }
        bateria = 100;
    }

    @Override
    public String descricao() {
        return super.descricao() + " - bateria " + bateria + "%";
    }

    protected abstract int consumoPorMinuto();

}
