package bytecode;

import interpreter.VirtualMachine;
import debugger.DebuggerVirtualMachine;
import java.util.ArrayList;

public class DebugReturnCode extends ReturnCode{

    @Override
    public void init(ArrayList<String> args){
        super.init(args);
    }
    
    @Override
    public void execute(VirtualMachine vm){ 
        super.execute(vm);
        
        // Remove current FER
        DebuggerVirtualMachine dvm = (DebuggerVirtualMachine)vm;
        dvm.executeReturn(vm.getRTStack().peek()); // Return value
    }
    
}
