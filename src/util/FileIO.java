package util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileIO {

	//Creates a file with the given Path
	public static void createFile(Path file) throws IOException
	{		
		if(Files.notExists(file))
		{
			Files.createFile(file);
			System.out.println(file + " has been created.");
		}
		else
			throw new IOException(file + " already exists");

	}

	//Deletes a file at the given Path
	public static void deleteFile(Path file) throws IOException
	{
		if(Files.notExists(file))
			throw new IOException(file + " does not exist.");
		else
		{
			Files.delete(file);
			System.out.println(file + " has been deleted.");
		}
	}
	
	//Writes string data to a file at the given Path
	public static void writeToFile(Path file, String data) throws IOException
	{
		if(Files.notExists(file))
			createFile(file);
		
		ByteArrayOutputStream contents = new ByteArrayOutputStream();
		Files.copy(file, contents);
		
		data = contents.toString("UTF-8").concat(data);
		System.out.println("File contents: ");
		System.out.println(contents.toString("UTF-8"));
		
		Files.write(file, data.getBytes());
	}
	

	public static void main(String[] args) {
		//testing
		String path = "C:\\users\\Alex\\workspace\\rpg\\", fileName = "test.txt";
		Path file = Paths.get(path, fileName);
		try {
			//deleteFile(file);
			//createFile(file);
			
			Scanner in = new Scanner(System.in);
			String data  = "";
			while(true){
				
				data = data.concat(in.nextLine()) + "\n";
			
				if(data.endsWith("done\n"))
					break;
			}
			
			writeToFile(file, data);
			in.close();



		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
