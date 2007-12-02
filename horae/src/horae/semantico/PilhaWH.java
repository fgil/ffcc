/*
 * PilhaWH.java
 *
 * Created on December 2, 2007, 2:20 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package horae.semantico;

import horae.util.BlocoIf;
import horae.util.BlocoWhile;
import horae.util.Pilha;

/**
 *
 * @author Fernando
 */
public class PilhaWH {
    
    /* Mais uma clase singleton */
    private static PilhaWH aPilhaWH;
    
    public Pilha pWHILEs;
    
    public static PilhaWH getInstance(){
       if (aPilhaWH == null) {
            aPilhaWH = new PilhaWH();
        }
        return aPilhaWH;
    }
    
    /** Creates a new instance of PilhaEA */
    private PilhaWH() {
        pWHILEs = new Pilha();
    }
    
    public void limpaPilha() {//Suposto de não ser usada
        pWHILEs = new Pilha();
    }

    public void adicionaWhile(BlocoWhile bloco){
        pWHILEs.adicionar(bloco);
    }
    
    public BlocoWhile getTopo() {
        if (pWHILEs.getTamanho() != 0) {
            return (BlocoWhile) pWHILEs.consultar(0);
        } else {
            return null;
        }
    }
    
    public BlocoWhile removeWhile(){
        return (BlocoWhile) pWHILEs.remover();
    }
    
}
