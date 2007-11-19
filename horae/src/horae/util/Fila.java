/*
 * Fila.java
 *
 * Created on November 2, 2007, 3:47 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package horae.util;

import java.nio.BufferUnderflowException;

/**
 *
 * @author Fernando
 */
public class Fila {
    protected FilaItem primeiro;
    protected FilaItem ultimo;
    protected int tamanho;
    
    /** Creates a new instance of Fila */
    public Fila() {
        primeiro = null;
        ultimo = null;
        tamanho = 0;
    }
    
    public void adicionar(Object novoObjeto) {
        FilaItem novo = new FilaItem(novoObjeto);
        
        if(primeiro == null){
            primeiro = novo;
        }
        
        if(ultimo != null){
            ultimo.setProximo(novo);
        }
        
        ultimo = novo;
        
        tamanho++;
    }
    
    public Object remover() {        
        FilaItem removido = primeiro;
        
        if(primeiro != null){
            primeiro = primeiro.getProximo();
        }
        else {
            throw new BufferUnderflowException();
        }
        
        if(primeiro == null){
            ultimo = null;
        }
        
        tamanho--;
        
        return removido.getItem();
    }
    
    public Object consultar(int n){
        FilaItem retorno = primeiro;
        
        for(int i=0; i<n; i++){
            if(retorno != null){
                retorno = retorno.getProximo();
            }
            else {
                throw new BufferUnderflowException();
            }
        }
        
        return retorno.getItem();
    }
    
    public int getTamanho(){
        return tamanho;
    }
}
