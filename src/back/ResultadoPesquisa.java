package back;

import java.util.List;

public record ResultadoPesquisa(String[] colunas, List<String[]> linhas, String resumo) {
}