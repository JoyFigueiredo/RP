Olá,  
> Venho compartilhar meu conhecimento em **Reconhecimento de Padrões**, deixando exemplos, explicações e conceitos de fácil acesso.  
> Este repositório funciona como um portfólio pessoal para organização e estudos na área de **aprendizado de máquina e inteligência artificial**.  

# 🤖 Reconhecimento de Padrões

## 📌 Índice
- [🤖 Reconhecimento de Padrões](#-reconhecimento-de-padrões)
  - [📌 Índice](#-índice)
  - [📌 Descrição](#-descrição)
    - [📊 Estimadores de Densidade Probabilística](#-estimadores-de-densidade-probabilística)
      - [🔹 Estimador Gaussiano](#-estimador-gaussiano)
      - [🔹 Kernel Density Estimation (KDE)](#-kernel-density-estimation-kde)
    - [📌 Classificador Bayesiano](#-classificador-bayesiano)
      - [📍 Teorema de Bayes](#-teorema-de-bayes)
      - [⚙️ Naive Bayes](#️-naive-bayes)
  - [📌 Exemplos de Implementação](#-exemplos-de-implementação)
  - [📌 Requisitos](#-requisitos)
  - [📌 Uso](#-uso)

---

## 📌 Descrição  

O projeto aborda conceitos fundamentais de **Reconhecimento de Padrões** utilizando **estimadores de densidade probabilística** e **classificadores Bayesianos**.  
O objetivo é aplicar técnicas estatísticas para identificar a qual classe uma amostra pertence, com base em seu vetor de características.

---

### 📊 Estimadores de Densidade Probabilística  

Um **estimador de densidade** é utilizado para calcular a probabilidade de um dado valor ocorrer, modelando a distribuição de atributos em cada classe.  

#### 🔹 Estimador Gaussiano  

O **Estimador Gaussiano** assume que os dados seguem uma **distribuição normal**.  
Ele utiliza **média** e **desvio padrão** para cada atributo de cada classe, aplicando a função de densidade da normal:

\[
f(x) = \frac{1}{\sqrt{2\pi\sigma^2}} \cdot e^{-\frac{(x - \mu)^2}{2\sigma^2}}
\]

✔️ Vantagens: rápido e simples.  
❌ Limitação: funciona bem apenas se os dados forem aproximadamente normais.  

---

#### 🔹 Kernel Density Estimation (KDE)  

O **KDE** é um estimador **não paramétrico**, que não assume forma prévia da distribuição.  
Ele constrói a densidade a partir de **núcleos (kernels)** suavizados sobre cada ponto de treino:

\[
\hat{f}(x) = \frac{1}{n h} \sum_{i=1}^{n} K\left(\frac{x - x_i}{h}\right)
\]

Onde:  
- `K` é a função núcleo (por exemplo, Gaussiana).  
- `h` é a largura de banda (*bandwidth*).  

✔️ Vantagens: captura distribuições complexas.  
❌ Limitação: mais custoso computacionalmente e depende da escolha de `h`.  

---

### 📌 Classificador Bayesiano  

Um **Classificador Bayesiano** estima a probabilidade de uma amostra pertencer a uma classe `C` a partir dos atributos `X`, aplicando o **Teorema de Bayes**.  

#### 📍 Teorema de Bayes  

\[
P(C|X) = \frac{P(X|C) \cdot P(C)}{P(X)}
\]

- **P(C)** → Probabilidade a priori da classe (frequência no treino).  
- **P(X|C)** → Probabilidade da amostra dado a classe (estimada com Gaussiana ou KDE).  
- **P(X)** → Constante de normalização (mesma para todas as classes).  

---

#### ⚙️ Naive Bayes  

O **Naive Bayes** é uma simplificação que assume independência entre os atributos:  

\[
P(X|C) = \prod_{i=1}^{n} P(X_i | C)
\]

✔️ Fácil de implementar.  
✔️ Funciona bem mesmo em cenários simples.  
❌ Pode perder precisão quando os atributos são fortemente correlacionados.  

---

## 📌 Exemplos de Implementação  

🔹 **Gaussiana** → estimativa paramétrica (usa média e desvio).  
🔹 **KDE** → estimativa não paramétrica (usa todos os pontos do treino).  
🔹 **Naive Bayes** → classificador que combina os estimadores para prever a classe mais provável.  

---

## 📌 Requisitos  

✔ **Java JDK 17+**  
✔ **Biblioteca Swing (para seleção de arquivos)**  
✔ **Arquivos `.data` de treino e teste**  

---

## 📌 Uso  

1️⃣ Compile o projeto:  
```bash
javac *.java