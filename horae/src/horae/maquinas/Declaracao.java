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
import horae.semantico.Semantico;

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
    private Simbolo simboloTemporario = new Simbolo();
    public String escopo;
    private Semantico aSemantica = Semantico.getInstance("");
    
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
        
        maquina.criaTransicoes(3,0);//Aceitaçao, desnecessario
        
        maquina.criaTransicoes(4,1);
        maquina.setTransicao(4,0,"NUMERO",5,0,false);

        maquina.criaTransicoes(5,1);
        maquina.setTransicao(5,0,"]",6,0,false);
        
        maquina.criaTransicoes(6,4);
        maquina.setTransicao(6,0,";",3,0,true);
        maquina.setTransicao(6,1,",",3,0,true);
        maquina.setTransicao(6,3,")",3,0,true);
        maquina.setTransicao(6,2,"[",7,0,false);
        
        maquina.criaTransicoes(7,1);
        maquina.setTransicao(7,0,"NUMERO",8,0,false);
        
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
        analiseSemanticaPre(estadoAtual, transicao.proximoEstado, token);
        
        if (transicao.proximaMaquina > 0) {//Essa Maquina nao precisa
        }
        
        analiseSemanticaPos(estadoAtual, transicao.proximoEstado, token);
        
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

    private void analiseSemanticaPos(int estadoAtual, int proximoEstado,
            Token token){
        if (estadoAtual == 0) {
            if(proximoEstado == 1) {
                simboloTemporario.escopo = this.escopo;
                simboloTemporario.tipoDeDado = token.getType();
            }
        } else if (estadoAtual == 1) {
            simboloTemporario.identificador = token.getWord();
        } else if (estadoAtual == 2) {
            if(proximoEstado == 3) {//Era soh variavel
               //Aqui tenho que colocar a logica pra devolver o ponteiro pra variavel...
                TabelaSimbolos tSimbolos = TabelaSimbolos.getInstance();
                simboloTemporario.tipoDeSimbolo = "VARIAVEL";
                tSimbolos.adicionaSimbolo(simboloTemporario);
                
                aSemantica.addVariavel(simboloTemporario);
            } else if (proximoEstado == 4) {//Eh vetor ou matriz... nada agora
            }
        } else if (estadoAtual == 4) {
            simboloTemporario.dimensaoX = Integer.valueOf(token.getWord()).intValue();
        } else if (estadoAtual == 6) {
            if(proximoEstado == 3) {//Era soh vetor...
                TabelaSimbolos tSimbolos = TabelaSimbolos.getInstance();
                simboloTemporario.tipoDeSimbolo = "VETOR";
                tSimbolos.adicionaSimbolo(simboloTemporario);
                aSemantica.addVariavel(simboloTemporario);
                //Aqui tenho que colocar a logica pra devolver o ponteiro pro vetor...
            } else if (proximoEstado == 7) {//Era matriz... nada agora
            }
        } else if (estadoAtual == 7) {
            simboloTemporario.dimensaoY = Integer.valueOf(token.getWord()).intValue();
            TabelaSimbolos tSimbolos = TabelaSimbolos.getInstance();
            simboloTemporario.tipoDeSimbolo = "MATRIZ";
            tSimbolos.adicionaSimbolo(simboloTemporario);
            aSemantica.addVariavel(simboloTemporario);
            //devolver a matriz
        }
    }
    
    
   private void analiseSemanticaPre(int estadoAtual, int proximoEstado,
            Token token){
       //Pro Enquanto Nada
    }
}
