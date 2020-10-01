import java.util.concurrent.CompletionException;
import java.lang.IndexOutOfBoundsException;
import java.lang.IllegalArgumentException;
import java.util.Arrays;

public class Interpreter {

    private static Interpreter instance;
    
    private FileHandler file;
    private int programCounter;
    private int dataCounter;
	private byte[] memory;
	
    /*
     * Interpreter construction class.
	 * Defines the file instance, creates a new memory array filled with '0' and sets programCounter and dataCounter to '0'
     */
    private Interpreter(){
		this.file = FileHandler.getInstance();
		this.memory = new byte[1000];
		Arrays.fill(this.memory, (byte) 0);
		programCounter = 0;
		dataCounter = 0;
	}
	
	/**
     * Auxiliary construction class of Interpreter, where it allocates a new interpreter if null
	 * @return returns the allocated instance
	 */
    public static Interpreter getInstance(){
		if(instance == null)
			instance = new Interpreter();
		return instance;
    }

    /**
	 * Receives inputs one by one from the Source file and translates individually to their respective processes.
	 */
    public void run() throws CompletionException{
		char command = '$';
		
		do{
			try{
				command = file.getSource(programCounter);
			}catch (IndexOutOfBoundsException e){
				throw new IndexOutOfBoundsException("There is no argument to end the program. (Missing '$')");
			}
			switch (command) {
				case '>': dataCounter++;
					break;
				case '<': dataCounter--;
					break;
				case '+': memory[dataCounter]++; 
					break;
				case '-': memory[dataCounter]--;
					break;
				case '[': if(memory[dataCounter] == (byte) 0) programCounter = goTo();
					break;
				case ']': if(memory[dataCounter] != (byte) 0) programCounter = goTo();
					break;
				case ',': memory[dataCounter] = file.getIF();
					break;
				case '.': file.printOF(memory[dataCounter]);
					break;

				case '$': file.printOF(memory);
					break;
			}
			programCounter++;
		}while(command != '$');
	}
	
	/**
	 * Account for opening and closing brackets '[]',
	 * @return Returns an int that references the programCounter
	 */
	private int goTo(){
		char arg = file.getSource(programCounter);
		int fileSize = file.getSourceSize() - 1;
		int pc = programCounter;
		int openBlock = 1;
		int spot = -1;
			do{
				if(arg == '[') {
					pc++;
					if(file.getSource(pc) == '[') openBlock++;
					if(file.getSource(pc) == ']') openBlock--;
				} else {	
					pc--;			
					if(file.getSource(pc) == ']') openBlock++;
					if(file.getSource(pc) == '[') openBlock--;
				}
				if(openBlock == 0 && spot == -1)
					spot = pc;
			}while(pc > 0 && pc < fileSize);

			if( openBlock != 0 )
				throw new CompletionException(
					"The interpreter was not able to find a close square bracket.", new Throwable()
				);
		return spot;
	}

}


