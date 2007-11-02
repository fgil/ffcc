/*
 * Fila.java
 *
 * Created on November 2, 2007, 3:47 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package horae.util;

/**
 *
 * @author Fernando
 */
public class Fila {
    protected Object[] queue;
    protected int tamanho;
    /** Creates a new instance of Fila */
    public Fila() {
        tamanho = 0;
        queue = new Object[tamanho];
    }
    
    public void adicionar(Object novoObjeto) {
        tamanho++;
        Object[] newQueue = new Object[tamanho];
        for (int i=0;i<tamanho-1;i++) {
            newQueue[i] = this.queue[i];
        }
        newQueue[tamanho-1] = novoObjeto;
        this.queue = newQueue;
    }
    
    public Object remover() {
        tamanho--;
        Object[] newQueue = new Object[tamanho];
        for (int i=1;i<tamanho-1;i++) {
            newQueue[i] = this.queue[i+1];
        }
        this.queue = newQueue;
        return this.queue[0];
    }    
    
    
    
}
