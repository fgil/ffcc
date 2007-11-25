package mvn.montador;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Representa o passo 2 do montador, quando é gerado o código.
 * 
 * @author RRocha
 * @author FLevy
 * @version 10.10.2005
 * @version 27.07.2006 (Separação na classe Output - FLevy)
 */
 class Pass2 extends Pass {
	private RelocatedSymbolTable tab;

	private int locationCounter = ASM.CONST_INIT_COUNT;

	private boolean isRelocated = false;
	
	private Output out;

	/**
	 * Constrói um segundo passo para o montador.
	 * 
	 * @param tab
	 *            A tabela de símbolos preenchida.
	 * @param objFile
	 *            O arquivo de saída para o código de máquina.
	 * @param listFile
	 *            O arquivo de saída que conterá o código de máquina e o código
	 *            original comentado (arquivo list).
	 * @throws IOException
	 *             Caso tenha ocorrido algum problema ao abrir os arquivos de
	 *             saída.
	 */
	public Pass2(RelocatedSymbolTable tab, String objFile, String listFile)
			throws IOException {
		this.tab = tab;
		this.out = new Output (objFile, listFile);
	}

	public String getIOErrorMessage() {
		return ASM.MSG_PASS2_IO_ERROR;
	}

	public String getASMErrorMessage() {
		return ASM.MSG_PASS2_ASM_ERROR;
	}
	
	/**
	 * Fecha os arquivos de saída.
	 * @throws IOException Caso haja um problema de IO ao fechar.
	 */
	public void closeOutput() throws IOException {
		out.close();
	}

	public boolean analyzeLine(ArrayList symbols, String data)
			throws IOException {
		boolean result = false;

		String instrucao = null;
		String argumento = null;

		if (symbols.size() == 3) {
			// já foi testado se o simbolo foi resolvido
			instrucao = (String) symbols.get(1);
			argumento = (String) symbols.get(2);
		} else {
			instrucao = (String) symbols.get(0);
			argumento = (String) symbols.get(1);
		}

		if (InstructionsTable.getTable().instructionDefined(instrucao)) {
			// É uma instrução
			String enderecoArg = getArgumentValue(argumento);
			if (enderecoArg == null) {
				// endereço não definido
				System.out.println(ASM.MSG_PASS2_ASM_SYMBOL + ASM.SPACES
						+ argumento);
			} else if (Integer.parseInt(enderecoArg, 16) > ASM.LAST_VAL_ADDR) {
				// argumento da instrução precisa de um espaço maior do que
				// o disponível
				System.out.println(ASM.MSG_PASS2_ASM_SPACE);
			} else {
				// pegando o codigo da instrução
				int inst = InstructionsTable.getTable().getInstructionCode(instrucao);
				boolean isExternal = false;
				boolean isArgRelocable = false;
				
				if (tab.symbolInTable(argumento)) {
					// obtendo as informações sobre o símbolo
					if (tab.isExternal(argumento))
						isExternal = true;
					if (tab.isRelocable(argumento))
						isArgRelocable = true;
				}
				
				// escrevendo a instrução na saída
				putInstr(Integer.toHexString(inst), enderecoArg, isRelocated, isExternal, isArgRelocable, data);
				result = true;
			}
		} else if (PseudoTable.getTable().pseudoDefined(instrucao)) {
			// É uma pseudoInstrução
			result = dataFromPseudo(PseudoTable.getTable().getPseudoCode(instrucao), argumento, data);
		} else if (argumento.equals(PseudoTable.ENP_STR) || argumento.equals(PseudoTable.EXP_STR)) {
			// necessário inverter devido ao formato das pseudos de Entry Point e External Point
			result = dataFromPseudo(PseudoTable.getTable().getPseudoCode(argumento), instrucao, data);
		}
		return result;
	}

	/**
	 * Joga o comentário no arquivo List do mesmo jeito da entrada.
	 * 
	 * @param data
	 *            O texto de comentário.
	 * @throws IOException
	 *             Caso haja um problema de IO ao escrever.
	 */
	protected void processComment(String data) throws IOException {
		// joga na saída do arquivo List
		out.writeComment(data);
	}

	/**
	 * Coloca uma instrução nos arquivos de saída.
	 * 
	 * @param inst
	 *            A instrução a ser escrita.
	 * @param arg
	 *            O argumento da instrução.
	 * @param isRelocated Se a instrução é relocável ou não.
	 * @param isExternal Se a instrução é externa.
	 * @param isArgRelocable Se o argumento é relocável.
	 * @param originalLine A linha original
	 * @throws IOException
	 *             Caso haja um problema de IO ao escrever.
	 */
	private void putInstr(String inst, String arg, boolean isRelocated,
			boolean isExternal, boolean isArgRelocable, String originalLine) throws IOException {
		// calculando as informações relativas à relocação
		int nibble = 0;
		if (isRelocated)
			nibble += 8;
		if (isExternal)
			nibble += 5;
		if (isArgRelocable)
			nibble += 2;
		
		// escrevendo na saida.
		out.writeInstruction(locationCounter, nibble, inst, arg, originalLine);
		locationCounter += 2;
	}

	/**
	 * Processa os dados de uma Pseudo-instrução e coloca-os na saída.
	 * 
	 * @param pseudo
	 *            A pseudo-instrução.
	 * @param arg
	 *            O argumento da pseudo-instrução.
	 * @param originalLine A linha original.
	 * @return Falso caso haja um erro na instrução.
	 * @throws IOException
	 *             Caso haja um problema de IO ao escrever.
	 */
	private boolean dataFromPseudo(int pseudo, String arg, String originalLine) throws IOException {
		int nibble;
		String addr;
		switch (pseudo) {
		case PseudoTable.ORG:
			// origem
			locationCounter = getDecNumber(arg);
			isRelocated = false;
			out.writePseudo(originalLine);
			break;
		case PseudoTable.ORGR:
			// origem relocavel
			locationCounter = getDecNumber(arg);
			isRelocated = true;
			out.writePseudo(originalLine);
			break;
		case PseudoTable.DC:
			// constante
			String addrArg = getArgumentValue(arg);
			if (addrArg == null) {
				// argumento não resolvido
				System.out.println(ASM.MSG_PASS2_ASM_SYMBOL + ASM.SPACES + arg);
				return false;
			} else if (Integer.parseInt(addrArg, 16) > ASM.LAST_VAL_ADDR * 16) {
				// argumento da instrução precisa de um espaço maior do que
				// o disponível (uma constante pode ocupar 1 nibble a mais 
				// do que o normal.
				System.out.println(ASM.MSG_PASS2_ASM_SPACE);
				return false;
			}
			// Vendo se o argumento é um endereço e se é relocável
			boolean isExternal = false;
			boolean isArgRelocable = false;
			
			if (tab.definedSymbol(arg)) {
				isExternal = tab.isExternal(arg);
				isArgRelocable = tab.isRelocable(arg);
			}
			
			nibble = calculateNibble(isRelocated, isExternal, isArgRelocable);
			out.writeConstant(locationCounter, nibble, addrArg, originalLine);
			locationCounter += 2;
			break;
		case PseudoTable.ENP:
			// entry-point: escrevendo na saída
			addr = getArgumentValue(arg);
			if (addr == null) {
				System.out.println(ASM.MSG_PASS2_ASM_SYMBOL + ASM.SPACES + arg);
				return false;
			}
			
			nibble = 1; // público
			if (tab.isRelocable(arg))
				nibble += 2;
			
			out.writeEntryPoint(nibble, addr, arg, originalLine);
			break;
		case PseudoTable.EXP:
			nibble = 5; // pendente e público
			out.writeExternalPoint(nibble, tab.getSymbolValue(arg), arg, originalLine);
			break;
		case PseudoTable.MEM:
			locationCounter += getDecNumber(arg);
			out.writePseudo(originalLine);
		}
		return true;
	}

	/**
	 * Retorna o valor de um argumento.<br>
	 * 
	 * @return Caso o argumento seja um número, retorna o número em hexadecimal.
	 *         Caso o argumento seja um símbolo, retorna a posição. Retorna nulo
	 *         caso o símbolo não tenha sido definido.
	 */
	private String getArgumentValue(String arg) {
		if (tab.symbolInTable(arg)) {
			if (tab.definedSymbol(arg)) {
				return tab.getSymbolValue(arg);
			} else {
				return null;
			}
		} else {
			return Integer.toHexString(getDecNumber(arg));
		}
	}
	
	/**
	 * Calcula o nibble.
	 * @param isRelocated Se a instrução for relocável.
	 * @param isExternal Se a instrução for externa.
	 * @param isArgRelocable Se o argumento for relocável.
	 * @return O nibble.
	 */
	private int calculateNibble(boolean isRelocated, boolean isExternal, boolean isArgRelocable) {
		int nibble = 0;
		if (isRelocated)
			nibble += 8;
		if (isExternal)
			nibble += 4;
		if (isArgRelocable)
			nibble += 2;
		return nibble;
	}
}