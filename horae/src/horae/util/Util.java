/*
 * Util.java
 *
 * Created on December 1, 2007, 8:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package horae.util;

/**
 *
 * @author Fernando
 */
public class Util {
    
    /** Creates a new instance of Util */
    public Util() {
    }
    
    public static String decimalToHex3(String decimal){
        
        if(decimal != null){
        
            String retorno = Integer.toHexString(Integer.parseInt(decimal));
            while(retorno.length() < 4){
                retorno = "0" + retorno;
            }

            return retorno;
        } else {
            return "0000";
        }
    }
    
    public static void main(String[] args) {
        for(Integer i = 0; i < 4096; i++){
            System.out.println(decimalToHex3(i.toString()));
        }
    }
}
