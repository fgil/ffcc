/*
 * Estado.java
 *
 * Created on November 3, 2007, 3:19 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package horae.util;

/**
 *
 * @author Fernando
 */
public class Estado {
    private Transicao[] transicoes;
    
    /** Creates a new instance of Estado */
    public Estado() {
    }

    public void criaTransicoes(int qtdTransicoes) {
        transicoes = new Transicao[qtdTransicoes];
    }
    
    public Transicao proximoEstado(String tokenRecebido) {
        for(int i = 0; i < transicoes.length; i++){
            if (transicoes[i].tokenEsperado == tokenRecebido) return transicoes[i];
        }
        return null;//Se ele retornou null, siginifica que nao tem rota pra esse token... panico!!!
    }
    
    
    public void setTransicao(int indice, String tokenEsperado, int proximoEstado, String proximaMaquina){
        transicoes[indice].tokenEsperado = tokenEsperado;
        transicoes[indice].proximaMaquina = proximaMaquina;
        transicoes[indice].proximoEstado = proximoEstado;
    }
}
