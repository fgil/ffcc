/*
 * FilaTest.java
 * JUnit based test
 *
 * Created on November 3, 2007, 4:33 PM
 */

package horae.util;

import junit.framework.*;

/**
 *
 * @author Fernando
 */
public class FilaTest extends TestCase {
    
    private Fila fila;
    
    public FilaTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
        fila = new Fila();
        
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of adicionar method, of class horae.util.Fila.
     */
    public void testAdicionar() {       
        Object novoObjeto1 = new String("teste1");
        Object novoObjeto2 = new String("teste2");
        Object novoObjeto3 = new String("teste3");
        Object teste;
        
        System.out.println("Adicionar seguido de remover");
        
        fila.adicionar(novoObjeto1);
        
        teste = fila.remover();
        
        assertEquals("Erro ao adicionar e remover com a fila vazia.",novoObjeto1,teste);
        
        fila.adicionar(novoObjeto2);
        fila.adicionar(novoObjeto3);
        
        teste = fila.remover();
        assertEquals("Com 2 objetos na fila, erro ao remover o primeiro.",novoObjeto2, teste);
        
        teste = fila.remover();
        assertEquals("Com 2 objetos na fila, erro ao remover o ultimo (segundo colocado).",novoObjeto3, teste);
        
        assertEquals("A fila não esvaziou.",0,fila.getTamanho());
        
    }
    
}
