import java.nio.file.Paths;
import java.io.IOException;
import java.io.FileReader;
import java.io.File;

public class FileHandler{

	private static FileHandler instance;
	private char[] source;

	private FileHandler(){
		try {
			source();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static FileHandler getInstance(){
		if(instance == null)
			instance = new FileHandler();
		return instance;
	}

	/*
     * @return String
     */
	private void source() throws IOException {
		String filePath = Paths.get("").toAbsolutePath().toString();
		filePath += "\\files\\";
		String fileName = "source.txt";
	
		File file = new File(filePath + fileName);
		FileReader fReader = null;
		try {
			fReader = new FileReader(file);
			source = new char[(int) file.length()];
			fReader.read(source);
		}
		catch (IOException e) {
			e.printStackTrace();
		} finally {
			fReader.close();
		}
	}

	public char getSource(int address){
		return source[address];
	}

	public byte getIF(){
		return (byte) 0;
	}

	public void printOF(){

	}
}