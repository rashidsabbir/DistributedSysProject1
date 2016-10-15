package sockets;

import java.io.*;
import java.net.*;

public class Client {
	public static void main(String[] args) throws IOException {
		
		if (args.length != 2) {
            System.err.println("Usage: java Client <host name> <port number>");
            System.exit(1);
        }
		
		String hostName = args[0];
		int portNumber = Integer.parseInt(args[1]);

		try {
			Socket clientSocket = new Socket(hostName, portNumber);
		    PrintWriter out =
		        new PrintWriter(clientSocket.getOutputStream(), true);
		    BufferedReader in =
		        new BufferedReader(
		            new InputStreamReader(clientSocket.getInputStream()));
		    BufferedReader stdIn =
		        new BufferedReader(
		            new InputStreamReader(System.in));
		    
		    String userInput;
			while ((userInput = stdIn.readLine()) != null) {
			    out.println(userInput);
			    System.out.println("echo: " + in.readLine());
			}
			clientSocket.close();
			
		} catch (IOException e) {
			System.err.println("Caught IOException: " + e.getMessage());
		} finally {
			
		}
				
	}
}
