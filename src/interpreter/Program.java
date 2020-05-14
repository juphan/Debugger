package interpreter;

import bytecode.*;
import java.util.*;

public class Program {
    
    private ArrayList<ByteCode> program = new ArrayList<>();// Holds bytecode instances
    private static HashMap<String, Integer> adrss = new HashMap<>(); // Hold addresses of branching bytecodes, LABEL -> PC
    
    public Program(){
    }
    
    // Adds an instance of a bytecode to the program
    public void addBytes(ByteCode bc) {
        // If the bytecode is LABEL, Then add target for branches to HashMap
        if (bc.getClass().getSimpleName().equals("LabelCode") ){
            adrss.put( ((LabelCode)bc).getLabel(), program.size() );
        }
        
        // Add bytecode instance
        program.add(bc);
    }
    
    // Get bytecode instance at PC
    public ByteCode getCode(int pc) {
        return program.get(pc);
    }
    
    // Resolve addresses for branching bytecodes
    public void resolve() {
        program.forEach((bc) -> {
            // Check each bytecode instance and identify if address needs to be resolved
            boolean branch = false;
            switch (bc.getClass().getSimpleName()) {
                case "CallCode":
                case "FalseBranchCode":
                case "GoToCode":
                case "DebugCallCode":
                    branch = true;
            }
            
            // Set the appropriate PC to jump to for each branch bytecode
            if(branch){
                bc.setAddrs((int) adrss.get(bc.getLabel())); // Jump to appropriate address
            }
        });
    }
    
    // Getters
    public ArrayList<ByteCode> getProgram() {
        return program;
    }
    
}
