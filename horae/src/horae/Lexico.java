/*
 * Lexico.java
 *
 * Created on November 2, 2007, 2:55 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package horae;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Fernando
 */
public class Lexico {
    
    String fileName;
    FileReader fileStream;
    int estado;
    int charLido = -10;

    // O valor -10 tem o intuito de indicar que nenhum caracter foi lido.
    // Quando o nextToken acabar OK, c sera -10. Caso tenha algo, como um ;
    // este ficarà armazenado em c.
    
    /** Creates a new instance of Lexico */
    public Lexico(String filename) {
        this.fileName = filename;
        try{
            fileStream = new FileReader(fileName);
        } catch(IOException e){
            System.out.println("Exception e: " + e.getMessage());
        }
    }
    
    public Token nextToken() {
        estado = 0;
        Token token = new Token();
        String lido = "";
        
        //Testando o fileStream
        if(fileStream == null) {
            System.out.println("Erro no fileStream.");
            return token;
        }
        try {
            if (charLido == -10) charLido = fileStream.read();
            while (true) {
//                System.out.println((char)charLido + " " + (int) charLido +
//                        "Estado Atual: " + (int) estado);
                switch(estado) {
                    case 0:
                        switch (charLido) {
                            case -1://EOF
                                return null;
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                                estado = 0;
                                break;
                            case (int) 'S':
                                lido = "" + (char) charLido;
                                estado = 1;
                                break;
                            case (int) 'E':
                                lido = "" + (char) charLido;
                                estado = 6;
                                break;
                            case (int) 'I':
                                lido = "" + (char) charLido;
                                estado = 15;
                                break;
                            case (int) 'B':
                                lido = "" + (char) charLido;
                                estado = 22;
                                break;
                            case (int) 'C':
                                lido = "" + (char) charLido;
                                estado = 29;
                                break;
                            case (int) 'O':
                                lido = "" + (char) charLido;
                                estado = 33;
                                break;
                            case (int) 'T':
                                lido = "" + (char) charLido;
                                estado = 40;
                                break;
                            case (int) 'F':
                                lido = "" + (char) charLido;
                                estado = 47;
                                break;
                            case (int) 'N':
                                lido = "" + (char) charLido;
                                estado = 52;
                                break;
                            case (int) 'A':
                                lido = "" + (char) charLido;
                                estado = 55;
                                break;
                            case (int) 'R':
                                lido = "" + (char) charLido;
                                estado = 80;
                                break;
                            case (int) 'W':
                                lido = "" + (char) charLido;
                                estado = 90;
                                break;
                            case (int) 'D':
                                lido = "" + (char) charLido;
                                estado = 95;
                                break;

                            case (int) '{':
                                token.type = "{";
                                estado = 0;
                                charLido = -10;
                                return token;
                            case (int) '}':
                                token.type = "}";
                                estado = 0;
                                charLido = -10;
                                return token;
                            case (int) ';':
                                token.type = ";";
                                estado = 0;
                                charLido = -10;
                                return token;
                            case (int) '+':
                                token.type = "+";
                                charLido = -10;
                                estado = 0;
                                return token;
                            case (int) '-':
                                token.type = "-";
                                estado = 0;
                                charLido = -10;
                                return token;
                            case (int) '/':
                                token.type = "/";
                                estado = 0;
                                charLido = -10;
                                return token;
                            case (int) '*':
                                token.type = "*";
                                estado = 0;
                                charLido = -10;
                                return token;
                            case (int) '=':
                                estado = 60;
                                break;
                            case (int) '!':
                                estado = 62;
                                break;
                            case (int) '<':
                                estado = 64;
                                break;
                            case (int) '>':
                                estado = 66;
                                break;
                            case (int) '(':
                                token.type = "(";
                                estado = 0;
                                charLido = -10;
                                return token;
                            case (int) ')':
                                token.type = ")";
                                estado = 0;
                                charLido = -10;
                                return token;
                            case (int) '[':
                                token.type = "[";
                                estado = 0;
                                charLido = -10;
                                return token;
                            case (int) ']':
                                token.type = "]";
                                estado = 0;
                                charLido = -10;
                                return token;
                            case (int) ',':
                                token.type = ",";
                                estado = 0;
                                charLido = -10;
                                return token;
                            case (int) '\'':
                                token.type = "CADEIA";
                                estado = 70;
                                lido = "";
                                break;
                            case (int) '0':
                            case (int) '1':
                            case (int) '2':
                            case (int) '3':
                            case (int) '4':
                            case (int) '5':
                            case (int) '6':
                            case (int) '7':
                            case (int) '8':
                            case (int) '9':
                                estado = 59;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                estado = 58;
                                lido = lido + (char) charLido;
                                break;
                        }
                        break;
                    case 1:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'T':
                                estado = 2;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                                break;
                        }
                        break;
                    case 2:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'A':
                                estado = 3;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 3:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'R':
                                estado = 4;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                                break;
                        }
                        break;
                    case 4:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'T':
                                estado = 5;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                                break;
                        }
                        break;
                    case 5:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n': case 13:
                            case (int) ';':
                                token.type = "START";
                                
                                estado = 0;
                                return token;
                                //break;

                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 6:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'N':
                                estado = 7;
                                lido = lido + (char) charLido;
                                break;
                            case (int) 'L':
                                estado = 12;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 7:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'D':
                                estado = 8;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 8:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n': case 13:
                            case (int) ';':
                                token.type = "END";
                                
                                estado = 0;
                                return token;
                                //break;
                            case (int) 'W':
                                estado = 9;
                                lido = lido + (char) charLido;
                                break;
                            case (int) 'I':
                                estado = 10;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 9:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n': case 13:
                            case (int) ';':
                                token.type = "ENDW";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 10:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'F':
                                estado = 11;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 11:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n': case 13:
                            case (int) ';':
                                token.type = "ENDIF";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;

                    case 12:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'S':
                                estado = 13;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                        
                    case 13:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'E':
                                estado = 14;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                        
                    case 14:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n': case 13:
                            case (int) ';':
                                token.type = "ELSE";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                        
                    case 15:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'N':
                                estado = 16;
                                lido = lido + (char) charLido;
                                break;
                            case (int) 'F':
                                estado = 21;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                        
                    case 16:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'T':
                                estado = 17;
                                lido = lido + (char) charLido;
                                break;
                            case (int) 'P':
                                estado = 18;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;

                    case 17:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n': case 13:
                            case (int) ';':
                                token.type = "INT";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                        
                    case 18:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'U':
                                estado = 19;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                        
                    case 19:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'T':
                                estado = 20;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 20:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                                token.type = "INPUT";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 21:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n': case 13:
                            case (int) '(':
                            case (int) ';':
                                token.type = "IF";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                        
                        
                        
                        
                    case 22:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'O':
                                estado = 23;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 23:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'O':
                                estado = 24;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 24:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'L':
                                estado = 25;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 25:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'E':
                                estado = 26;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 26:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'A':
                                estado = 27;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 27:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'N':
                                estado = 28;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;

                    case 28:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n': case 13:
                            case (int) ';':
                                token.type = "BOOLEAN";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                        
                    case 29:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'H':
                                estado = 30;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 30:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'A':
                                estado = 31;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 31:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'R':
                                estado = 32;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;

                    case 32:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n': case 13:
                            case (int) ';':
                                token.type = "CHAR";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    
                    case 33:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'R':
                                estado = 34;
                                lido = lido + (char) charLido;
                                break;
                            case (int) 'U':
                                estado = 35;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 34:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n': case 13:
                            case (int) ';':
                                token.type = "OR";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;

                    case 35:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'T':
                                estado = 36;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 36:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'P':
                                estado = 37;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 37:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'U':
                                estado = 38;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 38:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'T':
                                estado = 39;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;

                    case 39:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n': case 13:
                            case (int) ';':
                                token.type = "OUTPUT";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                        
                    case 40:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'H':
                                estado = 44;
                                lido = lido + (char) charLido;
                                break;
                            case (int) 'R':
                                estado = 41;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;

                    case 41:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'U':
                                estado = 42;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 42:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'E':
                                estado = 43;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;

                    case 43:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "TRUE";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;                        
                        
                    case 44:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'E':
                                estado = 45;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 45:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'N':
                                estado = 46;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;

                    case 46:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n': case 13:
                            case (int) ';':
                                token.type = "THEN";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;                        
                        
                    case 47:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'A':
                                estado = 48;
                                lido = lido + (char) charLido;
                                break;
                            case (int) 'U':
                                estado = 99;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 48:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'L':
                                estado = 49;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 49:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'S':
                                estado = 50;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 50:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'E':
                                estado = 51;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;

                    case 51:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "FALSE";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                        
                        
                    case 52:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'O':
                                estado = 53;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 53:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'T':
                                estado = 54;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;

                    case 54:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                                token.type = "NOT";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                        
                        
                    case 55:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'N':
                                estado = 56;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 56:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'D':
                                estado = 57;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;

                    case 57:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':

                                token.type = "AND";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                        
                        
                        
                        
                        
                        
                        
                        
                        
                    case 58:
                        switch (charLido) {
                            case -1://EOF
                            case (int) '\n': case 13://EOF
                            case 32://Espaço: fim do identificador
                            case (int) ';'://; fim do identificador
                            case (int) '(':
                            case (int) '[':
                            case (int) ',':
                            case (int) '!':
                            case (int) '=':
                            case (int) '<':
                            case (int) '>':
                            case (int) '+':
                            case (int) '-':
                            case (int) '/':
                            case (int) '*':
                            case (int) '{':
                            case (int) ')':
                                token.type = "identificador";
                                token.setWord(lido);
                                estado = 0;
                                return token;
                                //break;
                            case (int) '}':
                            case (int) ']':
//agora os casos em que o cara fez besteira... por enquanto tah igual, mas vamos ver mais pra frente o que fazer
                                token.type = "identificador";
                                token.setWord(lido);
                                estado = 0;
                                return token;                                
                            default://
                                //token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;

                    case 59:
                        switch (charLido) {
                            case (int) '0':
                            case (int) '1':
                            case (int) '2':
                            case (int) '3':
                            case (int) '4':
                            case (int) '5':
                            case (int) '6':
                            case (int) '7':
                            case (int) '8':
                            case (int) '9':
                                lido = lido + (char) charLido;
                                estado = 59;
                                break;
                            case -1://EOF
                            case (int) '\n': case 13://EOF
                            case 32://Espaço: fim do numero
                            case (int) ';'://; fim do numero
                            case (int) '(':
                            case (int) '[':
                            case (int) ',':
                            case (int) '!':
                            case (int) '=':
                            case (int) '<':
                            case (int) '>':
                            case (int) '+':
                            case (int) '-':
                            case (int) '/':
                            case (int) '*':
                            case (int) '{':
                            case (int) ')':
                            case (int) '}':
                            case (int) ']':
                                token.type = "NUMERO";
                                token.word = lido;
                                estado = 0;
                                return token;
                                //break;
//agora os casos em que o cara fez besteira... por enquanto tah igual, mas vamos ver mais pra frente o que fazer
                            default://
                                //token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;

                
                    case 60:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                                token.type = "=";
                                
                                estado = 0;
                                return token;
                                //break;
                            case (int) '=':
                                estado = 61;
                                break;
                            default://O default aqui é o cara ter acabado... vou mandar pro 0
                                token.type = "=";
                                estado = 0;
                                return token;
                        }
                        break;

                    case 61:
                        switch (charLido) {//esse no final das contas nao tem o que fazer... é voltar pro 0 mesmo...
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                                token.type = "==";
                                
                                estado = 0;
                                return token;
                                //break;
                            default://O default aqui é o cara ter acabado... vou mandar pro 0
                                token.type = "==";
                                estado = 0;
                                return token;
                        }

                    case 62:
                        switch (charLido) {
                            case -1://EOF: erro
                            case 32://Espaço: erro
                            case (int) '\n': case 13://erro
                            case (int) ';':
                            case (int) '(':
                            case (int) '[':
                            case (int) ')':
                            case (int) ']'://erro
                                token.type = "!";
                                estado = 0;
                                return token;
                            case (int) '=':
                                estado = 63;
                                break;
                            default:
                                token.type = "!";
                                estado = 0;
                                return token;
                            
                        }
                        break;

                    case 63:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                                token.type = "!=";
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "!=";
                                estado = 0;
                                return token;
                        }

                
                    case 64:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                                token.type = "<";
                                estado = 0;
                                return token;
                                //break;
                            case (int) '=':
                                estado = 65;
                                break;
                            default://O default aqui é o cara ter acabado... vou mandar pro 0
                                token.type = "=";
                                estado = 0;
                                return token;
                        }
                        break;

                    case 65:
                        switch (charLido) {//esse no final das contas nao tem o que fazer... é voltar pro 0 mesmo...
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                                token.type = "<=";
                                estado = 0;
                                return token;
                                //break;
                            default://O default aqui é o cara ter acabado... vou mandar pro 0
                                token.type = "<=";
                                estado = 0;
                                return token;
                        }
                
                    case 66:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                                token.type = ">";
                                
                                estado = 0;
                                return token;
                                //break;
                            case (int) '=':
                                estado = 67;
                                break;
                            default://O default aqui é o cara ter acabado... vou mandar pro 0
                                token.type = ">";
                                estado = 0;
                                return token;
                        }
                        break;

                    case 67:
                        switch (charLido) {//esse no final das contas nao tem o que fazer... é voltar pro 0 mesmo...
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                                token.type = ">=";
                                
                                estado = 0;
                                return token;
                                //break;
                            default://O default aqui é o cara ter acabado... vou mandar pro 0
                                token.type = ">=";
                                estado = 0;
                                return token;
                        }

                    case 70:
                        switch (charLido) {
                            case -1://EOF
                            case (int) '\n': case 13://EOF
                            case 32://Espaço: fim do identificador
                            case (int) '&'://; fim do identificador
                            case (int) '(':
                            case (int) '[':
                            case (int) ',':
                            case (int) '!':
                            case (int) '=':
                            case (int) '<':
                            case (int) '>':
                            case (int) '+':
                            case (int) '-':
                            case (int) '/':
                            case (int) '*':
                            case (int) '{':
                            case (int) ')'://Fez besteira
                                token.type = "CADEIA";
                                token.setWord(lido);
                                estado = 0;
                                return token;
                                //break;
                            case (int) '\'':
                                token.type = "CADEIA";
                                token.setWord(lido);
                                estado = 0;
                                charLido = -10;
                                return token;                                
                            default://
                                //token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 70;
                            break;
                        }
                        break;
                        
                        
                    case 80:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'E':
                                estado = 81;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 81:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'T':
                                estado = 82;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 82:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'U':
                                estado = 83;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 83:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'R':
                                estado = 84;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 84:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'N':
                                estado = 85;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;

                    case 85:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                                token.type = "RETURN";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                        

                    case 90:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'H':
                                estado = 91;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 91:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'I':
                                estado = 92;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 92:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'L':
                                estado = 93;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 93:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'E':
                                estado = 94;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 94:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) ']':
                            case (int) '{':
                                token.type = "WHILE";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                        
                    case 95:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) ')':
                            case (int) '(':
                            case (int) ']':
                            case (int) '[':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'O':
                                estado = 96;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 96:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n': case 13:
                            case (int) ';':
                            case (int) '(':
                            case (int) ']':
                            case (int) '{':
                                token.type = "DO";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    
                        
                        case 99:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'N':
                                estado = 100;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 100:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'C':
                                estado = 101;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 101:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'A':
                                estado = 102;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;
                    case 102:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n': case 13:
                            case (int) ';':
                                token.type = "identificador";
                                token.word = lido;
                                estado = 0;
                                return token;
                            case (int) 'O':
                                estado = 103;
                                lido = lido + (char) charLido;
                                break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;

                    case 103:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n': case 13:
                            case (int) ';':
                                token.type = "FUNCAO";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                lido = lido + (char) charLido;
                                estado = 58;
                            break;
                        }
                        break;

                    
                    default:
                        return null;

                        
                        
                        
                        
                }//Fim do case

                
                

                
//                if ((char)charLido == 'T'){
//
//                    //Quando consumir tudo, deixar o -10
//                    //Quando nao, deixar o q ficou pq começarà o novo token
//                    charLido = -10;
//                    break;
//            }
                
                charLido = fileStream.read();
            }
            //return null;
        } catch(FileNotFoundException fnfE){
            System.out.println("FileNotFoundException: " + fnfE.getMessage());
        } catch(IOException ioE){
            System.out.println("IOException: " + ioE.getMessage());
        }
        return token;
    }
    
    int digereChar(int hummm) {
        
        if (hummm == -1) return -1;
        //Retorna -1 quando é fim de arquivo
        
        //Retorna 0 quando o caracter foi aceito
        return 0;
    }
}
