package bytecode;

import interpreter.VirtualMachine;
import debugger.DebuggerVirtualMachine;
import java.util.ArrayList;

public class DebugFormalCode extends ByteCode{
    
    private String formal;
    private int off; // Offset of current frame that stores input values
    
    @Override
    public void init(ArrayList<String> args){
        formal = args.get(0);
        off = Integer.parseInt(args.get(1));
    }
    
    @Override
    public void execute(VirtualMachine vm){
        // Push formal into FER
        DebuggerVirtualMachine dvm = (DebuggerVirtualMachine) vm;
        dvm.executeFormal(formal, off);
    }
    
    // Getter
    public int getOff(){
        return off;
    }
    
}
