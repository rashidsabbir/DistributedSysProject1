/**
 * 
 */
package distributed;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

import distributed.IOFunctions;
import raymonds.Process;
import raymonds.Tree;

/**
 * @author Sabbir Rashid
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		// processes with neighbors derived from file
		ArrayList<Process> processes = Tree.CreateTree("tree.txt");
		// Print Process State
		PrintProcessStates(processes);
		// Token names list
		LinkedHashMap<String,String> tokenMap = new LinkedHashMap<String,String>();
		// Token owners list
		LinkedHashMap<String, String> tokenOwner = new LinkedHashMap<String,String>();
		//Process test = new Process("testID");
		//runIO(test, tokenMap, tokenOwner);
		for (Process p : processes) {
			runIO(p, tokenMap, tokenOwner);
			PrintProcessStates(processes);
		}
		System.out.println("Program Finished Running...");
	}

	public static void PrintProcessStates(ArrayList<Process> processes){
		for (Process p : processes) {
			System.out.println("Process ID:" + p.getProcessID());
			System.out.println("Using Resource?:" + p.usingResource);
			System.out.println("Asked?:" + p.asked);
			System.out.println("Holder?:" + p.holderEnum.toString());
			System.out.println("Neighbors:");
			for (Process n : p.getNeighbors()) {
				System.out.println("\t" + n.getProcessID());
			}
			System.out.println("Request Queue:");
			for (Process r : p.requestQueue) {
				System.out.println("\t" + r.getProcessID());
			}
			System.out.println("");
		}
	}
	
	public static void runIO(Process p, LinkedHashMap<String,String> tokenMap, LinkedHashMap<String,String> tokenOwner) throws IOException {
		// TODO Auto-generated method stub
		
		while(true) {
			Scanner console = new Scanner(System.in);
			System.out.println("Select the following command that you want to execute:");
			System.out.println("1: create <filename>: creates an empty file named <filename>");
			System.out.println("2: delete <filename>: deletes file named <filename>");
			System.out.println("3: read <filename>: displays the contents of <filename>");
			System.out.println("4: append <filename> <line>: appends a <line> to <filename>");
			System.out.println("5: list: lists token owner and contents");
			System.out.println("6: exit: exit");
			
			if (console.hasNextLine()) {
				String result = console.nextLine();
				//Note: Calling create, delete, read, list, and append go here:
				// Ordered by string length
				if(result.equals("5")) // list shortcut
				{
					for (String token : tokenMap.keySet()){
						System.out.println("Token Name: \"" + token + "\"\nToken Owner: \"" + tokenOwner.get(token) + "\"\nContents: \n" + tokenMap.get(token) + "\n");
					}
				}
				else if(result.equals("6")) // exit shortcut
				{
					break;
				}
				else if(result.substring(0,1).equalsIgnoreCase("3")) //read shortcut case
				{
					System.out.println("Reading File...");
					String fileName = result.substring(2,result.length());
					if (tokenMap.containsKey(fileName)){
						if (tokenOwner.get(fileName)==p.getProcessID()) {
							System.out.println(IOFunctions.Read(tokenMap,fileName));
							System.out.println("SUCCESS: Read file \"" + fileName + "\"\n");
						}
						else {
							System.out.println("ERROR: You must have token to read from file...\n");
							System.out.println("The current token owner for file \"" + fileName + "\" is \"" + tokenOwner.get(fileName) + "\"...\n");
						}
					}
					else {
						System.out.println("ERROR: File \"" + fileName + "\" does not exist in token map...\n");
					}
				}
				else if(result.substring(0,1).equalsIgnoreCase("1")) //create shortcut case
				{
					System.out.println("Creating File...");
					String fileName = result.substring(2,result.length());
					if (fileName.contains(" ")){
						System.out.println("ERROR: Filename \"" + fileName + "\" cannot contain a space...\n");
					}
					else if (tokenMap.containsKey(fileName)){
						System.out.println("ERROR: File \"" + fileName + "\" already exists in token map...\n");
					}
					else {
						IOFunctions.Create(tokenMap, fileName);
						tokenOwner.put(fileName, p.getProcessID());
						System.out.println("SUCCESS: Created file \"" + fileName + "\" with owner \"" + tokenOwner.get(fileName) + "\"\n");
					}
					
				}
				else if(result.substring(0,1).equalsIgnoreCase("2")) // delete shortcut case
				{
					System.out.println("Deleting File...");
					String fileName = result.substring(2,result.length());
					if (tokenMap.containsKey(fileName)){
						if (tokenOwner.get(fileName)==p.getProcessID()) {
							IOFunctions.Delete(tokenMap, fileName);
							System.out.println("SUCCESS: Deleted file \"" + fileName + "\"\n");
						}
						else {
							System.out.println("ERROR: You must have token to delete a file...\n");
							System.out.println("The current token owner for file \"" + fileName + "\" is \"" + tokenOwner.get(fileName) + "\"...\n");
						}
					}
					else {
						System.out.println("ERROR: File \"" + fileName + "\" does not exist in token map...\n");
					}
				}
				else if(result.substring(0,1).equalsIgnoreCase("4")) // append shortcut case
				{
					System.out.println("Appending to File...");
					String tmp = result.substring(2,result.length());
					if(tmp.contains(" ")){
						int index = tmp.indexOf(' ');
						String fileName = tmp.substring(0,index);
						String line = tmp.substring(index+1,tmp.length());
						if (tokenMap.containsKey(fileName)){
							if(tokenOwner.get(fileName)==p.getProcessID()){
								IOFunctions.Append(tokenMap, fileName,line);
								System.out.println("SUCCESS: Appended line \"" + line + "\" to file \"" + fileName + "\"\n");
							}
							else {
								System.out.println("ERROR: You must have token to append to a file...\n");
								System.out.println("The current token owner for file \"" + fileName + "\" is \"" + tokenOwner.get(fileName) + "\"...\n");
							}
						} else {
							System.out.println("ERROR: File \"" + fileName + "\" does not exist in token map...\n");
						}
					} else {
						System.out.println("ERROR: Must include a line to append...\n");
					}
				}
				else if(result.substring(0,4).equalsIgnoreCase("read")) // read case
				{
					System.out.println("Reading File...");
					String fileName = result.substring(5,result.length());
					if (tokenMap.containsKey(fileName)){
						if (tokenOwner.get(fileName)==p.getProcessID()) {
							System.out.println(IOFunctions.Read(tokenMap,fileName));
							System.out.println("SUCCESS: Read file \"" + fileName + "\"\n");
						}
						else {
							System.out.println("ERROR: You must have token to read from file...\n");
							System.out.println("The current token owner for file \"" + fileName + "\" is \"" + tokenOwner.get(fileName) + "\"...\n");
						}
					}
					else {
						System.out.println("ERROR: File \"" + fileName + "\" does not exist in token map...\n");
					}
				}
				else if(result.substring(0,4).equalsIgnoreCase("list")) // list case
				{
					for (String token : tokenMap.keySet()){
						System.out.println("Token Name: \"" + token + "\"\nToken Owner: \"" + tokenOwner.get(token) + "\"\nContents: \n" + tokenMap.get(token) + "\n");
					}
				}
				else if(result.substring(0,4).equalsIgnoreCase("exit")) // exit case
				{
					break;
				}
				else if(result.substring(0,6).equalsIgnoreCase("create")) // create case
				{
					System.out.println("Creating File..."); 
					String fileName = result.substring(7,result.length()); 
					if (fileName.contains(" ")){
						System.out.println("ERROR: Filename \"" + fileName + "\" cannot contain a space...\n");
					}
					else if (tokenMap.containsKey(fileName)){
						System.out.println("ERROR: File \"" + fileName + "\" already exists in token map...\n");
					}
					else {
						IOFunctions.Create(tokenMap, fileName);
						tokenOwner.put(fileName, p.getProcessID());
						System.out.println("SUCCESS: Created file \"" + fileName + "\" with owner \"" + tokenOwner.get(fileName) + "\"\n");
					}
					
				}
				else if(result.substring(0,6).equalsIgnoreCase("delete")) // delete case
				{
					System.out.println("Deleting File...");
					String fileName = result.substring(7,result.length());
					if (tokenMap.containsKey(fileName)){
						if (tokenOwner.get(fileName)==p.getProcessID()) {
							IOFunctions.Delete(tokenMap, fileName);
							System.out.println("SUCCESS: Deleted file \"" + fileName + "\"\n");
						}
						else {
							System.out.println("ERROR: You must have token to delete a file...\n");
							System.out.println("The current token owner for file \"" + fileName + "\" is \"" + tokenOwner.get(fileName) + "\"...\n");
						}
					}
					else {
						System.out.println("ERROR: File \"" + fileName + "\" does not exist in token map...\n");
					}
				}
				else if(result.substring(0,6).equalsIgnoreCase("append")) // append case
				{
					System.out.println("Appending to File...");
					String tmp = result.substring(7,result.length());
					if(tmp.contains(" ")){
						int index = tmp.indexOf(' ');
						String fileName = tmp.substring(0,index);
						String line = tmp.substring(index+1,tmp.length());
						if (tokenMap.containsKey(fileName)){
							if(tokenOwner.get(fileName)==p.getProcessID()){
								IOFunctions.Append(tokenMap, fileName,line);
								System.out.println("SUCCESS: Appended line \"" + line + "\" to file \"" + fileName + "\"\n");
							}
							else {
								System.out.println("ERROR: You must have token to append to a file...\n");
								System.out.println("The current token owner for file \"" + fileName + "\" is \"" + tokenOwner.get(fileName) + "\"...\n");
							}
						} else {
							System.out.println("ERROR: File \"" + fileName + "\" does not exist in token map...\n");
						}
					} else {
						System.out.println("ERROR: Must include a line to append...\n");
					}
				}
				else 
					System.out.println("ERROR: Unknown Command...\n");
			}
			//console.close();
		}

	}




}
