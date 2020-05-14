package bytecode;

import interpreter.*;
import java.util.*;

public class DebugReadCode extends ReadCode{
    
    @Override
    public void init(ArrayList args){
    }
    
    @Override
    public void execute(VirtualMachine vM){
        // Prompt user for input
        // Load the inputted integer onto Runtime Stack
        System.out.print("Enter an integer: ");
        Scanner sc = new Scanner(System.in);
        int in = sc.nextInt();
        vM.getRTStack().push(in);
        System.out.println();
        
        // Perform in case DUMP is on
        if( (vM.getDumpState()).equals("ON") ){
            System.out.println("READ " + in);
        }
    }
    
}
