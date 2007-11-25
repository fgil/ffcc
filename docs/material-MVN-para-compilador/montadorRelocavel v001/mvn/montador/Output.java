package mvn.montador;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe respons�vel por criar os arquivos de sa�da do montador.
 * 
 * @author FLevy
 * @version 27.07.2006
 */
public class Output {
	private BufferedWriter out;

	private BufferedWriter list;

	public Output(String objFile, String listFile) throws IOException {
		out = new BufferedWriter(new FileWriter(objFile));
		list = new BufferedWriter(new FileWriter(listFile));
	}

	/**
	 * Joga o coment�rio no arquivo List do mesmo jeito da entrada.
	 * 
	 * @param data
	 *            O texto de coment�rio.
	 * @throws IOException
	 *             Caso haja um problema de IO ao escrever.
	 */
	public void writeComment(String data) throws IOException {
		list.write(data);
		list.newLine();
	}

	/**
	 * Coloca uma instru��o nos arquivos de sa�da.
	 * 
	 * @param locationCounter
	 *            O contador de instru��es
	 * @param nibble
	 *            O nibble com as informa��es da reloca��o.
	 * @param inst
	 *            A instru��o a ser escrita.
	 * @param arg
	 *            O argumento da instru��o.
	 * @param originalLine
	 *            A linha original (para oarquivo list)
	 * @throws IOException
	 *             Caso haja um problema de IO ao escrever.
	 */
	public void writeInstruction(int locationCounter, int nibble, String inst,
			String arg, String originalLine) throws IOException {
		String addr = Integer.toHexString(locationCounter);
		addr = ASM.HEX_4_ZEROS + addr;
		addr = addr.substring(addr.length() - 3, addr.length());

		// colocando o nibble no come�o
		addr = Integer.toHexString(nibble) + addr;

		// arrumando o tamanho do argumento
		arg = ASM.HEX_4_ZEROS + arg;
		arg = arg.substring(arg.length() - 3, arg.length());

		out.write(addr + ASM.SPACES + inst + arg);
		out.newLine();
		list.write(addr + ASM.SPACES + inst + arg);
		list.write(ASM.TABS + ASM.TABS + ASM.COMM + ASM.TABS + originalLine);
		list.newLine();
	}

	/**
	 * Coloca a pseudo instru��o constante no arquivo de sa�da.
	 * 
	 * @param locationCounter
	 *            O contador de instru��es.
	 * @param nibble
	 *            O nibble da pseudo instru��o.
	 * @param constant
	 *            A constante.
	 * @param originalLine
	 *            A linha original.
	 * @throws IOException
	 *             Caso haja um problema de IO ao escrever.
	 */
	public void writeConstant(int locationCounter, int nibble, String constant,
			String originalLine) throws IOException {
		String addr = Integer.toHexString(locationCounter);
		addr = ASM.HEX_4_ZEROS + addr;
		addr = addr.substring(addr.length() - 3, addr.length());

		// adicionando o nibble
		addr = Integer.toHexString(nibble) + addr;

		constant = ASM.HEX_4_ZEROS + constant;
		constant = constant.substring(constant.length() - 4, constant.length());

		// escrevendo na saida
		out.write(addr + ASM.SPACES + constant);
		out.newLine();
		list.write(addr + ASM.SPACES + constant);
		writePseudoOriginalLine(originalLine);
	}

	/**
	 * Escreve a pseudo instru��o no arquivo de sa�da. <br>
	 * <b>Pr�-condi��o </b>: n�o podem ser pseudo instru��es de constante,
	 * external point e entry point.
	 * 
	 * @param originalLine
	 *            A linha original a ser escrita na sa�da.
	 * @throws IOException
	 *             Caso haja um problema de IO ao escrever.
	 */
	public void writePseudo(String originalLine) throws IOException {
		list.write(ASM.TABS + ASM.TABS);
		writePseudoOriginalLine(originalLine);
	}

	/**
	 * Escreve um external point na sa�da.
	 * 
	 * @param nibble
	 *            O nibble da instru��o.
	 * @param address
	 *            O endere�o a ser escrito.
	 * @param name
	 *            O nome do external point.
	 * @param originalLine
	 *            A linha original.
	 * @throws IOException
	 *             Caso haja um problema de IO ao escrever.
	 */
	public void writeExternalPoint(int nibble, String address, String name,
			String originalLine) throws IOException {
		String addr = ASM.HEX_4_ZEROS + address;
		addr = addr.substring(addr.length() - 3, addr.length());
		addr = Integer.toHexString(nibble) + addr;

		// external-point: escrevendo na saida
		out.write(addr + ASM.SPACES + ASM.HEX_4_ZEROS + ASM.SPACES + "; \""
				+ name + "<\"");
		out.newLine();
		list.write(addr + ASM.SPACES + ASM.HEX_4_ZEROS + ASM.SPACES + "; \""
				+ name + "<\"");
		writePseudoOriginalLine(originalLine);
	}

	/**
	 * Escreve um entry point na sa�da.
	 * 
	 * @param nibble
	 *            O nibble.
	 * @param address
	 *            O endere�o a ser escrito.
	 * @param name
	 *            O nome do entry point.
	 * @param originalLine
	 *            A linha original.
	 * @throws IOException
	 *             Caso haja um problema de IO ao escrever.
	 */
	public void writeEntryPoint(int nibble, String address, String name,
			String originalLine) throws IOException {
		String addr = ASM.HEX_4_ZEROS + address;
		addr = addr.substring(addr.length() - 3, addr.length());

		// adicionando o nibble
		addr = Integer.toHexString(nibble) + addr;

		out.write(addr + ASM.SPACES + ASM.HEX_4_ZEROS + ASM.SPACES + "; \""
				+ name + ">\"");
		out.newLine();
		list.write(addr + ASM.SPACES + ASM.HEX_4_ZEROS + ASM.SPACES + "; \""
				+ name + ">\"");
		writePseudoOriginalLine(originalLine);
	}

	/**
	 * Escreve a linha original da pseudo instru��o no arquivo list.
	 * 
	 * @param originalLine
	 *            A linha original.
	 */
	private void writePseudoOriginalLine(String originalLine)
			throws IOException {
		list.write(ASM.TABS + ASM.TABS + ASM.COMM + ASM.TABS + originalLine);
		list.newLine();
	}

	/**
	 * Fecha os arquivos depois de terminar o uso.
	 * 
	 * @throws IOException
	 *             Caso haja um problema ao fechar os arquivos de sa�da.
	 */
	public void close() throws IOException {
		out.close();
		list.close();
	}
}
