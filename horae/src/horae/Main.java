/*
 * Main.java
 *
 * Created on November 2, 2007, 11:20 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package horae;

import horae.maquinas.Programa;
import horae.util.Fila;
import horae.util.TabelaSimbolos;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author Fernando
 */
public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        if(args.length == 0) {
            info();
        } else {
            boolean montar = false;
            Token token = new Token();
            Fila filaLida = new Fila();
            TabelaSimbolos variaveis = TabelaSimbolos.getInstance();
            String fileName = "fonte.horae";
            
            if(args.length > 1){
                if(args[0].equals("m")) {
                    montar = true;
                    fileName = args[1];
                }
            } else {
                fileName = args[0];
            }
            
            Lexico lexico = new Lexico(fileName);
            token = lexico.nextToken();
            while (token != null){
                filaLida.adicionar(token);
                System.out.println(token.getType() + " - " + token.getWord());
                token = lexico.nextToken();
            }
            Programa programa = new Programa(filaLida, fileName);
            //System.out.println(programa.filaLida.getTamanho());
            
            token = null;
            while(filaLida.getTamanho() > 0){
                if (token == null) {
                    token = programa.processaToken((Token) filaLida.remover());
                } else {
                    token = programa.processaToken(token);
                }
            }
            System.out.println(variaveis.toSring());
            System.out.println("FIM");
            
            if(montar){
                try {
                    System.out.println("Executando montador...");
                    String line;
                    Process p = Runtime.getRuntime().exec("java -jar montador.jar " + fileName.split("\\.")[0] + ".asm");
                    BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    while ((line = input.readLine()) != null) {
                        System.out.println("Montador: " + line);
                    }
                    input.close();
                } catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
        
        
    }
    
    public static void info() {
        System.out.println("As horae (vulgarmente designadas como horas pela\n" +
                " corrupção do vocábulo original), constituíam um grupo de\n" +
                " deusas gregas que presidiam às estações dos anos. Eram \n" +
                " filhas de Zeus e Têmis são: Irene (paz), Dike (justiça) e \n" +
                " Eumônia (disciplina); estas são as Horas mais velhas e estão\n" +
                " ligadas a legislação e ordem natural, sendo uma extensão dos\n" +
                " atributos de sua mãe Têmis. Eumônia está relacionada com a\n" +
                " representação da divindade da justiça. Temis e Dike elucidam\n" +
                " o lado ético do instinto, a voz miúda e calma no seio do\n" +
                " impulso. Dike para a humanidade é a função de base institual\n" +
                " muito sintônica com o que chama de instinto para reflexão. \n" +
                "Existem mais nove Horas que são guardiãs da ordem natural, do\n" +
                " ciclo anual de crescimento da vegetação e das estações\n" +
                " climaticas anuais. (Talo, Carpo, Auxo, Acme, Anatole, Disis,\n" +
                " Dicéia, Eupória, Gimnásia).");
        System.out.println("");
        System.out.println("Uso: java -jar horae.jar fonte.horae");
    }
}
