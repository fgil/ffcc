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
public class PilhaEA {
    
    /* Mais uma clase singleton */
    private static PilhaEA aPilhaEA;
    
    public Pilha pOperandos;
//    public Pilha pOperadores;
    
    public static PilhaEA getInstance(){
       if (aPilhaEA == null) {
            aPilhaEA = new PilhaEA();
        }
        return aPilhaEA;
    }
    
    /** Creates a new instance of PilhaEA */
    private PilhaEA() {
        pOperandos = new Pilha();
    }
    
    public void limpaPilha() {
        pOperandos = new Pilha();
    }

    public void adiciona(String novo){
        Operando operando = new Operando();
        operando.nome = novo;
        pOperandos.adicionar(operando);
    }

    
    public String toString() {
        String temp = new String();
        Operando operando;
        Operador operador;
        temp = "Operandos:\nTOPO\n";
        for (int i = 0; i< pOperandos.getTamanho(); i++) {
            operando = (Operando)pOperandos.consultar(i);
            temp = temp + operando.nome + "\n";
        }
        
//       temp = "Operandores:\nTOPO\n";
//        for (int i = 0; i< pOperadores.getTamanho(); i++) {
//            operando = (Operando)pOperandos.consultar(i);
//            temp = operando.nome + "\n";
//        }
            
        return temp;
    }
}
