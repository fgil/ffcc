package mvn.montador;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Implementa o passo 1 do montador.<br>
 * Nesse passo os símbolos são resolvidos e colocados na tabela de símbolos e
 * também é verificado se as instruções estão corretas.
 * 
 * @author RRocha
 * @author FLevy
 * @version 10.10.2005
 */
class Pass1 extends Pass {
	private RelocatedSymbolTable tab;

	private int locationCounter = ASM.CONST_INIT_COUNT;

	private boolean isRelocated = false;

	private int numExternal = 0; // número de símbolos externos

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
			// contém label
			if (symbols.size() > 3) {
				// a linha contem mais símbolos do que devia
				System.out.println(ASM.MSG_PASS1_PSEUDO_ERROR);
				return false;
			}

			if (!tab.symbolInTable((String) symbols.get(0))) {
				// se símbolo ainda não usado, coloca na tabela e resolve
				tab.insertSymbol((String) symbols.get(0));
				tab.setSymbolValue((String) symbols.get(0), Integer.toHexString(locationCounter), isRelocated, false);
			} else if (!tab.definedSymbol((String) symbols.get(0))) {
				// se simbolo já usado, mas ainda não resolvido, coloca o
				// endereco na tabela
				tab.setSymbolValue((String) symbols.get(0), Integer.toHexString(locationCounter), isRelocated, false);
			} else {
				// símbolo já definido (ou seja, definido duas vezes!!!
				System.out.println(ASM.MSG_PASS1_SIMB_ERROR + (String) symbols.get(0));
				return false;
			}

			// testa se o resto da linha é correta
			result = testForCode((String) symbols.get(1), (String) symbols.get(2));
		} else if (symbols.size() == 2) {
			// não contem label

			// adaptação para corrigir o posicionamento do operando
			result = testForCode((String) symbols.get(0), (String) symbols.get(1));
		} else {
			// a linha contem menos tokens do que o utilizado
			System.out.println(ASM.MSG_PASS1_PSEUDO_ERROR);
			return false;
		}

		return result;
	}

	/**
	 * Testa se é uma instrução ou pseudo-instrução.
	 * 
	 * @param code
	 *            O código da instrução.
	 * @param arg
	 *            O argumento da instrução.
	 * @return Verdadeiro caso seja uma instrução ou pseudo-instrução, falso
	 *         caso contrário.
	 */
	private boolean testForCode(String code, String arg) {
		boolean result = false;

		// Testa se o argumento é número ou label
		if (!isNumber(arg)) {

			// testa se não é um erro na codificação do número
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

		// Testa se é instrução
		if (InstructionsTable.getTable().instructionDefined(code)) {
			locationCounter += 2;
			if (locationCounter < ASM.LAST_VAL_ADDR)
				result = true;
			else {
				System.out.println(ASM.MSG_PASS1_LOC_ERROR);
			}
		} else {
			// É uma pseudo instrução
			result = testForPseudo(code, arg);
		}

		return result;
	}

	/**
	 * Testa se é uma pseudo-instrução.
	 * 
	 * @param code
	 *            Código da pseudo-instrução.
	 * @param arg
	 *            Argumento da pseudo-instrução.
	 * @return Verdadeiro caso seja uma pseudo-instrução, falso caso contrário.
	 */
	private boolean testForPseudo(String code, String arg) {
		boolean result = false;
		// É código de pseudo-instrução.
		if (PseudoTable.getTable().pseudoDefined(code)) {
			int ps = PseudoTable.getTable().getPseudoCode(code);
			switch (ps) {
			case PseudoTable.ORG:
				// Pseudo-Instrução que troca a origem
				if (defineNewOrigin(arg))
					result = true;
				break;
			case PseudoTable.DC:
				// Pseudo-Instrução para definição de constantes
				if (defineConstant())
					result = true;
				break;
			case PseudoTable.END:
				// Pseudo-Instrução para o fim do assembler
				result = endAsm(arg);
				break;
			case PseudoTable.ORGR:
				// Pseudo-Instrução de origem relocável
				result = defineNewRelocatedOrigin(arg);
				break;
			case PseudoTable.MEM:
				// Pseudo-Instrução de origem relocável
				result = reserveBlock(arg);
				break;
			default:
				System.out.println(ASM.MSG_PASS1_PSEUDO_ERROR + code);
			}
		} else if (arg.equals(PseudoTable.ENP_STR)) {
			// Pseudo-Instrução de ponto de entrada
			result = defineEntryPoint(code);
		} else if (arg.equals(PseudoTable.EXP_STR)) {
			// Pseudo-Instrução de ponto externo
			result = defineExternalPoint(code);
		} else
			System.out.println(ASM.MSG_PASS1_PSEUDO_ERROR + code);
		return result;
	}

	/**
	 * Define uma nova origem (pseudo-instrução ORG).
	 * 
	 * @param arg
	 *            String com a nova origem em HEXADECIMAL, BINÁRIO, OCTAL,
	 *            DECIMAL ou ASCII.
	 * @return Verdadeiro caso tenha sido possível alterar a origem, falso caso
	 *         a origem solicitada seja maior do que a possível de ser
	 *         processada pelo montador.
	 */
	private boolean defineNewOrigin(String arg) {
		boolean result = false;

		// a origem tem que ser um número
		if (isNumber(arg)) {
			locationCounter = getDecNumber(arg);
			isRelocated = false;
			// a origem deve ser um número valido
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
	 * Define uma constante (pseudo-instrução DC). Permite que um valor ou um
	 * símbolo (endereço do símbolo).<br>
	 * Na prática apenas move o contador de localização (as demais verificações
	 * já foram feitas).
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

	// novas instruções (montador relocável)

	/**
	 * Pára o assembler (pseudo-instrução END).<br>
	 * Atualmente sem uso.
	 * 
	 * @param arg
	 *            O endereço de execução do programa
	 * @return Verdadeiro
	 */
	private boolean endAsm(String arg) {
		return true;
	}

	/**
	 * Define uma nova origem relocável (pseudo-instrução ORGR)
	 * 
	 * @param arg
	 *            A nova origem relocável.
	 * @return Verdadeiro caso tenha sido possível definir uma nova origem
	 *         relocável para o programa.
	 */
	private boolean defineNewRelocatedOrigin(String arg) {
		boolean result = false;
		// a origem tem que ser um número
		if (isNumber(arg)) {
			locationCounter = getDecNumber(arg);
			isRelocated = true;
			// a origem deve ser um número valido
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
	 * Reserva um bloco de dados (pseudo-instrução MEM)
	 * 
	 * @param arg
	 *            O espaço a ser reservado.
	 * @return Verdadeiro caso tenha sido possível alocar a área de dados e
	 *         falso caso contrário.
	 */
	private boolean reserveBlock(String arg) {
		boolean result = false;

		// o espaço a ser reservado deve ser um número
		if (isNumber(arg)) {
			locationCounter += getDecNumber(arg);
			// a origem deve ser um número valido
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
	 * Define um endereço simbólico de entrada (pseudo-instrução ENP).<br>
	 * O endereço simbólico deve ser definido em alguma outra parte do programa.
	 * 
	 * @param arg
	 *            O endereço simbólico.
	 * @return Verdadeiro caso tenha sido possivel definir o endereço simbólico
	 *         de entrada.
	 */
	private boolean defineEntryPoint(String arg) {
		// não precisa fazer nada. Só coloca o símbolo como uma
		// pendência. Em algum lugar do código ele deve ser resolvido

		if (!tab.symbolInTable(arg))
			tab.insertSymbol(arg);

		return true;
	}

	/**
	 * Define um endereço simbólico externo (pseudo-instrução EXP).
	 * 
	 * @param arg
	 *            O endereço simbólico externo.
	 * @return Verdadeiro caso tenha sido possível definir o endereço simbólico
	 *         externo.
	 */
	private boolean defineExternalPoint(String arg) {

		if (!tab.symbolInTable(arg)) {
			// se símbolo ainda não usado, coloca na tabela e resolve
			// -1 representa endereço externo
			tab.insertSymbol(arg);
			tab.setSymbolValue(arg, numExternal+"", false, true);
		} else if (!tab.definedSymbol(arg)) {
			// se simbolo já usado, mas ainda não resolvido, coloca o
			// endereco na tabela
			// -1 representa endereço externo
			tab.setSymbolValue((String) arg, numExternal+"", false, true);
		} else {
			// símbolo já definido (ou seja, definido duas vezes!!!
			System.out.println(ASM.MSG_PASS1_SIMB_ERROR + arg);
			return false;
		}
		numExternal++;
		return true;
	}
}