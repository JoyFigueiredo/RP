package Comun;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

/*===================================================================================================
 * Classe responsavél por: 
 *  -   Listar arquivos no diretório atual
 *  -   Guardar esses arquivos em uma lista encadeada(ReaderWriter$CelulaNew)
 *  -   Ler os arquivos e transforma-lá em um vetor de objetos Amostra
 ====================================================================================================*/


public class ReaderWriter {

    public static ReaderWriter aux = new ReaderWriter(); // Instância estática para facilitar o acesso global à classe
    private CelulaNew files = new CelulaNew();

    /**
     * Lista os arquivos com extensão ".pgm" presentes na pasta raiz do projeto.
     * Também armazena esses arquivos em uma lista encadeada.
     *
     * @return String contendo a lista numerada de arquivos encontrados.
     */
    public String getFiles() {
        // A raiz do projeto (pasta atual)
        File file = new File("./");
        StringBuilder string = new StringBuilder();
        int i = 3;

        // Limpa a lista de arquivos antes de popular novamente
        this.files.next = null;
        CelulaNew atual = this.files;

        // Percorre os arquivos na pasta atual
        for (File li : file.listFiles()) {
            String nome = li.getName();
            // Verifica apenas arquivos com extensão .pgm
            if (nome.contains(".pgm")) {
                atual.next = new CelulaNew(li); // Cria nova célula
                atual = atual.next;             // Avança para a próxima
                string.append(String.format("%02d- %s\n", i++, nome));
            }
        }
        return string.toString();
    }
    /**
     * Retorna o arquivo selecionado pelo índice informado.
     *
     * @param i índice do arquivo na lista (começando em 0)
     * @return vetor de Amostras lidas do arquivo, ou null se não existir
     */
    public Amostra[] read(int i) {
        CelulaNew atual = this.files.next;
        for (int j = 0; j < i; j++) {
            if (atual == null) {
                return null; // Índice inválido
            }
            atual = atual.next;
        }
        return (atual != null) ? read(atual.file) : null;
    }

    /**
     * Abre uma janela gráfica para o usuário escolher um arquivo .data
     *
     * @return vetor de Amostras do arquivo escolhido, ou null se cancelado
     */
    public Amostra[] readWindow() {
        JFileChooser escolhedor = new JFileChooser();
        escolhedor.setFileFilter(new FileNameExtensionFilter("Somente arquivos .data", "data"));

        JFrame jf = new JFrame();
        jf.setVisible(true);

        int opcaoEscolhida = escolhedor.showOpenDialog(jf);
        jf.dispose();

        if (opcaoEscolhida == JFileChooser.APPROVE_OPTION) {
            return read(escolhedor.getSelectedFile());
        } else {
            return null;
        }
    }

    /**
     * Lê um arquivo pelo caminho digitado no console.
     *
     * @param url caminho do arquivo (com ou sem .pgm)
     * @return vetor de Amostras lidas do arquivo, ou null se não existir
     */
    public Amostra[] readConsole(String url) {
        if (!url.endsWith(".pgm")) {
            url = url.concat(".pgm");
        }
        File file = new File(url);
        if (file.canRead()) {
            return read(file);
        }
        return null;
    }

    /**
     * Lê os dados de um arquivo .pgm e os converte em um vetor de Amostras.
     *
     * @param file arquivo a ser lido
     * @return vetor de Amostras ou null se ocorrer erro
     */
    protected Amostra[] read(File file) {
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            // Cria o vetor de amostras com tamanho definido pela classe Amostra
            Amostra[] amostra = new Amostra[Amostra.amostra];

            // Preenche o vetor com os dados lidos do arquivo
            for (int i = 0; i < Amostra.amostra; i++) {
                amostra[i] = new Amostra();
                String linha = br.readLine();
                String[] particao = linha.replaceAll(" ", "").split(",");

                for (int j = 0; j < Amostra.qtdIn; j++) {
                    amostra[i].X[j] = Double.parseDouble(particao[j]);
                }
                for (int j = 0; j < Amostra.qtdOut; j++) {
                    amostra[i].Y[j] = Double.parseDouble(particao[j + Amostra.qtdIn]);
                }
            }

            br.close();
            fr.close();
            return amostra;

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Verifica se um arquivo existe.
     *
     * @param url caminho do arquivo
     * @return true se existir, false caso contrário
     */
    public boolean exist(String url) {
        File file = new File(url);
        return file.exists();
    }
}
