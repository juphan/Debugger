package bytecode;

import interpreter.*;
import java.util.ArrayList;

public class LoadCode extends ByteCode{
    
    private int n;
    private String id;
    
    @Override
    public void init(ArrayList<String> args){
        this.n = Integer.parseInt(args.get(0));
        this.id = args.get(1);
    }
    
    @Override
    public void execute(VirtualMachine vM){
        // Push a value from this offset onto the top of the stack
        vM.getRTStack().load(n);
        
        // Perform in case DUMP is on
        if( (vM.getDumpState()).equals("ON") ){
            System.out.println("LOAD " + n + " " + id + "    <load " + id + ">");
        }
    }
    
}