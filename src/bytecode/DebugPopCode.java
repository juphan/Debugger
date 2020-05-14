package bytecode;

import interpreter.VirtualMachine;
import debugger.DebuggerVirtualMachine;
import java.util.ArrayList;

public class DebugPopCode extends PopCode{
    
    @Override
    public void init(ArrayList<String> args){
        super.init(args);
    }
    
    @Override
    public void execute(VirtualMachine vm){
        super.execute(vm);
        
        // Pop the last N var/val pairs entered in the FER
        DebuggerVirtualMachine dvm = (DebuggerVirtualMachine)vm;
        dvm.executePop(n);
    }
    
}
