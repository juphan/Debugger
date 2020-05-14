package interpreter;

import bytecode.*;
import java.util.Stack;

public class VirtualMachine {
    
    protected RunTimeStack RTStack = new RunTimeStack();
    protected Stack<Integer> returnAddrs = new Stack<>(); 
    protected Program program; 
    protected int pc = 0; // Program Counter
    protected boolean notHalt = true; // Has the HALT bytecode been detected?
    protected String dumpState = "OFF"; // Boolean on whether to output RTStack operations
    
    public VirtualMachine(Program program){
        this.program = program;
    }
    
    public void executeProgram(){
        while(notHalt) {
            ByteCode code = program.getCode(pc); // Start at PC
            code.execute(this); // Execute each bytecode
            
            // Perform in case DUMP is on
            // Print out statement/description of operations taking place
            if(dumpState.equals("ON") && !(code.getClass().getSimpleName()).equals("DumpCode") ){
                RTStack.dump();
            }
            
            pc++;
        }
    }
    
    // Getters
    public RunTimeStack getRTStack(){
        return this.RTStack;
    }
    
    public int popReturnAddrs(){
        return this.returnAddrs.pop();
    }
    
    public int getPC(){
        return this.pc;
    }
    
    public String getDumpState(){
        return this.dumpState;
    }
    
    public Program getProgram(){
        return program;
    }
    
    public int getVal(int off){
        return RTStack.getVal(off);
    }
    
    // Setters
    public void pushReturnAddrs(int n){
        returnAddrs.push(n);
    }
    
    public void setPC(int n){
        this.pc = n;
    }
    
    public void setDumpState(String s){
        this.dumpState = s;
    }
    
    public void setNotHalt(boolean nh){
        this.notHalt = nh;
    }
    
    // Stop program
    public void stopProgram(){
        this.notHalt = false;
    }
    
    // Increment PC
    public void incPC(){
        this.pc++;
    }
    
}
