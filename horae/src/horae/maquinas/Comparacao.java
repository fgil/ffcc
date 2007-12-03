/*
 * Expressao.java
 *
 * Created on 21 de Novembro de 2007, 10:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package horae.maquinas;

import horae.semantico.PilhaCO;
import horae.semantico.PilhaEA;
import horae.semantico.Semantico;
import horae.util.*;
import horae.Token;

/**
 *
 * @author fernando
 */
public class Comparacao {
    
    public Fila filaLida;
    private Maquina maquina;
    public int estadoAtual;
    public int estadoAceito = 12;
    public boolean consome;
    public Token restoToken;
    public String escopo;
    private String maquinaNome = "Comparacao";
    public TabelaSimbolos tSimbolos;
    private PilhaEA pilhaEA;
    private PilhaCO pilhaCO;
    int caso;
    
    /** Creates a new instance of Expressao */
    public Comparacao(Fila filaLida) {
                this.filaLida = filaLida;
        maquina = new Maquina(39);
        estadoAtual = 0;
        consome = false;
        tSimbolos = TabelaSimbolos.getInstance();

        //Cria transicoes do estado 0
        maquina.criaTransicoes(0,6);
        maquina.setTransicao(0, 0, "(",             4,  0,                   false,4);
        maquina.setTransicao(0, 1, "identificador", 1,  maquina.A_Expressao, true,5);
        maquina.setTransicao(0, 2, "NUMERO",        1,  maquina.A_Expressao, true,5);
        maquina.setTransicao(0, 3, "-",             1,  maquina.A_Expressao, true,5);
        maquina.setTransicao(0, 4, "TRUE",          10, 0,                   false,1);
        maquina.setTransicao(0, 5, "FALSE",         11, 0,                   false,1);

        maquina.criaTransicoes(1,9);
        maquina.setTransicao(1, 0, ")",   12, 0, true);
        maquina.setTransicao(1, 1, "==",  2,  0, false,3);
        maquina.setTransicao(1, 2, "<=",  2,  0, false,3);
        maquina.setTransicao(1, 3, ">=",  2,  0, false,3);
        maquina.setTransicao(1, 4, "!=",  2,  0, false,3);
        maquina.setTransicao(1, 5, "<",   2,  0, false,3);
        maquina.setTransicao(1, 6, ">",   2,  0, false,3);
        maquina.setTransicao(1, 7, "OR",  0,  0, false,3);
        maquina.setTransicao(1, 8, "AND", 0,  0, false,3);
        
        maquina.criaTransicoes(2,6);
        maquina.setTransicao(2, 0, "(",             3, maquina.A_Expressao, true,5);
        maquina.setTransicao(2, 1, "identificador", 3, maquina.A_Expressao, true,5);
        maquina.setTransicao(2, 2, "TRUE",          3, maquina.A_Expressao, true,5);
        maquina.setTransicao(2, 3, "FALSE",         3, maquina.A_Expressao, true,5);
        maquina.setTransicao(2, 4, "NUMERO",        3, maquina.A_Expressao, true,5);
        maquina.setTransicao(2, 5, "-",             3, maquina.A_Expressao, true,5);

        maquina.criaTransicoes(3,3);
        maquina.setTransicao(3, 0, ")",   12, 0, true);
        maquina.setTransicao(3, 1, "OR",  0,  0, false,3);
        maquina.setTransicao(3, 2, "AND", 0,  0, false,3);
        
        maquina.criaTransicoes(4,7);
        maquina.setTransicao(4, 0, "(",             5, maquina.A_Condicao, true);
        maquina.setTransicao(4, 1, "identificador", 5, maquina.A_Condicao, true);
        maquina.setTransicao(4, 2, "TRUE",          5, maquina.A_Condicao, true);
        maquina.setTransicao(4, 3, "FALSE",         5, maquina.A_Condicao, true);
        maquina.setTransicao(4, 4, "NUMERO",        5, maquina.A_Condicao, true);
        maquina.setTransicao(4, 5, "NOT",           5, maquina.A_Condicao, true);
        maquina.setTransicao(4, 6, "-",             5, maquina.A_Condicao, true);  
        
        maquina.criaTransicoes(5,1);
        maquina.setTransicao(5, 0, ")", 6, 0, false);
        
        maquina.criaTransicoes(6,9);
        maquina.setTransicao(6, 0, ")"  , 12, 0, true,2);
        maquina.setTransicao(6, 1, "OR" ,  0,  0, false,3);
        maquina.setTransicao(6, 2, "AND",  0,  0, false,3);
        maquina.setTransicao(6, 3, "==" ,  0,  0, false,3);
        maquina.setTransicao(6, 4, "!=" ,  0,  0, false,3);
        maquina.setTransicao(6, 5, "<"  ,  0,  0, false,3);
        maquina.setTransicao(6, 6, "<=" ,  0,  0, false,3);
        maquina.setTransicao(6, 7, ">=" ,  0,  0, false,3);
        maquina.setTransicao(6, 8, ">"  ,  0,  0, false,3);
        

        maquina.criaTransicoes(7,2); //sem uso
        maquina.setTransicao(7, 0, "TRUE",  8, 0, false);
        maquina.setTransicao(7, 1, "FALSE", 9, 0, false);
        
        maquina.criaTransicoes(8,3); //sem uso
        maquina.setTransicao(8, 0, ")",   12, 0, true);
        maquina.setTransicao(8, 1, "OR",  0,  0, false);
        maquina.setTransicao(8, 2, "AND", 0,  0, false);      

        maquina.criaTransicoes(9,3); //sem uso
        maquina.setTransicao(9, 0, ")",   12, 0, true);
        maquina.setTransicao(9, 1, "OR",  0,  0, false);
        maquina.setTransicao(9, 2, "AND", 0,  0, false);
        
        maquina.criaTransicoes(10,3);
        maquina.setTransicao(10, 0, ")",   12, 0, true,2);
        maquina.setTransicao(10, 1, "OR",  0,  0, false,3);
        maquina.setTransicao(10, 2, "AND", 0,  0, false,3);      

        maquina.criaTransicoes(11,3);
        maquina.setTransicao(11, 0, ")",   12, 0, true,2);
        maquina.setTransicao(11, 1, "OR",  0,  0, false,3);
        maquina.setTransicao(11, 2, "AND", 0,  0, false,3); 
        

    }
    
        
    public int processaToken(Token token) {
        System.out.println(filaLida.getTamanho());
        try {
            if (token == null) {
                caso = 0;
            }
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
                    System.out.println(filaLida.getTamanho());
                    maquinaExpressao.escopo = this.escopo;
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
                
                case 6://Maquina Comparacao
                    Comparacao maquinaComparacao = new Comparacao(filaLida);
                    System.out.println(filaLida.getTamanho());
                    maquinaComparacao.escopo = this.escopo;
                    //Aqui ve se precisa mandar o ultimo token lido ou se vai pro proximo
                    if (transicao.consome) {
                        proximoToken = token;                        
                    } else {
                        proximoToken = (Token) filaLida.remover();
                    }
                    while(maquinaComparacao.processaToken(proximoToken) == 0){
                        if (maquinaComparacao.consome) {
                            proximoToken = maquinaComparacao.restoToken;                          
                        } else {
                            proximoToken = (Token) filaLida.remover();
                        }
                    }
                        consome = maquinaComparacao.consome;
                        proximoToken = maquinaComparacao.restoToken;
//                      
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
            System.out.println(pilhaCO.toString());
            return 1;
        } else {
            return 0;
        }

        } catch(Exception e) {
        System.out.println(maquinaNome + " - " + token.getType() + " - " + token.getWord() + " - Estado Atual: " + estadoAtual + 
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
        pilhaCO    = PilhaCO.getInstance();
        tSimbolos  = TabelaSimbolos.getInstance();
        Contadores contador = Contadores.getInstance();
        String     novaVar;
        
        switch(acao) {
            case 1:// Usado para - XX
                novaVar = contador.nextCocont();
                pilhaCO.adicionaOperando("BOOLEAN",novaVar);
                tSimbolos.adicionaSimbolo(this.escopo, "BOOLEAN", novaVar,token.getType());
                break;
            case 2://Vai esvaziar a pilha, fazendo os calculos
                System.out.println("Ja temos alguma coisa lah que devemos desempilhar");
                while (pilhaCO.operadorTopo() != null && pilhaCO.operadorTopo()!= "(") {
                    pilhaCO.adicionaOperando(executaOperacaoCO());//Aqui ele desempilha e executa a operação
                }
                if (pilhaCO.operadorTopo() == "(") {
                    //Remove soh o (
                    pilhaCO.removeOperador();
                }
                break;
            case 3:
                pilhaCO.adicionaOperador(token.getWord());
                break;
            case 4:
                //Adiciona (
                pilhaCO.adicionaOperador(token.getWord());
                break;
            case 5://Copia o conteudo da pilha de EA para a pilha de CO
            case 6://Deve tentar calcular o que tem na pilhaEA
                //Depois copia isso pra pilhaCO
                System.out.println("Ja temos alguma coisa lah que devemos desempilhar");
                while (pilhaEA.operadorTopo() != null && pilhaEA.operadorTopo()!= "(") {
                    pilhaEA.adicionaOperando(executaOperacaoEA());//Aqui ele desempilha e executa a operação
                }
                if (pilhaEA.operadorTopo() == "(") {
                    //Remove soh o (
                    pilhaEA.removeOperador();
                }
                pilhaCO.adicionaOperando(pilhaEA.removeOperando());
                //Agora tenta resolver as coisas pendentes na pilhaCO
                while (pilhaCO.operadorTopo() != null && pilhaCO.operadorTopo()!= "(") {
                    pilhaCO.adicionaOperando(executaOperacaoCO());//Aqui ele desempilha e executa a operação
                }
                if (pilhaCO.operadorTopo() == "(") {
                    //Remove soh o (
                    pilhaCO.removeOperador();
                }                
                break;
//            case 7:
//                if (pilhaEA.operadorTopo() != null && (pilhaEA.operadorTopo() == "*" ||
//                        pilhaEA.operadorTopo() == "/")) {
//                    System.out.println("Ja temos alguma coisa lah que podemos desempilhar");
//                    pilhaEA.adicionaOperando(executaOperacaoCO());//Aqui ele desempilha e executa a operação
//                    pilhaEA.adicionaOperador(token.getWord());
//                } else {
//                    System.out.println("Temos que pindurar");
//                    pilhaEA.adicionaOperador(token.getWord());
//                }
//                break;
//                
//            case 8:
//                System.out.println("Ja temos alguma coisa lah que devemos desempilhar");
//                while (pilhaEA.operadorTopo() != null && pilhaEA.operadorTopo()!= "(") {
//                    pilhaEA.adicionaOperando(executaOperacaoCO());//Aqui ele desempilha e executa a operação
//                }
//                if (pilhaEA.operadorTopo() == "(") {
//                    //Remove soh o (
//                    pilhaEA.removeOperador();
//                }
//                break;
            default:
                System.out.println("Nao implementado");
                break;
        }
    }
    
    private Operando executaOperacaoCO(){
        tSimbolos = TabelaSimbolos.getInstance();
        Contadores contador = Contadores.getInstance();
        String novaVar = contador.nextCocont();
        Semantico aSemantica = Semantico.getInstance("fonte.horae");
        
        Operando resultado = new Operando();
        Operando tmpB = pilhaCO.removeOperando();
        Operando tmpA = pilhaCO.removeOperando();
        Operador operador = pilhaCO.removeOperador();
        System.out.println("Operaçao: " + tmpA.valor + operador.nome + tmpB.valor);
        resultado.tipo = tmpA.tipo;
        resultado.valor = novaVar;
        tSimbolos.adicionaSimbolo(this.escopo,resultado.tipo,novaVar,"0");
        
        aSemantica.addComparacao(tmpA.valor, tmpB.valor, operador.nome, resultado.valor);
        
        return resultado;
    }
    
    private Operando executaOperacaoEA(){
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
