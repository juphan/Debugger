package bytecode;

import interpreter.VirtualMachine;
import debugger.DebuggerVirtualMachine;
import java.util.ArrayList;

public class DebugLineCode extends ByteCode{
    
    private int lineNum; // Line number
    
    @Override
    public void init(ArrayList<String> args){
        lineNum = Integer.parseInt(args.get(0));       
    }
    
    @Override
    public void execute(VirtualMachine vm){
        // Set current line
        DebuggerVirtualMachine dvm = (DebuggerVirtualMachine)vm;
        dvm.executeLine(lineNum);   
    }
    
    // Get line number
    public int getLineNum() {
        return lineNum;
    }
    
}
