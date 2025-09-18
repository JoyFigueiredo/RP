import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Main {

    static double proporcaoTreino = 0.7;

    private static Amostra[] base; // Vetor com todas as amostras carregadas do arquivo

    /*
    * ======================================================================
    *                  Arrays e Estruturas de Dados
    * ======================================================================
     */
    private static List<Amostra> treino = new ArrayList<>(); // Amostras de treino
    private static List<Amostra> teste = new ArrayList<>();  // Amostras de teste

    // Armazena as médias e desvios padrão de cada classe para cada feature
    private static HashMap<Integer, double[]> medias = new HashMap<>();
    private static HashMap<Integer, double[]> desvios = new HashMap<>();


    public static void main(String[] args) {
        // Lê o arquivo de dados selecionado pelo usuário
        base = ReaderWriter.aux.readWindow();

        HashMap<Integer, List<Amostra>> basePorClasse = separarPorClasse(base); // Agrupa as amostras por classe usando HashMap
        //1. 
        embaralharBases(basePorClasse); // Embaralha as amostras de cada classe
        //2.
        dividirTreinoTeste(basePorClasse, proporcaoTreino);// Divide as amostras em treino e teste
        //3.
        calcularMediaDesvioPorClasse(treino, basePorClasse); // Calcula médias e desvios padrão para cada classe

        // Avaliação do modelo na base de treino
        System.out.println("=== Avaliação na base de treino ===");
        avaliarBase(treino, basePorClasse);

        // Avaliação do modelo na base de teste
        System.out.println("=== Avaliação na base de teste ===");
        avaliarBase(teste, basePorClasse);
    }

    /**
     * Agrupa as amostras em um HashMap, usando a classe como chave
     * @param base vetor de amostras
     * @return HashMap com chave = classe e valor = lista de amostras da classe
     */
    private static HashMap<Integer, List<Amostra>> separarPorClasse(Amostra[] base) {
        HashMap<Integer, List<Amostra>> map = new HashMap<>();
        for(Amostra a : base){
            //Verifica Y
            int classe = (int)a.Y[0]; // Identifica a classe da amostra
            map.putIfAbsent(classe, new ArrayList<>()); // Cria lista vazia se ainda não existir
            map.get(classe).add(a); // Adiciona a amostra à lista da sua classe
        }
        return map;
    }

    /**
     * Embaralha as listas de amostras de cada classe
     * @param basePorClasse HashMap com listas de amostras por classe
     */
    private static void embaralharBases(HashMap<Integer, List<Amostra>> basePorClasse) {
        for (List<Amostra> lista : basePorClasse.values()) {
            Collections.shuffle(lista); // Embaralha aleatoriamente
        }
    }

    /**
     * Divide as amostras de cada classe em treino e teste
     * @param basePorClasse HashMap com listas de amostras por classe
     * @param proporcaoTreino proporção de amostras para treino (ex: 0.7 = 70%)
     */
    private static void dividirTreinoTeste(HashMap<Integer, List<Amostra>> basePorClasse, double proporcaoTreino) {
        for (Integer classe : basePorClasse.keySet()) {
            List<Amostra> lista = basePorClasse.get(classe);
            int limite = (int) (lista.size() * proporcaoTreino); // Número de amostras para treino

            treino.addAll(lista.subList(0, limite)); // Adiciona ao treino
            teste.addAll(lista.subList(limite, lista.size())); // Adiciona ao teste
        }
    }

    /**
     * Calcula médias e desvios padrão de cada feature para cada classe
     * @param baseTreino lista de amostras de treino
     * @param basePorClasse HashMap com listas de amostras por classe
     */
    private static void calcularMediaDesvioPorClasse(List<Amostra> baseTreino, HashMap<Integer, List<Amostra>> basePorClasse) {
        int qtdFeatures = Amostra.qtdIn; // Número de features (X)

        for (Integer classe : basePorClasse.keySet()) {
            // Coleta apenas amostras de treino da classe atual
            List<Amostra> subset = new ArrayList<>();
            for (Amostra a : baseTreino) if ((int) a.Y[0] == classe) subset.add(a);

            double[] media = new double[qtdFeatures];
            double[] desvio = new double[qtdFeatures];

            // Calcula média e desvio para cada feature
            for (int j = 0; j < qtdFeatures; j++) {
                double soma = 0;
                for (Amostra a : subset) soma += a.X[j];
                media[j] = soma / subset.size();

                double somaQuad = 0;
                for (Amostra a : subset) somaQuad += Math.pow(a.X[j] - media[j], 2);
                desvio[j] = Math.sqrt(somaQuad / subset.size());
            }

            medias.put(classe, media);  // Armazena média
            desvios.put(classe, desvio); // Armazena desvio padrão
        }
    }

    /**
     * Avalia a acurácia do modelo em uma base (treino ou teste)
     * @param baseAvaliar lista de amostras a serem avaliadas
     * @param basePorClasse HashMap com listas de amostras por classe
     */
    private static void avaliarBase(List<Amostra> baseAvaliar, HashMap<Integer, List<Amostra>> basePorClasse) {
        int erros = 0;
        int total = baseAvaliar.size();
        int qtdFeatures = Amostra.qtdIn;

        // Calcula proporção de cada classe na base de treino (para P(C))
        HashMap<Integer, Double> proporcao = new HashMap<>();
        for (Integer classe : basePorClasse.keySet()) {
            long count = treino.stream().filter(a -> (int) a.Y[0] == classe).count();
            proporcao.put(classe, count / (double) treino.size());
        }

        // Para cada amostra, calcula a probabilidade de pertencer a cada classe
        for (Amostra a : baseAvaliar) {
            HashMap<Integer, Double> probClasse = new HashMap<>();

            for (Integer classe : basePorClasse.keySet()) {
                double prob = proporcao.get(classe); // P(C)

                // Multiplica pelas probabilidades das features usando KDE
                for (int j = 0; j < qtdFeatures; j++) {
                    prob *= Gaussiana.distribDensidadeProbKde(a.X[j], coletarValores(treino, classe, j));
                }

                probClasse.put(classe, prob);
            }

            // Predição: classe com maior probabilidade
            int classePrevista = probClasse.entrySet().stream()
                    .max((e1, e2) -> Double.compare(e1.getValue(), e2.getValue()))
                    .get().getKey();

            // Conta erro se a classe prevista for diferente da real
            if (classePrevista != (int) a.Y[0]) erros++;
        }

        System.out.println("Total amostras: " + total + " -- Erros: " + erros);
        System.out.printf("Acurácia: %.2f%%\n", 100.0 * (total - erros) / total);
    }

    /**
     * Coleta todos os valores de uma feature específica para uma classe
     * @param base lista de amostras (treino)
     * @param classe classe que queremos coletar
     * @param indiceFeature índice da feature
     * @return lista de valores da feature para a classe
     */
    private static List<Double> coletarValores(List<Amostra> base, int classe, int indiceFeature) {
        List<Double> valores = new ArrayList<>();
        for (Amostra a : base) {
            if ((int) a.Y[0] == classe) valores.add(a.X[indiceFeature]);
        }
        return valores;
    }
}


