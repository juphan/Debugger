package bytecode;

import interpreter.*;
import java.util.ArrayList;

public class CallCode extends ByteCode{
    
    protected String fcn; // Function name
    protected int addrs; // Target address
    
    @Override
    public void init(ArrayList<String> args){
        this.fcn = args.get(0);
    }
    
    @Override
    public void execute(VirtualMachine vm){
        // Save return address (PC for bytecodes)
        vm.pushReturnAddrs(vm.getPC());
        vm.setPC(addrs);
        
        // Perform in case DUMP is on
        if( (vm.getDumpState()).equals("ON") ){
            String parts[] = fcn.split("<");
            System.out.println("CALL " + fcn + "    " + parts[0] + "(" + vm.getRTStack().peek() + ")"); // CALL f    f(5)
        }
    }
    
    @Override
    public String getLabel(){
        return fcn;
    }
    
    // Set during resolve
    @Override
    public void setAddrs(int n){
        addrs = n;
    }
    
}