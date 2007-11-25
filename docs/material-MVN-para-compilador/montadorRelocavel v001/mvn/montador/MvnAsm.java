package mvn.montador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Classe principal do Montador.
 * 
 * @author RRocha
 * @version 04.10.2005
 * @version 10.10.2005 (Refatoramento - FLevy)
 */
public class MvnAsm {

	/**
	 * Executa o programa do montador. <br>
	 * Uso: MvnAsm source_file object_file list_file
	 * 
	 * @param args
	 *            O primeiro argumento deve ser o arquivo o qual contém o código
	 *            em linguagem de montador.Caso não seja fornecido argumentos, o
	 *            programa solicitará o nome do arquivo. <br>
	 *            O extensão do arquivo não pode ser igual à extensão do
	 *            montador
	 */
	public static void main(String[] args) {
		String arg = "";
		String name;
		if (args.length >= 1) {
			arg = args[0];
		} else {
			// não tem argumentos - solicita o nome do arquivo
			System.out.println("Digite o nome do arquivo, com extensao diferente de 'mvn': ");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			arg = lerStringTeclado(in);
			if (arg == null || arg.equals("")) {
				// o usuário não digitou nada
				System.exit(1);
			}
			System.out.println(); // pula uma linha
		}
		if (arg.indexOf(".") != -1) {
			name = arg.substring(0, arg.length() - 3);
			String ext = arg.substring(arg.length() - 3, arg.length());
			// verificando se a extensao e igual a uma das extensoes de saida
			if (ext.toLowerCase().equals(ASM.MVN_EXT) || ext.toLowerCase().equals(ASM.LST_EXT)) {
				System.out.println(ASM.MSG_HEADER);
				System.out.println(ASM.MSG_USO_ASM + ASM.MSG_USO_ASM_EXT
						+ ASM.NEWLINES + ASM.MSG_USO_ASM_EXT_MUDAR);
				System.out.println();
				System.exit(1);
			}
		} else {
			// arquivo sem extensão
			name = arg;
		}
		
		// Iniciando o uso do assembler
		AbstractAssembler asm = new TwoPassAssembler(arg, name + ASM.MVN_EXT,
				name + ASM.LST_EXT);
		if (!asm.assemble()) {
			// não conseguiu montar - sai com erro
			System.exit(1);
		}
	}

	/**
	 * Método auxiliar para obter uma String entrada pelo teclado.
	 * 
	 * @return Se a entrada for inválida retorna uma string nula. Se não,
	 *         retorna o valor lido.
	 */
	private static String lerStringTeclado(BufferedReader in) {
		String s = "";
		try {
			s = in.readLine();
		} catch (IOException ex) {
			System.out.println("Erro na entrada de teclado.");
		}
		return s;
	}

}
