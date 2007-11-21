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
    public int estadoAceito = 3;
    public boolean consome;
    public Token restoToken;
    private String maquinaNome = "Declaração";
    
    /** Creates a new instance of Declaracao
     * Lembrando:
     * setTransicao(int estado, int indiceRota, String tokenEsperado,
     *       int proximoEstado, int proximaMaquina, boolean consome){
     */
    public Declaracao(Fila filaLida) {
        this.filaLida = filaLida;
        maquina = new Maquina(9);
        estadoAtual = 0;
        consome = false;

        //Cria transicoes do estado 0
        maquina.criaTransicoes(0,3);
        maquina.setTransicao(0,0,"INT",1,0,false);
        maquina.setTransicao(0,1,"CHAR",1,0,false);
        maquina.setTransicao(0,2,"BOOLEAN",1,0,false);

        
        maquina.criaTransicoes(1,1);
        maquina.setTransicao(1,0,"identificador",2,0,false);
        
        
        maquina.criaTransicoes(2,4);
        maquina.setTransicao(2,0,";",3,0,true);
        maquina.setTransicao(2,1,",",3,0,true);
        maquina.setTransicao(2,3,")",3,0,true);
        maquina.setTransicao(2,2,"[",4,0,false);
        
        maquina.criaTransicoes(3,0);
//        maquina.setTransicao(2,0,";",3,0,true);
//        maquina.setTransicao(2,1,"[",4,0,false);
        
        maquina.criaTransicoes(4,1);
        maquina.setTransicao(4,0,"numero",5,0,false);

        maquina.criaTransicoes(5,1);
        maquina.setTransicao(5,0,"]",6,0,false);
        
        maquina.criaTransicoes(6,4);
        maquina.setTransicao(6,0,";",3,0,true);
        maquina.setTransicao(6,1,",",3,0,true);
        maquina.setTransicao(6,3,")",3,0,true);
        maquina.setTransicao(6,2,"[",7,0,false);
        
        maquina.criaTransicoes(7,1);
        maquina.setTransicao(7,0,"numero",8,0,false);
        
       maquina.criaTransicoes(8,1);
        maquina.setTransicao(8,0,"]",3,0,false);
        
    }
    
    public int processaToken(Token token) {
        System.out.println(filaLida.getTamanho());
        try {
        Transicao transicao =
                maquina.estados[estadoAtual].proximoEstado(token.getType());
        System.out.println(maquinaNome + " - " + token.getType() + " - Estado Atual: " + estadoAtual);
        System.out.println("Proximo Estado: " + transicao.proximoEstado);
        
        if (transicao.proximaMaquina > 0) {
//            switch(transicao.proximaMaquina) {
//                case 1:
//                    Declaracao declaracao = new Declaracao(filaLida);
//                    //System.out.println(filaLida.getTamanho());
//                    break;
//                default:
//                    //Ainda nao implementado
//
//            }
        }
        consome = transicao.consome;
        estadoAtual = transicao.proximoEstado;
        
        
        // Aqui deverá verificar se o estado é aceito e se podemos retornar
        if (transicao.proximoEstado == this.estadoAceito) {
            return 1;
        } else {
            return 0;
        }

        } catch(Exception e) {
        System.out.println(maquinaNome + " - " + token.getType() + " - Estado Atual: " + estadoAtual + 
                " Transicao nao encontrada: ");
            
            return 0;

        }
    }

}
