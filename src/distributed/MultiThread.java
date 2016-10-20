package distributed;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import distributed.Main;
import raymonds.Process;
import raymonds.Tree;

public class MultiThread {
	
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.err.println("Usage: java MultiThread <port number>");
			System.exit(1);
		}
		System.out.println("Running MultiThread...");
		int portNumber = Integer.parseInt(args[0]);
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
	
	LinkedHashMap<String,String> tokenMap;
	LinkedHashMap<String,String> tokenOwner;
	Process clientProcess = new Process();
	
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
            
			while (running) {	
				System.out.println("SERVER: In running loop.");
				System.out.println("SERVER: In try. About to enter while loop.");
				System.out.println("Reading Client ID");
				String ID = in.readLine();
				System.out.println("Accepted Client : ID - " + ID + " : Address - "
						+ clientSocket.getInetAddress().getHostName());
				Main.runIO(clientProcess, tokenMap, tokenOwner);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}