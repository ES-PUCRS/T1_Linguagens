import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Source{

	public static void read() throws IOException {
		Reader fileReader = new FileReader("c:\\data\\input-text.txt");

		int data = fileReader.read();
		while (data != -1) {
			System.out.println(data);
			data = fileReader.read();
		}
		fileReader.close();
	}
}