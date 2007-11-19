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
import java.util.logging.ConsoleHandler;

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
        info();
        Token token = new Token();
        Fila filaLida = new Fila();

        
        String fileName = "fonte.horae";
        if(args==null) fileName = "fonte.horae";
        //else fileName = args[0];
        Lexico lexico = new Lexico(fileName);
        token = lexico.nextToken(); 
        while (token != null){
            filaLida.adicionar(token);
            System.out.println(token.getType() + " - " + token.getWord());
            token = lexico.nextToken(); 
        }
        Programa programa = new Programa(filaLida);
        //System.out.println(programa.filaLida.getTamanho());
        
        while(filaLida.getTamanho() > 0){
            programa.processaToken((Token) filaLida.remover());
        }
        

        
        
    }
    
    public static void info() {
        System.out.println("As horae (vulgarmente designadas como horas pela" +
                " corrupção do vocábulo original), constituíam um grupo de" +
                " deusas gregas que presidiam às estações dos anos. Eram " +
                " filhas de Zeus e Têmis são: Irene (paz), Dike (justiça) e " +
                " Eumônia (disciplina); estas são as Horas mais velhas e estão" +
                " ligadas a legislação e ordem natural, sendo uma extensão dos" +
                " atributos de sua mãe Têmis. Eumônia está relacionada com a" +
                " representação da divindade da justiça. Temis e Dike elucidam" +
                " o lado ético do instinto, a voz miúda e calma no seio do" +
                " impulso. Dike para a humanidade é a função de base institual" +
                " muito sintônica com o que chama de instinto para reflexão. \n" +
                "Existem mais nove Horas que são guardiãs da ordem natural, do" +
                " ciclo anual de crescimento da vegetação e das estações" +
                " climaticas anuais. (Talo, Carpo, Auxo, Acme, Anatole, Disis," +
                " Dicéia, Eupória, Gimnásia).");
    }
}
