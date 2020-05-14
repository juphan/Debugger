package debugger.UI;

import debugger.*;
import java.util.*;

public class UI {
    
    private DebuggerVirtualMachine dvm;
    
    public UI(DebuggerVirtualMachine dvm){
        this.dvm = dvm;
    }
    
    // Run debugger
    // * Prompt user for command
    public void run(){
        // Display program and prompt user input
        dvm.displayFunc();
        
        // Read user input
        Scanner sc = new Scanner(System.in);
        String input;
        
        // Continuously prompt user for debug commands
        while(dvm.getDebugRun()){
            System.out.println("Type \"?\" for help.");
            System.out.print(">> ");
            input = sc.nextLine();
            execute(input);
        }
    }
    
    // Execute debugger commands from user
    public void execute(String input){
        StringTokenizer toks = new StringTokenizer(input);
        
        // Read all tokens from the command
        while(toks.hasMoreTokens()){
            String tok = toks.nextToken();
            switch(tok){
                case "?": // Help
                    displayHelp();
                    break;
                case "sb": // Set breakpoints
                    while(toks.hasMoreTokens()){ // Set breakpoints at each inputted line
                        dvm.setBreak( Integer.parseInt(toks.nextToken()) );
                    }
                    System.out.println();
                    break;
                case "cb": // Clear breakpoints
                    while(toks.hasMoreTokens()){ // Clear breakpoints at each inputted line
                        dvm.clearBreak( Integer.parseInt(toks.nextToken()) );
                    }
                    System.out.println();
                    break;
                case "lb": // List breakpoints
                    dvm.listBreaks();
                    break;
                case "df": // Display the current function
                    System.out.println();
                    dvm.displayFunc();
                    break;
                case "c": // Continue execution
                    dvm.continueExec();
                    break;
                case "h": // Halt execution
                    dvm.haltExec();
                    break;
                case "q": // Quit execution
                    dvm.quitExec();
                    break;
                case "dv": // Display variables
                    dvm.displayVarVals();
                    break;
                case "so": // Step out of current function
                    dvm.stepOut();
                    break;
                case "sr": // Step over current line
                    dvm.stepOver();
                    break;
                case "si": // Step into the current line's function
                    dvm.stepIn();
                    break;
                case "ft": // Set function tracing
                    if(toks.hasMoreTokens()){
                        dvm.setFuncTrace(toks.nextToken());
                    }else{
                        System.out.println("ERROR: Please enter a tracing setting or enter \"?\" to see an example of a valid command.\n");
                    }
                    break;
                case "cs": // Print call stack
                    dvm.printCallStack();
                    break;
                default:
                    System.out.println("ERROR: Please enter a valid input or enter \"?\" to see all available commands.\n");
            }
        }
        
    }
    
    // Display help menu
    public void displayHelp(){
        System.out.println("\nDebugger Command Line Options:");
        System.out.println("\"?\" \t-> Display the help menu with all possible debug commands.");
        System.out.println("\"sb\" \t-> Set breakpoints at the given line numbers. (Ex: \"sb 1\" or \"sb 9 13\") ");
        System.out.println("\"cb\" \t-> Clear breakpoints at the given line numbers. (Ex: \"cb 1\" or \"cb 9 13\") ");
        System.out.println("\"lb\" \t-> List current breakpoint settings.");
        System.out.println("\"df\" \t-> Display the current function.");
        System.out.println("\"c\" \t-> Continue execution of debugger.");
        System.out.println("\"h\" \t-> Halt execution of debugger.");
        System.out.println("\"q\" \t-> Quit execution of debugger.");
        System.out.println("\"dv\" \t-> Display variables in the current function.");
        System.out.println("\"so\" \t-> Step out of the current function.");
        System.out.println("\"sr\" \t-> Step over the current line.");
        System.out.println("\"si\" \t-> Step into function of the current line.");
        System.out.println("\"ft\" \t-> Set function tracing ON/OFF. (Ex: \"ft ON\" or \"ft OFF\") ");
        System.out.println("\"cs\" \t-> Print call stack.");
        System.out.println();
    }

}
