import java.util.List;

/**==========================================================================================
 * Classe para estimativa de densidade de probabilidade usando kernel Gaussiano (KDE)
 ============================================================================================*/

public class Gaussiana {

    /**
     * Calcula a densidade de probabilidade em um ponto x usando KDE.
     * 
     * @param x ponto onde a densidade será estimada
     * @param xiVet lista de amostras (dados observados)
     * @return valor estimado da densidade de probabilidade em x
     */
     public static double distribDensidadeProbKde(double x, List<Double> xiVet) {
// Calcula média e desvio padrão dos dados
        double media = calculaMedia(xiVet);
        double dp = calculateStandardDeviation(xiVet);
        int N = xiVet.size();

        // Calcula a largura de banda h usando a regra de Silverman
        double h = 1.06 * dp * Math.pow(N, -1.0 / 5.0);// bandwidth

        // Soma o efeito do kernel gaussiano para cada ponto xi
        double result = 0.0;
        for (Double xi : xiVet) {
            result += (1.0 / h) * Kernel(x, xi, h);
        }

        // Normaliza pela quantidade de amostras
        result = result * (1.0 / N);
        return result;
     }

     /**
     * Calcula a média de uma lista de valores
     * 
     * @param values lista de valores
     * @return média aritmética
     */
    private static double calculaMedia(List<Double> values) {
        double soma = 0.0;
        for (Double value : values) {
            soma += value;
        }
        return soma / values.size();
    }

    /**
     * Calcula o desvio padrão de uma lista de valores
     * 
     * @param values lista de valores
     * @return desvio padrão
     */
    private static double calculateStandardDeviation(List<Double> values) {
        double media = calculaMedia(values);
        double soma = 0.0;
        for (Double value : values) {
            soma += Math.pow(value - media, 2);
        }
        return Math.sqrt(soma / values.size());
    }

    /**
     * Kernel Gaussiano
     * 
     * @param x ponto de avaliação
     * @param xi ponto da amostra
     * @param h largura de banda
     * @return valor do kernel gaussiano para xi
     */
    private static double Kernel(double x, double xi, double h) {
        double r = 1.0 / Math.sqrt(2 * Math.PI); // Normalização do kernel gaussiano
        r = r * Math.exp(-Math.pow((x - xi) / h, 2) / 2); // Fórmula do kernel gaussiano
        return r;
    }
}
