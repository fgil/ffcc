/*
 * TabelaVariaveis.java
 *
 * Created on November 24, 2007, 6:38 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package horae.util;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Fernando
 */
public class TabelaVariaveis {
    
    private static TabelaVariaveis tVariaveis;
    
    public ArrayList tabela = new ArrayList(); 
    
    private TabelaVariaveis() {}
    
    public static TabelaVariaveis getInstance() {
        if (tVariaveis == null) {
            tVariaveis = new TabelaVariaveis();
        }
        return tVariaveis;
    }

    public void adicionaVariavel(Variavel novaVariavel) {
//        for (Iterator it = names.iterator(); it.hasNext(); ) {
//          String name = (String)it.next();
//            System.out.println(name.charAt(0));
//}
        boolean inclui = true;
        for(Iterator it = tabela.iterator(); it.hasNext();) {
            Variavel temp = (Variavel)it.next();
            if (temp.escopo.equals(novaVariavel.escopo)) {
                    if (temp.identificador.equals(novaVariavel.identificador)) {
                System.out.println("OPS!! Foi inserida uma variavel repetida. ERRO");
                inclui = false;
                //(Variavel)novaVariavel;
                }
            }
 
        }
        if (inclui) tabela.add(novaVariavel);
        
    }
    
}