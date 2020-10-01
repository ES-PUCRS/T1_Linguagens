import java.nio.file.Paths;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.util.Arrays;
import java.io.File;

public class FileHandler{

	private static PrintWriter logDataWriter;
	private static FileHandler instance;
	private int ifCounter;
	private byte[] ifArray;
	private char[] source;

	/**
	 * FileHandler building class
	 * Read the Source and IF files, if nothing has been read from the IF file
	 */
	private FileHandler(){
		ifCounter = -1;
		try {
			createFolder();
			readSource();
			readIf();
			printOF("System output:\n", false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createFolder(){
		String filePath = Paths.get("").toAbsolutePath().toString();
		filePath += "\\files";
		File file = new File(filePath);
		File ifFile = new File(filePath+"\\if.txt");
		File sourceFile = new File(filePath+"\\source.txt");
		if(!file.exists() || !file.isDirectory())
			file.mkdir();

		try{
			if(!ifFile.exists())
				ifFile.createNewFile();
			if(!sourceFile.exists()){
				sourceFile.createNewFile();
				logDataWriter = new PrintWriter(new FileWriter(sourceFile, false));
				logDataWriter.println("$");
				logDataWriter.close();
			}
		}catch(Exception e) { e.printStackTrace(); }
	}

	/**
	 * Construction class assistant.
	 * Define a new file instance if none has been allocated and return the current instance.
	 * @return Returns the file instance
	 */
	public static FileHandler getInstance(){
		if(instance == null)
			instance = new FileHandler();
		return instance;
	}
	
	/**
	 * Reads the if file and stores content in a byte vector
	 * @throws IOException Attempted to read the file
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
	 * Reads the Source file and stores content in a char vector
	 * @throws IOException Attempted to read the file
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
	 * Receive the number (int) for which line the desired command is and return the content (char) of the requested line
	 * @param address reference to the command (line) of the file
	 * @return command content requested
	 */
	public char getSource(int address) throws ArrayIndexOutOfBoundsException{
		return source[address];
	}

	/**
	 * @return int source file length
	 */
	public int getSourceSize(){
		return source.length;
	}

	/**
	 * IF => Input file
	 * Returns the next contents of the memory file (bytes)
	 * @return returns the byte of the requested memory content
	 */
	public byte getIF(){
		ifCounter++;
		return ifArray[ifCounter];
	}

	/**
	 * Receives the content that must be printed at the end of the output file
	 * @param content Content (byte) to be printed on the output file
	 * @throws IOException Performs the printing attempt at the end of the file
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

		// logDataWriter.append("\n");
		if(fileName.equals("ofChar.txt") && append == true){
			if(Integer.parseInt(content) > 31 && Integer.parseInt(content) < 127)
				logDataWriter.append((char)Integer.parseInt(content));
			else
				logDataWriter.append("b"+content);
		}else
			logDataWriter.append(content);

        logDataWriter.close();
	}

	/**
	 * Receives the content that must be printed at the end of the output file
	 * @param content Content (byte []) to be printed in the output file
	 * @throws IOException Performs the printing attempt at the end of the file
	 */
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
