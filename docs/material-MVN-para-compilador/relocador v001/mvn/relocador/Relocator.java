package mvn.relocador;
import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;

/**
 * Classe responsável pela relocação do código.<br>
 * @author FLevy
 * @version 20.11.2005
 */
class Relocator {
  private File arqEntrada;
  private File arqSaida;
  private int base;

  /**
   * Constrói um relocador.
   * @param entrada O arquivo de entrada.
   * @param saida O arquivo de saída.
   * @param base A base para relocação.
   */
  public Relocator(File entrada, File saida, int base) {
    this.arqEntrada = entrada;
    this.arqSaida = saida;
    this.base = base;
  }

  /**
   * Processo o arquivo de entrada e gera a saída relocável.
   * @return Verdadeiro caso a relocação tenha sido completada com sucesso.
   * @throws IOException Caso haja algum erro para ao ler ou escrever nos
   * arquivos.
   */
  public boolean processar() throws IOException{
    BufferedWriter saida = new BufferedWriter(new FileWriter(arqSaida));
    String operacao, operacaoSaida;
    int code;

    StringTokenizer tokenizer;
    String endereco, enderecoSaida, simbolo;
    int nibble;
    BufferedReader leitor;
    int numLinha = 0;
    String linha;
    int end = 0; // endereco de saida

    leitor = new BufferedReader(new FileReader(arqEntrada));
    linha = leitor.readLine();
    numLinha = 0;

    while (linha != null) {
      numLinha++;
      tokenizer = new StringTokenizer(linha);

      if (tokenizer.countTokens() == 2) {
        // só endereço e código
        endereco = tokenizer.nextToken();
        operacao = tokenizer.nextToken();

        if (endereco.length() != 4) {
          // endereco invalido
          System.out.println(MvnRelocator.MSG_SINTAXE_INADEQUADA + numLinha);
          return false;
        }

        if (operacao.length() != 4) {
          // operacao invalida
          System.out.println(MvnRelocator.MSG_SINTAXE_INADEQUADA + numLinha);
          return false;
        }

        try {
          nibble = Integer.parseInt(endereco.charAt(0)+"", 16);
        } catch (NumberFormatException e) {
          System.out.println(MvnRelocator.MSG_SINTAXE_INADEQUADA + numLinha);
          return false;
        }

        if (isRelocable(nibble)) {
          // obtendo endereco
          try {
            end = Integer.parseInt(endereco.substring(1, endereco.length()), 16);
          } catch (NumberFormatException e) {
            System.out.println(MvnRelocator.MSG_SINTAXE_INADEQUADA + numLinha);
            return false;
          }

          if (end + base > MvnRelocator.LAST_VAL_ADDR) {
            System.out.println(MvnRelocator.MSG_ERRO_LIMITE_MEM + numLinha);
            return false;
          }

          enderecoSaida = Integer.toHexString(end + base);
          enderecoSaida = "0000" + enderecoSaida;
          enderecoSaida = enderecoSaida.substring(enderecoSaida.length() - 4, enderecoSaida.length());
        } else {
          // tira o nibble
          enderecoSaida = "0000" + endereco.substring(1, endereco.length());
          enderecoSaida = enderecoSaida.substring(enderecoSaida.length() - 4, enderecoSaida.length());
        }

        if (isArgumentRelocable(nibble)) {
          // precisa corrigir o argumento a partir da base
          operacaoSaida = "0000" + Integer.toHexString(Integer.parseInt(operacao.substring(1, operacao.length()), 16) + base);
          operacaoSaida = operacaoSaida.substring(operacaoSaida.length() - 3, operacaoSaida.length());
          operacaoSaida = operacao.charAt(0) + operacaoSaida;
        } else {
          operacaoSaida = operacao;
        }
        saida.write(enderecoSaida + MvnRelocator.ASM_SPACE + operacaoSaida);
        saida.newLine();
      } else if (tokenizer.countTokens() != 0) {
        // número de tokens inadequado
        System.out.println(MvnRelocator.MSG_SINTAXE_INADEQUADA + numLinha + " (" + arqEntrada.getName() + ")");
        return false;
      }
      linha = leitor.readLine(); // proxima linha
    }
    leitor.close();
    leitor = null;
    saida.close();
    saida = null;
    return true;
  }

  /**
   * Verifica se o nibble representa um endereço relocável.
   * @param nibble O nibble a ser verificado.
   * @return Verdadeiro se o endereço for relocável.
   */
  private boolean isRelocable(int nibble) {
    return (nibble >> 3) %2 > 0;
  }

  /**
   * Verifica se o nibble representa um argumento relocável.
   * @param nibble O nibble a ser verificado.
   * @return Verdadeiro se o argumento for relocável.
   */
  private boolean isArgumentRelocable(int nibble) {
    return (nibble >> 1) %2 > 0;
  }
}