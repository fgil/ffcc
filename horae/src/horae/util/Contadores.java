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
    
    private int eacont;
    
    /** Creates a new instance of Contadores */
    public Contadores() {
    }

    public int getEacont() {
        return eacont;
    }
    
    public int nextEacont() {
        eacont++;
        return eacont;
    }
}
