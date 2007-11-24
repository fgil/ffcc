/*
 * Expressao.java
 *
 * Created on 21 de Novembro de 2007, 10:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package horae.maquinas;

import horae.util.*;
import horae.Token;

/**
 *
 * @author fernando
 */
public class ExpressaoAritmetica {
    
    public Fila filaLida;
    private Maquina maquina;
    public int estadoAtual;
    public int estadoAceito = 12;
    public boolean consome;
    public Token restoToken;
    private String maquinaNome = "EAritmetica";
    
    /** Creates a new instance of Expressao */
    public ExpressaoAritmetica(Fila filaLida) {
                this.filaLida = filaLida;
        maquina = new Maquina(39);
        estadoAtual = 0;
        consome = false;

        //Cria transicoes do estado 0
        maquina.criaTransicoes(0,6);
        maquina.setTransicao(0,0,"(",4,0,false);
        maquina.setTransicao(0,1,"identificador",1,maquina.A_Expressao,true);
        maquina.setTransicao(0,2,"NUMERO",1,maquina.A_Expressao,true);
        maquina.setTransicao(0,3,"-",1,maquina.A_Expressao,true);
        maquina.setTransicao(0,4,"TRUE",10,0,false);
        maquina.setTransicao(0,5,"FALSE",11,0,false);

        maquina.criaTransicoes(1,9);
        maquina.setTransicao(1,0,")",12,0,true);
        maquina.setTransicao(1,1,"==",2,0,false);
        maquina.setTransicao(1,2,"<=",2,0,false);
        maquina.setTransicao(1,3,">=",2,0,false);
        maquina.setTransicao(1,4,"!=",2,0,false);
        maquina.setTransicao(1,5,"<",2,0,false);
        maquina.setTransicao(1,6,">",2,0,false);
        maquina.setTransicao(1,7,"OR",0,0,false);
        maquina.setTransicao(1,8,"AND",0,0,false);
        
        maquina.criaTransicoes(2,6);
        maquina.setTransicao(2,0,"(",3,maquina.A_Expressao,true);
        maquina.setTransicao(2,1,"identificador",3,maquina.A_Expressao,true);
        maquina.setTransicao(2,2,"TRUE",3,maquina.A_Expressao,true);
        maquina.setTransicao(2,3,"FALSE",3,maquina.A_Expressao,true);
        maquina.setTransicao(2,4,"NUMERO",3,maquina.A_Expressao,true);
        maquina.setTransicao(2,5,"-",3,maquina.A_Expressao,true);        

        maquina.criaTransicoes(3,3);
        maquina.setTransicao(3,0,")",12,0,true);
        maquina.setTransicao(3,1,"OR",0,0,false);
        maquina.setTransicao(3,2,"AND",0,0,false);
        
        maquina.criaTransicoes(4,7);
        maquina.setTransicao(4,0,"(",5,maquina.A_ExpAritmetica,true);
        maquina.setTransicao(4,1,"identificador",5,maquina.A_ExpAritmetica,true);
        maquina.setTransicao(4,2,"TRUE",5,maquina.A_ExpAritmetica,true);
        maquina.setTransicao(4,3,"FALSE",5,maquina.A_ExpAritmetica,true);
        maquina.setTransicao(4,4,"NUMERO",5,maquina.A_ExpAritmetica,true);
        maquina.setTransicao(4,5,"NOT",5,maquina.A_ExpAritmetica,true);
        maquina.setTransicao(4,6,"-",5,maquina.A_ExpAritmetica,true);  
        
        maquina.criaTransicoes(5,1);
        maquina.setTransicao(5,0,")",6,0,false);
        
        maquina.criaTransicoes(6,3);
        maquina.setTransicao(6,0,")",12,0,true);
        maquina.setTransicao(6,1,"OR",0,0,false);
        maquina.setTransicao(6,2,"AND",0,0,false);

        maquina.criaTransicoes(7,2);
        maquina.setTransicao(7,0,"TRUE",8,0,false);
        maquina.setTransicao(7,1,"FALSE",9,0,false);
        
        maquina.criaTransicoes(8,3);
        maquina.setTransicao(8,0,")",12,0,true);
        maquina.setTransicao(8,1,"OR",0,0,false);
        maquina.setTransicao(8,2,"AND",0,0,false);      

        maquina.criaTransicoes(9,3);
        maquina.setTransicao(9,0,")",12,0,true);
        maquina.setTransicao(9,1,"OR",0,0,false);
        maquina.setTransicao(9,2,"AND",0,0,false);
        
        maquina.criaTransicoes(10,3);
        maquina.setTransicao(10,0,")",12,0,true);
        maquina.setTransicao(10,1,"OR",0,0,false);
        maquina.setTransicao(10,2,"AND",0,0,false);      

        maquina.criaTransicoes(11,3);
        maquina.setTransicao(11,0,")",12,0,true);
        maquina.setTransicao(11,1,"OR",0,0,false);
        maquina.setTransicao(11,2,"AND",0,0,false); 
        

    }
    
        
    public int processaToken(Token token) {
        System.out.println(filaLida.getTamanho());
        try {
        Transicao transicao =
                maquina.estados[estadoAtual].proximoEstado(token.getType());
        System.out.println(maquinaNome + " - " + token.getType() + " - Estado Atual: " + estadoAtual);
        System.out.println("Proximo Estado: " + transicao.proximoEstado);
        
        Token proximoToken = null;
        if (transicao.consome) proximoToken = token;
        
        if (transicao.proximaMaquina > 0) {
            switch(transicao.proximaMaquina) {
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
                        consome = maquinaExpressao.consome;
                        proximoToken = maquinaExpressao.restoToken;
//                      
                break;
                
                case 6://Maquina ExpressaoAritmetica
                    ExpressaoAritmetica maquinaExpAritmetica = new ExpressaoAritmetica(filaLida);
                    System.out.println(filaLida.getTamanho());
                    //Aqui ve se precisa mandar o ultimo token lido ou se vai pro proximo
                    if (transicao.consome) {
                        proximoToken = token;                        
                    } else {
                        proximoToken = (Token) filaLida.remover();
                    }
                    while(maquinaExpAritmetica.processaToken(proximoToken) == 0){
                        if (maquinaExpAritmetica.consome) {
                            proximoToken = maquinaExpAritmetica.restoToken;                          
                        } else {
                            proximoToken = (Token) filaLida.remover();
                        }
                    }
                        consome = maquinaExpAritmetica.consome;
                        proximoToken = maquinaExpAritmetica.restoToken;
//                      
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
