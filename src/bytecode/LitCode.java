package bytecode;

import interpreter.*;
import java.util.ArrayList;

public class LitCode extends ByteCode{
    
    protected int n; // Literal value
    protected String id; // Variable ID
    
    @Override
    public void init(ArrayList<String> args){
        this.n = Integer.parseInt(args.get(0)); // Literal value
        this.id = ""; // Optional variable name
        
        if(args.size() == 2){
            this.id = (String) args.get(1); // ID
        }
    }
    
    @Override
    public void execute(VirtualMachine vm){
        // Load value into Runtime Stack
        if(id.equals("")){
            vm.getRTStack().push(n); // Load literal value
        }else{
            vm.getRTStack().push(0); // Load 0, Initialise with 0
        }
        
        // Perform in case DUMP is on
        if( (vm.getDumpState()).equals("ON") ){
            String out = "LIT " + n + " " + id;
            
            if(!id.equals("")){
                out = out + "    int " + id;
            }
            
            System.out.println(out);
        }
    }
}