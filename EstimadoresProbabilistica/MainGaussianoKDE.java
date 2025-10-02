package EstimadoresProbabilistica;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MainGaussianoKDE {

    static double proporcaoTreino = 0.7;

    private static Amostra[] base; // Amostras carregadas do arquivo

    private static List<Amostra> treino = new ArrayList<>(); // Amostras de treino
    private static List<Amostra> teste = new ArrayList<>();  // Amostras de teste

    /*==========================================================================================
     *                              ATENÇÃO !!!! - MUDE PARA QUAL ESTIMADOR USAR
     *              - TRUE  = KDE
     *              - FALSE = GAUSSIANO
     ===========================================================================================*/
    private static boolean usarKDE = true; // <<== mude para false se quiser usar Gaussiana Normal

    public static void main(String[] args) {
        // Lê o arquivo de dados
        base = ReaderWriter.aux.readWindow();

        HashMap<Integer, List<Amostra>> basePorClasse = separarPorClasse(base);

        embaralharBases(basePorClasse);
        dividirTreinoTeste(basePorClasse, proporcaoTreino);

        // Avaliação
        System.out.println("=== Avaliação na base de treino ===");
        avaliarBase(treino, basePorClasse);

        System.out.println("=== Avaliação na base de teste ===");
        avaliarBase(teste, basePorClasse);
    }

    private static HashMap<Integer, List<Amostra>> separarPorClasse(Amostra[] base) {
        HashMap<Integer, List<Amostra>> map = new HashMap<>();
        for(Amostra a : base){
            int classe = (int)a.Y[0];
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

    private static void avaliarBase(List<Amostra> baseAvaliar, HashMap<Integer, List<Amostra>> basePorClasse) {
        int erros = 0;
        int total = baseAvaliar.size();
        int qtdFeatures = Amostra.qtdIn;

        // Proporção P(C)
        HashMap<Integer, Double> proporcao = new HashMap<>();
        for (Integer classe : basePorClasse.keySet()) {
            long count = treino.stream()
                .filter(a -> (int) a.Y[0] == classe)
                .count();
            proporcao.put(classe, count / (double) treino.size());
        }

        // Avaliação
        for (Amostra a : baseAvaliar) {
            HashMap<Integer, Double> probClasse = new HashMap<>();

            for (Integer classe : basePorClasse.keySet()) {
                double prob = proporcao.get(classe);

                for (int j = 0; j < qtdFeatures; j++) {
                    List<Double> valores = coletarValores(treino, classe, j);

                    if (usarKDE) {
                        prob *= GaussianaKDE.distribDensidadeProbKde(a.X[j], valores);
                    } else {
                        prob *= GaussianaKDE.distribDensidadeProb(a.X[j], valores);
                    }
                }

                probClasse.put(classe, prob);
            }

            // Classe com maior probabilidade
            int classePrevista = probClasse.entrySet().stream()
                    .max((e1, e2) -> Double.compare(e1.getValue(), e2.getValue()))
                    .get().getKey();

            if (classePrevista != (int) a.Y[0]) erros++;
        }

        System.out.println("Total amostras: " + total + " -- Erros: " + erros);
        System.out.printf("Acurácia: %.2f%%\n", 100.0 * (total - erros) / total);
    }

    private static List<Double> coletarValores(List<Amostra> base, int classe, int indiceFeature) {
        List<Double> valores = new ArrayList<>();
        for (Amostra a : base) {
            if ((int) a.Y[0] == classe) valores.add(a.X[indiceFeature]);
        }
        return valores;
    }
}
