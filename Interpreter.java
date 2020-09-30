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
     * Classe construtora Interpreter.
	 * Define a instância do arquivo, cria um novo array de memoria preenchido com '0' e define programCounter e dataCounter como '0'
     */
    private Interpreter(){
	this.file = FileHandler.getInstance();
	this.memory = new byte[1000];
	Arrays.fill(this.memory, (byte) 0);
	programCounter = 0;
	dataCounter = 0;
    }
	/**
     * Classe contrutora  auxiliar de Interpreter, onde aloca um novo interpreter caso seja null 
	 * @return retorna a instância alocada
	 */
    public static Interpreter getInstance(){
	if(instance == null)
	    instance = new Interpreter();
	return instance;
    }

    /**
	 * Recebe os inputs um por um vindos do arquivo Source e traduz individualmente para seus respectivos processos.
	 * 
	 */
    public void run() throws CompletionException{
		char command = '$';
		boolean debug = false;
		do{
			try{
				command = file.getSource(programCounter);
			}catch (IndexOutOfBoundsException e){
				throw new IndexOutOfBoundsException("There is no argument to end the program. (Missing '$')");
			}
			switch (command) {
			case '>': dataCounter++; if(debug){ System.out.println("\'>\' dataCounter:\t" + dataCounter); }
				break;
			case '<': dataCounter--; if(debug){ System.out.println("\'<\'dataCounter:\t" + dataCounter); }
				break;
			case '+': memory[dataCounter]++; if(debug){ System.out.println("\'+\' memory[dataCounter]:\t" + memory[dataCounter]); }
				break;
			case '-': memory[dataCounter]--; if(debug){ System.out.println("\'-\' memory[dataCounter]:\t" + memory[dataCounter]); }
				break;
			case '[': if(memory[dataCounter] == (byte) 0) programCounter = goTo(); if(debug){ if(memory[dataCounter] == 0) System.out.println("\'[\' " + goTo() + " goTo " + programCounter); else System.out.println("\'[\' memory[dataCounter] != 0"); }
				break;
			case ']': if(memory[dataCounter] != (byte) 0) programCounter = goTo(); if(debug){ if(memory[dataCounter] == 0) System.out.println("\']\' " + goTo() + " goTo " + programCounter); else System.out.println("\'[\' memory[dataCounter] != 0"); }
				break;
			case ',': memory[dataCounter] = file.getIF();
				break;
			case '.': file.printOF(memory[dataCounter]);
				break;

			case '$': file.printOF(memory);
				break; //print dump memoria TODAS AS 1K
			
			default:
				throw new IllegalArgumentException(
					"The argument \'ANSII " + ((int)command) + "\' does not belong to this alphabet"
				);
			}
			programCounter++;
		}while(command != '$');
    }
	/**
	 * Contabiliza a abertura e fechamento de colchetes '[]', 
	 * @return Retorna um int que se referencia ao programCounter
	 */
	private int goTo(){
		char arg = file.getSource(programCounter);
		int pc = programCounter;
		int openBlock = 1;
			do{
				try{
					if(arg == '[') {
						pc++;
						if(file.getSource(pc) == '[') openBlock++;
						if(file.getSource(pc) == ']') openBlock--;
					} else {	
						pc--;			
						if(file.getSource(pc) == ']') openBlock++;
						if(file.getSource(pc) == '[') openBlock--;
					}
				} catch (Exception e){
					throw new CompletionException(
						"The interpreter was not able to find a close square bracket.", e
					);
				}
			}while(openBlock > 0);
		return pc;
	}

}


