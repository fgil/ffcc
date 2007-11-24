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
    private String maquinaNome = "DeclaracaoFuncao";
    
    /** Creates a new instance of Declaracao
     * Lembrando:
     * setTransicao(int estado, int indiceRota, String tokenEsperado,
     *       int proximoEstado, int proximaMaquina, boolean consome){
     */
    public DeclaracaoFuncao(Fila filaLida) {
        this.filaLida = filaLida;
        maquina = new Maquina(16);
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
        maquina.setTransicao(6,0,",",15,0,false);
        maquina.setTransicao(6,1,")",5,0,false);
        
        maquina.criaTransicoes(7,9);
        maquina.setTransicao(7,0,"RETURN",11,0,false);
        maquina.setTransicao(7,1,"INT",8,maquina.A_Declaracao,true);
        maquina.setTransicao(7,2,"CHAR",8,maquina.A_Declaracao,true);
        maquina.setTransicao(7,3,"BOOLEAN",8,maquina.A_Declaracao,true);
        maquina.setTransicao(7,4,"identificador",10,maquina.A_Comando,true);
        maquina.setTransicao(7,5,"INPUT",10,maquina.A_Comando,true);
        maquina.setTransicao(7,6,"OUTPUT",10,maquina.A_Comando,true);
        maquina.setTransicao(7,7,"IF",10,maquina.A_Comando,true);
        maquina.setTransicao(7,8,"WHILE",10,maquina.A_Comando,true);
        
        
        maquina.criaTransicoes(8,1);
        maquina.setTransicao(8,0,";",7,0,false);
        
        maquina.criaTransicoes(9,6);
        maquina.setTransicao(9,0,"RETURN",11,0,false);
        maquina.setTransicao(9,1,"identificador",10,maquina.A_Comando,true);
        maquina.setTransicao(9,2,"INPUT",10,maquina.A_Comando,true);
        maquina.setTransicao(9,3,"OUTPUT",10,maquina.A_Comando,true);
        maquina.setTransicao(9,4,"IF",10,maquina.A_Comando,true);
        maquina.setTransicao(9,5,"WHILE",10,maquina.A_Comando,true);
        
        maquina.criaTransicoes(10,1);
        maquina.setTransicao(10,0,";",9,0,false);
        
        
        maquina.criaTransicoes(11,6);
        //maquina.setTransicao(11,0,"EXP",12,0,false);//Aqui nao implementado
        maquina.setTransicao(11,0,"-",12,1,true);
        maquina.setTransicao(11,1,"(",12,maquina.A_Expressao,true);
        maquina.setTransicao(11,2,"identificador",12,maquina.A_Expressao,true);
        maquina.setTransicao(11,3,"TRUE",12,maquina.A_Expressao,true);
        maquina.setTransicao(11,4,"FALSE",12,maquina.A_Expressao,true);
        maquina.setTransicao(11,5,"NUMERO",12,maquina.A_Expressao,true);
        
        maquina.criaTransicoes(12,1);
        maquina.setTransicao(12,0,";",13,0,false);
        
        maquina.criaTransicoes(13,1);
        maquina.setTransicao(13,0,"}",14,0,false);
        
        maquina.criaTransicoes(15,3);
        maquina.setTransicao(15,0,"INT",6,maquina.A_Declaracao,true);
        maquina.setTransicao(15,1,"CHAR",6,maquina.A_Declaracao,true);
        maquina.setTransicao(15,2,"BOOLEAN",6,maquina.A_Declaracao,true);


        
        
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
        System.out.println(maquinaNome + " - " + token.getType() + " - Estado Atual: " + estadoAtual);
        System.out.println("Proximo Estado: " + transicao.proximoEstado);
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
                 
                    consome = maquinaComando.consome;
                    proximoToken = maquinaComando.restoToken;
                    break;
                    
                case 5://Maquina Expressao
                    Expressao maquinaExpressao = new Expressao(filaLida);
                    System.out.println(filaLida.getTamanho());
                    //Aqui ve se precisa mandar o ultimo token lido ou se vai pro proximo
                    if (transicao.consome) {
                        proximoToken = token;                        
                    } else {
                        proximoToken = (Token) filaLida.remover();
                    }
                    while(maquinaExpressao.processaToken(proximoToken) == 0){
                        if (maquinaExpressao.consome) {
                            proximoToken = maquinaExpressao.restoToken;                        
                        } else {
                            proximoToken = (Token) filaLida.remover();
                        }
                    }
                    //Aqui ve se precisa mandar o ultimo token lido ou se vai pro proximo
                    //Como anteriormente, mas agora ao sair do loop
//                        if (maquinaExpressao.consome) {
//                            proximoToken = proximoToken;
//                            System.out.println("Proximo token (n): " + proximoToken.getType());
//                        } else {
                           proximoToken = maquinaExpressao.restoToken;
//                            //System.out.println("Proximo token (s): " + proximoToken.getType());
//                        }                    
                    break;
                default:
                    //Ainda nao implementado

            }
        }
        consome = transicao.consome;
        estadoAtual = transicao.proximoEstado;
        restoToken = proximoToken;
        
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
