/*
 * Expressao.java
 *
 * Created on 21 de Novembro de 2007, 10:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package horae.maquinas;

import horae.semantico.PilhaEA;
import horae.semantico.Semantico;
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
    public TabelaSimbolos tSimbolos;
    public String escopo;
    private PilhaEA pilhaEA;
    int caso;
    
    /** Creates a new instance of Expressao */
    public Expressao(Fila filaLida) {
        this.filaLida = filaLida;
        maquina = new Maquina(22);
        estadoAtual = 0;
        consome = false;
        //System.out.println("Nasci");
        tSimbolos = TabelaSimbolos.getInstance();
        
        //Cria transicoes do estado 0
        maquina.criaTransicoes(0,6);
        maquina.setTransicao(0, 0, "-",             1, 0, false, 1);
        maquina.setTransicao(0, 1, "(",             4, 0, false, 2);
        maquina.setTransicao(0, 2, "identificador", 7, 0, false, 4);
        maquina.setTransicao(0, 3, "TRUE",          8, 0, false, 3);
        maquina.setTransicao(0, 4, "FALSE",         8, 0, false, 3);
        maquina.setTransicao(0, 5, "NUMERO",        9, 0, false, 5);
        
        maquina.criaTransicoes(1,5);
        maquina.setTransicao(1, 0, "(",             2, maquina.A_Expressao, true);
        maquina.setTransicao(1, 1, "identificador", 2, maquina.A_Expressao, true);
        maquina.setTransicao(1, 2, "TRUE",          2, maquina.A_Expressao, true);
        maquina.setTransicao(1, 3, "FALSE",         2, maquina.A_Expressao, true);
        maquina.setTransicao(1, 4, "NUMERO",        2, maquina.A_Expressao, true);
        
        maquina.criaTransicoes(2,5);
        maquina.setTransicao(2, 0, ";", 3,  0, true,  8);
        maquina.setTransicao(2, 1, "+", 10, 0, false, 6);
        maquina.setTransicao(2, 2, "-", 10, 0, false, 6);
        maquina.setTransicao(2, 3, "*", 10, 0, false, 7);
        maquina.setTransicao(2, 4, "/", 10, 0, false, 7);
        
        maquina.criaTransicoes(3,1);
        
        maquina.criaTransicoes(4,5);
        maquina.setTransicao(4, 0, "(",             5, maquina.A_Expressao, true);
        maquina.setTransicao(4, 1, "identificador", 5, maquina.A_Expressao, true);
        maquina.setTransicao(4, 2, "TRUE",          5, maquina.A_Expressao, true);
        maquina.setTransicao(4, 3, "FALSE",         5, maquina.A_Expressao, true);
        maquina.setTransicao(4, 4, "NUMERO",        5, maquina.A_Expressao, true);
        
        maquina.criaTransicoes(5,1);
        maquina.setTransicao(5, 0, ")", 6, 0, false, 9);
        
        maquina.criaTransicoes(6,7);
        maquina.setTransicao(6, 0, ";", 3,  0, true,  8);
        maquina.setTransicao(6, 1, "+", 10, 0, false, 6);
        maquina.setTransicao(6, 2, "-", 10, 0, false, 6);
        maquina.setTransicao(6, 3, "*", 10, 0, false, 7);
        maquina.setTransicao(6, 4, "/", 10, 0, false, 7);
        maquina.setTransicao(6, 5, "]", 3,  0, true,  8);
        maquina.setTransicao(6, 6, ")", 3,  0, true,  8);
        
        maquina.criaTransicoes(7,14);//esse vai ser o mais chato
        maquina.setTransicao(7, 0,  ";",  3,  0, true,  8);
        maquina.setTransicao(7, 1,  ")",  3,  0, true,  8);
        maquina.setTransicao(7, 2,  "(",  12, 0, false);
        maquina.setTransicao(7, 3,  "+",  10, 0, false, 6);
        maquina.setTransicao(7, 4,  "-",  10, 0, false, 6);
        maquina.setTransicao(7, 5,  "/",  10, 0, false, 7);
        maquina.setTransicao(7, 6,  "*",  10, 0, false, 7);
        maquina.setTransicao(7, 7,  "[",  16, 0, false);
        maquina.setTransicao(7, 8,  "]",  3,  0, true,  8);
        maquina.setTransicao(7, 9,  "<=", 3,  0, true,  8);
        maquina.setTransicao(7, 10, "<",  3,  0, true,  8);
        maquina.setTransicao(7, 10, ">",  3,  0, true,  8); //Tinha faltado essa
        maquina.setTransicao(7, 11, ">=", 3,  0, true,  8);
        maquina.setTransicao(7, 12, "==", 3,  0, true,  8);
        maquina.setTransicao(7, 13, "!=", 3,  0, true,  8);
        
        maquina.criaTransicoes(8,2);
        maquina.setTransicao(8, 0, ";", 3, 0, true, 8);
        maquina.setTransicao(8, 1, ")", 3, 0, true, 8);
        
        maquina.criaTransicoes(9, 7);
        maquina.setTransicao(9,   0, ";", 3,  0, true,  8);
        maquina.setTransicao(9,   5, ")", 3,  0, true,  8);
        maquina.setTransicao(9,   1, "+", 10, 0, false, 6);
        maquina.setTransicao(9,   2, "-", 10, 0, false, 6);
        maquina.setTransicao(9,   3, "*", 10, 0, false, 7);
        maquina.setTransicao(9,   4, "/", 10, 0, false, 7);
        maquina.setTransicao(9,   6, "]", 3,  0, true,  8);
        
        maquina.criaTransicoes(10,5);
        maquina.setTransicao(10, 0, "(",             11,maquina.A_Expressao,true);
        maquina.setTransicao(10, 1, "identificador", 11,maquina.A_Expressao,true);
        maquina.setTransicao(10, 2, "TRUE",          11,maquina.A_Expressao,true);
        maquina.setTransicao(10, 3, "FALSE",         11,maquina.A_Expressao,true);
        maquina.setTransicao(10, 4, "NUMERO",        11,maquina.A_Expressao,true);
        
        maquina.criaTransicoes(11,3);
        maquina.setTransicao(11, 0, ";", 3, 0, true, 8);
        maquina.setTransicao(11, 1, ")", 3, 0, true, 8);
        maquina.setTransicao(11, 2, "]", 3, 0, true, 8);
        
        maquina.criaTransicoes(12,7);
        maquina.setTransicao(12, 0, "(",             14, maquina.A_Expressao, true);
        maquina.setTransicao(12, 1, "identificador", 14, maquina.A_Expressao, true);
        maquina.setTransicao(12, 2, "TRUE",          14, maquina.A_Expressao, true);
        maquina.setTransicao(12, 3, "FALSE",         14, maquina.A_Expressao, true);
        maquina.setTransicao(12, 4, "NUMERO",        14, maquina.A_Expressao, true);
        maquina.setTransicao(12, 5, "-",             14, maquina.A_Expressao, true);
        maquina.setTransicao(12, 6, ")",             15, 0,                   true);
        
        maquina.criaTransicoes(13,5); // Sem uso
        maquina.setTransicao(13, 1, "identificador", 14, maquina.A_Expressao, true);
        maquina.setTransicao(13, 2, "TRUE",          14, maquina.A_Expressao, true);
        maquina.setTransicao(13, 3, "FALSE",         14, maquina.A_Expressao, true);
        maquina.setTransicao(13, 4, "NUMERO",        14, maquina.A_Expressao, true);
        maquina.setTransicao(13, 0, "-",             14, maquina.A_Expressao, true);
        
        maquina.criaTransicoes(14,2);
        maquina.setTransicao(14, 0, ",", 13, 0, false);
        maquina.setTransicao(14, 0, ")", 15, 0, false);
        
        maquina.criaTransicoes(15,12);
        maquina.setTransicao(15, 0,  ";",  3,  0, true,  8);
        maquina.setTransicao(15, 1,  ")",  3,  0, true,  8);
        maquina.setTransicao(15, 2,  "+",  10, 0, false, 6);
        maquina.setTransicao(15, 3,  "-",  10, 0, false, 6);
        maquina.setTransicao(15, 4,  "/",  10, 0, false, 7);
        maquina.setTransicao(15, 5,  "*",  10, 0, false, 7);
        maquina.setTransicao(15, 6,  "]",  3,  0, true,  8);
        maquina.setTransicao(15, 7,  "<=", 3,  0, true,  8);
        maquina.setTransicao(15, 8,  "<",  3,  0, true,  8);
        maquina.setTransicao(15, 8,  ">",  3,  0, true,  8);
        maquina.setTransicao(15, 9,  ">=", 3,  0, true,  8);
        maquina.setTransicao(15, 10, "==", 3,  0, true,  8);
        maquina.setTransicao(15, 11, "!=", 3,  0, true,  8);
        
        maquina.criaTransicoes(16,4);
        maquina.setTransicao(16, 0, "identificador", 17, maquina.A_Expressao, true);
        maquina.setTransicao(16, 1, "NUMERO",        17, maquina.A_Expressao, true);
        maquina.setTransicao(16, 2, "-",             17, maquina.A_Expressao, true);
        maquina.setTransicao(16, 3, "(",             17, maquina.A_Expressao, true);
        
        maquina.criaTransicoes(17,1);
        maquina.setTransicao(17, 0, "]", 18, 0, false);
        
        maquina.criaTransicoes(18,13);
        maquina.setTransicao(18, 0,  ";",  3,  0, true,  8);
        maquina.setTransicao(18, 1,  ")",  3,  0, true,  8);
        maquina.setTransicao(18, 2,  "+",  10, 0, false, 6);
        maquina.setTransicao(18, 3,  "-",  10, 0, false, 6);
        maquina.setTransicao(18, 4,  "/",  10, 0, false, 7);
        maquina.setTransicao(18, 5,  "*",  10, 0, false, 7);
        maquina.setTransicao(18, 6,  "[",  19, 0, false);
        maquina.setTransicao(18, 8,  "]",  3,  0, true,  8);
        maquina.setTransicao(18, 9,  "<=", 3,  0, true,  8);
        maquina.setTransicao(18, 10, "<",  3,  0, true,  8);
        maquina.setTransicao(18, 11, ">=", 3,  0, true,  8);
        maquina.setTransicao(18, 12, "==", 3,  0, true,  8);
        maquina.setTransicao(18, 7,  "!=", 3,  0, true,  8);
        
        maquina.criaTransicoes(19,4);
        maquina.setTransicao(19, 0, "identificador", 20, maquina.A_Expressao, true);
        maquina.setTransicao(19, 1, "NUMERO",        20, maquina.A_Expressao, true);
        maquina.setTransicao(19, 2, "-",             20, maquina.A_Expressao, true);
        maquina.setTransicao(19, 3, "(",             20, maquina.A_Expressao, true);
        
        maquina.criaTransicoes(20,1);
        maquina.setTransicao(20, 0, "]", 21, 0, false);
        
        maquina.criaTransicoes(21,12);
        maquina.setTransicao(21, 0,  ";",  3,  0, true);
        maquina.setTransicao(21, 1,  ")",  3,  0, true);
        maquina.setTransicao(21, 2,  "+",  10, 0, false, 6);
        maquina.setTransicao(21, 3,  "-",  10, 0, false, 6);
        maquina.setTransicao(21, 4,  "/",  10, 0, false, 7);
        maquina.setTransicao(21, 5,  "*",  10, 0, false, 7);
        maquina.setTransicao(21, 6,  "]",  3,  0, true);
        maquina.setTransicao(21, 7,  "<=", 3,  0, true);
        maquina.setTransicao(21, 8,  "<",  3,  0, true);
        maquina.setTransicao(21, 9,  ">=", 3,  0, true);
        maquina.setTransicao(21, 10, "==", 3,  0, true);
        maquina.setTransicao(21, 11, "!=", 3,  0, true);
        
    }
    
    
    public int processaToken(Token token) {
        System.out.println(filaLida.getTamanho());
        try {
            Transicao transicao =
                    maquina.estados[estadoAtual].proximoEstado(token.getType());
            System.out.println(maquinaNome + " - " + token.getType() + " - Estado Atual: " + estadoAtual);
            System.out.println("Proximo Estado: " + transicao.proximoEstado);
            caso = transicao.caso;
            analiseSemanticaPre(estadoAtual,transicao.proximoEstado,token,caso);
            
            Token proximoToken = null;
            if (transicao.consome) proximoToken = token;
            
            if (transicao.proximaMaquina > 0) {
                switch(transicao.proximaMaquina) {
                    case 5://Maquina Expressao
                        Expressao maquinaExpressao = new Expressao(filaLida);
                        maquinaExpressao.escopo = this.escopo;
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
                        break;
                    default:
                        //Ainda nao implementado
                        
                }
            }
            
            analiseSemanticaPos(estadoAtual,transicao.proximoEstado,token,caso);
            consome = transicao.consome;
            estadoAtual = transicao.proximoEstado;
            restoToken = proximoToken;
            
            // Aqui deverá verificar se o estado é aceito e se podemos retornar
            if (transicao.proximoEstado == this.estadoAceito) {
                //System.out.println("Morri");
                //pilhaEA = PilhaEA.getInstance();
                //System.out.println(pilhaEA.toString());
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
            Token token, int caso){
        System.out.println("Caso:" + caso);
        switch (caso) {
            case 0:
                break;
            case 1:
                acaoOperadores(1,token);
                break;
            case 2:
                acaoOperadores(2,token);
                break;
            case 3:
                acaoOperadores(3,token);
                break;
            case 4:
                acaoOperadores(4,token);
                break;
            case 5:
                acaoOperadores(5,token);
                break;
            case 6:
                acaoOperadores(6,token);
                break;
            case 7:
                acaoOperadores(7,token);
                break;
            case 8:
                acaoOperadores(8,token);
                break;
                
            default:
                System.out.println("Ainda Nao Implementado");
                break;
        }
        
        
    }
    
    
    private void analiseSemanticaPre(int estadoAtual, int proximoEstado,
            Token token, int caso){
        //Pro Enquanto Nada
    }
    
    
    private void acaoOperadores(int acao, Token token) {
        pilhaEA    = PilhaEA.getInstance();
        tSimbolos  = TabelaSimbolos.getInstance();
        Contadores contador = Contadores.getInstance();
        String     novaVar;
        
        switch(acao) {
            case 1:// Usado para - XX
                novaVar = contador.nextEacont();
                pilhaEA.adicionaOperando("INT",novaVar);
                tSimbolos.adicionaSimbolo(this.escopo, "INT", novaVar,"0");
                pilhaEA.adicionaOperador(token.getWord());
                break;
            case 2://Empilhar ( - Como se começasse pilha nova
                pilhaEA.adicionaOperador(token.getWord());
                break;
            case 3:
                novaVar = contador.nextEacont();
                pilhaEA.adicionaOperando("BOOLEAN",novaVar);
                tSimbolos.adicionaSimbolo(this.escopo, "BOOLEAN", novaVar, token.getWord());
                break;
            case 4:
                //buscar o tipo do identificador
                Simbolo simbolo = tSimbolos.procuraSimbolo(this.escopo, token.getWord());
                pilhaEA.adicionaOperando(simbolo.getTipoDeDado(),token.getWord());
                break;
            case 5:
                novaVar = contador.nextEacont();
                tSimbolos.adicionaSimbolo(this.escopo, "INT", novaVar, token.getWord());
                pilhaEA.adicionaOperando("INT",novaVar);
                break;
            case 6:
                if (pilhaEA.operadorTopo() == null ||
                        pilhaEA.operadorTopo() == "(") {
                    System.out.println("Temos que pindurar");
                    pilhaEA.adicionaOperador(token.getWord());
                } else {
                    System.out.println("Ja temos alguma coisa lah que podemos desempilhar");
                    pilhaEA.adicionaOperando(executaOperacao());//Aqui ele desempilha e executa a operação
                    pilhaEA.adicionaOperador(token.getWord());
                }
                break;
            case 7:
                if (pilhaEA.operadorTopo() != null && (pilhaEA.operadorTopo() == "*" ||
                        pilhaEA.operadorTopo() == "/")) {
                    System.out.println("Ja temos alguma coisa lah que podemos desempilhar");
                    pilhaEA.adicionaOperando(executaOperacao());//Aqui ele desempilha e executa a operação
                    pilhaEA.adicionaOperador(token.getWord());
                } else {
                    System.out.println("Temos que pindurar");
                    pilhaEA.adicionaOperador(token.getWord());
                }
                break;
                
            case 8:
                System.out.println("Ja temos alguma coisa lah que devemos desempilhar");
                while (pilhaEA.operadorTopo() != null && pilhaEA.operadorTopo()!= "(") {
                    pilhaEA.adicionaOperando(executaOperacao());//Aqui ele desempilha e executa a operação
                }
                if (pilhaEA.operadorTopo() == "(") {
                    //Remove soh o (
                    pilhaEA.removeOperador();
                }
                break;
            default:
                
        }
    }
    
    private Operando executaOperacao(){
        tSimbolos = TabelaSimbolos.getInstance();
        Contadores contador = Contadores.getInstance();
        String novaVar = contador.nextEacont();
        Semantico aSemantica = Semantico.getInstance("fonte.horae");
        
        Operando resultado = new Operando();
        Operando tmpB = pilhaEA.removeOperando();
        Operando tmpA = pilhaEA.removeOperando();
        Operador operador = pilhaEA.removeOperador();
        System.out.println("Operaçao: " + tmpA.valor + operador.nome + tmpB.valor);
        resultado.tipo = tmpA.tipo;
        resultado.valor = novaVar;
        tSimbolos.adicionaSimbolo(this.escopo,resultado.tipo,novaVar,"0");
        
        aSemantica.addOperacao(tmpA.valor, tmpB.valor, operador.nome, resultado.valor);
        
        return resultado;
    }
}