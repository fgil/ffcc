/*
 * Token.java
 *
 * Created on November 2, 2007, 12:20 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package horae;

/**
 *
 * @author Fernando
 */
public class Token {
    
    protected String word;
    protected String type;
//    protected int line;
//    protected int column;
    
    public String A_NAORECONHECIDO = "Atomo não reconhecido.";
    public String A_IDENTIFICADOR = "Identificador.";
    public String A_NUMERO = "Número.";
    public String A_RESERVADA = "Palavra reservada.";
    public String A_CADEIA = "Cadeia de caracteres.";
    public String A_SIMBOLO = "Simbolo.";
    
    /** Creates a new instance of Atomo */
    public Token() {
    }

    public String getWord() {
        if (word == null){
            return type;
        } else {
            return word;
        }
    }

    public String getType() {
        return type;
    }

//    public int getLine() {
//        return line;
//    }
//
//    public int getColumn() {
//        return column;
//    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setType(String type) {
        this.type = type;
    }

//    public void setColumn(int column) {
//        this.column = column;
//    }
//
//    public void setLine(int line) {
//        this.line = line;
//    }
}