/*
 * Programa.java
 *
 * Created on November 3, 2007, 9:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package horae.maquinas;

import horae.util.*;

/**
 *
 * @author Fernando
 */
public class Programa {

    Maquina maquina;
    /** Creates a new instance of Programa */
    public Programa() {
        maquina = new Maquina(9);
        //Cria transicoes do estado 0
        maquina.criaTransicoes(0,1);
        maquina.setTransicao(0,0,"START",1,"");

        //Cria transicoes do estado 1
        maquina.criaTransicoes(1,4);
        maquina.setTransicao(1,0,"tipo",2,"");
        maquina.setTransicao(1,1,"tipo",2,"");

    }
    
}
