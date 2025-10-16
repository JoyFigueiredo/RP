package KNN;

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


    public static void main(String[] args) {
        // Lê o arquivo de dados
        base = ReaderWriter.aux.readWindow();

        HashMap<Integer, List<Amostra>> basePorClasse = separarPorClasse(base);
        String[] metricas = {"manhattan", "euclidiana", "minkowski3", "minkowski4"};

        /*========================================================================================
         *                      RESULTADOS ARMAZENADOS
         =========================================================================================*/
        HashMap<Integer, HashMap<String, List<Double>>> resultados = new HashMap<>();
        
    }
}
