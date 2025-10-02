package EstimadoresProbabilistica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import Comun.Amostra;

/*===================================================================================================
 * Classe que representa um Classificador Bayesiano (Naive Bayes)
 * 
 * Funcionalidade:
 * - Treina um modelo Bayesiano usando KDE ou Gaussiana normal.
 * - Armazena proporções de classes P(C).
 * - Armazena valores das features por classe.
 * - Classifica novas amostras usando a regra de Bayes:
 *      P(C|X) ∝ P(C) * Π P(X_j|C)
 * 
 * Parâmetros:
 * - usarKDE: define se a densidade condicional será estimada via KDE ou Gaussiana normal.
 ===================================================================================================*/


public class ClassificadorBayesiano {

    private boolean usarKDE; //Escolha do estimador: KDE ou Gaussiana normal
    private HashMap<Integer, Double> proporcaoClasse; //Proporção P(C) para cada classe
    private HashMap<Integer, List<List<Double>>> valoresPorClasse; //Valores das features agrupados por classe

    /**
     * Construtor
     * 
     * @param usarKDE define se será usado KDE ou Gaussiana normal para estimar densidade 
     */
    public ClassificadorBayesiano(boolean usarKDE) {
        this.usarKDE = usarKDE;
        proporcaoClasse = new HashMap<>();
        valoresPorClasse = new HashMap<>();
    }

     /**
     * Treina o classificador Bayesiano
     * - Calcula P(C) para cada classe
     * - Agrupa valores das features por classe
     * 
     * @param treino lista de amostras de treino
     */

    public void treinar(List<Amostra> treino) {
        int total = treino.size();

        // Separar amostras por classe e por feature
        for (Amostra a : treino) {
            int classe = (int) a.Y[0];

            //Conta ocorrencia da classe
            proporcaoClasse.put(classe, proporcaoClasse.getOrDefault(classe, 0.0) + 1);

            //Inicializa estrutura para armazenar valores das features por classe
            valoresPorClasse.putIfAbsent(classe, new ArrayList<>());
            while (valoresPorClasse.get(classe).size() < a.X.length) {
                valoresPorClasse.get(classe).add(new ArrayList<>());
            }

            // Armazena valores das features
            for (int j = 0; j < a.X.length; j++) {
                valoresPorClasse.get(classe).get(j).add(a.X[j]);
            }
        }

        // Calcular proporções P(C)
        for (Integer classe : proporcaoClasse.keySet()) {
            proporcaoClasse.put(classe, proporcaoClasse.get(classe) / total);
        }
    }

    /**
     * Classifica uma amostra usando a regra de Bayes
     * 
     * @param a amostra a ser classificada
     * @return classe prevista
     */
    public int classificar(Amostra a) {
        HashMap<Integer, Double> probClasse = new HashMap<>();

        //Para cada classe
        for (Integer classe : proporcaoClasse.keySet()) {
            double prob = proporcaoClasse.get(classe);

            // Multiplica pelas probabilidades condicionais P(X_j|C)
            for (int j = 0; j < a.X.length; j++) {
                List<Double> valores = valoresPorClasse.get(classe).get(j);

                if (usarKDE) {
                    prob *= GaussianaKDE.distribDensidadeProbKde(a.X[j], valores);
                } else {
                    prob *= GaussianaKDE.distribDensidadeProb(a.X[j], valores);
                }
            }
            probClasse.put(classe, prob);
        }

        // Retorna a classe com maior probabilidade
        return probClasse.entrySet() // retorna conjunto de entradas da classe 
                .stream() //transforma em um fluxo
                /*busca o maior elemento do fluxo de acordo com a regra 
                * max()  retorna a entrada com maior probabilidade
                */
                .max((e1, e2) -> Double.compare(e1.getValue(), e2.getValue())) 
                .get().getKey();
    }

    /**
     * Avalia a acurácia do classificador em uma base de dados
     * 
     * @param base lista de amostras a serem avaliadas
     * @return acurácia (%) do classificador
     */
    public double avaliar(List<Amostra> base) {
        int erros = 0;
        for (Amostra a : base) {
            int previsto = classificar(a);
            if (previsto != (int) a.Y[0]) erros++;
        }
        return 100.0 * (base.size() - erros) / base.size();
    }
}
