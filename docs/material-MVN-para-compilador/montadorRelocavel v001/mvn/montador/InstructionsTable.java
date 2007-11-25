package mvn.montador;
import java.util.HashMap;
import java.util.Map;

/**
 * Tabela de instru��es.<br>
 * As instru��es s�o as representa��es dos comandos existentes no hardware o
 * qual se deseja gerar o c�digo.<br>
 * Para essa classe � um sigleton (apenas uma inst�ncia dela � acess�vel).
 * 
 * @author RRocha
 * @version 04.04.2005
 * @version 10.10.2005 (refatoramento - FLevy)
 */
public final class InstructionsTable {
	// C�digos das instru��es
	private static final int JP = 0;
	private final int JZ = 1;
	private final int JN = 2;
	private final int LV = 3;
	private static final int ADD = 4;
	private static final int SUB = 5;
	private static final int MUL = 6;
	private static final int DIV = 7;
	private static final int LOAD = 8;
	private static final int MOVE = 9;
	private static final int CALL = 10;
	private static final int RET = 11;
	private static final int HALT = 12;
	private static final int IN = 13;
	private static final int OUT = 14;
	private static final int OS = 15;

	// Strings das instru��es
	private static final String JP_STR = "JP";
	private static final String JZ_STR = "JZ";
	private static final String JN_STR = "JN";
	private static final String LV_STR = "LV";
	private static final String ADD_STR = "+";
	private static final String SUB_STR = "-";
	private static final String MUL_STR = "*";
	private static final String DIV_STR = "/";
	private static final String LOAD_STR = "LD";
	private static final String MOVE_STR = "MM";
	private static final String CALL_STR = "SC";
	private static final String RET_STR = "RS";
	private static final String HALT_STR = "HM";
	private static final String IN_STR = "GD";
	private static final String OUT_STR = "PD";
	private static final String OS_STR = "OS";

	// Singleton
	private static InstructionsTable it = null;

	// Vari�vel local
	private Map instr;

	/**
	 * Construtor de uma tabela de instru��es.<br>
	 * Para haver apenas uma inst�ncia da tabela de instru��es, o construtor �
	 * protegido.
	 */
	protected InstructionsTable() {
		instr = new HashMap();
		instr.put(JP_STR, JP + "");
		instr.put(JZ_STR, JZ + "");
		instr.put(JN_STR, JN + "");
		instr.put(LV_STR, LV + "");
		instr.put(ADD_STR, ADD + "");
		instr.put(SUB_STR, SUB + "");
		instr.put(MUL_STR, MUL + "");
		instr.put(DIV_STR, DIV + "");
		instr.put(LOAD_STR, LOAD + "");
		instr.put(MOVE_STR, MOVE + "");
		instr.put(CALL_STR, CALL + "");
		instr.put(RET_STR, RET + "");
		instr.put(HALT_STR, HALT + "");
		instr.put(IN_STR, IN + "");
		instr.put(OUT_STR, OUT + "");
		instr.put(OS_STR, OS + "");
	}

	/**
	 * Verifica se a instru��o � definida.
	 * 
	 * @param instruction
	 *            A instru��o a ser verificada (independente se em letras 
	 *            mai�sculas ou min�sculas).
	 * @return Verdadeiro caso a instru��o seja dispon�vel, falso caso
	 *         contr�rio.
	 */
	public boolean instructionDefined(String instruction) {
		return instr.containsKey(instruction.toUpperCase());
	}

	/**
	 * Obt�m o c�digo para uma determinada instru��o.
	 * 
	 * @param instruction
	 *            A instru��o a qual se deseja o c�digo (independente se 
	 *            em letras mai�sculas ou min�sculas). 
	 * @return O c�digo.
	 */
	public int getInstructionCode(String instruction) {
		return Integer.parseInt((String) instr.get(instruction.toUpperCase()));
	}

	/**
	 * Obt�m a inst�ncia dispon�vel da tabela de instru��es.
	 * 
	 * @return A tabela de instru��es.
	 */
	public static InstructionsTable getTable() {
		if (it == null) {
			it = new InstructionsTable();
		}
		return it;
	}
}
