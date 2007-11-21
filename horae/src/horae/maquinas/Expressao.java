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
public class Expressao {
    
    public Fila filaLida;
    private Maquina maquina;
    public int estadoAtual;
    public int estadoAceito = 3;
    public boolean consome;
    public Token restoToken;
    private String maquinaNome = "Expressão";
    
    /** Creates a new instance of Expressao */
    public Expressao(Fila filaLida) {
                this.filaLida = filaLida;
        maquina = new Maquina(14);
        estadoAtual = 0;
        consome = false;
        System.out.println("Nasci");

        //Cria transicoes do estado 0
        maquina.criaTransicoes(0,6);
        maquina.setTransicao(0,0,"-",1,0,false);
        maquina.setTransicao(0,1,"(",4,0,false);
        maquina.setTransicao(0,2,"identificador",7,0,false);
        maquina.setTransicao(0,3,"TRUE",8,0,false);
        maquina.setTransicao(0,4,"FALSE",8,0,false);
        maquina.setTransicao(0,5,"NUMERO",9,0,false);


        maquina.criaTransicoes(1,5);
        maquina.setTransicao(1,0,"(",2,maquina.A_Expressao,true);
        maquina.setTransicao(1,1,"identificador",2,maquina.A_Expressao,true);
        maquina.setTransicao(1,2,"TRUE",2,maquina.A_Expressao,true);
        maquina.setTransicao(1,3,"FALSE",2,maquina.A_Expressao,true);
        maquina.setTransicao(1,4,"NUMERO",2,maquina.A_Expressao,true);

        
        maquina.criaTransicoes(2,5);
        maquina.setTransicao(2,0,";",3,0,false);
        maquina.setTransicao(2,1,"+",10,0,false);
        maquina.setTransicao(2,2,"-",10,0,false);
        maquina.setTransicao(2,3,"*",10,0,false);
        maquina.setTransicao(2,4,"/",10,0,false);
        
        maquina.criaTransicoes(3,1);
        maquina.setTransicao(3,0,"(",4,0,false);
        
        maquina.criaTransicoes(4,5);
        maquina.setTransicao(4,0,"(",5,maquina.A_Expressao,true);
        maquina.setTransicao(4,1,"identificador",5,maquina.A_Expressao,true);
        maquina.setTransicao(4,2,"TRUE",5,maquina.A_Expressao,true);
        maquina.setTransicao(4,3,"FALSE",5,maquina.A_Expressao,true);
        maquina.setTransicao(4,4,"NUMERO",5,maquina.A_Expressao,true);
        
        maquina.criaTransicoes(5,1);
        maquina.setTransicao(5,0,")",6,0,false);
        
        maquina.criaTransicoes(6,5);
        maquina.setTransicao(6,0,";",3,0,false);
        maquina.setTransicao(6,1,"+",10,0,false);
        maquina.setTransicao(6,2,"-",10,0,false);
        maquina.setTransicao(6,3,"*",10,0,false);
        maquina.setTransicao(6,4,"/",10,0,false);
        
        
        maquina.criaTransicoes(7,5);//esse vai ser o mais chato
        maquina.setTransicao(7,1,"INT",12,maquina.A_Declaracao,true);
        maquina.setTransicao(7,2,"CHAR",12,maquina.A_Declaracao,true);
        maquina.setTransicao(7,3,"BOOLEAN",12,maquina.A_Declaracao,true);
        maquina.setTransicao(7,0,"COMMANDO",9,maquina.A_Comando,false);//Aqui nao implementado
        maquina.setTransicao(7,4,"RETURN",8,0,false);
        
        maquina.criaTransicoes(8,1);
        maquina.setTransicao(8,0,";",3,0,true);

        maquina.criaTransicoes(9,5);
        maquina.setTransicao(9,0,";",3,0,true);
        maquina.setTransicao(9,1,"+",10,0,false);
        maquina.setTransicao(9,2,"-",10,0,false);
        maquina.setTransicao(9,3,"*",10,0,false);
        maquina.setTransicao(9,4,"/",10,0,false);
        
        maquina.criaTransicoes(10,5);
        maquina.setTransicao(10,0,"(",11,maquina.A_Expressao,true);
        maquina.setTransicao(10,1,"identificador",11,maquina.A_Expressao,true);
        maquina.setTransicao(10,2,"TRUE",11,maquina.A_Expressao,true);
        maquina.setTransicao(10,3,"FALSE",11,maquina.A_Expressao,true);
        maquina.setTransicao(10,4,"NUMERO",11,maquina.A_Expressao,true);
        
        maquina.criaTransicoes(11,1);
        maquina.setTransicao(11,0,";",3,0,true);
        
//        maquina.criaTransicoes(12,1);
//        maquina.setTransicao(12,0,";",7,0,false);
//
//        maquina.criaTransicoes(13,2);
//        maquina.setTransicao(13,0,"COMMANDO",9,maquina.A_Comando,false);//Aqui nao implementado
//        maquina.setTransicao(13,1,"RETURN",8,0,false);
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
                    //Aqui ve se precisa mandar o ultimo token lido ou se vai pro proximo
                    //Como anteriormente, mas agora ao sair do loop
//                    if (maquinaExpressao.consome) {
//                        //proximoToken = proximoToken;
//                        System.out.println("Proximo token (n): " + proximoToken.getType());
//                    } else {
                        consome = maquinaExpressao.consome;
                        proximoToken = maquinaExpressao.restoToken;
//                        //System.out.println("Proximo token (s): " + proximoToken.getType());
//                    }
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
            System.out.println("Morri");
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
