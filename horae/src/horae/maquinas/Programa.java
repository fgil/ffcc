/*
 * Programa.java
 *
 * Created on November 3, 2007, 9:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package horae.maquinas;

import horae.Token;
import horae.util.*;
import semantico.Semantico;

/**
 *
 * @author Fernando
 */
public class Programa {

    public Fila filaLida;
    private Maquina maquina;
    public int estadoAtual;
    private String maquinaNome = "Programa";
    private Semantico aSemantica;
    
    /** Creates a new instance of Programa */
    public Programa(Fila filaLida) {
        this.filaLida = filaLida;
        maquina = new Maquina(9);
        estadoAtual = 0;
        
        //Cria transicoes do estado 0
        maquina.criaTransicoes(0,1);
        maquina.setTransicao(0,0,"START",1,0,false);

        //Cria transicoes do estado 1
        maquina.criaTransicoes(1,10);
        maquina.setTransicao(1,0,"INT",2,maquina.A_Declaracao,true);
        maquina.setTransicao(1,1,"CHAR",2,maquina.A_Declaracao,true);
        maquina.setTransicao(1,2,"BOOLEAN",2,maquina.A_Declaracao,true);
        maquina.setTransicao(1,3,"FUNCAO",4,maquina.A_DeclaracaoFuncao,true);
        maquina.setTransicao(1,4,"identificador",6,maquina.A_Comando,true);
        maquina.setTransicao(1,5,"INPUT",6,maquina.A_Comando,true);
        maquina.setTransicao(1,6,"OUTPUT",6,maquina.A_Comando,true);
        maquina.setTransicao(1,7,"IF",6,maquina.A_Comando,true);
        maquina.setTransicao(1,8,"WHILE",6,maquina.A_Comando,true);
        maquina.setTransicao(1,9,"END",7,0,false);

        //Cria transicoes do estado 2
        maquina.criaTransicoes(2,1);
        maquina.setTransicao(2,0,";",1,0,false);

        //Cria transicoes do estado 3
        maquina.criaTransicoes(3,7);
        maquina.setTransicao(3,0,"FUNCAO",4,maquina.A_DeclaracaoFuncao,true);
        maquina.setTransicao(3,1,"identificador",6,maquina.A_Comando,true);
        maquina.setTransicao(3,2,"INPUT",6,maquina.A_Comando,true);
        maquina.setTransicao(3,3,"OUTPUT",6,maquina.A_Comando,true);
        maquina.setTransicao(3,4,"IF",6,maquina.A_Comando,true);
        maquina.setTransicao(3,5,"WHILE",6,maquina.A_Comando,true);
        maquina.setTransicao(3,6,"END",7,0,false);

        //Cria transicoes do estado 4
        maquina.criaTransicoes(4,1);
        maquina.setTransicao(4,0,";",3,0,false);

        //Cria transicoes do estado 5
        maquina.criaTransicoes(5,6);
        maquina.setTransicao(5,0,"identificador",6,maquina.A_Comando,true);
        maquina.setTransicao(5,1,"INPUT",6,maquina.A_Comando,true);
        maquina.setTransicao(5,2,"OUTPUT",6,maquina.A_Comando,true);
        maquina.setTransicao(5,3,"IF",6,maquina.A_Comando,true);
        maquina.setTransicao(5,4,"WHILE",6,maquina.A_Comando,true);
        maquina.setTransicao(5,5,"END",7,0,false);

        //Cria transicoes do estado 6
        maquina.criaTransicoes(6,1);
        maquina.setTransicao(6,0,";",5,0,false);
        
        //Cria transicoes do estado 7
        maquina.criaTransicoes(7,1);//Nao sei o que fazer aqui
        
    }
 
    public Token processaToken(Token token) {
        //System.out.println(filaLida.getTamanho());
        Transicao transicao =
                maquina.estados[estadoAtual].proximoEstado(token.getType());
        System.out.println(maquinaNome + " - " + token.getType() + " - Estado Atual: " + estadoAtual);
        System.out.println("Proximo Estado: " + transicao.proximoEstado);
        Token proximoToken = null;
        
        analiseSemanticaPre(estadoAtual, transicao.proximoEstado,token);

        if (transicao.proximaMaquina > 0) {
            switch(transicao.proximaMaquina) {

                case 2:
                    Declaracao maquinaDeclaracao = new Declaracao(filaLida);
                    maquinaDeclaracao.escopo = "Programa";
                    //System.out.println(filaLida.getTamanho());
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

                case 3:
                    DeclaracaoFuncao maquinaFuncao = new DeclaracaoFuncao(filaLida);
                    System.out.println(filaLida.getTamanho());
                    //Aqui ve se precisa mandar o ultimo token lido ou se vai pro proximo
                    if (transicao.consome) {
                        proximoToken = token;                        
                    } else {
                        proximoToken = (Token) filaLida.remover();
                    }
                    while(maquinaFuncao.processaToken(proximoToken) == 0){
                        if (maquinaFuncao.consome) {
                            proximoToken = maquinaFuncao.restoToken;//descobri agora que o esquema eh mandar o resto token
                        } else {
                            proximoToken = (Token) filaLida.remover();
                        }
                    }
                    //Aqui ve se precisa mandar o ultimo token lido ou se vai pro proximo
                    //Como anteriormente, mas agora ao sair do loop
                        if (maquinaFuncao.consome) {
                            //proximoToken = proximoToken;
                            System.out.println("Proximo token (n): " + proximoToken.getType());
                        } else {
                            proximoToken = null;
                            //System.out.println("Proximo token (s): " + proximoToken.getType());
                        }                    
                    break;

                case 4://Maquina Comando
                    Comando maquinaComando = new Comando(filaLida);
                    System.out.println(filaLida.getTamanho());
                    //Aqui ve se precisa mandar o ultimo token lido ou se vai pro proximo
                    if (transicao.consome) {
                        proximoToken = token;                        
                    } else {
                        proximoToken = (Token) filaLida.remover();
                    }
                    while(maquinaComando.processaToken(proximoToken) == 0){
                        if (maquinaComando.consome) {
                            proximoToken = maquinaComando.restoToken;                          
                        } else {
                            proximoToken = (Token) filaLida.remover();
                        }
                    }
                        if(maquinaComando.consome) {
                            proximoToken = maquinaComando.restoToken;
                        }
                    break;

                default:
                    proximoToken = null;
                    //Ainda nao implementado
                
            }
        }
        
        analiseSemanticaPos(estadoAtual, transicao.proximoEstado,token);
        //Seta o estado de retorno
        estadoAtual = transicao.proximoEstado;
        return proximoToken;
        }
    
    
    
    private void analiseSemanticaPos(int estadoAtual, int proximoEstado,
            Token token){
        if (estadoAtual == 0) {
            if (proximoEstado == 1) {
                aSemantica = Semantico.getInstance("fonte.horae");
            }
            
        } else if (estadoAtual == 1) {
            if (proximoEstado == 7) {
                aSemantica.closeFile();
            }
        } else if (estadoAtual == 3) {
            if (proximoEstado == 7) {
                aSemantica.closeFile();
            }
        } else if (estadoAtual == 5) {
            if (proximoEstado == 7) {
                aSemantica.closeFile();
            }
        }
    }
    
    
   private void analiseSemanticaPre(int estadoAtual, int proximoEstado,
            Token token){
       //Pro Enquanto Nada
    }
   
   
}
