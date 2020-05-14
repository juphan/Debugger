package bytecode;

import interpreter.*;
import java.util.ArrayList;

public class DumpCode extends ByteCode{
    
    private String state; // Inputted Dump State
    
    @Override
    public void init(ArrayList<String> args) {
        state = args.get(0);
    }
    
    @Override
    public void execute(VirtualMachine vM) {
         vM.setDumpState(state);   
     }
}
