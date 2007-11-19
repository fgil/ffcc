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
    
    /** Creates a new instance of Declaracao
     * Lembrando:
     * setTransicao(int estado, int indiceRota, String tokenEsperado,
     *       int proximoEstado, int proximaMaquina, boolean consome){
     */
    public Declaracao(Fila filaLida) {
        this.filaLida = filaLida;
        maquina = new Maquina(3);
        estadoAtual = 0;
        consome = false;

        //Cria transicoes do estado 0
        maquina.criaTransicoes(0,3);
        maquina.setTransicao(0,0,"INT",1,0,false);
        maquina.setTransicao(0,1,"CHAR",1,0,false);
        maquina.setTransicao(0,2,"BOOLEAN",1,0,false);

        
        maquina.criaTransicoes(1,1);
        maquina.setTransicao(1,0,"identificador",2,0,false);
        
        
        maquina.criaTransicoes(2,2);
        maquina.setTransicao(2,0,";",3,0,true);
        maquina.setTransicao(2,1,"[",4,0,false);
        
    }
    
    public int processaToken(Token token) {
        System.out.println(filaLida.getTamanho());
        Transicao transicao =
                maquina.estados[estadoAtual].proximoEstado(token.getType());
        System.out.println("Maquina - " + token.getType() + " - Estado Atual: " + estadoAtual + 
                " Proximo Estado: " + transicao.proximoEstado);
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
    }

}
