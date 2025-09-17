/**
 * Classe abstrata que representa uma Rede Neural Artificial (RNA) genérica.
 * 
 * Esta classe define a estrutura básica que qualquer rede neural deve ter:
 *  - Um método para treinar a rede com dados de entrada e saída esperada.
 *  - Um método para testar a rede com dados de entrada e saída esperada.
 * 
 * As classes concretas que herdarem de RNA devem implementar esses dois métodos.
 */
public abstract class RNA {

    /**
     * Treina a rede neural com um conjunto de dados de entrada (Xin) e saída esperada (y).
     *
     * @param Xin vetor com os valores de entrada (features)
     * @param y vetor com os valores de saída esperados (rótulos)
     * @return vetor com a saída calculada pela rede após o treinamento
     */
    public abstract double[] treinar(double[] Xin, double[] y);

    /**
     * Testa a rede neural com um conjunto de dados de entrada (Xin) e saída esperada (Y).
     *
     * @param Xin vetor com os valores de entrada a serem testados
     * @param Y vetor com os valores de saída esperados para comparação
     * @return vetor com a saída calculada pela rede durante o teste
     */
    public abstract double[] teste(double[] Xin, double[] Y);
}
