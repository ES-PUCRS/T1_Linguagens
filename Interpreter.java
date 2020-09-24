import java.lang.IllegalArgumentException;
import java.util.Arrays;

public class Interpreter {

    private static Interpreter instance;
    
    private FileHandler file;
    private int programCounter;
    private int dataCounter;
    private byte[] memory;
    /*
     *
     */
    private Interpreter(){
	this.file = FileHandler.getInstance();
	this.memory = new byte[999];
	Arrays.fill(this.memory, (byte) 0);
	programCounter = 0;
	dataCounter = 0;
    }
    /*
     * 
     */
    public static Interpreter getInstance(){
	if(instance == null)
	    instance = new Interpreter();
	return instance;
    }

    /*  
     *  
     */ 
    public void run(){
		char command = 'a';
		do{
			command = file.getSource(programCounter);
			switch (command) {
				case '>': dataCounter++;
					break;
				case '<': dataCounter--;
					break;
				case '+': memory[dataCounter]++;
					break;
				case '-': memory[dataCounter]--;
					break;
				case '[': if(memory[dataCounter] == 0) programCounter = goTo();
					break;
				case ']': if(memory[dataCounter] == 0) programCounter = goTo();
					break;
				case ',': memory[dataCounter] = file.getIF();
					break;
				case '.': file.printOF();
					break;

				case '$': break;
				
				default:
					throw new IllegalArgumentException(
						"The argument does not belong to this alphabet"
					);
			}
		}while(command != '$');
    }

	private int goTo(){
		char arg = file.getSource(programCounter);
		int pc = programCounter;
		int found = 1;
			do{
				pc++;
				try{
					if(arg == '[') {
						if(file.getSource(pc) == '[') found++;
						if(file.getSource(pc) == ']') found--;
					} else {				
						if(file.getSource(pc) == ']') found++;
						if(file.getSource(pc) == '[') found--;
					}
				} catch (Exception e){
					e.printStackTrace();
					//Descrever melhor que não existe ] que fecha certim.
				}
			}while(found > 0);
		return 1;
	}

}
