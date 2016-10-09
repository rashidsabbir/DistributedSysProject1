package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		Scanner console = new Scanner(System.in);
		System.out.println("Select the following command that you want to execute:");
		System.out.println("1: create <filename>: creates an empty file named <filename>");
		System.out.println("2: delete <filename>: deletes file named <filename>");
		System.out.println("3: read <filename>: displays the contents of <filename>");
		System.out.println("4: append <filename> <line>: appends a <line> to <filename>");
		
		String result = console.nextLine();
		//Note: Calling create, delete, read, and append go here:
		if(result.substring(0,6).equalsIgnoreCase("create"))
			System.out.println("Creating File...");
		else if(result.substring(0,6).equalsIgnoreCase("delete"))
			System.out.println("Deleting File...");
		else if(result.substring(0,4).equalsIgnoreCase("read"))
			System.out.println("Reading File...");
		else if(result.substring(0,6).equalsIgnoreCase("append"))
			System.out.println("Appending to File...");
		else
			System.out.println("Error: Invalid Command");
	}
	
	public static File CreateFile( String fileName) throws IOException {
		
		File file = new File(fileName);
		file.createNewFile();
		return file;
	}

	public static void Append( File inputFile, String line) throws IOException {

		BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile.getAbsoluteFile()));
		writer.append(line);
		writer.flush();
		writer.close();
		
	}
	
	public static void ReadFile( File inputFile) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		String input = reader.readLine();
		while(input!=null)
		{
			System.out.println(input);
			input=reader.readLine();
		}
	}

	public static void DeleteFile( String inputFile) throws IOException {
		Runtime.getRuntime().exec(new String[]{"bash","-c","rm " + inputFile});
	}

}
