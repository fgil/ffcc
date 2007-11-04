/*
 * Declaracao.java
 *
 * Created on November 4, 2007, 11:51 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package horae.maquinas;

import horae.Token;
import horae.util.*;

/**
 *
 * @author Fernando
 */
public class Declaracao {
    
    public Fila filaLida;
    private Maquina maquina;
    public int estadoAtual;
    public int estadoAceito = 1;
    
    /** Creates a new instance of Declaracao */
    public Declaracao(Fila filaLida) {
        this.filaLida = filaLida;
        maquina = new Maquina(2);
        estadoAtual = 0;

        //Cria transicoes do estado 0
        maquina.criaTransicoes(0,3);
        maquina.setTransicao(0,0,"INT",1,0,true);
        maquina.setTransicao(0,1,"CHAR",1,0,true);
        maquina.setTransicao(0,2,"BOOLEAN",1,0,true);

        
        maquina.criaTransicoes(1,1);
        maquina.setTransicao(1,0,"identificador",2,0,true);
        
    }
    
    public int processaToken(Token token) {
        System.out.println(filaLida.getTamanho());
        Transicao transicao =
                maquina.estados[estadoAtual].proximoEstado(token.getType());
        System.out.println("Estado Atual: " + estadoAtual + 
                " Proximo Estado: " + transicao.proximoEstado);
        if (transicao.proximaMaquina > 0) {
            switch(transicao.proximaMaquina) {
                case 1:
                    Declaracao declaracao = new Declaracao(filaLida);
                    System.out.println(filaLida.getTamanho());
                    break;
                default:
                    //Ainda nao implementado

            }
        }
        if (transicao.proximoEstado == this.estadoAceito) {
            return 1;
        } else {
            return 0;
        }
    }

}
