package mvn.montador;
import java.util.HashMap;
import java.util.Map;

/**
 * Tabela de pseudo-instruções.<br>
 * As pseudo-instruções são instruções definidas pelo montador para facilitar a
 * programação.<br>
 * Para essa classe é um sigleton (apenas uma instância dela é acessível).
 * 
 * @author RRocha
 * @version 04.04.2005
 * @version 10.10.2005 (refatoramento - FLevy)
 * @version 30.10.2005 (novas instruções para o montador relocável - FLevy)
 */
public final class PseudoTable {
	// Código das pseudo-instruções
	static final int ORG = 0; // origem absoluta

	static final int DC = 1; // definição de constante

	static final int END = 2; // fim físico

	static final int MEM = 3; // reserva bloco de memória

	static final int ORGR = 4; // origem relativa

	static final int ENP = 5; // ponto de entrada

	static final int EXP = 6; // ponto de entrada externo

	// Strings das pseudo-instruçõe
	static final String ORG_STR = "@";

	static final String DC_STR = "K";

	static final String END_STR = "#";

	static final String MEM_STR = "$";

	static final String ORGR_STR = "&";

	static final String ENP_STR = ">";

	static final String EXP_STR = "<";

	// Singleton
	private static PseudoTable pt;

	// Variável local
	private Map pseudos;

	/**
	 * Construtor de uma tabela de pseudo-instruções.<br>
	 * Para haver apenas uma instância da tabela de pseudo-instruções, o
	 * construtor é protegido.
	 */
	protected PseudoTable() {
		pseudos = new HashMap();
		pseudos.put(ORG_STR, ORG + "");
		pseudos.put(DC_STR, DC + "");
		pseudos.put(END_STR, END + "");
		pseudos.put(MEM_STR, MEM + "");
		pseudos.put(ORGR_STR, ORGR + "");
		pseudos.put(ENP_STR, ENP + "");
		pseudos.put(EXP_STR, EXP + "");
	}

	/**
	 * Obtêm o código da pseudo-instrução.
	 * 
	 * @param pseudo
	 *            A pseudo-instrução a qual se deseja saber o código
	 *            (independente se em letras maiúsculas ou minúsculas)..
	 * @return O código.
	 */
	public int getPseudoCode(String pseudo) {
		return Integer.parseInt((String) pseudos.get(pseudo.toUpperCase()));
	}

	/**
	 * Verifica se a pseudo-instrução está definida.
	 * 
	 * @param pseudo
	 *            A pseudo-instrução a ser verifida (independente se em letras
	 *            maiúsculas ou minúsculas).
	 * @return Verdadeiro caso a instrução seja definida, falso caso contrário.
	 */
	public boolean pseudoDefined(String pseudo) {
		return pseudos.containsKey(pseudo.toUpperCase());
	}

	/**
	 * Obtêm a instância disponível da tabela de pseudo-instruções.
	 * 
	 * @return A tabela de pseudo-instruções.
	 */
	public static PseudoTable getTable() {
		if (pt == null) {
			pt = new PseudoTable();
		}
		return pt;
	}
}