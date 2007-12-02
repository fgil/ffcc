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
        TabelaSimbolos variaveis = TabelaSimbolos.getInstance();

        
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
        
        
    }
    
    public static void info() {
        System.out.println("As horae (vulgarmente designadas como horas pela\n" +
                " corrup��o do voc�bulo original), constitu�am um grupo de\n" +
                " deusas gregas que presidiam �s esta��es dos anos. Eram \n" +
                " filhas de Zeus e T�mis s�o: Irene (paz), Dike (justi�a) e \n" +
                " Eum�nia (disciplina); estas s�o as Horas mais velhas e est�o\n" +
                " ligadas a legisla��o e ordem natural, sendo uma extens�o dos\n" +
                " atributos de sua m�e T�mis. Eum�nia est� relacionada com a\n" +
                " representa��o da divindade da justi�a. Temis e Dike elucidam\n" +
                " o lado �tico do instinto, a voz mi�da e calma no seio do\n" +
                " impulso. Dike para a humanidade � a fun��o de base institual\n" +
                " muito sint�nica com o que chama de instinto para reflex�o. \n" +
                "Existem mais nove Horas que s�o guardi�s da ordem natural, do\n" +
                " ciclo anual de crescimento da vegeta��o e das esta��es\n" +
                " climaticas anuais. (Talo, Carpo, Auxo, Acme, Anatole, Disis,\n" +
                " Dic�ia, Eup�ria, Gimn�sia).");
    }
}
