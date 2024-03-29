/*
 * Simbolo.java
 *
 * Created on November 24, 2007, 2:52 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package horae.util;

/**
 *
 * @author Fernando
 */
public class Simbolo {
    
    private String tipoDeSimbolo;
    private String identificador;
    private String tipoDeDado;
    private String valorInicial;
    private String escopo;
    private int dimensaoX;
    private int dimensaoY;
    private boolean declarado = false;
    
    /**
     * Creates a new instance of Simbolo
     */
    public Simbolo() {
    }

    public String getTipoDeSimbolo() {
        return tipoDeSimbolo;
    }

    public void setTipoDeSimbolo(String tipoDeSimbolo) {
        this.tipoDeSimbolo = tipoDeSimbolo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getTipoDeDado() {
        return tipoDeDado;
    }

    public void setTipoDeDado(String tipoDeDado) {
        this.tipoDeDado = tipoDeDado;
    }

    public String getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(String posicaoMemoria) {
        this.valorInicial = posicaoMemoria;
    }

    public String getEscopo() {
        return escopo;
    }

    public void setEscopo(String escopo) {
        this.escopo = escopo;
    }

    public int getDimensaoX() {
        return dimensaoX;
    }

    public void setDimensaoX(int dimensaoX) {
        this.dimensaoX = dimensaoX;
    }

    public int getDimensaoY() {
        return dimensaoY;
    }

    public void setDimensaoY(int dimensaoY) {
        this.dimensaoY = dimensaoY;
    }
    
    public void setDeclarado(boolean declarado){
        this.declarado = declarado;
    }
    
    public boolean isDeclarado(){
        return declarado;
    }
}
