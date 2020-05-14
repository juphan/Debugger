package bytecode;

import interpreter.VirtualMachine;
import debugger.DebuggerVirtualMachine;
import java.util.ArrayList;

public class DebugFunctionCode extends ByteCode{
    
    private String fName; // Function name
    private int start; // Starting line of function
    private int end; // Ending line of function
    
    @Override
    public void init(ArrayList<String> args){
        fName = args.get(0);
        start = Integer.parseInt(args.get(1)); 
        end = Integer.parseInt(args.get(2));
    }
    
    @Override
    public void execute(VirtualMachine vm){
        // Set function info in FER
        DebuggerVirtualMachine dvm = (DebuggerVirtualMachine)vm;
        dvm.executeFunction(fName, start, end);
    }

}
