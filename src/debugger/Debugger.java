package debugger;

import interpreter.*;
import debugger.UI.UI;

public class Debugger {
    
    private UI ui; // User interface class
    
    public Debugger(String xFile, String byteFile){
        Interpreter intr = new Interpreter(byteFile);
        CodeTable.debugInit(); // Add debug bytecodes to CodeTable
        
        DebuggerVirtualMachine dvm = new DebuggerVirtualMachine(intr.bcl.loadCodes(), xFile);
        ui = new UI(dvm);
    }
    
    // Run the debugger by calling the UI
    public void run(){
        ui.run();
    }
    
}
