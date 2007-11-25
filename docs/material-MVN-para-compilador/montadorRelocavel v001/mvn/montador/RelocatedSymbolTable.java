package mvn.montador;
import java.util.Map;
import java.util.HashMap;

import mvn.util.SymbolTable;

/**
 * Tabela de símbolos que permite a distinção de símbolos relocáveis e símbolos
 * externos.
 * @author FLevy
 * @version 07.11.2005
 */
public class RelocatedSymbolTable extends SymbolTable {
	protected Map dataTable;
	
	public RelocatedSymbolTable() {
		super();
		dataTable = new HashMap();
	}
	
    /**
     * Estrutura de dados de apoio para permitir saber o endereço e se o
     * símbolo é relocável.
     */
    protected class SymbolData {
      boolean relocable;
      boolean external;
    }
    
	/**
	 * Define um valor para um símbolo.<br>
	 * O símbolo já deve existir na tabela.
	 *
	 * @param sym
	 *            O símbolo.
	 * @param address
	 *            O valor.
     * @param relocable Se o símbolo é relocável ou não.
     * @param external Se o símbolo é externo ou não.
	 * @return Verdadeiro caso a definição tenha obtido sucesso, falso caso
	 *         contrário.
	 */
	public boolean setSymbolValue(String sym, String address, boolean relocable, boolean external) {
		boolean result = false;
		if (tab.containsKey(sym.toUpperCase())) {
			super.setSymbolValue(sym, address);
            SymbolData data = new SymbolData();
            data.relocable = relocable;
            data.external = external;
            dataTable.put(sym.toUpperCase(), data);
            result = true;
		}
		return result;
	}
	
    /**
     * Verifica se o símbolo é relocável.
     * @param sym O símbolo o qual se deseja saber se é relocável.
     * @return Verdadeiro caso ele seja relocável.
     */
    public boolean isRelocable(String sym) {
      SymbolData temp = (SymbolData) dataTable.get(sym.toUpperCase());
      if (temp == null) return false;
      else return temp.relocable;
    }
    
    /**
     * Verifica se o símbolo é externo.
     * @param sym O símbolo o qual se deseja saber se é externo.
     * @return Verdadeiro caso ele seja externo.
     */        
    public boolean isExternal(String sym) {
        SymbolData temp = (SymbolData) dataTable.get(sym.toUpperCase());
        if (temp == null) return false;
        else return temp.external;        	
    }
}