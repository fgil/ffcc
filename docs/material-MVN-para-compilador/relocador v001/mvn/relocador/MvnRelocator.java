package mvn.relocador;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Relocador para a MVN.
 * @author FLevy
 * @version 20.11.2005
 */
public class MvnRelocator {
  // Mensagens do programa
  static final String MSG_USO_RELOCADOR  = "\t Uso do Relocador:\njava MvnRelocador <arquivo-objeto> <arquivo-saida> <base>\n";
  static final String MSG_FALTA_SAIDA  = "E preciso determinar o arquivo de saida.";
  static final String MSG_FALTA_BASE = "Informe a base de relocacao (em hexa): ";
  static final String MSG_ERRO_BASE = "Valor de base inadequado. ";
  static final String MSG_ACESSO_ENTRADA  = "Nao e possivel ler o arquivo: ";
  static final String MSG_ACESSO_SAIDA  = "Nao e possivel escrever no arquivo: ";
  static final String MSG_SINTAXE_INADEQUADA = "Linha com sintaxe inadequada. Linha: ";
  static final String MSG_ENDERECAMENTO_INCORRETO = "Nibble de enderecamento incorreto. Linha: ";
  static final String MSG_ENDERECO_INVALIDO = "Endereco invalido. Linha: ";
  static final String MSG_ERRO_LIMITE_MEM  = "Contador de instrucoes ultrapassou o limite de memoria. Linha: ";
  static final String MSG_SUCESSO = "Arquivo gerado com sucesso.";
  static final String ASM_SPACE = " ";

  // Limite dos endereços válidos
  static final int LAST_VAL_ADDR = 4096;

  public static void main (String[] args) {
    File saida = null;
    File entrada = null;
    int base = -1;
    Relocator relocador;

    if (args.length < 2 || args.length > 3) {
      System.out.println(MSG_USO_RELOCADOR);
      System.exit(1);
    }

    // obtendo a entrada
    entrada = new File(args[0]);
    if (!entrada.canRead()) {
      System.out.println(MSG_ACESSO_ENTRADA + args[0]);
      System.exit(1);
    }

    // obtendo a saida
    saida = new File(args[1]);
    if (saida.exists() && !saida.canWrite()) {
      System.out.println(MSG_ACESSO_SAIDA);
      System.exit(1);
    }

    // obtendo base
    if (args.length > 2) {
      try {
        base = Integer.parseInt(args[2], 16);
      } catch(NumberFormatException e) {
      }
    }

    if (base < 0) {
      String temp;
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      while (base < 0) {
        System.out.print(MSG_FALTA_BASE);
        try {
          temp = reader.readLine();
          base = Integer.parseInt(temp, 16);
        } catch(Exception e) {
          System.out.println(MSG_ERRO_BASE);
        }
      }
    }

    relocador = new Relocator(entrada, saida, base);
    try {
      if (!relocador.processar()) {
        System.exit(1);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

      // Sucesso!
      System.out.println(MSG_SUCESSO);
  }
}