package KNN;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import Comun.Amostra;
import Comun.ReaderWriter;

public class Main {

    static double proporcaoTreino = 0.7;
    static int repeticoes = 6;
    static int kMin = 2;
    static int kMax = 20;

    private static Amostra[] base; // Amostras carregadas do arquivo

    private static List<Amostra> treino = new ArrayList<>(); // Amostras de treino
    private static List<Amostra> teste = new ArrayList<>(); // Amostras de teste

    public static void main(String[] args) {
        // Lê o arquivo de dados
        base = ReaderWriter.aux.readWindow();

        String[] metricas = { "manhattan", "euclidiana", "minkowski3", "minkowski4" };

        /*
         * =============================================================================
         * RESULTADOS ARMAZENADOS
         * =============================================================================
         */
        HashMap<String, HashMap<String, List<Double>>> resultados = new HashMap<>();

        for (String metrica : metricas) {
            resultados.put(metrica, new HashMap<>());
            for (int e = 1; e <= repeticoes; e++) { //
                // divisao de treino e teste
                HashMap<Integer, List<Amostra>> basePorClasse = separarPorClasse(base);
                embaralharBases(basePorClasse);
                treino.clear();
                teste.clear();
                dividirTreinoTeste(basePorClasse, proporcaoTreino);

                KNN knn;
                List<Double> aux = new ArrayList<>();

                // FAZ K DO 2 AOU 20
                for (int k = kMin; k <= kMax; k++) {
                    knn = new KNN(treino, k, metrica);
                    double aux2 = knn.avaliar(teste);
                    aux.add(aux2);
                }
                resultados.get(metrica).put("E" + e, aux);
            }

        }
        //Imprime a tabela dos resultados E1 - E6
        imprimirTabela(resultados, metricas);
        //Imprime a tabela de media
        imprimirTabelaMedia(resultados, metricas);

    }

    /*
     * =============================================================================
     * FUNÇÕES AUXILIARES
     * =============================================================================
     */

    private static HashMap<Integer, List<Amostra>> separarPorClasse(Amostra[] base) {
        HashMap<Integer, List<Amostra>> map = new HashMap<>();
        for (Amostra a : base) {
            int classe = (int) a.Y[0];
            map.putIfAbsent(classe, new ArrayList<>());
            map.get(classe).add(a);
        }
        return map;
    }

    private static void embaralharBases(HashMap<Integer, List<Amostra>> basePorClasse) {
        for (List<Amostra> lista : basePorClasse.values()) {
            Collections.shuffle(lista);
        }
    }

    private static void dividirTreinoTeste(HashMap<Integer, List<Amostra>> basePorClasse, double proporcaoTreino) {
        for (Integer classe : basePorClasse.keySet()) {
            List<Amostra> lista = basePorClasse.get(classe);
            int limite = (int) (lista.size() * proporcaoTreino);
            treino.addAll(lista.subList(0, limite));
            teste.addAll(lista.subList(limite, lista.size()));
        }
    }

    /*
     * =============================================================================
     * IMPRESSÃO FORMATADA DOS RESULTADOS
     * =============================================================================
     */

    private static void imprimirTabela(HashMap<String, HashMap<String, List<Double>>> resultados, String[] metricas) {
        for (String metrica : metricas) {
            System.out.println("===================================================");
            System.out.printf("========== Execução %-10s ==========\n", metrica);
            System.out.println("===================================================");

            // Cabeçalho
            System.out.print("   -  ");
            for (int e = 1; e <= repeticoes; e++) {
                System.out.printf("|  E%-2d ", e);
            }
            System.out.println();

            // Linhas com valores de k2 a k20
            for (int k = kMin; k <= kMax; k++) {
                System.out.printf("  k%-2d ", k);
                for (int e = 1; e <= repeticoes; e++) {
                    List<Double> accs = resultados.get(metrica).get("E" + e);
                    double valor = accs.get(k - kMin);
                    System.out.printf("| %8.4f", valor);
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    private static void imprimirTabelaMedia(HashMap<String, HashMap<String, List<Double>>> resultados, String[] metricas) {
        System.out.println("===================================================");
        System.out.println("========== Resultado Media =======================");
        System.out.println("===================================================");

        // Cabeçalho
        System.out.print("   -   ");
        for (String metrica : metricas) {
            System.out.printf("| %-11s ", metrica);
        }
        System.out.println();

        // Linhas com k2 a k20
        for (int k = kMin; k <= kMax; k++) {
            System.out.printf("   k%-2d ", k);
            for (String metrica : metricas) {
                double soma = 0;
                for (int e = 1; e <= repeticoes; e++) {
                    soma += resultados.get(metrica).get("E" + e).get(k - kMin);
                }
                double media = soma / repeticoes;
                System.out.printf("|  %10.4f ", media);
            }
            System.out.println();
        }
        System.out.println();
    }
}
