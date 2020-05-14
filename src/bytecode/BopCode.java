package bytecode;

import interpreter.*;
import java.util.ArrayList;

public class BopCode extends ByteCode{
    
    private String op; // Inputted operator
    
    @Override
    public void init(ArrayList args){
        op = (String) args.get(0);
    }
    
    @Override
    public void execute(VirtualMachine vM){
        // Get operands
        int opnd2 = vM.getRTStack().pop();
        int opnd1 = vM.getRTStack().pop();
        
        // Perform the selected operations
        switch(op){
            case "*": vM.getRTStack().push(opnd1 * opnd2);
                      break;
            case "/": vM.getRTStack().push(opnd1 / opnd2);
                      break;
            case "+": vM.getRTStack().push(opnd1 + opnd2);
                      break;
            case "-": vM.getRTStack().push(opnd1 - opnd2);
                      break;
            case ">": vM.getRTStack().push( (opnd1 > opnd2) ? 1:0 );
                      break;
            case "<": vM.getRTStack().push( (opnd1 < opnd2) ? 1:0 );
                      break;
            case "==": vM.getRTStack().push( (opnd1 == opnd2) ? 1:0 );
                      break;
            case "!=": vM.getRTStack().push( (opnd1 != opnd2) ? 1:0 );
                      break;
            case ">=": vM.getRTStack().push( (opnd1 >= opnd2) ? 1:0 );
                      break;
            case "<=": vM.getRTStack().push( (opnd1 <= opnd2) ? 1:0 );
                      break;
            case "|": 
                if( (opnd1 >= 1) || (opnd2 >= 1) ){
                    vM.getRTStack().push(1);
                    break;
                }else{
                    vM.getRTStack().push(0);
                    break;
                }
            case "&":
                if( (opnd1 >= 1) && (opnd2 >= 1) ){
                    vM.getRTStack().push(1);
                    break;
                }else{
                    vM.getRTStack().push(0);
                    break;
                }
        }
        
        // Perform in case DUMP is on
        if( (vM.getDumpState()).equals("ON") ){
            System.out.println("BOP " + op);
        }
    }
}