/*
 * PilhaEA.java
 *
 * Created on 26 de Novembro de 2007, 10:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package horae.semantico;

import horae.util.Operador;
import horae.util.Operando;
import horae.util.Pilha;

/**
 *
 * @author fernando
 */
public class PilhaCO {
    
    /* Mais uma clase singleton */
    private static PilhaCO aPilhaCO;
    
    public Pilha pOperandos;
    public Pilha pOperadores;
    
    public static PilhaCO getInstance(){
       if (aPilhaCO == null) {
            aPilhaCO = new PilhaCO();
        }
        return aPilhaCO;
    }
    
    /** Creates a new instance of PilhaEA */
    private PilhaCO() {
        pOperandos = new Pilha();
        pOperadores = new Pilha();
    }
    
    public void limpaPilha() {//Suposto de não ser usada
        pOperandos = new Pilha();
        pOperadores = new Pilha();
    }

    public void adicionaOperando(String tipo, String valor){
        Operando operando = new Operando();
        operando.tipo = tipo;
        operando.valor = valor;
        adicionaOperando(operando);
    }
    
    public void adicionaOperador(String nome){
        Operador operador = new Operador();
        operador.nome = nome;
        pOperadores.adicionar(operador);
    }

    public Operando removeOperando() {
        return (Operando) pOperandos.remover();
    }

    public Operador removeOperador() {
        return (Operador) pOperadores.remover();
    }
    
    public String toString() {
        String temp = new String();
        Operando operando;
        Operador operador;
        temp = "Operandos:\nTOPO\n";
        for (int i = 0; i< pOperandos.getTamanho(); i++) {
            operando = (Operando)pOperandos.consultar(i);
            temp = temp + operando.valor + "\n";
        }
        
       temp = temp + "Operandores:\nTOPO\n";
        for (int i = 0; i< pOperadores.getTamanho(); i++) {
            operador = (Operador)pOperadores.consultar(i);
            temp = temp + operador.nome + "\n";
        }
            
        return temp;
    }

    public String operadorTopo() {
        if (pOperadores.getTamanho() != 0) {
            Operador operador = (Operador) pOperadores.consultar(0);
            return operador.nome;
        } else {
            return null;
        }
        
    }

    public void adicionaOperando(Operando operando) {
        pOperandos.adicionar(operando);
    }
}
