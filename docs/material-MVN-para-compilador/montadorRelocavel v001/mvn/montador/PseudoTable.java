package mvn.montador;
import java.util.HashMap;
import java.util.Map;

/**
 * Tabela de pseudo-instru��es.<br>
 * As pseudo-instru��es s�o instru��es definidas pelo montador para facilitar a
 * programa��o.<br>
 * Para essa classe � um sigleton (apenas uma inst�ncia dela � acess�vel).
 * 
 * @author RRocha
 * @version 04.04.2005
 * @version 10.10.2005 (refatoramento - FLevy)
 * @version 30.10.2005 (novas instru��es para o montador reloc�vel - FLevy)
 */
public final class PseudoTable {
	// C�digo das pseudo-instru��es
	static final int ORG = 0; // origem absoluta

	static final int DC = 1; // defini��o de constante

	static final int END = 2; // fim f�sico

	static final int MEM = 3; // reserva bloco de mem�ria

	static final int ORGR = 4; // origem relativa

	static final int ENP = 5; // ponto de entrada

	static final int EXP = 6; // ponto de entrada externo

	// Strings das pseudo-instru��e
	static final String ORG_STR = "@";

	static final String DC_STR = "K";

	static final String END_STR = "#";

	static final String MEM_STR = "$";

	static final String ORGR_STR = "&";

	static final String ENP_STR = ">";

	static final String EXP_STR = "<";

	// Singleton
	private static PseudoTable pt;

	// Vari�vel local
	private Map pseudos;

	/**
	 * Construtor de uma tabela de pseudo-instru��es.<br>
	 * Para haver apenas uma inst�ncia da tabela de pseudo-instru��es, o
	 * construtor � protegido.
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
	 * Obt�m o c�digo da pseudo-instru��o.
	 * 
	 * @param pseudo
	 *            A pseudo-instru��o a qual se deseja saber o c�digo
	 *            (independente se em letras mai�sculas ou min�sculas)..
	 * @return O c�digo.
	 */
	public int getPseudoCode(String pseudo) {
		return Integer.parseInt((String) pseudos.get(pseudo.toUpperCase()));
	}

	/**
	 * Verifica se a pseudo-instru��o est� definida.
	 * 
	 * @param pseudo
	 *            A pseudo-instru��o a ser verifida (independente se em letras
	 *            mai�sculas ou min�sculas).
	 * @return Verdadeiro caso a instru��o seja definida, falso caso contr�rio.
	 */
	public boolean pseudoDefined(String pseudo) {
		return pseudos.containsKey(pseudo.toUpperCase());
	}

	/**
	 * Obt�m a inst�ncia dispon�vel da tabela de pseudo-instru��es.
	 * 
	 * @return A tabela de pseudo-instru��es.
	 */
	public static PseudoTable getTable() {
		if (pt == null) {
			pt = new PseudoTable();
		}
		return pt;
	}
}