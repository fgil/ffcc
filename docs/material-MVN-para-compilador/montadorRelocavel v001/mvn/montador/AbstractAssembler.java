package mvn.montador;

/**
 * Montador abstrato. <br>
 * A partir dessa abstra��o podem ser implementados diversos montadores, como um
 * montador de um passo ou um montador de dois passos.
 * 
 * @author RRocha
 * @version 04.10.2005
 */
public abstract class AbstractAssembler {

	private String inFile = "";

	/**
	 * Cria um montador abstrato.
	 * 
	 * @param fileName
	 *            O arquivo a passar pelo montador.
	 */
	public AbstractAssembler(String fileName) {
		this.inFile = fileName;
	}

	/**
	 * Obt�m o arquivo a passar pelo montador.
	 * 
	 * @return O arquivo a ser montado.
	 */
	public String getInFile() {
		return inFile;
	}

	/**
	 * Define o arquivo a passar pelo montador.
	 * 
	 * @param inFile
	 *            O arquivo.
	 */
	public void setInFile(String inFile) {
		this.inFile = inFile;
	}

	/**
	 * Monta o arquivo definido.
	 * 
	 * @return Verdadeiro caso tenha sido possivel montar, falso caso contr�rio.
	 */
	public abstract boolean assemble();
}
