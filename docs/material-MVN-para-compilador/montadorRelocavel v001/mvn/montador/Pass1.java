package mvn.montador;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Implementa o passo 1 do montador.<br>
 * Nesse passo os s�mbolos s�o resolvidos e colocados na tabela de s�mbolos e
 * tamb�m � verificado se as instru��es est�o corretas.
 * 
 * @author RRocha
 * @author FLevy
 * @version 10.10.2005
 */
class Pass1 extends Pass {
	private RelocatedSymbolTable tab;

	private int locationCounter = ASM.CONST_INIT_COUNT;

	private boolean isRelocated = false;

	private int numExternal = 0; // n�mero de s�mbolos externos

	public Pass1() {
		tab = new RelocatedSymbolTable();
	}

	public RelocatedSymbolTable getSymbolTable() {
		return tab;
	}

	public String getIOErrorMessage() {
		return ASM.MSG_PASS1_IO_ERROR;
	}

	public String getASMErrorMessage() {
		return ASM.MSG_PASS1_ASM_ERROR;
	}

	protected void processComment(String data) throws IOException {
		// nao faz nada
	}

	public boolean analyzeLine(ArrayList symbols, String line)
			throws IOException {
		boolean result = false;

		if (symbols.size() > 2) {
			// cont�m label
			if (symbols.size() > 3) {
				// a linha contem mais s�mbolos do que devia
				System.out.println(ASM.MSG_PASS1_PSEUDO_ERROR);
				return false;
			}

			if (!tab.symbolInTable((String) symbols.get(0))) {
				// se s�mbolo ainda n�o usado, coloca na tabela e resolve
				tab.insertSymbol((String) symbols.get(0));
				tab.setSymbolValue((String) symbols.get(0), Integer.toHexString(locationCounter), isRelocated, false);
			} else if (!tab.definedSymbol((String) symbols.get(0))) {
				// se simbolo j� usado, mas ainda n�o resolvido, coloca o
				// endereco na tabela
				tab.setSymbolValue((String) symbols.get(0), Integer.toHexString(locationCounter), isRelocated, false);
			} else {
				// s�mbolo j� definido (ou seja, definido duas vezes!!!
				System.out.println(ASM.MSG_PASS1_SIMB_ERROR + (String) symbols.get(0));
				return false;
			}

			// testa se o resto da linha � correta
			result = testForCode((String) symbols.get(1), (String) symbols.get(2));
		} else if (symbols.size() == 2) {
			// n�o contem label

			// adapta��o para corrigir o posicionamento do operando
			result = testForCode((String) symbols.get(0), (String) symbols.get(1));
		} else {
			// a linha contem menos tokens do que o utilizado
			System.out.println(ASM.MSG_PASS1_PSEUDO_ERROR);
			return false;
		}

		return result;
	}

	/**
	 * Testa se � uma instru��o ou pseudo-instru��o.
	 * 
	 * @param code
	 *            O c�digo da instru��o.
	 * @param arg
	 *            O argumento da instru��o.
	 * @return Verdadeiro caso seja uma instru��o ou pseudo-instru��o, falso
	 *         caso contr�rio.
	 */
	private boolean testForCode(String code, String arg) {
		boolean result = false;

		// Testa se o argumento � n�mero ou label
		if (!isNumber(arg)) {

			// testa se n�o � um erro na codifica��o do n�mero
			String base = arg.substring(0, 1);
			if (base.equals(ASM.HEX_CODE) || base.equals(ASM.ASCII_CODE)
					|| base.equals(ASM.DECIMAL_CODE)
					|| base.equals(ASM.OCTAL_CODE)
					|| base.equals(ASM.BINARY_CODE)) {

				System.out.println(ASM.MSG_PASS1_ARG_ERROR + arg);
				return false;
			}

			// coloca o simbolo na tabela
			if (!tab.symbolInTable(arg))
				tab.insertSymbol(arg);
		}

		// Testa se � instru��o
		if (InstructionsTable.getTable().instructionDefined(code)) {
			locationCounter += 2;
			if (locationCounter < ASM.LAST_VAL_ADDR)
				result = true;
			else {
				System.out.println(ASM.MSG_PASS1_LOC_ERROR);
			}
		} else {
			// � uma pseudo instru��o
			result = testForPseudo(code, arg);
		}

		return result;
	}

	/**
	 * Testa se � uma pseudo-instru��o.
	 * 
	 * @param code
	 *            C�digo da pseudo-instru��o.
	 * @param arg
	 *            Argumento da pseudo-instru��o.
	 * @return Verdadeiro caso seja uma pseudo-instru��o, falso caso contr�rio.
	 */
	private boolean testForPseudo(String code, String arg) {
		boolean result = false;
		// � c�digo de pseudo-instru��o.
		if (PseudoTable.getTable().pseudoDefined(code)) {
			int ps = PseudoTable.getTable().getPseudoCode(code);
			switch (ps) {
			case PseudoTable.ORG:
				// Pseudo-Instru��o que troca a origem
				if (defineNewOrigin(arg))
					result = true;
				break;
			case PseudoTable.DC:
				// Pseudo-Instru��o para defini��o de constantes
				if (defineConstant())
					result = true;
				break;
			case PseudoTable.END:
				// Pseudo-Instru��o para o fim do assembler
				result = endAsm(arg);
				break;
			case PseudoTable.ORGR:
				// Pseudo-Instru��o de origem reloc�vel
				result = defineNewRelocatedOrigin(arg);
				break;
			case PseudoTable.MEM:
				// Pseudo-Instru��o de origem reloc�vel
				result = reserveBlock(arg);
				break;
			default:
				System.out.println(ASM.MSG_PASS1_PSEUDO_ERROR + code);
			}
		} else if (arg.equals(PseudoTable.ENP_STR)) {
			// Pseudo-Instru��o de ponto de entrada
			result = defineEntryPoint(code);
		} else if (arg.equals(PseudoTable.EXP_STR)) {
			// Pseudo-Instru��o de ponto externo
			result = defineExternalPoint(code);
		} else
			System.out.println(ASM.MSG_PASS1_PSEUDO_ERROR + code);
		return result;
	}

	/**
	 * Define uma nova origem (pseudo-instru��o ORG).
	 * 
	 * @param arg
	 *            String com a nova origem em HEXADECIMAL, BIN�RIO, OCTAL,
	 *            DECIMAL ou ASCII.
	 * @return Verdadeiro caso tenha sido poss�vel alterar a origem, falso caso
	 *         a origem solicitada seja maior do que a poss�vel de ser
	 *         processada pelo montador.
	 */
	private boolean defineNewOrigin(String arg) {
		boolean result = false;

		// a origem tem que ser um n�mero
		if (isNumber(arg)) {
			locationCounter = getDecNumber(arg);
			isRelocated = false;
			// a origem deve ser um n�mero valido
			if (locationCounter < ASM.LAST_VAL_ADDR)
				result = true;
			else
				System.out.println(ASM.MSG_PASS1_LOC_ERROR);
		} else {
			System.out.println(ASM.MSG_PASS1_ORG_ERROR + ASM.SPACES + arg);
		}

		return result;
	}

	/**
	 * Define uma constante (pseudo-instru��o DC). Permite que um valor ou um
	 * s�mbolo (endere�o do s�mbolo).<br>
	 * Na pr�tica apenas move o contador de localiza��o (as demais verifica��es
	 * j� foram feitas).
	 * 
	 * @return Falso caso tenha ocorrido um erro ao definir as constantes.
	 */
	private boolean defineConstant() {
		boolean result = false;
		if (locationCounter < ASM.LAST_VAL_ADDR) {
			locationCounter += 2;
			result = true;
		} else {
			System.out.println(ASM.MSG_PASS1_LOC_ERROR);
		}
		return result;
	}

	// novas instru��es (montador reloc�vel)

	/**
	 * P�ra o assembler (pseudo-instru��o END).<br>
	 * Atualmente sem uso.
	 * 
	 * @param arg
	 *            O endere�o de execu��o do programa
	 * @return Verdadeiro
	 */
	private boolean endAsm(String arg) {
		return true;
	}

	/**
	 * Define uma nova origem reloc�vel (pseudo-instru��o ORGR)
	 * 
	 * @param arg
	 *            A nova origem reloc�vel.
	 * @return Verdadeiro caso tenha sido poss�vel definir uma nova origem
	 *         reloc�vel para o programa.
	 */
	private boolean defineNewRelocatedOrigin(String arg) {
		boolean result = false;
		// a origem tem que ser um n�mero
		if (isNumber(arg)) {
			locationCounter = getDecNumber(arg);
			isRelocated = true;
			// a origem deve ser um n�mero valido
			if (locationCounter < ASM.LAST_VAL_ADDR)
				result = true;
			else
				System.out.println(ASM.MSG_PASS1_LOC_ERROR);
		} else {
			System.out.println(ASM.MSG_PASS1_ORGR_ERROR + ASM.SPACES + arg);
		}

		return result;
	}

	/**
	 * Reserva um bloco de dados (pseudo-instru��o MEM)
	 * 
	 * @param arg
	 *            O espa�o a ser reservado.
	 * @return Verdadeiro caso tenha sido poss�vel alocar a �rea de dados e
	 *         falso caso contr�rio.
	 */
	private boolean reserveBlock(String arg) {
		boolean result = false;

		// o espa�o a ser reservado deve ser um n�mero
		if (isNumber(arg)) {
			locationCounter += getDecNumber(arg);
			// a origem deve ser um n�mero valido
			if (locationCounter < ASM.LAST_VAL_ADDR)
				result = true;
			else
				System.out.println(ASM.MSG_PASS1_LOC_ERROR);
		} else {
			System.out.println(ASM.MSG_PASS1_DATA_ERROR + ASM.SPACES + arg);
		}

		return result;
	}

	/**
	 * Define um endere�o simb�lico de entrada (pseudo-instru��o ENP).<br>
	 * O endere�o simb�lico deve ser definido em alguma outra parte do programa.
	 * 
	 * @param arg
	 *            O endere�o simb�lico.
	 * @return Verdadeiro caso tenha sido possivel definir o endere�o simb�lico
	 *         de entrada.
	 */
	private boolean defineEntryPoint(String arg) {
		// n�o precisa fazer nada. S� coloca o s�mbolo como uma
		// pend�ncia. Em algum lugar do c�digo ele deve ser resolvido

		if (!tab.symbolInTable(arg))
			tab.insertSymbol(arg);

		return true;
	}

	/**
	 * Define um endere�o simb�lico externo (pseudo-instru��o EXP).
	 * 
	 * @param arg
	 *            O endere�o simb�lico externo.
	 * @return Verdadeiro caso tenha sido poss�vel definir o endere�o simb�lico
	 *         externo.
	 */
	private boolean defineExternalPoint(String arg) {

		if (!tab.symbolInTable(arg)) {
			// se s�mbolo ainda n�o usado, coloca na tabela e resolve
			// -1 representa endere�o externo
			tab.insertSymbol(arg);
			tab.setSymbolValue(arg, numExternal+"", false, true);
		} else if (!tab.definedSymbol(arg)) {
			// se simbolo j� usado, mas ainda n�o resolvido, coloca o
			// endereco na tabela
			// -1 representa endere�o externo
			tab.setSymbolValue((String) arg, numExternal+"", false, true);
		} else {
			// s�mbolo j� definido (ou seja, definido duas vezes!!!
			System.out.println(ASM.MSG_PASS1_SIMB_ERROR + arg);
			return false;
		}
		numExternal++;
		return true;
	}
}