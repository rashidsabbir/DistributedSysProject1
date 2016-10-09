package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Create {

	public static BufferedWriter WriteFile( String outputFile) throws IOException {
		
		File file = new File(outputFile);
		file.createNewFile();
		BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
		return writer;
	}
	
}
