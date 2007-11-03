/*
 * FilaItem.java
 *
 * Created on November 3, 2007, 2:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package horae.util;

/**
 *
 * @author Fernando
 */
public class FilaItem {
    
    private FilaItem proximo;
    private Object item;
    
    /** Creates a new instance of FilaItem */
    public FilaItem(Object item) {
        this.item = item;
        this.proximo = null;
    }
    
    public Object getItem(){
        return item;
    }
    
    public FilaItem getProximo(){
        return proximo;
    }
    
    public void setProximo(FilaItem proximo){
        this.proximo = proximo;
    }
}
