/*
 * Semantico.java
 *
 * Created on November 25, 2007, 8:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package horae.semantico;

import java.io.FileOutputStream;
import horae.util.Simbolo;

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
    }
    

    public void closeFile(){
        try {
            fileStream.write(("HM /00" + (char)10).getBytes());
            //String declaracao = (String)declaracoes.removeTop();
//            while(declaracao!=null){
//                fileStream.write((declaracao + (char)10 + (char)13).getBytes());
//                declaracao = (String)declaracoes.removeTop();
//            }
            fileStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public void addVariavel(Simbolo sTemp){
        String temp = sTemp.getIdentificador() + " K /0000";
        writetoFile(temp);
    }
    
    public void addAtribuicao(String origem, String destino){
        String temp = "LD " + origem;
        writetoFile(temp);
        temp = "MM " + destino;
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
}
