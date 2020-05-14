package bytecode;

import interpreter.*;
import java.util.ArrayList;

public class ReturnCode extends ByteCode{
    
    protected String fcn = ""; // Function name
    
    @Override
    public void init(ArrayList<String> args){
        if(args.size()>0){
            this.fcn = args.get(0);
        }
    }
    
    @Override
    public void execute(VirtualMachine vm){
        // Get return address
        vm.setPC(vm.popReturnAddrs());
        vm.getRTStack().popFrame();
        
        // Perform in case DUMP is on
        if( (vm.getDumpState()).equals("ON") ){
            String parts[] = fcn.split("<");
            System.out.println("RETURN " + fcn + "    " + parts[0] + " => " + vm.getRTStack().peek()); // RETURN f    f => 5
        }
    }
    
}