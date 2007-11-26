/*
 * PilhaItem.java
 *
 * Created on 26 de Novembro de 2007, 09:52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package horae.util;

/**
 *
 * @author fernando
 */
public class PilhaItem {
    
    private PilhaItem anterior;
    private Object item;
    /**
     * Creates a new instance of PilhaItem
     */
    public PilhaItem(Object item) {
        this.item = item;
        anterior = null;
    }

    public PilhaItem getProximo() {
        return anterior;
    }

    public Object  getItem() {
        return item;
    }
    
    public void setAnterior(PilhaItem anterior) {
        this.anterior = anterior;
    }
    
}
