package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Read {
	
	public static BufferedReader ReadFile( String inputFile) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		return reader;
	}

}
