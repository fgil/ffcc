/*
 * Semantico.java
 *
 * Created on November 25, 2007, 8:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package horae.semantico;

import horae.util.Contadores;
import horae.util.Operando;
import horae.util.TabelaSimbolos;
import java.io.FileOutputStream;
import horae.util.Simbolo;
import horae.util.Util;
import java.util.Iterator;

/**
 *
 * @author Fernando
 */
public class Semantico {
    
    private static Semantico aSemantico;//Variavel para o Singleton
    
    private String fileName;
    FileOutputStream fileStream;
    
    
    public static Semantico getInstance(String filename){
       if (aSemantico == null) {
            aSemantico = new Semantico(filename);
        }
        return aSemantico;
    }
    
    public static Semantico getInstance(){
           return Semantico.getInstance("temporario.hades");
    }
    
    /** Creates a new instance of Semantico */
    private Semantico(String filename) {
        this.fileName = filename.substring(0, filename.indexOf('.'));
        this.fileName+= ".asm";
        try {
            fileStream = new FileOutputStream(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.startFile();
    }
    
    public void startFile(){
        String start = "@ /0";
        writetoFile(start);
        String temp = "JP INICIO_0";
        writetoFile(temp);
    }
    

    public void closeFile(){
        try {
            writetoFile("HM /00");
            
            TabelaSimbolos tSimbolos = TabelaSimbolos.getInstance();
            
            //Declara o que ainda não foi declarado
            for (Iterator simbolos = tSimbolos.tabela.iterator(); simbolos.hasNext();) {
                Simbolo simbolo = (Simbolo) simbolos.next();
                
                if(!simbolo.isDeclarado() && simbolo.getTipoDeSimbolo() != "FUNCAO"){
                    addVariavel(simbolo);
                }
            }

            //Declara TRUE e FALSE
            addVariavel("TRUE",  "1");
            addVariavel("FALSE", "0");
            addVariavel("CONST_SHIFT","256");
            
            //Grava as instruções usadas nos saltos
//            String temp = "0_JP JP /0000";
//            writetoFile(temp);
//            temp = "0_MM MM /0000";
//            writetoFile(temp);
//            temp = "0_LD LD /0000";
//            writetoFile(temp);
//            temp = "0_JZ JZ /0000";
//            writetoFile(temp);
//            temp = "0_MAIS + /0000";
//            writetoFile(temp);
            

            //Rotinas do push e pop
            addPushCode();
            addPopCode();
            addPilhaVars();
            

            
            //String declaracao = (String)declaracoes.removeTop();
//            while(declaracao!=null){
//                writetoFile(declaracao);
//                declaracao = (String)declaracoes.removeTop();
//            }
            fileStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void addVariavel(Simbolo sTemp){
        String temp = sTemp.getIdentificador() + " K /" + Util.decimalToHex3(sTemp.getValorInicial());
        writetoFile(temp);
        sTemp.setDeclarado(true);
    }
    
    public void addVariavel(String nome, String Valor) {
        String temp = nome + " K /" + Util.decimalToHex3(Valor);
        writetoFile(temp);
    }
    
    public void addLoad(String origem){
        String temp = "LD " + origem;
        writetoFile(temp);
    }
    
    public void addStore(String destino){
        String temp = "MM " + destino;
        writetoFile(temp);
    }
    
    public void addAtribuicao(String origem, String destino){
        addLoad(origem);
        addStore(destino);
    }
    
    public void addOperacao(String operando1, String operando2, String operador, String resultado){
        addLoad(operando1);
        
        String temp = operador + "  " + operando2;
        writetoFile(temp);
        
        addStore(resultado);
    }
    
    public void addComparacao(String operando1, String operando2, String operador, String resultado){
        String comando = "HM";
        String temp, pulaTrue, pulaFalse;
        Contadores count = Contadores.getInstance();
        if (operador == "OR") {
            addLoad(operando1);
            comando = "+";
            temp = comando + "  " + operando2;
            writetoFile(temp);
            addStore(resultado);
        } else if (operador == "AND") {
            addLoad(operando1);
            comando = "*";
            temp = comando + "  " + operando2;
            writetoFile(temp);
            addStore(resultado);
        } else if (operador == "==") {
            addLoad(operando1);
            temp = "-  " + operando2;
            writetoFile(temp);
            pulaTrue = count.nextCocont();
            pulaFalse = count.nextCocont();
            temp = "JZ " + pulaTrue;
            writetoFile(temp);
            temp = "LD FALSE";
            writetoFile(temp);
            temp = "JP " + pulaFalse;
            writetoFile(temp);
            temp = pulaTrue + " LD TRUE";
            writetoFile(temp);
            temp = pulaFalse + " MM " + resultado;
            writetoFile(temp);
        } else if(operador == "!=") {
            addLoad(operando1);
            temp = "-  " + operando2;
            writetoFile(temp);
            pulaFalse = count.nextCocont();
            temp = "JZ " + pulaFalse;
            writetoFile(temp);
            temp = "LD TRUE";
            writetoFile(temp);
            temp = pulaFalse + " MM " + resultado;
            writetoFile(temp);
        } else if(operador == "<" || operador == "<=") {
            addLoad(operando1);
            temp = "-  " + operando2;
            writetoFile(temp);
            pulaTrue = count.nextCocont();

            pulaFalse = count.nextCocont();
            temp = "JN " + pulaTrue;
            writetoFile(temp);
            if (operador == "<=") {
                temp = "JZ " + pulaTrue;
                writetoFile(temp);
            }
            temp = "LD FALSE";
            writetoFile(temp);
            temp = "JP " + pulaFalse;
            writetoFile(temp);
            temp = pulaTrue + " LD TRUE";
            writetoFile(temp);
            temp = pulaFalse + " MM " + resultado;
            writetoFile(temp);
        } else if(operador == ">" || operador == ">=") {
            addLoad(operando2);
            temp = "-  " + operando1;
            writetoFile(temp);
            pulaTrue = count.nextCocont();

            pulaFalse = count.nextCocont();
            temp = "JN " + pulaTrue;
            writetoFile(temp);
            if (operador == ">=") {
                temp = "JZ " + pulaTrue;
                writetoFile(temp);
            }
            temp = "LD FALSE";
            writetoFile(temp);
            temp = "JP " + pulaFalse;
            writetoFile(temp);
            temp = pulaTrue + " LD TRUE";
            writetoFile(temp);
            temp = pulaFalse + " MM " + resultado;
            writetoFile(temp);
        }
         
    }
    
    public void addJump(String label){
        writetoFile("JP " + label);
    }
    
    public void addJumpIfZero(String label){
        writetoFile("JZ " + label);
    }
    
    public void addPut(){
        writetoFile("PD /0100"); // output para o monitor
    }
    
    public void addGet(){
        writetoFile("GD /0000"); // input do teclado
        writetoFile("/ CONST_SHIFT");
    }
    
    public void addLabel(String label){
        writetoFile(label + " + FALSE ; NOP");
    }
    
    public void addPushCode() {
        String temp = "PUSH LD PONTEIRO_TOPO";
        writetoFile(temp);
        temp = "+ INST_MM";
        writetoFile(temp);
        temp = "MM GRAVAR";
        writetoFile(temp);
        temp = "LD TEMP";
        writetoFile(temp);
        temp = "GRAVAR 	K /0000";
        writetoFile(temp);
        temp = "LD PONTEIRO_TOPO";
        writetoFile(temp);
        temp = "+ DOIS";
        writetoFile(temp);
        temp = "MM PONTEIRO_TOPO";
        writetoFile(temp);
        temp = "RS PUSH";
        writetoFile(temp);
    }
    
    public void addPopCode() {
        String temp = "POP	LD PONTEIRO_TOPO";
        writetoFile(temp);
        temp = "- DOIS";
        writetoFile(temp);
        temp = "MM PONTEIRO_TOPO";
        writetoFile(temp);
        temp = "+ INST_LD";
        writetoFile(temp);
        temp = "MM LER";
        writetoFile(temp);
        temp = "LER 	K /0000";
        writetoFile(temp);
        temp = "MM TEMP";
        writetoFile(temp);
        temp = "RS POP";
        writetoFile(temp);
    }
    
    //Funçao que escreve no arquivo de saida
    private void writetoFile(String msg){
        try {
            fileStream.write((msg + (char)10).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addPilhaVars() {
        String temp = "DOIS K /0002";
        writetoFile(temp);
        temp = "INST_LD LD /0000";
        writetoFile(temp);
        temp = "INST_MM MM /0000";
        writetoFile(temp);
        temp = "PONTEIRO_TOPO K TOPO";
        writetoFile(temp);
        temp = "RETORNO K /0000";
        writetoFile(temp);
        temp = "TEMP K /0000";
        writetoFile(temp);
        temp = "TOPO K /0000";
        writetoFile(temp);
    }

    public void addPush(Operando operando) {
        String temp = "LD " + operando.valor;
        writetoFile(temp);
        addStore("TEMP");
        temp = "SC PUSH";
        writetoFile(temp);
    }

    public void addEndOfFunction(String escopo) {
        addStore("RETORNO");
        String temp = "RS 0_" + escopo;
        writetoFile(temp);
    }

    public void addCallFunction(String funcao) {
        String temp = "SC 0_" + funcao;
        writetoFile(temp);
    }

    public void addPop(Simbolo novoSimbolo) {
        String temp = "SC POP";
        writetoFile(temp);
        temp = "LD TEMP";
        writetoFile(temp);
        temp = "MM " + novoSimbolo.getIdentificador();
        writetoFile(temp);
    }
}
