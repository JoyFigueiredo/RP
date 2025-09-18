import java.util.Arrays;

/**
 * Representa uma amostra de dados, com valores de entrada (X) e saída (Y).
 * 
 * - Cada amostra tem:
 *    - qtdIn valores de entrada
 *    - qtdOut valores de saída
 * - A classe também define o número total de amostras que devem ser lidas de um arquivo.
 */
public class Amostra {
    static int qtdIn = 4;    // Número de entradas
    static int qtdOut = 1;   // Número de saídas
    static int amostra = 748; // Quantidade de amostras

    // Vetor de valores de entrada
    double[] X;

    // Vetor de valores de saída
    double[] Y;

    /**
     * Construtor padrão.
     * Cria os vetores X e Y com os tamanhos definidos por qtdIn e qtdOut.
     */
    public Amostra() {
        this.X = new double[qtdIn];
        this.Y = new double[qtdOut];
    }

    /**
     * Construtor que recebe vetores prontos.
     * @param X valores de entrada
     * @param Y valores de saída
     */
    public Amostra(double[] X, double[] Y) {
        this.X = X;
        this.Y = Y;
    }

    /**
     * Retorna uma representação em texto da amostra, 
     * mostrando os valores de X e Y.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Amostra{");
        sb.append("X=");
        sb.append(Arrays.toString(X));
        sb.append(", Y=");
        sb.append(Arrays.toString(Y));
        sb.append('}');
        return sb.toString();
    }
}
