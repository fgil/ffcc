/*
 * PilhaIF.java
 *
 * Created on December 1, 2007, 10:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package horae.semantico;

import horae.util.BlocoIf;
import horae.util.Operador;
import horae.util.Operando;
import horae.util.Pilha;

/**
 *
 * @author Fernando
 */
public class PilhaIF {
    
    /* Mais uma clase singleton */
    private static PilhaIF aPilhaIF;
    
    public Pilha pIFs;
    
    public static PilhaIF getInstance(){
       if (aPilhaIF == null) {
            aPilhaIF = new PilhaIF();
        }
        return aPilhaIF;
    }
    
    /** Creates a new instance of PilhaEA */
    private PilhaIF() {
        pIFs = new Pilha();
    }
    
    public void limpaPilha() {//Suposto de não ser usada
        pIFs = new Pilha();
    }

    public void adicionaIF(BlocoIf ifThenElse){
        pIFs.adicionar(ifThenElse);
    }
    
    public BlocoIf getTopo() {
        if (pIFs.getTamanho() != 0) {
            return (BlocoIf) pIFs.consultar(0);
        } else {
            return null;
        }
    }
    
    public BlocoIf removeIF(){
        return (BlocoIf) pIFs.remover();
    }
    
}
