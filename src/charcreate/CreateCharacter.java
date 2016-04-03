package charcreate;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import util.Dice;
import util.UF;

/**
 * 
 * @author Alex
 * This class handles the creation of a DnD character
 * It is currently not complete
 *
 */
public class CreateCharacter {
	/*roll 4d6 for stats*/

	private final ArrayList<String> classList = new ArrayList<String>(); //store the available classes read from "class list.txt"
	private int charClass = -1; //Integer corresponding to the class
	private int level = 0; //initial level
	private float experience = 0; //initial xp
	private float hitpoints = 0; // initial hp
	//private Inventory
	private int[] stats = new int[6]; //initial stats; order = STR, DEX, CON, INT, WIS, CHA
	private String name = ""; //Character name
	private File classListLoc = new File("class-list.txt"); //Location of the classlist 

	public CreateCharacter() //runs the character creation
	{
		for(int i = 0; i < 6; i++)
			stats[i] = 0;
		try {
			readClassList();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createCharacter();
		printCharacter();
	};

	// handles human input for the creation of the character
	public void createCharacter()
	{
		Scanner in = new Scanner(System.in);
		System.out.println("What is your name adventurer?\t");
		name = in.nextLine();
		String cl = "";
		String rr = "";
		do{
			System.out.println("What is you class?");
			printClassList();
			cl = in.nextLine();
		} while(!isValidClass(cl));
		charClass = getCharCreateClass(cl);
		
		do{
			System.out.println("Rolling Stats: ");
			for(int i = 0; i < 5; i++)
			{
				System.out.print(UF.statsList[i] + " = ");
				stats[i] = Dice.rollSum(4, 6, 0, 3);
			}
			System.out.print("Keep stats? (yes/no)\t");
			rr = in.nextLine();
			System.out.println();
		}while(rr.contains("n"));
		
		
		
		in.close();
	}

	//prints the current class list
	private void printClassList()
	{
		for(int i = 0; i < classList.size(); i++)
			System.out.printf("%d: %s\n", i+1, classList.get(i));
	}

	//Checks if the input is a valid class option
	private boolean isValidClass(String in)
	{
		if(UF.isInt(in))
			return true;
		else
			for(int i = 0; i < classList.size(); i++)
			{
				if(in.equalsIgnoreCase(classList.get(i)))
					return true;
			}

		return false;
	}


	//Returns an integer value corresponding to the character's class
	private int getCharCreateClass(String cl)
	{
		if(UF.isInt(cl))
			return Integer.parseInt(cl)-1;
		else
			for(int i = 0; i < classList.size(); i++)
			{
				if(cl.equalsIgnoreCase(classList.get(i)))
					return i;
			}
		return 0;
	}
	
	//Prints the created character's stats
	private void printStats()
	{
		System.out.printf("Character Stats:\n");
		for(int i = 0; i < 5; i++)
		{
			System.out.printf("%s = %d\n", UF.statsList[i],stats[i]);
		}
	}
	
	//Reads the class list from file
	private void readClassList() throws FileNotFoundException
	{
		Scanner in = new Scanner(classListLoc);
		while(in.hasNextLine())
			classList.add(in.nextLine());
		in.close();
	}
	
	//Prints all created character information
	public void printCharacter()
	{
		System.out.printf("Name:\t%s\nClass:\t%s\nLevel:\t%d\nHitpoints:\t%f\nExperience:\t%f\n\n", name, classList.get(charClass),level, hitpoints, experience);
		printStats();
		
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CreateCharacter c = new CreateCharacter();

	}

}
