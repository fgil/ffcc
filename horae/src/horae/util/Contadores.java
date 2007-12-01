/*
 * Contadores.java
 *
 * Created on November 29, 2007, 5:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package horae.util;

/**
 *
 * @author Fernando
 */
public class Contadores {
    
    private int eacont = 0;
    
    /** Creates a new instance of Contadores */
    private Contadores() {
        eacont = 0;
    }

    private static Contadores oContador;
    
    public Pilha pOperandos;
    public Pilha pOperadores;
    
    public static Contadores getInstance(){
       if (oContador == null) {
            oContador = new Contadores();
        }
        return oContador;
    }

    
    
    public String getEacont() {
        return eacont + "_EA";
    }
    
    public String nextEacont() {
        eacont++;
        return eacont + "_EA";
    }
}
