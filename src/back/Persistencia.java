package back;

import java.io.*;

public class Persistencia {

    private static final File PASTA = new File("dados-gb26");
    private static final File ARQUIVO = new File(PASTA, "sistema.bin");

    public static void salvar(Sistema sistema) {
        PASTA.mkdirs();
        try (FileOutputStream fos = new FileOutputStream(ARQUIVO); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(sistema);
        } catch (IOException e) {
            throw new RegraDeNegocioException("Não foi possível salvar os dados: " + e.getMessage());
        }
    }

    public static Sistema carregar() {
        if (!ARQUIVO.exists()) {
            return new Sistema();
        }
        try (FileInputStream fis = new FileInputStream(ARQUIVO); ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (Sistema) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RegraDeNegocioException("Não foi possível carregar os dados: " + e.getMessage());
        }
    }

}
