package bytecode;

import interpreter.*;
import java.util.ArrayList;

public class WriteCode extends ByteCode{
    
    @Override
    public void init(ArrayList<String> args){
    }
    
    @Override
    public void execute(VirtualMachine vm){
        // Write the top value of the stack
        System.out.println(vm.getRTStack().peek());
        
        // Perform in case DUMP is on
        if( (vm.getDumpState()).equals("ON") ){
            System.out.println("WRITE " + vm.getRTStack().peek());
        }
    }
    
}