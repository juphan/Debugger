package bytecode;

import interpreter.*;
import java.util.ArrayList;

public class ArgsCode extends ByteCode {
    
    private int numArgs;
        
    @Override
    public void init(ArrayList<String> args) {
        numArgs = Integer.parseInt(args.get(0));
    }
    
    @Override
    public void execute(VirtualMachine vm) {
        // Setup frame with N elements
        vm.getRTStack().makeNewFrame(numArgs);
        
        // Perform in case DUMP is on
        if( (vm.getDumpState()).equals("ON") ){
            System.out.println("ARGS " + numArgs);
        }
    }
}
