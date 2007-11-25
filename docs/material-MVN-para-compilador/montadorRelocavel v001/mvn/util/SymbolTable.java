package mvn.util;
import java.util.Map;
import java.util.HashMap;

/**
 * Representação de uma tabela de símbolos.<br>
 * O símbolo é considerado igual independente da presença de letras minúsculas
 * ou maiúsculas diferentes em outras chamadas (<i>case insensitive</i>).
 *
 * @author RRocha
 * @version 04.10.2005
 */
public class SymbolTable {

	protected Map tab;

	/**
	 * Cria a tabela de símbolos.
	 */
	public SymbolTable() {
		this.tab = new HashMap();
	}

	/**
	 * Verifica se um símbolo está na tabela.
	 *
	 * @param sym
	 *            O símbolo a ser verificado.
	 */
	public boolean symbolInTable(String sym) {
		return tab.containsKey(sym.toUpperCase());
	}

	/**
	 * Define um valor para um símbolo.<br>
	 * O símbolo já deve existir na tabela.
	 *
	 * @param sym
	 *            O símbolo.
	 * @param address
	 *            O valor.
	 * @return Verdadeiro caso a definição tenha obtido sucesso, falso caso
	 *         contrário.
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
	 * Verifica se um símbolo já foi definido (já há o endereço).
	 *
	 * @param sym
	 *            O símbolo o qual se deseja saber se já está definido.
	 */
	public boolean definedSymbol(String sym) {
		boolean result = false;
		if (tab.containsKey(sym.toUpperCase()))
			result = (tab.get(sym.toUpperCase()) != null);
		return result;
	}

	/**
	 * Insere um símbolo na tabela.
	 *
	 * @param sym
	 *            O símbolo a ser inserido.
	 * @return Falso caso o símbolo já esteja definido, verdadeiro caso
	 * contrário.
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
	 * Obtêm o valor definido para um símbolo.
	 *
	 * @param sym
	 *            O símbolo o qual se deseja saber o valor.
	 * @return O valor do símbolo.
	 */
	public String getSymbolValue(String sym) {
          String result = (String) tab.get(sym.toUpperCase());
          if (result == null) return "";
          return result;
	}
}