package bytecode;

import interpreter.*;
import java.util.ArrayList;

public class FalseBranchCode extends ByteCode{
    
    private String label;
    private int addrs;
    
    @Override
    public void init(ArrayList<String> args){
        this.label = args.get(0);
    }
    
    @Override
    public void execute(VirtualMachine vM){
        // Check if statement is "False", If so, branch to <label>
        if(vM.getRTStack().pop() == 0){
            vM.setPC(addrs);
        }
        
        // Perform in case DUMP is on
        if( (vM.getDumpState()).equals("ON") ){
            System.out.println("FALSEBRANCH " + label);
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
