# Debugger
Final project for the CSC 413: Software Development course at SFSU.
Includes:
- An interpreter mode to run bytecodes in an X code source file
- A debugger mode that allows the user to run debugger commands during program run

# Run
The program can be run in two ways.
1) Open the project in NetBeans. Then, right-click the opened project and then open the "Properties" menu item. Select the category named "Run" and then make sure you select the appropriate values in the "Arguments" and "Working Directory" textboxes. Then, run the program in NetBeans.
2) Open your command prompt and change directories to the file that contains "Debugger.jar" (Located in the "dist" folder). Make sure the appropriate test files are in the same directory and then run the JAR file with the appropriate arguments.

# Example
`java -jar Debugger.jar <filename.x.cod>`

`java -jar Debugger.jar -d <filename no extension>`
