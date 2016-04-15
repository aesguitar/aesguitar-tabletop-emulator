package testing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

import util.FileIO;


public class makeClassListFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String to_write = "";
		Scanner in2 = null;
		try {
			in2 = new Scanner(new File("class--list.txt"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Integer i = 1;
		while(in2.hasNext())
		{
			to_write = to_write.concat("{").concat(i.toString()).concat("}\n").concat("\tc:name = \"").concat(in2.nextLine()).concat("\"\n");
			i++;
		}
		in2.close();
		
		System.out.println("Writing: \n" + to_write);
		try {
			FileIO.deleteFile(Paths.get("class-list.txt"));
			FileIO.createFile(Paths.get("class-list.txt"));
			FileIO.writeToFile(Paths.get("class-list.txt"), to_write);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
