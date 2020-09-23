import java.util.Arrays;

public class Compiler {

    private static Compiler instance;
    
    private FileHandler file;
    private int programCounter;
    private int dataCounter;
    private byte[] memory;
    /*
     *
     */
    private Compiler(){
        this.file = FileHandler.getInstance();
        this.memory = new byte[999];
        Arrays.fill(this.memory, (byte) 0);
        programCounter = 0;
        dataCounter = 0;
    }
    /*
     * 
     */
    public static Compiler getInstance(){
        if(instance == null)
            instance = new Compiler();
        return instance;
    }

    /*  
     *  
     */ 
    public void run(){
        char command = 'a';
        do{
                command = file.getSource(programCounter);
                // byte memoryPos = memory[dataCounter];
                switch (command) {
                        case '>': 
                                break;
                        case '<': 
                                break;
                        case '+': 
                                break;
                        case '-': 
                                break;
                        case '[': 
                                break;
                        case ']': 
                                break;
                        case ',': 
                                break;
                        case '.': 
                                break;
                        case '$':
                                break;
                        
                        default:
                                throw exception and handle error (word not in alphabet)
                
                }
        }while(command != '$');
    }
}
