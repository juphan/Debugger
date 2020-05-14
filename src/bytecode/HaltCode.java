package bytecode;

import interpreter.*;
import java.util.ArrayList;

public class HaltCode extends ByteCode {
    
    @Override
    public void init(ArrayList<String> args) {
        
    }
    
    @Override
    public void execute(VirtualMachine vm) {
        // Stop program
        vm.stopProgram();
        
        // Perform in case DUMP is on
        if( (vm.getDumpState()).equals("ON") ){
            System.out.println("HALT");
        }
    }
}
