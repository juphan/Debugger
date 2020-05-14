package bytecode;

import interpreter.VirtualMachine;
import debugger.DebuggerVirtualMachine;
import java.util.*;

public class DebugCallCode extends CallCode {
    
    @Override
    public void init(ArrayList<String> args){
        super.init(args);        
    }
    
    @Override
    public void execute(VirtualMachine vm){
        super.execute(vm);
        
        // Push new FER into the FER Stack for the new function call
        DebuggerVirtualMachine dvm = (DebuggerVirtualMachine)vm;
        dvm.executeCall();
    }
 
}
