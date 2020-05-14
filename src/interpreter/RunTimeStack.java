package interpreter;

import java.util.*;

public class RunTimeStack {
    
    private ArrayList<Integer> RTStack = new ArrayList<>();
    private Stack<Integer> frames = new Stack<>(); // Keep track of where frames start
    
    
    public RunTimeStack(){
        frames.add(0); // Index 0 is the start of the "main" frame 
    }
    
    // Peek at the top item in Run Time Stack
    public int peek(){
        return RTStack.get(RTStack.size() - 1);
    }
    
    // Pop top element in Run Time Stack
    public int pop(){
        return RTStack.remove(RTStack.size() - 1);
    }
    
    // Push item onto Runtime Stack
    public void push(int i){
        RTStack.add(i);
    }
    
    // Setup a new frame by pushing the index location of the start of the new frame into the Stack
    // Call before a function call
    public void makeNewFrame(int off) {
        frames.push(this.RTStack.size() - off);
    }
    
    // Pop frame when returning a function
    public void popFrame(){
        int val = RTStack.get(RTStack.size() - 1);
        int frm = frames.pop();
        
        // Remove all elements in the top frame
        for(int i=RTStack.size()-1; i>=frm; i--){
            pop();
        }
        
        push(val);
    }
    
    // Load variables on top of the Runtime Stack
    public void load(int off){
        RTStack.add(RTStack.get(frames.peek() + off));
    }
    
    // Store variables
    public void store(int off){
        int n = RTStack.remove(RTStack.size() - 1);; // Pop top element
        RTStack.set(frames.peek() + off, n); // Store at nth offset in frame
    }
    
    // Print out the contents of the Runtime Stack, Divided into frames
    public void dump() {
        // Iterate through the Stack of frames
        Iterator frmIterator = frames.iterator();
        int thisFrame = (Integer)frmIterator.next();
        int nextFrame;
        
        // Iterate through each frame in the Runtime Stack
        for(Integer frame : frames) {
            
            System.out.print("[");
            
            // Find where next frame starts
            if(frmIterator.hasNext()){
                nextFrame = (Integer) frmIterator.next();
            }else{
                nextFrame = RTStack.size();
            }
            
            // Print the contents in each frame
            for(int i=thisFrame; i<nextFrame; i++){
                System.out.print(RTStack.get(i));
                if(i != nextFrame - 1){
                    System.out.print(",");
                }
            }
            
            System.out.print("]");
            thisFrame = nextFrame;
        }
        System.out.print("\n");
    }

    public int getOffset(){
        return RTStack.size() - frames.peek() - 1;
    }
    
    public int getValueAtOffset(int offset) {
        return RTStack.get(frames.peek() + offset);
    }
    
    public int getVal(int off){
        return RTStack.get(frames.peek() + off); // Get val from RT Stack
    }
    
}

