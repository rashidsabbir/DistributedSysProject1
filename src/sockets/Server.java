package sockets;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        
        if (args.length != 1) {
            System.err.println("Usage: java Server <port number>");
            System.exit(1);
        }
        
        int portNumber = Integer.parseInt(args[0]);
    	System.out.println("SERVER: About to try to create a server socket.");
        try {
       		System.out.println("SERVER: Creating server socket.");
            ServerSocket serverSocket =
                new ServerSocket(Integer.parseInt(args[0]));
            System.out.println("SERVER: About to set Client Socket.");
            Socket clientSocket = serverSocket.accept();
            System.out.println("SERVER: Created Client Socket.");
            PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);
            System.out.println("SERVER: Created print writer out.");
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("SERVER: Created buffered reader.");
            
            System.out.println("SERVER: About to print line.");
         
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);
            }
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
