/*
 * Maquina.java
 *
 * Created on November 3, 2007, 3:19 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package horae.util;

/**
 *
 * @author Fernando
 */
public class Maquina {
    public Estado[] estados;
    /** Creates a new instance of Maquina */
    public Maquina(int qtdEstados) {
        this.estados = new Estado[qtdEstados];
    }
    
    
    //Metodo usado para montar a maquina
    public void setTransicao(int estado, int indiceRota, String tokenEsperado, int proximoEstado, String proximaMaquina){
        this.estados[estado].setTransicao(indiceRota, tokenEsperado, proximoEstado, proximaMaquina);
    }

    public void criaTransicoes (int estado, int qtdTransicoes) {
        this.estados[estado].criaTransicoes(qtdTransicoes);
    }
    
}
