package EstimadoresProbabilistica;

import java.util.List;

/**==========================================================================================
 * Classe para estimativa de densidade de probabilidade
 * - distribDensidadeProbKde : usa KDE (Kernel Density Estimation)
 * - distribDensidadeProb    : usa distribuição Normal clássica (paramétrica)
 ============================================================================================*/

public class GaussianaKDE {

    /**
     * Estima a densidade usando KDE (Kernel Density Estimation)
     *
     * @param x ponto onde a densidade será estimada
     * @param xiVet lista de amostras (dados observados)
     * @return valor estimado da densidade em x
     */
    public static double distribDensidadeProbKde(double x, List<Double> xiVet) {
        double dp = calculateStandardDeviation(xiVet);
        int N = xiVet.size();

        // largura de banda h (Silverman)
        double h = 1.06 * dp * Math.pow(N, -1.0 / 5.0);

        // Soma o efeito dos kernels
        double result = 0.0;
        for (Double xi : xiVet) {
            result += (1.0 / h) * Kernel(x, xi, h);
        }

        // Normaliza pela quantidade de amostras
        return result / N;
    }

    /**
     * Estima a densidade assumindo distribuição normal paramétrica
     *
     * @param x ponto de avaliação
     * @param xiVet lista de amostras
     * @return valor da densidade normal em x
     */
    public static double distribDensidadeProb(double x, List<Double> xiVet) {
        double media = calculaMedia(xiVet);
        double dp = calculateStandardDeviation(xiVet);

        double coef = 1.0 / (dp * Math.sqrt(2 * Math.PI));
        double expoente = Math.exp(-Math.pow(x - media, 2) / (2 * dp * dp));

        return coef * expoente;
    }

    /**
     * Calcula a média de uma lista
     */
    private static double calculaMedia(List<Double> values) {
        double soma = 0.0;
        for (Double value : values) soma += value;
        return soma / values.size();
    }

    /**
     * Calcula o desvio padrão de uma lista
     */
    private static double calculateStandardDeviation(List<Double> values) {
        double media = calculaMedia(values);
        double soma = 0.0;
        for (Double value : values) soma += Math.pow(value - media, 2);
        return Math.sqrt(soma / values.size());
    }

    /**
     * Kernel Gaussiano para o KDE
     */
    private static double Kernel(double x, double xi, double h) {
        double r = 1.0 / Math.sqrt(2 * Math.PI);
        return r * Math.exp(-Math.pow((x - xi) / h, 2) / 2);
    }
}
