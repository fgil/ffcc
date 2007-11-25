package mvn.montador;
import java.util.Map;
import java.util.HashMap;

import mvn.util.SymbolTable;

/**
 * Tabela de s�mbolos que permite a distin��o de s�mbolos reloc�veis e s�mbolos
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
     * Estrutura de dados de apoio para permitir saber o endere�o e se o
     * s�mbolo � reloc�vel.
     */
    protected class SymbolData {
      boolean relocable;
      boolean external;
    }
    
	/**
	 * Define um valor para um s�mbolo.<br>
	 * O s�mbolo j� deve existir na tabela.
	 *
	 * @param sym
	 *            O s�mbolo.
	 * @param address
	 *            O valor.
     * @param relocable Se o s�mbolo � reloc�vel ou n�o.
     * @param external Se o s�mbolo � externo ou n�o.
	 * @return Verdadeiro caso a defini��o tenha obtido sucesso, falso caso
	 *         contr�rio.
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
     * Verifica se o s�mbolo � reloc�vel.
     * @param sym O s�mbolo o qual se deseja saber se � reloc�vel.
     * @return Verdadeiro caso ele seja reloc�vel.
     */
    public boolean isRelocable(String sym) {
      SymbolData temp = (SymbolData) dataTable.get(sym.toUpperCase());
      if (temp == null) return false;
      else return temp.relocable;
    }
    
    /**
     * Verifica se o s�mbolo � externo.
     * @param sym O s�mbolo o qual se deseja saber se � externo.
     * @return Verdadeiro caso ele seja externo.
     */        
    public boolean isExternal(String sym) {
        SymbolData temp = (SymbolData) dataTable.get(sym.toUpperCase());
        if (temp == null) return false;
        else return temp.external;        	
    }
}