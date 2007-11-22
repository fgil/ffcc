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
public class Comando {
    
    public Fila filaLida;
    private Maquina maquina;
    public int estadoAtual;
    public int estadoAceito = 10;
    public boolean consome;
    public Token restoToken;
    private String maquinaNome = "Comando";
    
    /** Creates a new instance of Expressao */
    public Comando(Fila filaLida) {
                this.filaLida = filaLida;
        maquina = new Maquina(22);
        estadoAtual = 0;
        consome = false;

        //Cria transicoes do estado 0
        maquina.criaTransicoes(0,5);
        maquina.setTransicao(0,0,"identificador",1,0,false);
        maquina.setTransicao(0,1,"INPUT",11,0,false);
        maquina.setTransicao(0,2,"OUTPUT",12,0,false);
        maquina.setTransicao(0,3,"IF",14,0,false);
        maquina.setTransicao(0,4,"WHILE",15,0,false);


        maquina.criaTransicoes(1,2);
        maquina.setTransicao(1,0,"[",3,0,false);
        maquina.setTransicao(1,1,"=",8,0,false);

        
        maquina.criaTransicoes(2,1);
        maquina.setTransicao(2,0,"=",8,0,false);
        
        maquina.criaTransicoes(3,6);
        maquina.setTransicao(3,0,"(",4,maquina.A_Expressao,true);
        maquina.setTransicao(3,1,"identificador",4,maquina.A_Expressao,true);
        maquina.setTransicao(3,2,"TRUE",4,maquina.A_Expressao,true);
        maquina.setTransicao(3,3,"FALSE",4,maquina.A_Expressao,true);
        maquina.setTransicao(3,4,"NUMERO",4,maquina.A_Expressao,true);
        maquina.setTransicao(3,5,"-",4,maquina.A_Expressao,true);        
        
        maquina.criaTransicoes(4,5);
        maquina.setTransicao(4,0,"]",5,0,false);
        
        maquina.criaTransicoes(5,2);
        maquina.setTransicao(5,0,"=",8,0,false);
        maquina.setTransicao(5,1,"[",6,0,false);
        
        maquina.criaTransicoes(6,6);
        maquina.setTransicao(6,0,"(",7,maquina.A_Expressao,true);
        maquina.setTransicao(6,1,"identificador",7,maquina.A_Expressao,true);
        maquina.setTransicao(6,2,"TRUE",7,maquina.A_Expressao,true);
        maquina.setTransicao(6,3,"FALSE",7,maquina.A_Expressao,true);
        maquina.setTransicao(6,4,"NUMERO",7,maquina.A_Expressao,true);
        maquina.setTransicao(6,5,"-",7,maquina.A_Expressao,true);        
        
        maquina.criaTransicoes(7,1);
        maquina.setTransicao(7,0,"]",2,0,false);
        
        maquina.criaTransicoes(8,6);
        maquina.setTransicao(8,0,"(",9,maquina.A_Expressao,true);
        maquina.setTransicao(8,1,"identificador",9,maquina.A_Expressao,true);
        maquina.setTransicao(8,2,"TRUE",9,maquina.A_Expressao,true);
        maquina.setTransicao(8,3,"FALSE",9,maquina.A_Expressao,true);
        maquina.setTransicao(8,4,"NUMERO",9,maquina.A_Expressao,true);
        maquina.setTransicao(8,5,"-",9,maquina.A_Expressao,true);        

        maquina.criaTransicoes(9,1);
        maquina.setTransicao(9,0,";",10,0,true);
        
        maquina.criaTransicoes(10,1);//Vazio (Aceitaçao)
//        maquina.setTransicao(10,0,"(",11,maquina.A_Expressao,true);
//        maquina.setTransicao(10,1,"identificador",11,maquina.A_Expressao,true);
//        maquina.setTransicao(10,2,"TRUE",11,maquina.A_Expressao,true);
//        maquina.setTransicao(10,3,"FALSE",11,maquina.A_Expressao,true);
//        maquina.setTransicao(10,4,"NUMERO",11,maquina.A_Expressao,true);
        
        maquina.criaTransicoes(11,6);
        maquina.setTransicao(11,0,"(",13,maquina.A_Expressao,true);
        maquina.setTransicao(11,1,"identificador",13,maquina.A_Expressao,true);
        maquina.setTransicao(11,2,"TRUE",13,maquina.A_Expressao,true);
        maquina.setTransicao(11,3,"FALSE",13,maquina.A_Expressao,true);
        maquina.setTransicao(11,4,"NUMERO",13,maquina.A_Expressao,true);
        maquina.setTransicao(11,5,"-",13,maquina.A_Expressao,true);        
        
        maquina.criaTransicoes(12,6);
        maquina.setTransicao(12,0,"(",13,maquina.A_Expressao,true);
        maquina.setTransicao(12,1,"identificador",13,maquina.A_Expressao,true);
        maquina.setTransicao(12,2,"TRUE",13,maquina.A_Expressao,true);
        maquina.setTransicao(12,3,"FALSE",13,maquina.A_Expressao,true);
        maquina.setTransicao(12,4,"NUMERO",13,maquina.A_Expressao,true);
        maquina.setTransicao(12,5,"-",13,maquina.A_Expressao,true);        
        
        maquina.criaTransicoes(13,1);
        maquina.setTransicao(13,0,";",10,0,true);        
        
//        maquina.criaTransicoes(14,2);
//        maquina.setTransicao(14,0,",",13,0,false);
//        maquina.setTransicao(14,0,")",15,0,false);
//        
//        maquina.criaTransicoes(15,6);
//        maquina.setTransicao(15,0,";",3,0,true);
//        maquina.setTransicao(15,1,")",3,0,true);
//        maquina.setTransicao(15,2,"+",10,0,false);
//        maquina.setTransicao(15,3,"-",10,0,false);
//        maquina.setTransicao(15,4,"/",10,0,false);
//        maquina.setTransicao(15,5,"*",10,0,false);
//        
        maquina.criaTransicoes(16,2);
        maquina.setTransicao(16,0,"[",17,0,false);
        maquina.setTransicao(16,1,";",10,0,true);
        
//        
        maquina.criaTransicoes(17,4);
        maquina.setTransicao(17,0,"(",18,maquina.A_Expressao,true);
        maquina.setTransicao(17,1,"identificador",18,maquina.A_Expressao,true);
        maquina.setTransicao(17,2,"NUMERO",18,maquina.A_Expressao,true);
        maquina.setTransicao(17,3,"-",18,maquina.A_Expressao,true);        

        maquina.criaTransicoes(18,1);
        maquina.setTransicao(18,0,"]",19,0,false);
        
        maquina.criaTransicoes(19,2);
        maquina.setTransicao(19,0,"[",20,0,false);
        maquina.setTransicao(19,1,";",10,0,true);

        maquina.criaTransicoes(20,4);
        maquina.setTransicao(20,0,"(",21,maquina.A_Expressao,true);
        maquina.setTransicao(20,1,"identificador",21,maquina.A_Expressao,true);
        maquina.setTransicao(20,2,"NUMERO",21,maquina.A_Expressao,true);
        maquina.setTransicao(20,3,"-",21,maquina.A_Expressao,true);        

        maquina.criaTransicoes(21,1);
        maquina.setTransicao(21,0,"]",10,0,false);

//        
//        maquina.criaTransicoes(18,7);
//        maquina.setTransicao(18,0,";",3,0,true);
//        maquina.setTransicao(18,1,")",3,0,true);
//        maquina.setTransicao(18,2,"+",10,0,false);
//        maquina.setTransicao(18,3,"-",10,0,false);
//        maquina.setTransicao(18,4,"/",10,0,false);
//        maquina.setTransicao(18,5,"*",10,0,false);
//        maquina.setTransicao(18,6,"[",19,0,false);
//        
//        maquina.criaTransicoes(19,1);
//        maquina.setTransicao(19,0,"NUMERO",20,0,false);
//        
//        maquina.criaTransicoes(20,1);
//        maquina.setTransicao(20,0,"]",21,0,false);
//        
//        maquina.criaTransicoes(21,6);
//        maquina.setTransicao(21,0,";",3,0,true);
//        maquina.setTransicao(21,1,")",3,0,true);
//        maquina.setTransicao(21,2,"+",10,0,false);
//        maquina.setTransicao(21,3,"-",10,0,false);
//        maquina.setTransicao(21,4,"/",10,0,false);
//        maquina.setTransicao(21,5,"*",10,0,false);
        
        
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
