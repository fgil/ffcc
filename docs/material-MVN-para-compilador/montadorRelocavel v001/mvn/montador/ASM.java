
package mvn.montador;
/**
 * Classe com as constantes do programa.
 *
 * @author RRocha
 * @version 04.10.2005
 * @version 10.10.2005 (Refatoramento - FLevy)
 */
public final class ASM {

	// Comentário para o montador
	public static final String COMM = ";";

	// Códigos do tipo de dado
	public static final String HEX_CODE = "/";
	public static final String ASCII_CODE = "'";
	public static final String DECIMAL_CODE = "=";
	public static final String OCTAL_CODE = "@";
	public static final String BINARY_CODE = "#";

	// Expressões regulares para os valores de cada tipo
	public static final String HEX_CHARS = "[0-9a-fA-F]+";
	public static final String ASCII_CHARS = ".+";
	public static final String DECIMAL_CHARS = "[0-9]+";
	public static final String OCTAL_CHARS = "[0-7]+";
	public static final String BINARY_CHARS = "[0-1]+";

	// Variáveis internas de apoio
	public static final String HEX_ZERO = "0";
	public static final String HEX_4_ZEROS = "0000";
	public static final String SPACES = " ";
	public static final String TABS = "\t";
	public static final String NEWLINES = "\n";

	// Extensões
	public static final String MVN_EXT = "mvn";
	public static final String LST_EXT = "lst";

	// Limite dos endereços válidos
	public static final int LAST_VAL_ADDR = 4096;

        // inicio do programa
	public static final int CONST_INIT_COUNT = 0;

	// Mensagens do programa
	public static final String MSG_HEADER  = "**************************** A T E N C A O ********************************";
	public static final String MSG_USO_ASM  = "\t Uso do ASM: 'java MvnAsm <arquivo-programa>' \n";
	public static final String MSG_USO_ASM_EXT  = "\t (a extensao dos arquivos gerados sera 'mvn' e 'lst')";
	public static final String MSG_USO_ASM_EXT_MUDAR  = "\t o seu arquivo sera apagado pelo montador, mude a extensao ...";
	public static final String MSG_PASS1_IO_ERROR  = "Erro de I/O no passo 1, verifique o arquivo";
	public static final String MSG_PASS2_IO_ERROR  = "Erro de I/O no passo 2, verifique o arquivo";
	public static final String MSG_PASS1_LOC_ERROR  = "Erro: Contador de instrucoes ultrapassou o limite de memoria.";
	public static final String MSG_PASS1_CONST_ERROR  = "Erro: Definicao de constante nao usa valores numericos: ";
	public static final String MSG_PASS1_DATA_ERROR  = "Erro: Definicao de dado numerico usa base desconhecida: ";
	public static final String MSG_PASS1_ORG_ERROR  = "Erro: Definicao de origem de dado deve ser um numero: ";
    public static final String MSG_PASS1_ORGR_ERROR  = "Erro: Definicao de origem relocavel deve ser um numero: ";
	public static final String MSG_PASS1_PSEUDO_ERROR  = "Instrucao invalida, verificar o programa: ";
	public static final String MSG_PASS1_ARG_ERROR  = "Erro: O argumento nao e numerico nem rotulo, ou a codificacao esta incorreta: ";
	public static final String MSG_PASS1_SIMB_ERROR  = "Erro: Simbolo redefinido: ";
	public static final String MSG_PASS2_INST_ERROR  = "Erro: Instrucao fornecida nao reconhecida: ";
	public static final String MSG_PASS2_ARG_ERROR  = "Erro: Nao foi possivel definir o valor do argumento: ";
	public static final String MSG_PASS2_ARG_ABORT_ERROR  = " abortando ...";
	public static final String MSG_PASS2_WR_ERROR  = "Erro ao tentar gravar arquivo, abortando ...";
	public static final String MSG_PASS2_OK  = "Montador finalizou corretamente, arquivos gerados.";
	public static final String MSG_PASS1_ASM_ERROR  = "Erro na montagem no passo 1. Linha: ";
	public static final String MSG_PASS2_ASM_ERROR  = "Erro na montagem no passo 2. Linha: ";
	public static final String MSG_PASS2_ASM_SYMBOL = "Erro: simbolo nao encontrado.";
	public static final String MSG_PASS2_ASM_SPACE = "Erro: argumento ocupa mais espaco do que disponivel";
}
