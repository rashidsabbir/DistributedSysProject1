package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import raymonds.Process;

public class Main {

	public static void main(String[] args) throws IOException {
		FileReader fr = new FileReader("tree.txt");
		BufferedReader br = new BufferedReader(fr);
		String input = br.readLine();
		boolean first = true;
		ArrayList<Process> processes = new ArrayList<Process>();
		while(input!=null)
		{
			if(first)
			{
				processes.add(new Process(input.substring(1, 2),Process.HolderEnum.Neighbor,false,false));
				processes.get(0).addNeighbor(new Process(input.substring(3, 4),Process.HolderEnum.Neighbor,false,false));
				processes.add(new Process(input.substring(3, 4),Process.HolderEnum.Neighbor,false,false));
				processes.get(1).addNeighbor(new Process(input.substring(1, 2),Process.HolderEnum.Neighbor,false,false));
				first = false;
			}
			else
			{
				int index = 0;
				boolean found=false;
				for(int i=0;i<processes.size();i++)
				{
					if(input.substring(1,2).equals(processes.get(i).getProcessID()))
					{
						found=true;
						index=i;
					}
					
				}
				if(!found)
				{
					processes.add(new Process(input.substring(1, 2),Process.HolderEnum.Neighbor,false,false));
					processes.get(processes.size()-1).addNeighbor(new Process(input.substring(3, 4),Process.HolderEnum.Neighbor,false,false));
				}
				else
				{
					if(!processes.get(index).getNeighbors().contains(new Process(input.substring(3, 4),Process.HolderEnum.Neighbor,false,false)))
					{
						processes.get(index).addNeighbor(new Process(input.substring(3, 4),Process.HolderEnum.Neighbor,false,false));
						System.out.println("CONTAINS 1");
					}
				}
				found=false;
				index = 0;
				for(int i=0;i<processes.size();i++)
				{
					if(input.substring(3,4).equals(processes.get(i).getProcessID()))
					{
						found=true;
						index=i;
					}
				}
				if(!found)
				{
					processes.add(new Process(input.substring(3, 4),Process.HolderEnum.Neighbor,false,false));
					processes.get(processes.size()-1).addNeighbor(new Process(input.substring(1, 2),Process.HolderEnum.Neighbor,false,false));
				}
				else
				{
					if(!processes.get(index).getNeighbors().contains(new Process(input.substring(1, 2),Process.HolderEnum.Neighbor,false,false)))
					{
						processes.get(index).addNeighbor(new Process(input.substring(1, 2),Process.HolderEnum.Neighbor,false,false));
						System.out.println("CONTAINS 2");
					}
				}
			}
			input=br.readLine();
		}
		for(int i=0;i<processes.size();i++)
		{
			System.out.println(processes.get(i).getProcessID());
			System.out.print("Neighbors: ");
			for(int j=0;j<processes.get(i).getNeighbors().size();j++)
				System.out.print(processes.get(i).getNeighbors().get(j).getProcessID()+" ");
			System.out.println();
		}
		
		Scanner console = new Scanner(System.in);
		System.out.println("Select the following command that you want to execute:");
		System.out.println("1: create <filename>: creates an empty file named <filename>");
		System.out.println("2: delete <filename>: deletes file named <filename>");
		System.out.println("3: read <filename>: displays the contents of <filename>");
		System.out.println("4: append <filename> <line>: appends a <line> to <filename>");
		
		String result = console.nextLine();
		//Note: Calling create, delete, read, and append go here:
		File testFile = null;
		if(result.substring(0,6).equalsIgnoreCase("create"))
		{
			System.out.println("Creating File...");
			testFile = CreateFile(result.substring(7,result.length()));
		}
		else if(result.substring(0,6).equalsIgnoreCase("delete"))
		{
			System.out.println("Deleting File...");
			DeleteFile(result.substring(7,result.length()));
		}
		else if(result.substring(0,4).equalsIgnoreCase("read"))
		{
			System.out.println("Reading File...");
			ReadFile(result.substring(5,result.length()));
		}
		else if(result.substring(0,6).equalsIgnoreCase("append"))
		{
			System.out.println("Appending to File...");
			String tmp = result.substring(7,result.length());
			int index = tmp.indexOf(' ');
			AppendFile(tmp.substring(0,index),tmp.substring(index+1,tmp.length()));
		}
		else
			System.out.println("Error: Invalid Command");
		
		br.close();
		console.close();
	}
	
	public static File CreateFile( String fileName) throws IOException {
		
		File file = new File(fileName);
		file.createNewFile();
		return file;
	}

	public static void AppendFile( String fileName, String line) throws IOException {

		FileWriter writer = new FileWriter(fileName, true);
		writer.append(line + "\n");
		writer.flush();
		writer.close();
		
	}
	
	public static String ReadFile( String fileName) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String input = reader.readLine();
		String result = input;
		while(input!=null)
		{
			input=reader.readLine();
			if(input!=null)
				result = result + "\n"+ input;
		}
		reader.close();
		return result;
	}
	
	/* while ((userInput = stdIn.readLine()) != null) {
    out.println(userInput);
	String ans = "";
    while((ans=in.readLine()) != null)
    {
    	System.out.println("CLIENT: In inner while loop.");
    	System.out.println(ans);
//    	ans=in.readLine();
    }
    System.out.println("CLIENT: Exited inner while loop.");
}
*/

	public static void DeleteFile( String fileName) throws IOException {
		Runtime.getRuntime().exec(new String[]{"bash","-c","rm " + fileName});
	}
	
	public static void ExitConnection()
	{
		
	}

}
