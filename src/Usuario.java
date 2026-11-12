public class Usuario {
    private String nome;
    private String email;
    private double saldo;

    public Usuario(String nome, String email, Plano plano) {
        this.nome = nome;
        this.email = email;
        this.saldo = 0;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public double getSaldo() {
        return saldo;
    }

    public void recarregar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor inválido.");
        }
        saldo += valor;
    }
}
