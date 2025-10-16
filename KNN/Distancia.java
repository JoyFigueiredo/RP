package KNN;

/**==========================================================================================
 * Classe que implementa diferentes métricas de distância.
 * Todas as funções recebem dois vetores numéricos (double[]) representando as amostras
 * e retornam um valor numérico representando a distância.
 ==========================================================================================*/

public class Distancia {

    /**
     * Calcula a distância Manhattan (L1) entre dois vetores.
     * Fórmula: Σ|xᵢ−yᵢ|
     *
     * @param x Vetor de características da amostra 1
     * @param y Vetor de características da amostra 2
     * @return Distância Manhattan entre x e y
     */
    public static double distanciaManhattan(double[] x, double[] y){
        double soma = 0;
        for(int i = 0; i < x.length; i++){
            soma += Math.abs(x[i] - y[i]);
        }
        return soma;
    }

    /**
     * Calcula a distância Euclidiana entre dois vetores.
     * Fórmula: √Σ(xᵢ−yᵢ)²
     *
     * @param x Vetor de características da amostra 1
     * @param y Vetor de características da amostra 2
     * @return 
     * @return Distância Euclidiana entre x e y
     */
    public static double distanciaEuclidiana(double[] x, double[] y){
        double soma = 0;
        for (int i = 0; i < x.length; i++) {
            soma += Math.pow(x[i] - y[i], 2);
        }
        return Math.sqrt(soma);
    }

    /**
     * Calcula a distância de Minkowski entre dois vetores para p = 3.
     * Fórmula: (Σ|xᵢ − yᵢ|³)^(1/3)
     *
     * @param x Vetor de características da amostra 1
     * @param y Vetor de características da amostra 2
     * @return Distância de Minkowski(p=3) entre x e y
     */
    public static double distanciaMinkowskP3(double[] x, double[] y){
        double soma = 0;
        for (int i = 0; i < x.length; i++) {
            soma += Math.pow(Math.abs(x[i] - y[i]),3);
        }
        return Math.pow(soma, 1.0 / 3);
    }

      /**
     * Calcula a distância de Minkowski entre dois vetores para p = 4.
     * Fórmula: (Σ|xᵢ − yᵢ|⁴)^(1/4)
     *
     * @param x Vetor de características da amostra 1
     * @param y Vetor de características da amostra 2
     * @return Distância de Minkowski(p=4) entre x e y
     */
    public static double distanciaMinkowskP4(double[] x, double[] y){
        double soma = 0;
        for (int i = 0; i < x.length; i++) {
            soma += Math.pow(Math.abs(x[i] - y[i]), 4);
        }
        return Math.pow(soma, 1.0 / 4);
    }


}
