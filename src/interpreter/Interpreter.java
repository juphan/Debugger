package interpreter;

import debugger.Debugger;
import java.io.*;

/**

* Interpreter class runs the interpreter:

* 1. Perform all initializations
* 2. Load the bytecodes from file
* 3. Run the virtual machine

*/

public class Interpreter {

    public ByteCodeLoader bcl;

    public Interpreter(String codeFile) {
        try {
            CodeTable.init();
            bcl = new ByteCodeLoader(codeFile);
        } catch (IOException e) {
        System.out.println("**** " + e);
        }
    }

    void run() {
        Program program = bcl.loadCodes();
        VirtualMachine vm = new VirtualMachine(program);

        vm.executeProgram();
    }

    public static void main(String args[]) {
        
        switch(args.length){
            case 1: // Run the interpretter
                (new Interpreter(args[0])).run();
                break;
            case 2: // Run the debugger
                if (args[0].equals("-d")) {
                    // "simple.x" - X code file
                    // "simple.x.cod" - Byte file
                    // Command Line: java -jar Debugger.jar -d simple
                    // Make sure there are Debugger bytecodes in the .x.cod file
                    (new Debugger(args[1]+".x", args[1]+".x.cod")).run();
                }
                break;
            default: // Incorrect arguments are inputted
                if(args.length == 0){ // No arguments
                    System.out.println("***Incorrect usage, try: java interpreter.Interpreter <file>");
                    System.exit(1);
                }
        }

    }

}