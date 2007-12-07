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
    private int cocont = 0;
    private int ifcont = 0;
    private int wcont = 0;
    private int fcont = 0;
    
    /** Creates a new instance of Contadores */
    private Contadores() {
        eacont = 0;
        ifcont = 0;
        wcont = 0;
        cocont = 0;
        fcont = 0;
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
        return "EA_" + eacont;
    }
    
    public String nextEacont() {
        eacont++;
        return "EA_" + eacont;
    }
    
    public String getIfcont() {
        return "IF_" + ifcont;
    }
    
    public String nextIfcont() {
        ifcont++;
        return "IF_" + ifcont;
    }
    
    public String getWcont() {
        return "W_" + wcont;
    }
    
    public String nextWcont() {
        wcont++;
        return "W_" + wcont;
    }
    
    public String getCocont() {
        return "CO_" + cocont;
    }
    
    public String nextCocont() {
        cocont++;
        return "CO_" + cocont;
    }
    
    public String getFcont() {
        return "FU_" + fcont;
    }
    
    public String nextFcont() {
        fcont++;
        return "FU_" + fcont;
    }
}
