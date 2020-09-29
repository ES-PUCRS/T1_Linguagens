import java.nio.file.Paths;
import java.util.Arrays;
import java.io.PrintWriter;
import java.io.IOException;
// import java.nio.file.Files;
// import java.nio.file.Path;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;

public class FileHandler{

	private static PrintWriter logDataWriter;
	private static FileHandler instance;
	private int ifCounter;
	private byte[] ifArray;
	private char[] source;

	/**
	 * Classe contrutora FileHandler
	 * Le os arquiivos Source e IF, caso nada tenha lido do arquivo IF
	 * 					Falta só o print do of. ta
	 */
	private FileHandler(){
		ifCounter = -1;
		try {
			readSource();
			readIf();
			printOF("System output: ", false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Auxiliador da classe construtora.
	 * Defini uma nova instancia de arquivo caso nenhuma tenha sido alocada e retorna a instancia atual.
	 * @return Retorna a instância do arquivo
	 */
	public static FileHandler getInstance(){
		if(instance == null)
			instance = new FileHandler();
		return instance;
	}
	/**
	 * Realiza a leitura do arquivo IF e armazena conteudo em um vetor de byte
	 * @throws IOException Tentativa de leitura do arquivo
	 */
	private void readIf() throws IOException {
		String filePath = Paths.get("").toAbsolutePath().toString();
		filePath += "\\files\\";
		String fileName = "if.txt";
	
		File file = new File(filePath + fileName);
		FileReader fReader = null;
		try {
			fReader = new FileReader(file);
			ifArray = new byte[(int) file.length()];
			
			int data = fReader.read(); int i = 0;
			while(data != -1) {
				ifArray[i] = Byte.parseByte(String.valueOf(data));
				data = fReader.read();
				i++;
			  }
		}
		catch (IOException e) {
			e.printStackTrace();
		} finally {
			fReader.close();
		}
	}

	/**
	 * Realiza a leitura do arquivo Source e armazena conteudo em um vetor de char
	 * @throws IOException Tentativa de leitura do arquivo
	 */
	private void readSource() throws IOException {
		String filePath = Paths.get("").toAbsolutePath().toString();
		filePath += "\\files\\";
		String fileName = "source.txt";
	
		File file = new File(filePath + fileName);
		FileReader fReader = null;
		try {
			fReader = new FileReader(file);
			char[] arr = new char[(int) file.length()];
			fReader.read(arr);
			char[] sublist = new char[arr.length];
			int j = 0;
			for(int i = 0; i < arr.length; i++){
				if(arr[i] != 10 && arr[i] != 13){
					sublist[i-j] = arr[i];
				}else{
					j++;
				}
			}
			source = Arrays.copyOfRange(sublist, 0, arr.length-j);
		}
		catch (IOException e) {
			e.printStackTrace();
		} finally {
			fReader.close();
		}
	}
	/**
	 * Recebe o numero (int) referente a qual linha esta o comando desejado e retorna o conteudo (char) da linha solicitada
	 * @param address referencia ao comando (linha) do arquivo 
	 * @return conteudo do comando solicitado
	 */
	public char getSource(int address){
		return source[address];
	}

	/**
	 * IF => Arquivo de input
	 * Retorna o proximo conteudo do arquivo de memoria (bytes)
	 * @return retorna o byte do conteudo da memoria solicitado
	 */
	public byte getIF(){
		ifCounter++;
		return ifArray[ifCounter];
	}

	/**
	 * Recebe o conteudo que deve ser impresso ao final do arquivo de saida
	 * @param content Conteudo (byte) a ser impresso no arquivo de saída
	 * @throws IOException Realiza a tentativa de impressao no final do arquivo
	 */
	public void printOF(char content){ printOF(Integer.toString((int)content)); }
	public void printOF(int content){ printOF(Integer.toString(content)); }
	public void printOF(String content){ printOF(content, true); }
	private void printOF(String content, boolean append){printOF(content,"of.txt",append); printOF(content,"ofChar.txt", append);}
	private void printOF(String content, String fileName, boolean append){
		String filePath = Paths.get("").toAbsolutePath().toString();
		filePath += "\\files\\";
		
		try{
			File ofFile = new File(filePath + fileName);
	
			if(!ofFile.exists()){
				ofFile.createNewFile();
			}
		
			logDataWriter = new PrintWriter(new FileWriter(ofFile, append));
		
		}catch(IOException x){
			 System.err.format("Erro de E/S: %s%n", x);
		}

		logDataWriter.append("\n");
		if(fileName.equals("ofChar.txt") && append == true){
			if(Integer.parseInt(content) > 31 && Integer.parseInt(content) < 127)
				logDataWriter.append((char)Integer.parseInt(content));
			else
				logDataWriter.append("b"+content);
		}else
			logDataWriter.append(content);

        logDataWriter.close();
	}

	public void printOF(byte[] content){ printOF(content, "of.txt", true); printOF(content,"ofChar.txt",true); }
	private void printOF(byte[] content, String fileName, boolean isOf){
		String filePath = Paths.get("").toAbsolutePath().toString();
		filePath += "\\files\\";
		
		try{
			File ofFile = new File(filePath + fileName);
	
			if(!ofFile.exists()){
				ofFile.createNewFile();
			}
			
			logDataWriter = new PrintWriter(new FileWriter(ofFile, isOf));
		
		}catch(IOException x){
			 System.err.format("Erro de E/S: %s%n", x);
		}

		logDataWriter.append("\n\n");
		if(fileName.equals("ofChar.txt"))
			if(content[0] > 31 && content[0] < 127)
				logDataWriter.append((char)content[0]);
			else
				logDataWriter.append("b"+String.valueOf((int)content[0]));
		else
			logDataWriter.append(String.valueOf((int)content[0]));
		int i = 1;
		do{
			logDataWriter.append(" ");
			if(fileName.equals("ofChar.txt"))
				if(content[i] > 31 && content[i] < 127)
					logDataWriter.append((char)content[i]);
				else
					logDataWriter.append("b" + String.valueOf((int)content[i]));
			else
				logDataWriter.append(String.valueOf((int)content[i]));

			i++;
		}while(i < content.length);
        logDataWriter.close();
	}
}
