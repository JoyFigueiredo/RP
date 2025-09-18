# Projeto Perceptron em Java

Este projeto implementa um **classificador Perceptron probabilístico** para distinguir amostras em diferentes classes usando **métodos de média, desvio e Kernel Density Estimation (KDE)**. O código organiza e processa os dados, calcula estatísticas por classe e avalia o modelo em bases de treino e teste.

---

## Estrutura do Projeto

- **PerceptronMain.java**  
  Classe principal que gerencia a execução do programa:  
  - Lê os dados de arquivo `.data`  
  - Separa amostras por classe  
  - Divide em treino e teste  
  - Calcula médias e desvios  
  - Avalia acurácia do modelo  

- **Amostra.java**  
  Representa uma amostra de dados:  
  - Vetores de entrada (`X`) e saída (`Y`)  
  - Construtores e métodos utilitários  

- **ReaderWriter.java**  
  Responsável por:  
  - Listar arquivos `.pgm` ou `.data`  
  - Ler dados do arquivo e converter em vetor de objetos `Amostra`  
  - Abrir janela de seleção de arquivo  

- **Gaussiana.java**  
  Implementa funções para cálculo de **densidade de probabilidade usando KDE**.

---

## Como Rodar

1. Compile o projeto:
   ```bash
   javac *.java
