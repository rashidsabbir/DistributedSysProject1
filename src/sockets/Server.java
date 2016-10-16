package sockets;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import main.Main;

public class Server {
    public static void main(String[] args) throws IOException {
        
        if (args.length != 1) {
            System.err.println("Usage: java Server <port number>");
            System.exit(1);
        }
        
        int portNumber = Integer.parseInt(args[0]);
    	System.out.println("SERVER: About to try to create a server socket.");
        try {
//       		System.out.println("SERVER: Creating server socket.");
            ServerSocket serverSocket =
                new ServerSocket(Integer.parseInt(args[0]));
//            System.out.println("SERVER: About to set Client Socket.");
            Socket clientSocket = serverSocket.accept();
//            System.out.println("SERVER: Created Client Socket.");
            PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);
//            System.out.println("SERVER: Created print writer out.");
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
//            System.out.println("SERVER: Created buffered reader.");
            
            System.out.println("SERVER: In try. About to print line to out.");
            String inputLine;
            System.out.println("SERVER: In try. About to enter while loop.");

     /*       out.println("Select the following command that you want to execute:");
            out.flush();
    		out.println("1: create <filename>: creates an empty file named <filename>");
    		out.flush();
    		out.println("2: delete <filename>: deletes file named <filename>");
    		out.flush();
    		out.println("3: read <filename>: displays the contents of <filename>");
    		out.flush();
    		out.println("4: append <filename> <line>: appends a <line> to <filename>");
       */     
            //while ((inputLine = in.readLine()) != null) {
            int i  = 0;
            while (i < 100){	
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
        			Main.ExitConnection();
        		}
        		else
        			out.println("Error: Invalid Command");
        		            	
            }
            System.out.println("SERVER: In try. Exited while loop.");
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
