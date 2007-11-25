package mvn.montador;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Representa o passo 2 do montador, quando � gerado o c�digo.
 * 
 * @author RRocha
 * @author FLevy
 * @version 10.10.2005
 * @version 27.07.2006 (Separa��o na classe Output - FLevy)
 */
 class Pass2 extends Pass {
	private RelocatedSymbolTable tab;

	private int locationCounter = ASM.CONST_INIT_COUNT;

	private boolean isRelocated = false;
	
	private Output out;

	/**
	 * Constr�i um segundo passo para o montador.
	 * 
	 * @param tab
	 *            A tabela de s�mbolos preenchida.
	 * @param objFile
	 *            O arquivo de sa�da para o c�digo de m�quina.
	 * @param listFile
	 *            O arquivo de sa�da que conter� o c�digo de m�quina e o c�digo
	 *            original comentado (arquivo list).
	 * @throws IOException
	 *             Caso tenha ocorrido algum problema ao abrir os arquivos de
	 *             sa�da.
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
	 * Fecha os arquivos de sa�da.
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
			// j� foi testado se o simbolo foi resolvido
			instrucao = (String) symbols.get(1);
			argumento = (String) symbols.get(2);
		} else {
			instrucao = (String) symbols.get(0);
			argumento = (String) symbols.get(1);
		}

		if (InstructionsTable.getTable().instructionDefined(instrucao)) {
			// � uma instru��o
			String enderecoArg = getArgumentValue(argumento);
			if (enderecoArg == null) {
				// endere�o n�o definido
				System.out.println(ASM.MSG_PASS2_ASM_SYMBOL + ASM.SPACES
						+ argumento);
			} else if (Integer.parseInt(enderecoArg, 16) > ASM.LAST_VAL_ADDR) {
				// argumento da instru��o precisa de um espa�o maior do que
				// o dispon�vel
				System.out.println(ASM.MSG_PASS2_ASM_SPACE);
			} else {
				// pegando o codigo da instru��o
				int inst = InstructionsTable.getTable().getInstructionCode(instrucao);
				boolean isExternal = false;
				boolean isArgRelocable = false;
				
				if (tab.symbolInTable(argumento)) {
					// obtendo as informa��es sobre o s�mbolo
					if (tab.isExternal(argumento))
						isExternal = true;
					if (tab.isRelocable(argumento))
						isArgRelocable = true;
				}
				
				// escrevendo a instru��o na sa�da
				putInstr(Integer.toHexString(inst), enderecoArg, isRelocated, isExternal, isArgRelocable, data);
				result = true;
			}
		} else if (PseudoTable.getTable().pseudoDefined(instrucao)) {
			// � uma pseudoInstru��o
			result = dataFromPseudo(PseudoTable.getTable().getPseudoCode(instrucao), argumento, data);
		} else if (argumento.equals(PseudoTable.ENP_STR) || argumento.equals(PseudoTable.EXP_STR)) {
			// necess�rio inverter devido ao formato das pseudos de Entry Point e External Point
			result = dataFromPseudo(PseudoTable.getTable().getPseudoCode(argumento), instrucao, data);
		}
		return result;
	}

	/**
	 * Joga o coment�rio no arquivo List do mesmo jeito da entrada.
	 * 
	 * @param data
	 *            O texto de coment�rio.
	 * @throws IOException
	 *             Caso haja um problema de IO ao escrever.
	 */
	protected void processComment(String data) throws IOException {
		// joga na sa�da do arquivo List
		out.writeComment(data);
	}

	/**
	 * Coloca uma instru��o nos arquivos de sa�da.
	 * 
	 * @param inst
	 *            A instru��o a ser escrita.
	 * @param arg
	 *            O argumento da instru��o.
	 * @param isRelocated Se a instru��o � reloc�vel ou n�o.
	 * @param isExternal Se a instru��o � externa.
	 * @param isArgRelocable Se o argumento � reloc�vel.
	 * @param originalLine A linha original
	 * @throws IOException
	 *             Caso haja um problema de IO ao escrever.
	 */
	private void putInstr(String inst, String arg, boolean isRelocated,
			boolean isExternal, boolean isArgRelocable, String originalLine) throws IOException {
		// calculando as informa��es relativas � reloca��o
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
	 * Processa os dados de uma Pseudo-instru��o e coloca-os na sa�da.
	 * 
	 * @param pseudo
	 *            A pseudo-instru��o.
	 * @param arg
	 *            O argumento da pseudo-instru��o.
	 * @param originalLine A linha original.
	 * @return Falso caso haja um erro na instru��o.
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
				// argumento n�o resolvido
				System.out.println(ASM.MSG_PASS2_ASM_SYMBOL + ASM.SPACES + arg);
				return false;
			} else if (Integer.parseInt(addrArg, 16) > ASM.LAST_VAL_ADDR * 16) {
				// argumento da instru��o precisa de um espa�o maior do que
				// o dispon�vel (uma constante pode ocupar 1 nibble a mais 
				// do que o normal.
				System.out.println(ASM.MSG_PASS2_ASM_SPACE);
				return false;
			}
			// Vendo se o argumento � um endere�o e se � reloc�vel
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
			// entry-point: escrevendo na sa�da
			addr = getArgumentValue(arg);
			if (addr == null) {
				System.out.println(ASM.MSG_PASS2_ASM_SYMBOL + ASM.SPACES + arg);
				return false;
			}
			
			nibble = 1; // p�blico
			if (tab.isRelocable(arg))
				nibble += 2;
			
			out.writeEntryPoint(nibble, addr, arg, originalLine);
			break;
		case PseudoTable.EXP:
			nibble = 5; // pendente e p�blico
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
	 * @return Caso o argumento seja um n�mero, retorna o n�mero em hexadecimal.
	 *         Caso o argumento seja um s�mbolo, retorna a posi��o. Retorna nulo
	 *         caso o s�mbolo n�o tenha sido definido.
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
	 * @param isRelocated Se a instru��o for reloc�vel.
	 * @param isExternal Se a instru��o for externa.
	 * @param isArgRelocable Se o argumento for reloc�vel.
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