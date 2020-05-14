package bytecode;

import interpreter.*;
import java.util.ArrayList;

public abstract class ByteCode {

    public abstract void init(ArrayList<String> args);
    public abstract void execute(VirtualMachine vm);
    
    // Functions for branching addresses
    // Used in CallCode, FalseBranchCode, and GoToCode
    public void setAddrs(int n){}
    public String getLabel(){ return "Error"; }
}
