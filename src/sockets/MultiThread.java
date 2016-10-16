package sockets;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import main.Main;

public class MultiThread {
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.err.println("Usage: java Server <port number>");
			System.exit(1);
		}

		int portNumber = Integer.parseInt(args[0]);
		
		@SuppressWarnings("resource")
		ServerSocket m_ServerSocket = new ServerSocket(portNumber);
		
		int id = 0;
		while (true) {
			Socket clientSocket = m_ServerSocket.accept();
			ClientServiceThread cliThread = new ClientServiceThread(clientSocket, id++);
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
	}

	public void run() {
		System.out.println("Accepted Client : ID - " + clientID + " : Address - "
				+ clientSocket.getInetAddress().getHostName());
		try {
			BufferedReader   in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			System.out.println("SERVER: Created buffered reader in.");
			PrintWriter   out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()),true);
			System.out.println("SERVER: Created print writer out.");
            
			while (running) {	
				System.out.println("SERVER: In running loop.");
				//String result = console.nextLine();
				//String result = inputLine;
				String result = in.readLine();
				//Note: Calling create, delete, read, and append go here:
				File testFile = null;
				if(result.substring(0,6).equalsIgnoreCase("create"))
				{
					out.println("Creating File...");
					testFile = Main.CreateFile(result.substring(7,result.length()));
				}
				else if(result.substring(0,6).equalsIgnoreCase("delete"))
				{
					out.println("Deleting File...");
					Main.DeleteFile(result.substring(7,result.length()));
				}
				else if(result.substring(0,4).equalsIgnoreCase("read"))
				{
					String temp = Main.ReadFile(result.substring(5,result.length()));
					out.println("Reading File...\n" + temp);
					out.flush();
				}
				else if(result.substring(0,6).equalsIgnoreCase("append"))
				{
					out.println("Appending to File...");
					String tmp = result.substring(7,result.length());
					int index = tmp.indexOf(' ');
					Main.AppendFile(tmp.substring(0,index),tmp.substring(index+1,tmp.length()));
				}
				else if(result.substring(0,4).equalsIgnoreCase("exit"))
				{
					out.println("Exiting...");
					out.flush();
					running = false;
					System.out.print("Stopping client thread for client : " + clientID);
					Main.ExitConnection();
				}
				else
					out.println("Error: Invalid Command");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}