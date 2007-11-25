package mvn.montador;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Representa um passo do montador.
 * 
 * @author RRocha
 * @author FLevy
 * @version 10.10.2005
 */
abstract class Pass {

	/**
	 * Analisa uma linha de código.
	 * 
	 * @param symbols
	 *            O array com os símbolos processados.
	 * @param line
	 *            A linha original.
	 * @return Verdadeiro caso a análise teve sucesso, falso caso contrário.
	 * @exception Caso
	 *                tenha ocorrido algum problema de IO.
	 */
	protected abstract boolean analyzeLine(ArrayList symbols, String line)
			throws IOException;

	/**
	 * Processa uma linha de comentário.
	 * 
	 * @param data
	 *            A linha apenas com comentário.
	 * @throws IOException
	 *             Caso tenha ocorrido algum problema de IO.
	 */
	protected abstract void processComment(String data) throws IOException;

	/**
	 * Retorna a mensagem de erro de IO para esse passo.
	 */
	public abstract String getIOErrorMessage();

	/**
	 * Retorna a mensagem de erro do montador para esse passo.
	 */
	public abstract String getASMErrorMessage();

	/**
	 * Analisa os dados e os processa.<br>
	 * 
	 * @param in
	 *            Arquivo com o código em linguagem de montador.
	 * @return -1 caso tenha ocorrido um erro de IO, 0 caso não haja erro ou o
	 *         número da linha com erro.
	 */
	public int tokenizeData(BufferedReader in) {
		ArrayList symbols;
		String data;
		StringTokenizer tokens;
		boolean comentario;
		String temp;
		int numLinha = 0;

		try {
			data = in.readLine();
			numLinha++;

			while (data != null) {
				tokens = new StringTokenizer(data);
				symbols = new ArrayList();

				comentario = false;
				while (tokens.hasMoreTokens() && !comentario) {
					temp = tokens.nextToken();
					if (temp.startsWith(ASM.COMM)) {
						// o resto da linha é comentario
						comentario = true;
					} else {
						symbols.add(temp);
					}
				}

				if (symbols.size() > 0) {
					if (!analyzeLine(symbols, data))
						return numLinha;
				} else {
					processComment(data);
				}
				data = in.readLine();
				numLinha++;
			}
			return 0; // ok
		} catch (IOException e) {
			return numLinha;
		}
	}

	/**
	 * Testa se o argumento é um número(HEX, ASCII, DECIMAL, OCTAL e BINARY)
	 * 
	 * @param code
	 *            O valor a ser testado se é número.
	 * @return Verdadeiro caso o argumento seja um número, falso caso contrário.
	 */
	protected boolean isNumber(String code) {
		String base = code.substring(0, 1);
		String resto = code.substring(1, code.length());
		if (!((base.equals(ASM.HEX_CODE) && resto.matches(ASM.HEX_CHARS))
				|| (base.equals(ASM.ASCII_CODE) && resto.matches(ASM.ASCII_CHARS))
				|| (base.equals(ASM.DECIMAL_CODE) && resto.matches(ASM.DECIMAL_CHARS))
				|| (base.equals(ASM.OCTAL_CODE) && resto.matches(ASM.OCTAL_CHARS))
				|| (base.equals(ASM.BINARY_CODE) && resto.matches(ASM.BINARY_CHARS)))) {
			return false;
		}
		return true;
	}

	/**
	 * Obtêm o número em decimal a partir de um número definido nas codificações
	 * válidas do montador.
	 * 
	 * @param arg
	 *            O número em uma determinada codificação.
	 * @return O número decimal correspondendo o número.
	 */
	protected int getDecNumber(String arg) {
		String base = arg.substring(0, 1);
		String valor = arg.substring(1, arg.length());
		int result = 0;
		if (base.equals(ASM.HEX_CODE)) {
			// hexadecimal
			result = Integer.parseInt(valor, 16);
		} else if (base.equals(ASM.ASCII_CODE)) {
			// ascii
			for (int i = 0; i < valor.length(); i++) {
				result += ((int) Math.pow(256, valor.length() - 1 - i)) * (int) valor.charAt(i);
			}
		} else if (base.equals(ASM.DECIMAL_CODE)) {
			// decimal
			// não faz nada
			result = Integer.parseInt(valor);
		} else if (base.equals(ASM.BINARY_CODE)) {
			// binário
			result = Integer.parseInt(valor, 2);
		} else if (base.equals(ASM.OCTAL_CODE)) {
			// octal
			result = Integer.parseInt(valor, 8);
		}
		return result;
	}
}
