/*
 * BlocoIf.java
 *
 * Created on December 1, 2007, 11:42 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package horae.util;

/**
 *
 * @author Fernando
 */
public class BlocoIf {
    
    public String labelElse;
    public String labelEndif;
    
    /**
     * Creates a new instance of BlocoIf
     */
    public BlocoIf(String labelElse, String labelEndif) {
        this.labelElse = labelElse;
        this.labelEndif = labelEndif;
    }
    
}
