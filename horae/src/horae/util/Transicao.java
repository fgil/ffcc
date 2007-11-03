/*
 * Transicao.java
 *
 * Created on November 3, 2007, 3:28 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package horae.util;

/**
 *
 * @author Fernando
 */
public class Transicao {
    
    public String tokenEsperado;
    /*Proximo estado ao receber o token esperado. Pode ser alcançadoù
     * as vezes so depois que volta de uma maquina nova.
     */
    public int proximoEstado;
    /*proxima Maquina avisa se sera necessario chamar outra maquina*/
    public String proximaMaquina;
    
    /**
     * Creates a new instance of Transicao
     */
    public Transicao() {
    }
    
}
