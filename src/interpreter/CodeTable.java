package interpreter;

import java.util.HashMap;

public class CodeTable {
    
    public static HashMap<String, String> codeTable = new HashMap<String,String>();
    
    // Map names of bytecodes with their associated Java class names 
    public static void init() {
        codeTable.put("HALT", "HaltCode");
        codeTable.put("POP", "PopCode");
        codeTable.put("FALSEBRANCH", "FalseBranchCode");
        codeTable.put("GOTO", "GoToCode");
        codeTable.put("STORE", "StoreCode");
        codeTable.put("LOAD", "LoadCode");
        codeTable.put("LIT", "LitCode");
        codeTable.put("ARGS", "ArgsCode");
        codeTable.put("CALL", "CallCode");
        codeTable.put("RETURN", "ReturnCode");
        codeTable.put("BOP", "BopCode");
        codeTable.put("READ", "ReadCode");
        codeTable.put("WRITE", "WriteCode");
        codeTable.put("LABEL", "LabelCode");
        codeTable.put("DUMP", "DumpCode");
    }
    
    // Add/replace new bytecodes for the debugger run 
    public static void debugInit(){
        codeTable.put("LINE", "DebugLineCode");
        codeTable.put("FUNCTION", "DebugFunctionCode");
        codeTable.put("FORMAL", "DebugFormalCode");
        codeTable.put("POP", "DebugPopCode");
        codeTable.put("RETURN", "DebugReturnCode");
        codeTable.put("CALL", "DebugCallCode");
        codeTable.put("LIT", "DebugLitCode");
        codeTable.put("READ", "DebugReadCode");
        codeTable.put("WRITE", "DebugWriteCode");
    }
    
    public static String getByteClass(String code) {
        return codeTable.get(code);
    }
}
