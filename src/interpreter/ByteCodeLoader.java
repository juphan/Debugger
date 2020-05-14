package interpreter;

import bytecode.*;
import java.io.*;
import java.util.*;

public class ByteCodeLoader {
    
    private Program program = new Program();
    private BufferedReader byteFile;
    
    public ByteCodeLoader(String codeFile) throws IOException {
       byteFile = new BufferedReader(new FileReader(codeFile));
    }
    
    public Program loadCodes(){
        ArrayList<String> byteTokens = new ArrayList<>();
        
        try{
            String nextLine = byteFile.readLine();
        
            while (nextLine != null) {
                StringTokenizer tokenizer = new StringTokenizer(nextLine); // Split each line in the byteFile
                String byteClass = tokenizer.nextToken(); // First token is the bytecode class name
                String byteClassCall = CodeTable.getByteClass(byteClass); // Get the name of the associated Java file
                ByteCode byteCode = (ByteCode)(Class.forName("bytecode."+byteClassCall).newInstance()); // call an instance of the associated Java file
                
                // Collect any other tokens in the line
                while(tokenizer.hasMoreTokens()) {
                    byteTokens.add(tokenizer.nextToken());
                }
           
                byteCode.init(byteTokens); // Initialise bytecode instance with parameters            
                program.addBytes(byteCode); // Store each bytecode instance in an ArrayList  
            
                // Prepare for reading the next line
                byteTokens.clear(); // Clear stored tokens
                nextLine = byteFile.readLine(); // Read next line
            }
            
            // Resolve the target addresses for branching bytecodes
            program.resolve();  
        }catch(Exception e){
            System.out.println("Could not open the file. Check file name and directory.");
        }
        
        // Return Program
        return program;
    }
}
