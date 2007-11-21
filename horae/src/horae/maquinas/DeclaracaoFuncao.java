/*
 * DeclaracaoFuncao.java
 *
 * Created on November 20, 2007, 5:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package horae.maquinas;

import horae.Token;
import horae.util.Fila;
import horae.util.Maquina;
import horae.util.Transicao;

/**
 *
 * @author Fernando
 */
public class DeclaracaoFuncao {
    
    public Fila filaLida;
    private Maquina maquina;
    public int estadoAtual;
    public int estadoAceito = 14;
    public boolean consome;
    public Token restoToken;
    
    /** Creates a new instance of Declaracao
     * Lembrando:
     * setTransicao(int estado, int indiceRota, String tokenEsperado,
     *       int proximoEstado, int proximaMaquina, boolean consome){
     */
    public DeclaracaoFuncao(Fila filaLida) {
        this.filaLida = filaLida;
        maquina = new Maquina(14);
        estadoAtual = 0;
        consome = false;

        //Cria transicoes do estado 0
        maquina.criaTransicoes(0,1);
        maquina.setTransicao(0,0,"FUNCAO",1,0,false);


        maquina.criaTransicoes(1,3);
        maquina.setTransicao(1,0,"INT",2,0,false);
        maquina.setTransicao(1,1,"CHAR",2,0,false);
        maquina.setTransicao(1,2,"BOOLEAN",2,0,false);

        
        maquina.criaTransicoes(2,1);
        maquina.setTransicao(2,0,"identificador",3,0,false);
        
        maquina.criaTransicoes(3,1);
        maquina.setTransicao(3,0,"(",4,0,false);
        
        maquina.criaTransicoes(4,4);
        maquina.setTransicao(4,0,")",5,0,false);
        maquina.setTransicao(4,1,"INT",6,maquina.A_Declaracao,true);
        maquina.setTransicao(4,2,"CHAR",6,maquina.A_Declaracao,true);
        maquina.setTransicao(4,3,"BOOLEAN",6,maquina.A_Declaracao,true);
        
        maquina.criaTransicoes(5,1);
        maquina.setTransicao(5,0,"{",7,0,false);
        
        maquina.criaTransicoes(6,2);
        maquina.setTransicao(6,0,",",4,0,false);
        maquina.setTransicao(6,1,")",5,0,false);
        
        
        maquina.criaTransicoes(7,5);
        maquina.setTransicao(7,1,"INT",12,maquina.A_Declaracao,true);
        maquina.setTransicao(7,2,"CHAR",12,maquina.A_Declaracao,true);
        maquina.setTransicao(7,3,"BOOLEAN",12,maquina.A_Declaracao,true);
        maquina.setTransicao(7,0,"COMMANDO",9,maquina.A_Comando,false);//Aqui nao implementado
        maquina.setTransicao(7,4,"RETURN",8,0,false);
        
        maquina.criaTransicoes(8,1);
        maquina.setTransicao(8,0,"EXP",10,0,false);//Aqui nao implementado

        maquina.criaTransicoes(9,1);
        maquina.setTransicao(9,0,";",13,0,false);
        
        maquina.criaTransicoes(10,1);
        maquina.setTransicao(10,0,";",11,0,false);
        
        maquina.criaTransicoes(11,1);
        maquina.setTransicao(11,0,"}",12,0,false);
        
        maquina.criaTransicoes(12,1);
        maquina.setTransicao(12,0,";",7,0,false);

        maquina.criaTransicoes(13,2);
        maquina.setTransicao(13,0,"COMMANDO",9,maquina.A_Comando,false);//Aqui nao implementado
        maquina.setTransicao(13,1,"RETURN",8,0,false);
        
//        
//        maquina.criaTransicoes(2,2);
//        maquina.setTransicao(2,0,";",3,0,true);
//        maquina.setTransicao(2,1,"[",4,0,false);
//        
//        maquina.criaTransicoes(3,0);
////        maquina.setTransicao(2,0,";",3,0,true);
////        maquina.setTransicao(2,1,"[",4,0,false);
//        
//
//        maquina.criaTransicoes(5,1);
//        maquina.setTransicao(5,0,"]",6,0,false);
//        
//        maquina.criaTransicoes(6,2);
//        maquina.setTransicao(6,0,";",3,0,true);
//        maquina.setTransicao(6,1,"[",7,0,false);
//        
//        maquina.criaTransicoes(7,1);
//        maquina.setTransicao(7,0,"numero",8,0,false);
//        
//       maquina.criaTransicoes(8,1);
//        maquina.setTransicao(8,0,"]",3,0,false);
        
    }
    
    public int processaToken(Token token) {
        System.out.println(filaLida.getTamanho());
        try {
        Transicao transicao =
                maquina.estados[estadoAtual].proximoEstado(token.getType());
        System.out.println("DeclaracaoFuncao - " + token.getType() + " - Estado Atual: " + estadoAtual + 
                " Proximo Estado: " + transicao.proximoEstado);
        Token proximoToken = null;        
                if (transicao.proximaMaquina > 0) {
            switch(transicao.proximaMaquina) {
                case 2:
                    Declaracao maquinaDeclaracao = new Declaracao(filaLida);
                    System.out.println(filaLida.getTamanho());
                    //Aqui ve se precisa mandar o ultimo token lido ou se vai pro proximo
                    if (transicao.consome) {
                        proximoToken = token;                        
                    } else {
                        proximoToken = (Token) filaLida.remover();
                    }
                    while(maquinaDeclaracao.processaToken(proximoToken) == 0){
                        if (maquinaDeclaracao.consome) {
                            //proximoToken = proximoToken;                        
                        } else {
                            proximoToken = (Token) filaLida.remover();
                        }
                    }
                    //Aqui ve se precisa mandar o ultimo token lido ou se vai pro proximo
                    //Como anteriormente, mas agora ao sair do loop
                        if (maquinaDeclaracao.consome) {
                            //proximoToken = proximoToken;
                            System.out.println("Proximo token (n): " + proximoToken.getType());
                        } else {
                            proximoToken = null;
                            //System.out.println("Proximo token (s): " + proximoToken.getType());
                        }                    
                    break;
                default:
                    //Ainda nao implementado

            }
        }
        consome = transicao.consome;
        estadoAtual = transicao.proximoEstado;
        restoToken = proximoToken;
        
        // Aqui dever� verificar se o estado � aceito e se podemos retornar
        if (transicao.proximoEstado == this.estadoAceito) {
            return 1;
        } else {
            return 0;
        }

        } catch(Exception e) {
        System.out.println("DeclaracaoFuncao - " + token.getType() + " - Estado Atual: " + estadoAtual + 
                " Transicao nao encontrada: ");
            
            return 0;

        }
    }

}