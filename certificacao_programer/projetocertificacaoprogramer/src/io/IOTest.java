package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class IOTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			File file = new File("textfile.txt");
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write("Teste\nGravando\n");
			fileWriter.flush();
			fileWriter.close();
			FileReader fileReader = new FileReader(file);
			char[] in = new char[50];
			System.out.println(fileReader.read(in));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
