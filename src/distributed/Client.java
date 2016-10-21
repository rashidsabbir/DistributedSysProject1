package distributed;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import raymonds.Process;
import raymonds.Tree;


public class Client {
	public static Process clientProcess = new Process();
	
	public static void main(String[] args) throws IOException {
        
        if (args.length != 3) {
            System.err.println(
                "Usage: java Client <host name> <port number> <client process id>");
            System.exit(1);
        }
 
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        String clientID=args[2];
        try {
			boolean found = false;
			ArrayList<Process> processes = Tree.CreateTree("tree.txt");
			for (Process p : processes) {
				//System.out.println(p.getProcessID());
				if (Integer.parseInt(clientID) == Integer.parseInt(p.getProcessID())){
					found = true;
					System.out.println("Found the Client Process in the tree...");
					clientProcess = p;
				}
			}
			if (!found){
				System.err.println("Error: Client Process " + clientID + " entered does not exist in tree");
				System.exit(1);
			}
		} catch (Exception e){
			System.err.println("Error: " + e);
		}
        
        System.out.println("CLIENT: About to try to create Client Socket");
        try (
            Socket clientSocket = new Socket(hostName, portNumber);
//        	System.out.println("CLIENT: Created Client Socket");
        	PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);
//        	System.out.println("CLIENT: Initiated print writer output stream.");
/*        	ObjectOutputStream oos = 
        		new ObjectOutputStream(clientSocket.getOutputStream());*/
        	BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
//        	System.out.println("CLIENT: Initiated print buffered reader input stream.");
            BufferedReader stdIn =
                new BufferedReader(
                    new InputStreamReader(System.in));
//        	System.out.println("CLIENT: Initiated print buffered reader stdIn.");
        	){
        	
/*        	Process clientProcess = new Process();
        	ArrayList<Process> processes = Tree.CreateTree("tree.txt");
			for (Process p : processes) {
				if (processID == p.getProcessID()){
					clientProcess = p;
				}
			}
  */      	
        	out.println(clientID);
  /*      	oos.writeObject(clientProcess);
        	oos.flush();*/
        	while(true){
        	String newLine;
        	while ( true ) {
        		newLine = in.readLine();
        		if (newLine.equalsIgnoreCase("ACQUIRETOKEN")){
        			for (Process n : clientProcess.neighbors){
        				Raymonds.requestResource(clientProcess, n);
        			}
        			break;
        		}
        		else if (newLine.equalsIgnoreCase("END"))
        		{
        			break;
        		}
        		System.out.println(newLine);
        	}
        	String userInput;
        	System.out.println("CLIENT: About to wait for user input.");
        	/*    		System.out.println("1: create <filename>: creates an empty file named <filename>");
    		System.out.println("2: delete <filename>: deletes file named <filename>");
    		System.out.println("3: read <filename>: displays the contents of <filename>");
    		System.out.println("4: append <filename> <line>: appends a <line> to <filename>");
    		System.out.println("5: exit: exits the program");
        	 */ 
        	userInput = stdIn.readLine();
        	out.println(userInput);
/*        	while ((userInput = stdIn.readLine()) != null) {
        		System.out.println("In stdIn while loop");
        		out.println(userInput);
        		out.flush();

        		while ( true ) {
            		newLine = in.readLine();              
            		if (newLine.equalsIgnoreCase("END"))
            		{
            		    break;
            		}
            		System.out.println(newLine);
            	}
        	}*/
        	}
            //System.out.println("CLIENT: Exited stdIn while loop.");
            //clientSocket.close();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        } 
    }
}
