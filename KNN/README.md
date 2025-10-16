# 📏 Comparando Métricas de Distância  
## utilizando o classificador KNN  

> Este repositório tem como objetivo **analisar e comparar diferentes métricas de distância** aplicadas ao **classificador K-Nearest Neighbors (KNN)**.  
> O projeto faz parte do estudo de **Reconhecimento de Padrões**, dentro da área de **Aprendizado de Máquina e Inteligência Artificial**.  

---

## 📌 Índice
- [� Comparando Métricas de Distância](#-comparando-métricas-de-distância)
  - [utilizando o classificador KNN](#utilizando-o-classificador-knn)
  - [📌 Índice](#-índice)
  - [📘 Descrição](#-descrição)
  - [⚙️ O Algoritmo KNN](#️-o-algoritmo-knn)
  - [📏 Métricas de Distância Comparadas](#-métricas-de-distância-comparadas)
    - [🔹 Distância Euclidiana](#-distância-euclidiana)
    - [🔹 Distância Manhattan](#-distância-manhattan)
    - [🔹 Distância de Minkowski](#-distância-de-minkowski)
    - [🔹 Distância de Chebyshev](#-distância-de-chebyshev)
  - [📊 Base de Dados Utilizada](#-base-de-dados-utilizada)
  - [🧠 Etapas do Experimento](#-etapas-do-experimento)
  - [📈 Resultados Esperados](#-resultados-esperados)
  - [💻 Requisitos](#-requisitos)
  - [🚀 Uso](#-uso)

---

## 📘 Descrição

O **K-Nearest Neighbors (KNN)** é um classificador baseado em **instâncias**, ou seja, não constrói um modelo explícito.  
A classificação de uma nova amostra é feita com base nas **K amostras mais próximas** no conjunto de treino, de acordo com uma **métrica de distância**.

O objetivo deste projeto é **comparar o impacto das diferentes métricas de distância** no desempenho do KNN.

---

## ⚙️ O Algoritmo KNN

1️⃣ Calcular a distância da amostra de teste para **todas as amostras de treino**.  
2️⃣ Selecionar as **K amostras mais próximas**.  
3️⃣ Realizar uma **votação majoritária** entre as classes dessas K amostras.  
4️⃣ Classificar a amostra com a classe mais votada.

---

## 📏 Métricas de Distância Comparadas

As métricas de distância influenciam diretamente o comportamento do KNN.  
Abaixo estão as principais utilizadas neste estudo.

---

### 🔹 Distância Euclidiana  

A métrica mais comum, mede a distância "reta" entre dois pontos:  

$$
d(x, y) = \sqrt{\sum_{i=1}^{n} (x_i - y_i)^2}
$$

✔️ Boa para dados contínuos e escalonados.  
❌ Sensível a atributos em diferentes escalas.

---

### 🔹 Distância Manhattan  

Baseia-se na soma das diferenças absolutas:  

$$
d(x, y) = \sum_{i=1}^{n} |x_i - y_i|
$$

✔️ Mais robusta a outliers do que a Euclidiana.  
❌ Pode distorcer distâncias em espaços de alta dimensão.

---

### 🔹 Distância de Minkowski  

Generaliza as anteriores, controlada por um parâmetro \( p \):  

$$
d(x, y) = \left( \sum_{i=1}^{n} |x_i - y_i|^p \right)^{1/p}
$$

- \( p = 1 \) → Manhattan  
- \( p = 2 \) → Euclidiana  

✔️ Permite ajustar a sensibilidade da métrica.  
❌ Requer escolha adequada do parâmetro \( p \).

---

### 🔹 Distância de Chebyshev  

Considera apenas a **maior diferença** entre os atributos:  

$$
d(x, y) = \max_i |x_i - y_i|
$$

✔️ Útil em casos onde o maior desvio domina a decisão.  
❌ Pode ignorar pequenas variações entre dimensões.

---

## 📊 Base de Dados Utilizada  

A base utilizada é a **transfusion.data**, contendo informações sobre doadores de sangue.  
Ela é a mesma usada no estudo de **classificadores bayesianos**, para manter a consistência entre os experimentos.

| Atributo    | Descrição                                                                                  |
|-------------|--------------------------------------------------------------------------------------------|
| **Recency (R)**   | Número de meses desde a última doação.                                                |
| **Frequency (F)** | Número total de doações realizadas.                                                   |
| **Monetary (M)**  | Volume total de sangue doado (c.c.).                                                 |
| **Time (T)**      | Meses desde a primeira doação.                                                       |
| **Class**         | Se o doador fez nova doação em março/2007 (1 = sim, 0 = não).                        |

---

## 🧠 Etapas do Experimento  

1️⃣ Normalização dos atributos (para evitar influência de escala).  
2️⃣ Escolha de **K** (número de vizinhos).  
3️⃣ Aplicação das **métricas de distância** (Euclidiana, Manhattan, Minkowski e Chebyshev).  
4️⃣ Avaliação da **taxa de acerto** e **matriz de confusão**.  
5️⃣ Comparação dos resultados entre as métricas.

---

## 📈 Resultados Esperados  

A comparação busca observar:  
- Variação na **acurácia** conforme a métrica.  
- Diferenças no comportamento do classificador para dados próximos da fronteira de decisão.  
- Impacto do **valor de K** no desempenho.

---

## 💻 Requisitos  

✔ **Java JDK 17+**  
✔ **Biblioteca Swing (para seleção de arquivos)**  
✔ **Arquivos `.data` de treino e teste**  

---

## 🚀 Uso  

1️⃣ Compile o projeto:  
```bash
javac *.java
