package debugger;

import java.util.*;

public class FunctionEnvironmentRecord {
    
    private Table symbolTable = new Table();
    private String fName = "-";
    private int sLine = 0;
    private int eLine = 0;
    private int cLine = 0;
    private List<Integer> inputs = new ArrayList<>();
    
    // Milestone 2 code
    // Written to test functionality of FunctionEnvironmentRecord class
    /*public static void main(String args[]){
        
        FunctionEnvironmentRecord fr = new FunctionEnvironmentRecord();

        fr.beginScope();
        fr.dump();
        fr.setFunctionInfo("g",1,20);
        fr.dump();
        fr.setCurrentLineNumber(5);
        fr.dump();
        fr.setVarVal("a",4);
        fr.dump();
        fr.setVarVal("b",2);
        fr.dump();
        fr.setVarVal("c",7);
        fr.dump();
        fr.setVarVal("a",1);
        fr.dump();
        fr.doPop(2);
        fr.dump();
        fr.doPop(1);
        fr.dump();
    }*/
    
    // Remember current scope when starting new scope
    public void beginScope(){
        symbolTable.beginScope();
    }
    
    // Set necessary information for the function
    /* Set:
     * (1) Function name
     * (2) Starting line of function
     * (3) Ending line of function
    */ 
    public void setFunctionInfo(String s, int start, int end){
        fName = s;
        sLine = start;
        eLine = end;
    }
    
    // Enter a Variable/Value pair into the symbol table
    public void setVarVal(String var, Object val){
        symbolTable.put(var, val);
    }
    
    // Pop N var/val pairs from the symbol table
    public void doPop(int n){
        for(int i=0; i<n; i++){
            symbolTable.endScope();
        }
    }
    
    // Dump Function Environment Record
    public void dump(){
        Set<String> keys = symbolTable.keys();
        Iterator it = keys.iterator();
        
        // Print var/val pairs
        System.out.print("(<");
        if( it.hasNext() ){
            while( it.hasNext() ){
                String str = (String)it.next();
                System.out.print(str + "/" + symbolTable.get(str));
                if(it.hasNext()){
                    System.out.print(", ");
                }
            }
        }
        System.out.print(">, ");
        
        // Print function name
        System.out.print(fName + ", ");
        
        // Print function info
        // Print starting line no.
        if(sLine == 0){
            System.out.print("-, ");
        }else{
            System.out.print(sLine + ", ");
        }
        
        // Print ending line no.
        if(eLine == 0){
            System.out.print("-, ");
        }else{
            System.out.print(eLine + ", ");
        }
        
        // Print current line no.
        if(cLine == 0){
            System.out.println("-)");
        }else{
            System.out.println(cLine + ")");
        }
        
    }
    
    // Getters
    public int getSLine(){
        return sLine;
    }
    
    public int getELine(){
        return eLine;
    }
    
    public int getCLine(){
        return cLine;
    }
    
    public Table getSymbTable(){
        return symbolTable;
    }
    
    public String getFunc(){
        return fName;
    }
    
    public int getInSize(){
        return inputs.size();
    }
    
    public int getInput(int i){
        return inputs.get(i);
    }
    
    // Setters
    public void setCLine(int n){
        cLine = n;
    }
    
    public void setSLine(int n){
        sLine = n;
    }
    
    public void setELine(int n){
        eLine = n;
    }
    
    public void addInputs(int n){
        inputs.add(n);
    }
    
}
