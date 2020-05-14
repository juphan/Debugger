package bytecode;

import interpreter.*;
import java.util.ArrayList;

public class PopCode extends ByteCode{
    
    protected int n;
    
    @Override
    public void init(ArrayList<String> args){
        this.n = Integer.parseInt(args.get(0));
    }
    
    @Override
    public void execute(VirtualMachine vm){
        // Pop N elements in Runtime Stack
        for(int i=0; i<n; i++){
            vm.getRTStack().pop();
        }
        
        // Perform in case DUMP is on
        if( (vm.getDumpState()).equals("ON") ){
            System.out.println("POP " + n);
        }
    }
    
}
