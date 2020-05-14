package bytecode;

import interpreter.VirtualMachine;
import java.util.ArrayList;

public class DebugWriteCode extends WriteCode{
    
    @Override
    public void init(ArrayList<String> args){
    }
    
    @Override
    public void execute(VirtualMachine vm){
        // Write the top value of the stack
        System.out.println("WRITE: " + vm.getRTStack().peek() + "\n");
        
        // Perform in case DUMP is on
        if( (vm.getDumpState()).equals("ON") ){
            System.out.println("WRITE " + vm.getRTStack().peek());
        }
    }
    
}
