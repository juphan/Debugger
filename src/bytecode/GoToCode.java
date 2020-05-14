package bytecode;

import interpreter.*;
import java.util.ArrayList;

public class GoToCode extends ByteCode{
    
    private String label;
    private int addrs;
    
    @Override
    public void init(ArrayList<String> args){
        this.label = args.get(0);
    }
    
    @Override
    public void execute(VirtualMachine vm){
        // Go to target address
        vm.setPC(addrs);
        
        // Perform in case DUMP is on
        if( (vm.getDumpState()).equals("ON") ){
            System.out.println("GOTO " + label);
        }
    }
    
    @Override
    public String getLabel(){
        return label;
    }
    
    @Override
    public void setAddrs(int n){
        addrs = n;
    }
}
