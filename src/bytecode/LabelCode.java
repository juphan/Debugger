package bytecode;

import interpreter.*;
import java.util.ArrayList;

public class LabelCode extends ByteCode{
    
    private String label; // Traget for branches
    
    @Override
    public void init(ArrayList<String> args){
        this.label = args.get(0);
    }
    
    @Override
    public void execute(VirtualMachine vm){
        // Perform in case DUMP is on
        if( (vm.getDumpState()).equals("ON") ){
            System.out.println("LABEL " + label);
        }
    }
    
    @Override
    public String getLabel(){
        return label;
    }
}