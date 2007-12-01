/*
 * TabelaSimbolos.java
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
public class TabelaSimbolos {
    
    private static TabelaSimbolos tSimbolos;
    
    public ArrayList tabela = new ArrayList(); 
    
    private TabelaSimbolos() {}
    
    public static TabelaSimbolos getInstance() {
        if (tSimbolos == null) {
            tSimbolos = new TabelaSimbolos();
        }
        return tSimbolos;
    }

    public void adicionaSimbolo(Simbolo novoSimbolo) {
//        for (Iterator it = names.iterator(); it.hasNext(); ) {
//          String name = (String)it.next();
//            System.out.println(name.charAt(0));
//}
        boolean inclui = true;
        for(Iterator it = tabela.iterator(); it.hasNext();) {
            Simbolo temp = (Simbolo)it.next();
            if (temp.getEscopo().equals(novoSimbolo.getEscopo())) {
                    if (temp.getIdentificador().equals(novoSimbolo.getIdentificador())) {
                System.out.println("OPS!! Foi inserida uma variavel repetida. ERRO");
                inclui = false;
                //(Simbolo)novoSimbolo;
                }
            }
 
        }
        if (inclui) tabela.add(novoSimbolo);
        
    }
    
    public void adicionaSimbolo(String escopo,String tipo, String id, String valor ) {
        Simbolo temp = new Simbolo();
        temp.setEscopo(escopo);
        temp.setTipoDeDado(tipo);
        temp.setTipoDeSimbolo("CONSTANTE");
        temp.setIdentificador(id);
        temp.setValorInicial(valor);
        adicionaSimbolo(temp);
    }
    
    public String toSring() {
        String stemp = new String();
        for(Iterator it = tabela.iterator(); it.hasNext();) {
            Simbolo temp = (Simbolo)it.next();
            stemp = stemp + "\n" + temp.getTipoDeSimbolo() +
                    " - " + temp.getTipoDeDado() +" - " + temp.getIdentificador() +" - "
                    + temp.getEscopo() +";";
        }
        return stemp;
    }
    
    public Simbolo procuraSimbolo(String escopo, String identificador) {
        System.out.println("ESCOPO: " + escopo + ", " + identificador);
        for(Iterator it = tabela.iterator(); it.hasNext();) {
            Simbolo temp = (Simbolo)it.next();
            if (temp.getEscopo().equals(escopo)) {
                if (temp.getIdentificador().equals(identificador)) {
                    System.out.println("AE!! Foi encontrada a variavel");
                    return temp;
                }
            }
        }
        System.out.println("AHH!! Variavel nao encontrada");
        return null;
    }
    
}