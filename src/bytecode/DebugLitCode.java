package bytecode;

import interpreter.VirtualMachine;
import debugger.DebuggerVirtualMachine;
import java.util.ArrayList;

public class DebugLitCode extends LitCode{
    
    @Override
    public void init(ArrayList<String> args){
        super.init(args);
    }
    
    @Override
    public void execute(VirtualMachine vm){
        super.execute(vm);
        
        // Add the literal var/val pair in the current FER
        if (!id.equals("")){
            DebuggerVirtualMachine dvm = (DebuggerVirtualMachine)vm;
            dvm.executeLit(id, dvm.getRTStack().getOffset());
        }
    }
    
}
