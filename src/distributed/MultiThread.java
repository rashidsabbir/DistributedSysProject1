package distributed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

import distributed.Main;
import raymonds.Process;
import raymonds.Tree;

public class MultiThread {
	public static Process serverProcess = new Process();
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.err.println("Usage: java MultiThread <port number> <server process id>");
			System.exit(1);
		}
		System.out.println("Running MultiThread...");
		int portNumber = Integer.parseInt(args[0]);
		String serverID = args[1];
		
		try {
			boolean found = false;
			ArrayList<Process> processes = Tree.CreateTree("tree.txt");
			for (Process p : processes) {
				//System.out.println(p.getProcessID());
				if (serverID == p.getProcessID()){
					found = true;
					System.out.println("Found the Server Process in the tree...");
					serverProcess = p;
				}
			}
			if (!found){
				System.err.println("Error: Server Process " + serverID + " entered does not exist in tree");
				System.exit(1);
			}
		} catch (Exception e){
			System.err.println("Error: " + e);
		}		
		int clientID = 1;
		
		@SuppressWarnings("resource")
		ServerSocket m_ServerSocket = new ServerSocket(portNumber);
		System.out.println("About to enter Wait For Client Loop...");
		while (true) {
			Socket clientSocket = m_ServerSocket.accept();
			ClientServiceThread cliThread = new ClientServiceThread(clientSocket, clientID);
			cliThread.start();
		}
	}
	
	public static void runMultiThread(String port, int clientID) throws Exception {
		System.out.println("Running MultiThread...");
		int portNumber = Integer.parseInt(port);
		
		@SuppressWarnings("resource")
		ServerSocket m_ServerSocket = new ServerSocket(portNumber);
		
		while (true) {
			Socket clientSocket = m_ServerSocket.accept();
			ClientServiceThread cliThread = new ClientServiceThread(clientSocket, clientID);
			cliThread.start();
		}
	}
	public static void runMultiThread(String port, String clientID) throws Exception {
		System.out.println("Running MultiThread...");
		int portNumber = Integer.parseInt(port);
		
		@SuppressWarnings("resource")
		ServerSocket m_ServerSocket = new ServerSocket(portNumber);
		
		while (true) {
			Socket clientSocket = m_ServerSocket.accept();
			ClientServiceThread cliThread = new ClientServiceThread(clientSocket, Integer.parseInt(clientID));
			cliThread.start();
		}
	}
}

class ClientServiceThread extends Thread {
	Socket clientSocket;
	
	int clientID = -1;
	boolean running = true;
	

	ClientServiceThread(Socket s, int i) {
		clientSocket = s;
		clientID = i;
/*		try {
			ArrayList<Process> processes = Tree.CreateTree("tree.txt");
			for (Process p : processes) {
				if (clientID == Integer.parseInt(p.getProcessID())){
					clientProcess = p;
				}
			}
			} catch (Exception e){
				System.out.println("Error: " + e);
			}
			*/
	}

	public void run() {
		//System.out.println("Accepted Client : ID - " + clientID + " : Address - "
		//		+ clientSocket.getInetAddress().getHostName());
		try {
			BufferedReader   in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			System.out.println("SERVER: Created buffered reader in.");
			PrintWriter   out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()),true);
			System.out.println("SERVER: Created print writer out.");
			LinkedHashMap<String,String> tokenMap = new LinkedHashMap<String,String>();
			LinkedHashMap<String,String> tokenOwner = new LinkedHashMap<String,String>();
			
			while (running) {	
				System.out.println("SERVER: In running loop.");
				System.out.println("SERVER: In try. About to enter while loop.");
				System.out.println("Reading Client ID");
				String clientID = in.readLine();
				boolean found = false;
				for (Process p : MultiThread.serverProcess.getNeighbors()){
					if (clientID == p.getProcessID()){
						found = true;
					}
				}
				if (found){
				System.out.println("Accepted Client : ID - " + clientID + " : Address - "
						+ clientSocket.getInetAddress().getHostName());
				runSocketIO(out, in, clientID, tokenMap, tokenOwner);
				} 
				else {
					System.out.println("Client : ID - " + clientID + " : is not a neighbor of Server : ID - " + MultiThread.serverProcess.getProcessID() );
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void runSocketIO(PrintWriter out, BufferedReader in, String processID, LinkedHashMap<String,String> tokenMap, LinkedHashMap<String,String> tokenOwner) throws IOException {
		// TODO Auto-generated method stub
		
		while(true) {
			out.println("SERVER: Select the following command that you want to execute:");
			out.println("1: create <filename>: creates an empty file named <filename>");
			out.println("2: delete <filename>: deletes file named <filename>");
			out.println("3: read <filename>: displays the contents of <filename>");
			out.println("4: append <filename> <line>: appends a <line> to <filename>");
			out.println("5: list: lists token owner and contents");
			out.println("6: exit: exit");
			out.println("END");
			if (true) {
				String result = in.readLine();
				//Note: Calling create, delete, read, list, and append go here:
				// Ordered by string length
				if(result.equals("5")) // list shortcut
				{
					for (String token : tokenMap.keySet()){
						out.println("Token Name: \"" + token + "\"\nToken Owner: \"" + tokenOwner.get(token) + "\"\nContents: \n" + tokenMap.get(token) + "\n");
//						out.println("END");
					}
				}
				else if(result.equals("6")) // exit shortcut
				{
					break;
				}
				else if(result.substring(0,1).equalsIgnoreCase("3")) //read shortcut case
				{
					out.println("Reading File...");
//					out.println("END");
					String fileName = result.substring(2,result.length());
					if (tokenMap.containsKey(fileName)){
						if (tokenOwner.get(fileName)==processID) {
							out.println(IOFunctions.Read(tokenMap,fileName));
							out.println("SUCCESS: Read file \"" + fileName + "\"\n");
//							out.println("END");
						}
						else {
							out.println("ERROR: You must have token to read from file...\n");
							out.println("The current token owner for file \"" + fileName + "\" is \"" + tokenOwner.get(fileName) + "\"...\n");
//							out.println("END");
						}
					}
					else {
						out.println("ERROR: File \"" + fileName + "\" does not exist in token map...\n");
//						out.println("END");
					}
				}
				else if(result.substring(0,1).equalsIgnoreCase("1")) //create shortcut case
				{
					out.println("Creating File...");
//					out.println("END");
					String fileName = result.substring(2,result.length());
					if (fileName.contains(" ")){
						out.println("ERROR: Filename \"" + fileName + "\" cannot contain a space...\n");
//						out.println("END");
					}
					else if (tokenMap.containsKey(fileName)){
						out.println("ERROR: File \"" + fileName + "\" already exists in token map...\n");
//						out.println("END");
					}
					else {
						IOFunctions.Create(tokenMap, fileName);
						tokenOwner.put(fileName, processID);
						out.println("SUCCESS: Created file \"" + fileName + "\" with owner \"" + tokenOwner.get(fileName) + "\"\n");
//						out.println("END");
					}
					
				}
				else if(result.substring(0,1).equalsIgnoreCase("2")) // delete shortcut case
				{
					out.println("Deleting File...");
//					out.println("END");
					String fileName = result.substring(2,result.length());
					if (tokenMap.containsKey(fileName)){
						if (tokenOwner.get(fileName)==processID) {
							IOFunctions.Delete(tokenMap, fileName);
							out.println("SUCCESS: Deleted file \"" + fileName + "\"\n");
//							out.println("END");
						}
						else {
							out.println("ERROR: You must have token to delete a file...\n");
							out.println("The current token owner for file \"" + fileName + "\" is \"" + tokenOwner.get(fileName) + "\"...\n");
//							out.println("END");
						}
					}
					else {
						out.println("ERROR: File \"" + fileName + "\" does not exist in token map...\n");
//						out.println("END");
					}
				}
				else if(result.substring(0,1).equalsIgnoreCase("4")) // append shortcut case
				{
					out.println("Appending to File...");
//					out.println("END");
					String tmp = result.substring(2,result.length());
					if(tmp.contains(" ")){
						int index = tmp.indexOf(' ');
						String fileName = tmp.substring(0,index);
						String line = tmp.substring(index+1,tmp.length());
						if (tokenMap.containsKey(fileName)){
							if(tokenOwner.get(fileName)==processID){
								IOFunctions.Append(tokenMap, fileName,line);
								out.println("SUCCESS: Appended line \"" + line + "\" to file \"" + fileName + "\"\n");
//								out.println("END");
							}
							else {
								out.println("ERROR: You must have token to append to a file...\n");
								out.println("The current token owner for file \"" + fileName + "\" is \"" + tokenOwner.get(fileName) + "\"...\n");
//								out.println("END");
							}
						} else {
							out.println("ERROR: File \"" + fileName + "\" does not exist in token map...\n");
//							out.println("END");
						}
					} else {
						out.println("ERROR: Must include a line to append...\n");
//						out.println("END");
					}
				}
				else if(result.substring(0,4).equalsIgnoreCase("read")) // read case
				{
					out.println("Reading File...");
//					out.println("END");
					String fileName = result.substring(5,result.length());
					if (tokenMap.containsKey(fileName)){
						if (tokenOwner.get(fileName)==processID) {
							out.println(IOFunctions.Read(tokenMap,fileName));
							out.println("SUCCESS: Read file \"" + fileName + "\"\n");
//							out.println("END");
						}
						else {
							out.println("ERROR: You must have token to read from file...\n");
							out.println("The current token owner for file \"" + fileName + "\" is \"" + tokenOwner.get(fileName) + "\"...\n");
//							out.println("END");
						}
					}
					else {
						out.println("ERROR: File \"" + fileName + "\" does not exist in token map...\n");
//						out.println("END");
					}
				}
				else if(result.substring(0,4).equalsIgnoreCase("list")) // list case
				{
					for (String token : tokenMap.keySet()){
						out.println("Token Name: \"" + token + "\"\nToken Owner: \"" + tokenOwner.get(token) + "\"\nContents: \n" + tokenMap.get(token) + "\n");
//						out.println("END");
					}
				}
				else if(result.substring(0,4).equalsIgnoreCase("exit")) // exit case
				{
					out.println("Exiting...");
//					out.println("END");
					break;
				}
				else if(result.substring(0,6).equalsIgnoreCase("create")) // create case
				{
					out.println("Creating File..."); 
//					out.println("END");
					String fileName = result.substring(7,result.length()); 
					if (fileName.contains(" ")){
						out.println("ERROR: Filename \"" + fileName + "\" cannot contain a space...\n");
//						out.println("END");
					}
					else if (tokenMap.containsKey(fileName)){
						out.println("ERROR: File \"" + fileName + "\" already exists in token map...\n");
//						out.println("END");
					}
					else {
						IOFunctions.Create(tokenMap, fileName);
						tokenOwner.put(fileName, processID);
						out.println("SUCCESS: Created file \"" + fileName + "\" with owner \"" + tokenOwner.get(fileName) + "\"\n");
//						out.println("END");
					}
					
				}
				else if(result.substring(0,6).equalsIgnoreCase("delete")) // delete case
				{
					out.println("Deleting File...");
//					out.println("END");
					String fileName = result.substring(7,result.length());
					if (tokenMap.containsKey(fileName)){
						if (tokenOwner.get(fileName)==processID) {
							IOFunctions.Delete(tokenMap, fileName);
							out.println("SUCCESS: Deleted file \"" + fileName + "\"\n");
//							out.println("END");
						}
						else {
							out.println("ERROR: You must have token to delete a file...\n");
							out.println("The current token owner for file \"" + fileName + "\" is \"" + tokenOwner.get(fileName) + "\"...\n");
//							out.println("END");
						}
					}
					else {
						out.println("ERROR: File \"" + fileName + "\" does not exist in token map...\n");
//						out.println("END");
					}
				}
				else if(result.substring(0,6).equalsIgnoreCase("append")) // append case
				{
					out.println("Appending to File...");
//					out.println("END");
					String tmp = result.substring(7,result.length());
					if(tmp.contains(" ")){
						int index = tmp.indexOf(' ');
						String fileName = tmp.substring(0,index);
						String line = tmp.substring(index+1,tmp.length());
						if (tokenMap.containsKey(fileName)){
							if(tokenOwner.get(fileName)==processID){
								IOFunctions.Append(tokenMap, fileName,line);
								out.println("SUCCESS: Appended line \"" + line + "\" to file \"" + fileName + "\"\n");
//								out.println("END");
							}
							else {
								out.println("ERROR: You must have token to append to a file...\n");
								out.println("The current token owner for file \"" + fileName + "\" is \"" + tokenOwner.get(fileName) + "\"...\n");
//								out.println("END");
							}
						} else {
							out.println("ERROR: File \"" + fileName + "\" does not exist in token map...\n");
//							out.println("END");
						}
					} else {
						out.println("ERROR: Must include a line to append...\n");
//						out.println("END");
					}
				}
				else {
					out.println("ERROR: Unknown Command...\n");
//					out.println("END");
				}
			}
			//console.close();
		}

	}
}