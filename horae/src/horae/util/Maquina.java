/*
 * Maquina.java
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
public class Maquina {
    public Estado[] estados;
    
    public int A_Programa = 1;
    public int A_Declaracao = 2;
    public int A_DeclaracaoFuncao = 3;
    public int A_Comando = 4;
    public int A_Expressao = 5;
    public int A_ExpAritmetica = 6;
    /** Creates a new instance of Maquina */
    public Maquina(int qtdEstados) {
        this.estados = new Estado[qtdEstados];
    }
    
    
    //Metodo usado para montar a maquina
    public void setTransicao(int estado, int indiceRota, String tokenEsperado,
            int proximoEstado, int proximaMaquina, boolean consome){
        this.estados[estado].setTransicao(indiceRota, tokenEsperado,
                proximoEstado, proximaMaquina, consome);
    }
    
    public void setTransicao(int estado, int indiceRota, String tokenEsperado,
            int proximoEstado, int proximaMaquina, boolean consome, int caso){
        this.estados[estado].setTransicao(indiceRota, tokenEsperado,
                proximoEstado, proximaMaquina, consome, caso);
    }

    public void criaTransicoes (int estado, int qtdTransicoes) {
        this.estados[estado] = new Estado(qtdTransicoes);
    }
    
}
