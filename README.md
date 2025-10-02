# Reconhecimento de Padrões

## 📑 Sumário
- [Reconhecimento de Padrões](#reconhecimento-de-padrões)
  - [📑 Sumário](#-sumário)
  - [Explicação de Estimadores de Densidade Probabilística](#explicação-de-estimadores-de-densidade-probabilística)
    - [Estimador Gaussiano](#estimador-gaussiano)
    - [Kernel Density Estimation (KDE)](#kernel-density-estimation-kde)
  - [Classificador Bayesiano](#classificador-bayesiano)
    - [Naive Bayes](#naive-bayes)

---

## Explicação de Estimadores de Densidade Probabilística

Um **estimador de densidade probabilística** é usado para modelar como os dados estão distribuídos no espaço.  
Esses estimadores permitem calcular a **probabilidade** de uma nova amostra pertencer a uma classe, com base nos dados de treino.

Existem duas abordagens principais implementadas neste projeto:

### Estimador Gaussiano
O estimador gaussiano assume que os dados seguem uma **distribuição normal**.  
Ele calcula a **média** e o **desvio padrão** para cada atributo de cada classe e, com isso, obtém a função de densidade de probabilidade:

- ✔️ **Vantagens**: rápido, simples, requer apenas média e variância.  
- ❌ **Limitações**: funciona bem apenas se os dados realmente tiverem comportamento aproximadamente normal.  

### Kernel Density Estimation (KDE)
O **KDE** é um estimador **não paramétrico**, ou seja, não assume que os dados seguem uma forma específica.  
Em vez disso, ele cria uma estimativa suavizada da densidade ao somar várias funções núcleo (como gaussianas) centradas em cada ponto de treino.

- ✔️ **Vantagens**: flexível, funciona para distribuições complexas ou multimodais.  
- ❌ **Limitações**: mais lento, depende da escolha da largura de banda (*bandwidth*).  

---

## Classificador Bayesiano

O **Classificador Bayesiano** é baseado no **Teorema de Bayes**, que calcula a probabilidade de uma amostra pertencer a uma classe `C`, dado um conjunto de atributos `X`.  

Ele se baseia na fórmula:

\[
P(C|X) = \frac{P(X|C) \cdot P(C)}{P(X)}
\]

- **P(C)**: probabilidade a priori da classe (frequência da classe no treino).  
- **P(X|C)**: probabilidade de observar os atributos X, dado que a amostra pertence à classe C.  
- **P(X)**: probabilidade total (constante para todas as classes, pode ser ignorada na prática).  

### Naive Bayes
Uma simplificação muito utilizada é o **Naive Bayes**, que assume que todos os atributos são **independentes** entre si.  
Nesse caso:

\[
P(X|C) = \prod_{i=1}^{n} P(X_i | C)
\]

No projeto:  
- O cálculo de \( P(X_i | C) \) pode ser feito com **Gaussiana** ou com **KDE**, dependendo do estimador escolhido.  
- A classe final atribuída é aquela que maximiza \( P(C|X) \).  

---
