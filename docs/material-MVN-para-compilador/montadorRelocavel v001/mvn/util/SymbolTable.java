package mvn.util;
import java.util.Map;
import java.util.HashMap;

/**
 * Representa��o de uma tabela de s�mbolos.<br>
 * O s�mbolo � considerado igual independente da presen�a de letras min�sculas
 * ou mai�sculas diferentes em outras chamadas (<i>case insensitive</i>).
 *
 * @author RRocha
 * @version 04.10.2005
 */
public class SymbolTable {

	protected Map tab;

	/**
	 * Cria a tabela de s�mbolos.
	 */
	public SymbolTable() {
		this.tab = new HashMap();
	}

	/**
	 * Verifica se um s�mbolo est� na tabela.
	 *
	 * @param sym
	 *            O s�mbolo a ser verificado.
	 */
	public boolean symbolInTable(String sym) {
		return tab.containsKey(sym.toUpperCase());
	}

	/**
	 * Define um valor para um s�mbolo.<br>
	 * O s�mbolo j� deve existir na tabela.
	 *
	 * @param sym
	 *            O s�mbolo.
	 * @param address
	 *            O valor.
	 * @return Verdadeiro caso a defini��o tenha obtido sucesso, falso caso
	 *         contr�rio.
	 */
	public boolean setSymbolValue(String sym, String address) {
		boolean result = false;
		if (tab.containsKey(sym.toUpperCase())) {
			tab.put(sym.toUpperCase(), address);
            result = true;
		}
		return result;
	}

	/**
	 * Verifica se um s�mbolo j� foi definido (j� h� o endere�o).
	 *
	 * @param sym
	 *            O s�mbolo o qual se deseja saber se j� est� definido.
	 */
	public boolean definedSymbol(String sym) {
		boolean result = false;
		if (tab.containsKey(sym.toUpperCase()))
			result = (tab.get(sym.toUpperCase()) != null);
		return result;
	}

	/**
	 * Insere um s�mbolo na tabela.
	 *
	 * @param sym
	 *            O s�mbolo a ser inserido.
	 * @return Falso caso o s�mbolo j� esteja definido, verdadeiro caso
	 * contr�rio.
	 */
	public boolean insertSymbol(String sym) {
		boolean result = true;
		if (symbolInTable(sym))
			result = false;
		else
			tab.put(sym.toUpperCase(), null);
		return result;
	}

	/**
	 * Obt�m o valor definido para um s�mbolo.
	 *
	 * @param sym
	 *            O s�mbolo o qual se deseja saber o valor.
	 * @return O valor do s�mbolo.
	 */
	public String getSymbolValue(String sym) {
          String result = (String) tab.get(sym.toUpperCase());
          if (result == null) return "";
          return result;
	}
}