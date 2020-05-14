package bytecode;

import interpreter.*;
import java.util.ArrayList;

public class StoreCode extends ByteCode{
    
    private int n; // Offset
    private String id;
    
    @Override
    public void init(ArrayList<String> args){
        this.n = Integer.parseInt(args.get(0));
        this.id = args.get(1);
    }
    
    @Override
    public void execute(VirtualMachine vm){
        // Store at offset
        vm.getRTStack().store(n);
        
        // Perform in case DUMP is on
        if( (vm.getDumpState()).equals("ON") ){
            System.out.println("STORE " + n + " " + id + "    " + id + " = " + vm.getRTStack().peek());
        }
    }
    
}
