package Comun;
import java.io.File;

 /*===================================================================================================
 *  Classe que representa um nó (célula) de uma lista encadeada de arquivos.
 *  Cada célula guarda uma referência para um arquivo e para a próxima célula da lista.
 ====================================================================================================*/

public class CelulaNew {
    File file; // Arquivos armazenado nesta célula
        CelulaNew next; //referencia para a proxima célula da lista

        /*
         * Construtor
         * Cria uma célula vazia.
         */
        public CelulaNew() {
        }

        /*
         * Construtor
         * Cria uma célula contento o arquivo informado
         * @param file arquivo que será armazenado nesta célula
         */
        public CelulaNew(File file) {
            this.file = file;
        }

}
