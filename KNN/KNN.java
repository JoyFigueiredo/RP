package KNN;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Comun.Amostra;
import EstimadoresProbabilistica.GaussianaKDE;

/**===============================================================================================
 * Classe que implementa o algoritmo KNN (K-Nearest Neighbors).
 =================================================================================================*/
public class KNN {
    
    private List<Amostra> treino;
    private int k; 
    private String metrica;

    public KNN(List<Amostra> treino, int k, String metrica) {
        this.treino = treino;
        this.k = k;
        this.metrica = metrica;
    }

    /**
     * Avalia a acurácia do classificador em uma base de dados
     * 
     * @param base lista de amostras a serem avaliadas
     * @return acurácia (%) do classificador
     */
    public double avaliar(List<Amostra> base){
        int erros = 0;
        for (Amostra a : base) {
            int previsto = classificar(a);
            if (previsto != (int) a.Y[0]) erros++;
        }
        return 100.0 * (base.size() - erros) / base.size();
    }

    private int classificar(Amostra amostra) {
        // Distâncias armazenadas como <distância, classe>
        List<double[]> distancias = new ArrayList<>();

        for (Amostra t : treino) {
            double dist = calcularDistancia(amostra.X, t.X);
            distancias.add(new double[]{dist, t.Y[0]});
        }

        // Ordenar por distância
        distancias.sort(Comparator.comparingDouble(a -> a[0]));

        // Contar classes dos k vizinhos mais próximos
        HashMap<Integer, Integer> contagem = new HashMap<>();
        for (int i = 0; i < k; i++) {
            int classe = (int) distancias.get(i)[1];
            contagem.put(classe, contagem.getOrDefault(classe, 0) + 1);
        }

        // Retorna a classe com maior probabilidade
        return contagem.entrySet() // retorna conjunto de entradas da classe 
                .stream() //transforma em um fluxo
                /*busca o maior elemento do fluxo de acordo com a regra 
                * max()  retorna a entrada com maior probabilidade
                */
                .max((e1, e2) -> Double.compare(e1.getValue(), e2.getValue())) 
                .get().getKey();
    }

    /**
     * Calcula a distância entre dois vetores usando a métrica escolhida.
     *
     * @param a vetor da amostra 1
     * @param b vetor da amostra 2
     * @return distância calculada
     */
    private double calcularDistancia(double[] a, double[] b) {
        switch (metrica.toLowerCase()) {
            case "manhattan":
                return Distancia.distanciaManhattan(a, b);
            case "euclidiana":
                return Distancia.distanciaEuclidiana(a, b);
            case "minkowski3":
                return Distancia.distanciaMinkowskP3(a, b);
            case "minkowski4":
                return Distancia.distanciaMinkowskP4(a, b);
            default:
                throw new IllegalArgumentException("Métrica desconhecida: " + metrica);
        }
    }

}
