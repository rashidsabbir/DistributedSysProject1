package sockets;

import java.io.*;
import java.net.*;

public class Client {
	public static void main(String[] args) throws IOException {
        
        if (args.length != 2) {
            System.err.println(
                "Usage: java Client <host name> <port number>");
            System.exit(1);
        }
 
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        System.out.println("CLIENT: About to try to create Client Socket");
        try (
            Socket echoSocket = new Socket(hostName, portNumber);
//        	System.out.println("CLIENT: Created Client Socket");
        	PrintWriter out =
                new PrintWriter(echoSocket.getOutputStream(), true);
//        	System.out.println("CLIENT: Initiated print writer output stream.");
        	BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(echoSocket.getInputStream()));
//        	System.out.println("CLIENT: Initiated print buffered reader input stream.");
            BufferedReader stdIn =
                new BufferedReader(
                    new InputStreamReader(System.in));
//        	System.out.println("CLIENT: Initiated print buffered reader stdIn.");
        	){
        	String userInput;
        	System.out.println("CLIENT: About to wait for user input.");
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println("echo: " + in.readLine());
            }
            echoSocket.close();
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
