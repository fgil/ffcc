/*
 * Pilha.java
 *
 * Created on 26 de Novembro de 2007, 09:52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package horae.util;

import java.nio.BufferUnderflowException;

/**
 *
 * @author fernando
 */
public class Pilha {
    
    /** Creates a new instance of Pilha */
    public Pilha() {
        topo = null;
        tamanho = 0;
    }
    
    protected PilhaItem topo;
    protected int tamanho;
    
  
    public void adicionar(Object novoObjeto) {
        PilhaItem novo = new PilhaItem(novoObjeto);

        if(topo == null){
            topo = novo;
        } else {
            novo.setAnterior(topo);
            topo = novo;
        }
        
        tamanho++;
    }
    
    public Object remover() {        
        PilhaItem removido = topo;
        
        if(topo != null){
            topo = topo.getProximo();
        }
        else {
            throw new BufferUnderflowException();
        }
        
        tamanho--;
        
        return removido.getItem();
    }
    
    public Object consultar(int n){
        PilhaItem retorno = topo;
        
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