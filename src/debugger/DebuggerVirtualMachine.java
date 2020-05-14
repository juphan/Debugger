package debugger;

import bytecode.*;
import interpreter.*;
import java.io.*;
import java.util.*;

public class DebuggerVirtualMachine extends VirtualMachine{
    
    private ArrayList<String> xLines = new ArrayList<>(); // Arraylist of lines from X code file
    private ArrayList<Boolean> isBreak = new ArrayList<>(); // Arraylist of set breakpoints (true)
    private ArrayList<Boolean> isValid = new ArrayList<>(); // Arraylist of valid lines that can have breakpoints (true)
    
    private Stack<FunctionEnvironmentRecord> fers = new Stack<>(); // Stack of FERs
    private boolean isDebugRun = true; // Check if debugger should keep running
    private boolean isStepOut = false; // Check if debugger should step out of current function
    private boolean isStepOver = false; // Check if debugger should step over current line's execution
    private boolean isStepIn = false; // Check if debugger should step into the current line's function
    private boolean isTrace = false; // Check if function tracing is on
    private int envSize; // Keep track of the number of FERS in the environment stack
    private String xFile; // Name of x source code file
    private int stepOverLine; // Line number after stepping over current line
    private StringBuilder traces = new StringBuilder("*** TRACES ***\n"); // Keep track of function traces
    
    public DebuggerVirtualMachine(Program program, String xFile){
        super(program);
        this.xFile = xFile;
        
        // Read each line in the source file and keep track of breakpoints
        try{
            Scanner sc = new Scanner(new File(xFile));
            while(sc.hasNextLine()){
                xLines.add(sc.nextLine());
                isBreak.add(false);
                isValid.add(false);
            }
        }catch(FileNotFoundException e){
            System.out.println("ERROR: Input X code file is not found.");
            System.exit(1);
        }
        
        // Push a default FER and initialize
        fers.push(new FunctionEnvironmentRecord()); 
        fers.peek().setFunctionInfo("main", 1, xLines.size());
        
        // Find valid breakpoints
        ArrayList<ByteCode> bytes = program.getProgram();
        for(int i=0; i<bytes.size(); i++){
            ByteCode bc = bytes.get(i);
            if(bc.getClass().getSimpleName().equals("DebugLineCode")){
                int lineNum = ((DebugLineCode)bc).getLineNum();
                if( lineNum > 0 ){ // Line number must be at least 1 to be valid
                    isValid.set(lineNum-1, true);
                }
            }
        }
        
    }
    
    // Execute program run from debugger
    @Override
    public void executeProgram(){
        while(super.notHalt){
            ByteCode bc = program.getCode(pc);
            bc.execute(this);
            pc++;
            
            // Check for step out command
            if(isStepOut && (envSize > fers.size())){ // Break after function "return"
                super.setNotHalt(false);
                isStepOut = false;
            }
            
            // Check for step over command
            if( isStepOver && (envSize > fers.size()) ){ // Break after a function "return"
                super.setNotHalt(false);                 
                isStepOver = false;                     
            }else if( isStepOver && (envSize == fers.size()) && (stepOverLine == fers.peek().getCLine()) ){ // Run all function calls in current line
                super.setNotHalt(false);                                                                    // * Break after reaching next line
                isStepOver = false;
            }
            
            // Check for step in command (step over if no functions in current line)
            if( isStepIn && (envSize > fers.size()) ){ // Break after a "return"
                super.setNotHalt(false);             
                isStepIn = false;
            }else if( isStepIn && (envSize == fers.size()) && (stepOverLine == fers.peek().getCLine()) ){ // Run all function calls in current line
                super.setNotHalt(false);                                                                  // * Break after reaching next line
                isStepIn = false;
            }
            
            // HALT bytecode detected
            if(bc.getClass().getSimpleName().equals("HaltCode")){
                // Print function trace after exiting main()
                if(isTrace){
                    for(int i=0; i<(fers.size()-1); i++){
                        traces.append("  "); // Function indentation
                    }
                    traces.append("exit ");
                    traces.append(fers.peek().getFunc());
                    traces.append("()");
                    traces.append("\n");
                    System.out.println(traces);
                }
                
                // Finish program run
                System.out.println("Execution complete.\n");
                System.exit(0);
            }
        }
    }
        
    // Execute DebugLineCode bytecode
    // * Check line for breakpoints
    public void executeLine(int lineNum){
        // Check for breakpoints
        if(lineNum > 0){
            fers.peek().setCLine(lineNum);
            
            // Stop at breakpoint
            if(isBreak.get(lineNum - 1)){
                super.setNotHalt(false); // Stop execution
                
                // Display current function code when stopped by a breakpoint
                // * Prevents repeat displays when a "step" operation occurs
                if( (isStepOut == false) && (isStepOver == false) && (isStepIn == false) ){ 
                    displayFunc();
                }
                
                // Breakpoints cancel step commands
                isStepOut = false;
                isStepOver = false;
                isStepIn = false;
            }
        }

        // Jump over branch and then break
        // * Do for step over and step in
        if( (isStepOver || isStepIn) && (envSize == fers.size()) ){
            super.setNotHalt(false); 
            isStepOver = false;
            isStepIn = false;
        }
    }
        
    // Execute DebugCallCode bytecode
    // * Push a new FER for the function call
    public void executeCall(){
        fers.push(new FunctionEnvironmentRecord());
    }
    
    // Execute DebugFunctionCode bytecode
    // * Set function info for the current FER 
    public void executeFunction(String fName, int start, int end){
        fers.peek().setFunctionInfo(fName, start, end);
        fers.peek().setCLine(start);

        // Print function trace
        // * Perform when you enter a function
        if(isTrace){
            for(int i=0; i<(fers.size()-1); i++){
                traces.append("  "); // Function indentation
            }
            traces.append(fers.peek().getFunc()); // Function name
            traces.append("(");
            int formalPC = pc+1;
            ByteCode bcForm = program.getCode(formalPC);
            while( bcForm.getClass().getSimpleName().equals("DebugFormalCode") ){
                int inputVal = RTStack.getValueAtOffset( ((DebugFormalCode)bcForm).getOff() ); // Get function input from RT Stack
                fers.peek().addInputs(inputVal); // Store input value in FER for return trace
                traces.append(inputVal); 
                formalPC++;
                bcForm = program.getCode(formalPC);
                if( bcForm.getClass().getSimpleName().equals("DebugFormalCode") ){
                    traces.append(", ");
                }
            }
            traces.append(")\n");
            System.out.println(traces);
        }
        
        // Push formals at start of function
        ByteCode bc = program.getCode(pc+1);
        while(bc.getClass().getSimpleName().equals("DebugFormalCode")){
            bc.execute(this);
            pc++;
            bc = program.getCode(pc+1);
        }
        
        // Check for step in command
        // * Break at first line of function
        if(isStepIn && (envSize < fers.size())){
            super.setNotHalt(false); 
            isStepIn = false;
        }
          
    }
    
    // Execute DebugReturnCode bytecode
    // * Exit current FER
    public void executeReturn(int returnVal){
        // Print function trace
        // * Perform when you exit a function
        if(isTrace){
            for(int i=0; i<(fers.size()-1); i++){
                traces.append("  "); // Function indentation
            }
            traces.append("exit ");
            traces.append(fers.peek().getFunc());
            traces.append("(");
            for(int i=0; i<fers.peek().getInSize(); i++){
                traces.append(fers.peek().getInput(i));
                if( (i+1) < fers.peek().getInSize() ){
                    traces.append(", ");
                }
            }
            traces.append("): return ");
            traces.append(returnVal);
            traces.append("\n");
            System.out.println(traces);
        }
        
        // Pop FER after function return
        fers.pop();
    }
    
    // Execute DebugLitCode bytecode
    // * Add a var/val pair to the current FER
    public void executeLit(String var, int off){
        fers.peek().setVarVal(var, off);
    }
    
    // Execute DebugPopCode bytecode
    // * Pop N var/val pairs from the current FER
    public void executePop(int n){
        fers.peek().doPop(n);
    }

    // Execute DebugFormalCode bytecode
    // * Add formal to current FER
    public void executeFormal(String var, int off){
        fers.peek().setVarVal(var, off);
    }
    
    // Set breakpoint at line N and display changes
    public void setBreak(int n){
        if(isValid.get(n-1) == false){
            System.out.printf("ERROR: A breakpoint cannot be set at line %d.\n", n);
        }else if(isBreak.get(n-1) == false){
            isBreak.set(n-1, true);
            System.out.printf("Breakpoint is set at line %d.\n", n);
        }else{
            System.out.printf("ERROR: Breakpoint is already set at line %d.\n", n);
        }
    }
    
    // Clear a breakpoint at line N and display changes
    public void clearBreak(int n){
        if(isBreak.get(n-1) == true){
            isBreak.set(n-1, false);
            System.out.printf("Breakpoint at line %d is cleared.\n", n);
        }else{
            System.out.printf("ERROR: No breakpoint is set at line %d.\n", n);
        }
    }
    
    // List current breakpoint settings
    public void listBreaks(){
        // No breakpoints set
        if(!isBreak.contains(true)){
            System.out.println("*** NO BREAKPOINTS SET ***\n");
            return;
        }
        
        // Store lines that contain breakpoints
        List<Integer> bp = new ArrayList<>();
        for(int i=0; i<isBreak.size(); i++){
            if(isBreak.get(i)){
                bp.add(i+1);
            }
        }
        
        // Print breakpoints
        System.out.print("Breakpoints are set on line(s): ");
        for(int j=0; j<bp.size(); j++){
            System.out.print(bp.get(j));
            if( (j+1) < bp.size() ){
                System.out.print(", ");
            }
        }
        System.out.println("\n");
    }
    
    // Display current function with line numbers and current line
    public void displayFunc() {
        StringBuilder sb = new StringBuilder();
        
        // Print intrinsic function (READ/WRITE) during step in
        if( fers.peek().getSLine() < 0 ){
            System.out.printf("****** %s ******\n\n", fers.peek().getFunc());
            return;
        }else{
            System.out.printf("*** Debugging %s ***\n\n", xFile);
        }
        
        // Loop through each saved line in the x file and print it out
        for (int i=(fers.peek().getSLine()) - 1; i<(fers.peek().getELine()); i++) {
            // Use an asterisk to represent breakpoint
            if( isBreak.get(i) ){
                sb.append("* ");
            }else{
                sb.append("  ");
            }
            
            // Display line number and then the line from the source file
            if(i<9){
                sb.append(" "); // Align line numbers
            }
             sb.append(i+1);
             sb.append(". ");
             sb.append(xLines.get(i));
            
            // Point to current line
            if( (i+1) == fers.peek().getCLine() ){
                sb.append(" <----- (Current Line)");
            }
            
            // Move to next line
            sb.append("\n");
        }
        
        System.out.printf("%s\n", sb);
    }
    
    // Continue execution of program
    public void continueExec(){
        super.notHalt = true;
        System.out.println("Continuing Debugger Execution...\n");
        executeProgram();
    }
    
    // Halt execution of program
    public void haltExec(){
        super.notHalt = false;
        System.out.println("*** EXECUTION HALTED ***\n");
        executeProgram();
    }
    
    // Quit debugger
    public void quitExec(){
        System.out.println("Exiting Debugger...\n");
        isDebugRun = false;
    }
    
    // Display variables
    public void displayVarVals() {
        Table st = fers.peek().getSymbTable();
        Set vars = st.keys(); // Variables stored in the current FER
        Iterator it = vars.iterator();
        StringBuilder sb = new StringBuilder("Display local variables: \n");

        // No local variables declared
        if(!it.hasNext()){
            sb.append("*** EMPTY ***\n");
            System.out.println(sb);
        }else{
            // Print out all local variables and their values
            while(it.hasNext()){
                String str = (String)it.next(); // Variable (Key in Table)
                sb.append("  ");
                sb.append(str);
                sb.append(" = ");
                sb.append( super.getVal( (int)st.get(str) ) ); // Value at offset
                System.out.println(sb);
                sb.setLength(0); // Clear StringBuilder
            }
            System.out.println();
        }
        
    }
    
    // Step out of current function
    // * Break at the line that called the function
    public void stepOut(){
        System.out.println("Stepping out...\n");
        isStepOut = true;
        envSize = fers.size(); // Current function's environment stack size
        
        super.setNotHalt(true); // Move past current breakpoint
        executeProgram();
        displayFunc();
    }
        
    // Step over the current line's execution
    // * Break after current line is run
    public void stepOver(){
        System.out.println("Stepping over...\n");
        isStepOver = true;
        envSize = fers.size(); // Current function's environment stack size
        stepOverLine = fers.peek().getCLine() + 1;
        
        super.setNotHalt(true); // Move past current breakpoint
        executeProgram();
        displayFunc();
    }
    
    // Step into the current line's function
    // * Break at first line of the function
    // * Step over if no function
    public void stepIn(){
        System.out.println("Stepping in...\n");
        isStepIn = true;
        envSize = fers.size(); // Current function's environment stack size
        stepOverLine = fers.peek().getCLine() + 1;
        
        super.setNotHalt(true); // Move past current breakpoint
        executeProgram();
        displayFunc();
    }
    
    // Set function tracing settings
    // * Print function traces when entering/exiting functions if ON
    public void setFuncTrace(String str){
        if(str.equalsIgnoreCase("ON")){
            isTrace = true;
            System.out.println("*** TRACE ON ***\n");
        }else if(str.equalsIgnoreCase("OFF")){
            isTrace = false;
            System.out.println("*** TRACE OFF ***\n");
        }else{
            System.out.println("ERROR: Unrecognized trace setting.\n");
        }
    }
    
    // Print call stack
    public void printCallStack(){
        String str;
        Iterator it = fers.iterator();
        ArrayList<String> revStack = new ArrayList<>();
        
        // Store each line in the call stack
        while(it.hasNext()){
            FunctionEnvironmentRecord fer = (FunctionEnvironmentRecord)it.next();
            if(it.hasNext()){
                str = "Called from " + fer.getFunc() + " at line " + fer.getCLine();
                revStack.add(str);
            }else{
                str = fer.getFunc() + " at line " + fer.getCLine();
                revStack.add(str);
            }
        }
        
        // Print the stack
        System.out.println("*** CALL STACK ***");
        for(int i=revStack.size()-1; i>=0; i--){
            System.out.println(revStack.get(i));
        }  
        System.out.println();
    }
    
    // Getters
    public boolean getDebugRun(){
        return isDebugRun;
    }
    
    public String getXFile(){
        return xFile;
    }
    
}
