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
				processes.add(new Process(input.substring(3, 4),Process.HolderEnum.Neighbor,false,false));
				first = false;
			}
			else
			{
				boolean found=false;
				for(int i=0;i<processes.size();i++)
				{
					if(input.substring(1,2).equals(processes.get(i).getProcessID()))
						found=true;
					
					if(!found)
						processes.add(new Process(input.substring(1, 2),Process.HolderEnum.Neighbor,false,false));
				}
				
				for(int i=0;i<processes.size();i++)
				{
					if(input.substring(3,4).equals(processes.get(i).getProcessID()))
						found=true;
					
					if(!found)
						processes.add(new Process(input.substring(3, 4),Process.HolderEnum.Neighbor,false,false));
				}
			}
			
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

		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		writer.append(line);
		writer.flush();
		writer.close();
		
	}
	
	public static void ReadFile( String fileName) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String input = reader.readLine();
		while(input!=null)
		{
			System.out.println(input);
			input=reader.readLine();
		}
		reader.close();
	}

	public static void DeleteFile( String fileName) throws IOException {
		Runtime.getRuntime().exec(new String[]{"bash","-c","rm " + fileName});
	}

}
