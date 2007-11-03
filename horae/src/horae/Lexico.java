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
                System.out.println((char)charLido + " " + (int) charLido +
                        "Estado Atual: " + (int) estado);
                switch(estado) {
                    case 0:
                        switch (charLido) {
                            case -1://EOF
                                return null;
                            case 32://Espaço: desencana
                            case (int) '\n':    
                                estado = 0;
                                break;
                            case (int) 'S':
                                estado = 1;
                                break;
                            case (int) 'E':
                                estado = 6;
                                break;
                            case (int) 'I':
                                estado = 15;
                                break;
                            case (int) 'B':
                                estado = 22;
                                break;
                            case (int) 'C':
                                estado = 29;
                                break;
                            case (int) 'O':
                                estado = 33;
                                break;
                            case (int) 'T':
                                estado = 40;
                                break;
                            case (int) 'F':
                                estado = 47;
                                break;
                            case (int) 'N':
                                estado = 52;
                                break;
                            case (int) 'A':
                                estado = 55;
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
                            case (int) ',':
                                token.type = ",";
                                estado = 0;
                                charLido = -10;
                                return token;
                            case (int) '\'':
                                token.type = "'";
                                estado = 0;
                                charLido = -10;
                                return token;
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
                                break;
                            default:
                                estado = 58;
                                break;
                        }
                        break;
                    case 1:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'T':
                                estado = 2;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                                break;
                        }
                        break;
                    case 2:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'A':
                                estado = 3;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                    case 3:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'R':
                                estado = 4;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                                break;
                        }
                        break;
                    case 4:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'T':
                                estado = 5;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                                break;
                        }
                        break;
                    case 5:
                        switch (charLido) {
                            case -1://EOF
                                token.type = "START";
                                estado = 0;
                                return token;

                            case 32://Espaço: fim do token
                            case (int) '\n':
                            case (int) ';':
                                token.type = "START";
                                
                                estado = 0;
                                return token;
                                //break;

                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                    case 6:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'N':
                                estado = 7;
                                break;
                            case (int) 'L':
                                estado = 12;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                    case 7:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'D':
                                estado = 8;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                    case 8:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n':
                            case (int) ';':
                                token.type = "END";
                                
                                estado = 0;
                                return token;
                                //break;
                            case (int) 'W':
                                estado = 9;
                                break;
                            case (int) 'I':
                                estado = 10;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                    case 9:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n':
                            case (int) ';':
                                token.type = "ENDW";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                    case 10:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'F':
                                estado = 11;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                    case 11:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n':
                            case (int) ';':
                                token.type = "ENDIF";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;

                    case 12:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'S':
                                estado = 13;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                        
                    case 13:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'E':
                                estado = 14;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                        
                    case 14:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n':
                            case (int) ';':
                                token.type = "ELSE";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                        
                    case 15:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'N':
                                estado = 16;
                                break;
                            case (int) 'F':
                                estado = 21;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                        
                    case 16:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'T':
                                estado = 17;
                                break;
                            case (int) 'P':
                                estado = 18;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;

                    case 17:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n':
                            case (int) ';':
                                token.type = "INT";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                        
                    case 18:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'U':
                                estado = 19;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                        
                    case 19:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'T':
                                estado = 20;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                    case 20:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n':
                            case (int) ';':
                                token.type = "INPUT";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                    case 21:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n':
                            case (int) '(':
                            case (int) ';':
                                token.type = "IF";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                        
                        
                        
                        
                    case 22:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'O':
                                estado = 23;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                    case 23:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'O':
                                estado = 24;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                    case 24:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'L':
                                estado = 25;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                    case 25:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'E':
                                estado = 26;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                    case 26:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'A':
                                estado = 27;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                    case 27:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'N':
                                estado = 28;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;

                    case 28:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n':
                            case (int) ';':
                                token.type = "BOOLEAN";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                        
                    case 29:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'H':
                                estado = 30;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                    case 30:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'A':
                                estado = 31;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                    case 31:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'R':
                                estado = 32;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;

                    case 32:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n':
                            case (int) ';':
                                token.type = "CHAR";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                    
                    case 33:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'R':
                                estado = 34;
                                break;
                            case (int) 'U':
                                estado = 35;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                    case 34:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n':
                            case (int) ';':
                                token.type = "OR";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;

                    case 35:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'T':
                                estado = 36;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                    case 36:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'P':
                                estado = 37;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                    case 37:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'U':
                                estado = 38;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                    case 38:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'T':
                                estado = 39;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;

                    case 39:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n':
                            case (int) ';':
                                token.type = "INPUT";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                        
                    case 40:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'H':
                                estado = 44;
                                break;
                            case (int) 'R':
                                estado = 41;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;

                    case 41:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'U':
                                estado = 42;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                    case 42:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'E':
                                estado = 43;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;

                    case 43:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n':
                            case (int) ';':
                                token.type = "TRUE";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;                        
                        
                    case 44:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'E':
                                estado = 45;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                    case 45:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'N':
                                estado = 46;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;

                    case 46:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n':
                            case (int) ';':
                                token.type = "THEN";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;                        
                        
                    case 47:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'A':
                                estado = 48;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                    case 48:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'L':
                                estado = 49;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                    case 49:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'S':
                                estado = 50;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                    case 50:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'E':
                                estado = 51;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;

                    case 51:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n':
                            case (int) ';':
                                token.type = "ELSE";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                        
                        
                    case 52:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'O':
                                estado = 53;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                    case 53:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'T':
                                estado = 54;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;

                    case 54:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n':
                            case (int) ';':
                                token.type = "NOT";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                        
                        
                    case 55:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'N':
                                estado = 56;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                    case 56:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: desencana
                            case (int) '\n':
                            case (int) ';':
                                token.type = "identificador";
                                estado = 0;
                                return token;
                            case (int) 'D':
                                estado = 57;
                                break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;

                    case 57:
                        switch (charLido) {
                            case -1://EOF
                            case 32://Espaço: fim do token
                            case (int) '\n':
                            case (int) ';':
                                token.type = "AND";
                                
                                estado = 0;
                                return token;
                                //break;
                            default:
                                token.type = "identificador";
                                estado = 58;
                            break;
                        }
                        break;
                        
                        
                        
                        
                        
                        
                        
                        
                        
                    case 58:
                        switch (charLido) {
                            case -1://EOF
                            case (int) '\n'://EOF
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
                                estado = 0;
                                return token;
                                //break;
                            case (int) '}':
                            case (int) ']':
//agora os casos em que o cara fez besteira... por enquanto tah igual, mas vamos ver mais pra frente o que fazer
                                token.type = "identificador";
                                estado = 0;
                                return token;                                
                            default://
                                //token.type = "identificador";
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
                                estado = 59;
                                break;
                            case -1://EOF
                            case (int) '\n'://EOF
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
                            case (int) '}':
                            case (int) ']':
                                token.type = "numero";
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
                            case (int) '\n':
                            case (int) ';':
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
                            case (int) '\n':
                            case (int) ';':
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
                            case (int) '\n'://erro
                            case (int) ';'://erro
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
                            case (int) '\n':
                            case (int) ';':
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
                            case (int) '\n':
                            case (int) ';':
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
                            case (int) '\n':
                            case (int) ';':
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
                            case (int) '\n':
                            case (int) ';':
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
                            case (int) '\n':
                            case (int) ';':
                                token.type = ">=";
                                
                                estado = 0;
                                return token;
                                //break;
                            default://O default aqui é o cara ter acabado... vou mandar pro 0
                                token.type = ">=";
                                estado = 0;
                                return token;
                        }

                    
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
