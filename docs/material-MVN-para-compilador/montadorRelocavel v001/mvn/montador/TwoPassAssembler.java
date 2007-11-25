package mvn.montador;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Montador de dois passos. No primeiro passo � criada a tabela de s�mbolo e no
 * segundo passo � gerado o c�digo de m�quina.
 *
 * @author RRocha
 * @version 04.10.2005
 * @version 10.10.2005 (Refatoramento - FLevy)
 */
public class TwoPassAssembler extends AbstractAssembler {
	private String objFile;

	private String listFile;

	/**
	 * Construtor do montador.
	 *
	 * @param fileName
	 *            Nome do arquivo a passar pelo montador.
	 * @param objFileName
	 *            Nome do arquivo de sa�da que conter� o c�digo.
	 * @param listFileName
	 *            Nome do arquivo de sa�da que conter� o c�digo e a listagem.
	 */
	public TwoPassAssembler(String fileName, String objFileName,
			String listFileName) {
		super(fileName);
		objFile = objFileName;
		listFile = listFileName;
	}

	/**
	 * Monta o arquivo definido, passando pelos dois passos do montador.
	 *
	 * @return Verdadeiro caso a montagem tenha obtido sucesso, falso caso
	 *         contr�rio.
	 */
	public boolean assemble() {
		Pass passo1 = new Pass1();
		Pass passo2 = null;
		if (executePass(passo1)) {
			try {
				// passo 1 com sucesso
				// executa o passo 2
				passo2 = new Pass2(((Pass1) passo1).getSymbolTable(), this.objFile, this.listFile);
				if (executePass(passo2)) {
					System.out.println(ASM.MSG_PASS2_OK);
					return true;
				}
			} catch (IOException e) {
				System.out.println(ASM.MSG_PASS2_IO_ERROR);
				e.printStackTrace();
				return false;
			} finally {
				// para fechar o IO que estava aberto
				try {
					if (passo2 != null)
						((Pass2) passo2).closeOutput();
				} catch (IOException unhandled) { }
			}
		}
		return false;
	}

	/**
	 * Execu��o de um passo no c�digo.
	 *
	 * @param pass
	 *            O passo a ser executado.
	 * @return Verdadeiro caso a execu��o tenha obtido sucesso, falso caso
	 *         contr�rio.
	 */
	private boolean executePass(Pass pass) {
		// Cria um buffer de leitura para o arquivo texto
		BufferedReader filIn;
		try {
			filIn = new BufferedReader(new FileReader(super.getInFile()));

			int linhaComErro = pass.tokenizeData(filIn);
			boolean resultado = false;

			if (linhaComErro == 0) {
				// sem erros no passo
				resultado = true;
			} else if (linhaComErro == -1) {
				// erro de IO
				System.out.println(pass.getIOErrorMessage());
			} else {
				System.out.println(pass.getASMErrorMessage() + linhaComErro);
			}

			filIn.close();
			return resultado;
		} catch (Exception e) {
			System.out.println(pass.getIOErrorMessage());
			e.printStackTrace();
			return false;
		}
	}
}