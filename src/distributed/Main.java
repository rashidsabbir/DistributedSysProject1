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

/**
 * @author Sabbir Rashid
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		Process test = new Process("testID");
		runIO(test);
	}
	
	public static void runIO(Process p) throws IOException {
		// TODO Auto-generated method stub
		LinkedHashMap<String,String> tokenMap = new LinkedHashMap<String,String>();
		LinkedHashMap<String, String> tokenOwner = new LinkedHashMap<String,String>();
		
		while(true) {
			Scanner console = new Scanner(System.in);
			System.out.println("Select the following command that you want to execute:");
			System.out.println("1: create <filename>: creates an empty file named <filename>");
			System.out.println("2: delete <filename>: deletes file named <filename>");
			System.out.println("3: read <filename>: displays the contents of <filename>");
			System.out.println("4: append <filename> <line>: appends a <line> to <filename>");
			System.out.println("5: list: lists tokens and their contents");
			
			if (console.hasNextLine()) {
				String result = console.nextLine();
				//Note: Calling create, delete, read, and append go here:
				if(result.substring(0,4).equalsIgnoreCase("read"))
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
				else if(result.substring(0,4).equalsIgnoreCase("list"))
				{
					for (String token : tokenMap.keySet()){
						System.out.println("Token Name: \"" + token + "\"\nContents: \n" + tokenMap.get(token) + "\n");
					}
				}
				else if(result.substring(0,6).equalsIgnoreCase("create"))
				{
					System.out.println("Creating File...");
					String fileName = result.substring(7,result.length());
					if (tokenMap.containsKey(fileName)){
						System.out.println("ERROR: File \"" + fileName + "\" already exists in token map...\n");
					}
					else {
						IOFunctions.Create(tokenMap, fileName);
						tokenOwner.put(fileName, "hi");
						System.out.println("SUCCESS: Created file \"" + fileName + "\" with owner \"" + tokenOwner.get(fileName) + "\"\n");
					}
					
				}
				else if(result.substring(0,6).equalsIgnoreCase("delete"))
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
				else if(result.substring(0,6).equalsIgnoreCase("append"))
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
