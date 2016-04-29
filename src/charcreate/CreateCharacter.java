package charcreate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JOptionPane;

import main.Character;
import main.ClassList;
import main.IdConflictException;
import main.Race;
import main.RaceList;
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

	private final ClassList cl = new ClassList(UF.classLoc); //store the available classes read from "class list.txt"
	public final RaceList rl = new RaceList(UF.raceLoc);
	private int charClass = -1; //Integer corresponding to the class
	private int charRace = -1;
	//	private int level = 0; //initial level
	//	private float experience = 0; //initial xp
	//	private float hitpoints = 0; // initial hp
	//private Inventory
	private int[] stats = new int[6]; //initial stats; order = STR, DEX, CON, INT, WIS, CHA
	private String name = ""; //Character name
	private File classListLoc = new File("class-list.txt"); //Location of the classlist 

	public CreateCharacter() //runs the character creation
	{
		for(int i = 0; i < 6; i++)
			stats[i] = 0;
		try {
			cl.buildList();
			rl.buildList();
		} catch (IdConflictException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//createCharacter();
		//printCharacter();
	};

	// handles human input for the creation of the character
	/*public void createCharacter()
	{
		Scanner in = new Scanner(System.in);
		System.out.println("What is your name adventurer?\t");
		name = in.nextLine();
		String cl = "";
		String rr = "";
		String ra = "";

		do{
			System.out.println("What is your race?");
			//printClassList();
			ra = in.nextLine();
		} while(!isValidRace(ra));
		charRace = rl.getRaceId(ra);


		do{
			System.out.println("What is your class?");
			printClassList();
			cl = in.nextLine();
		} while(!isValidClass(cl));
		charClass = getCharCreateClass(cl);

		do{
			System.out.println("Rolling Stats: ");
			stats = rollStats();
			for(int i = 0; i < UF.statsList.length; i++)
			{
				System.out.print(UF.statsList[i] + " = " + stats[i]);
			}
			System.out.print("Keep stats? (yes/no)\t");
			rr = in.nextLine();
			System.out.println();
		}while(rr.contains("n"));



		in.close();
	}
	 */

	public void createCharacter() throws IOException
	{
		CreationForm cf = new CreationForm(rl,cl);
		int confirm = 0;
		do{
			cf.setVisible(true);
			cf.setVisible(true);
			//confirm = JOptionPane.showConfirmDialog(null, "Are you sure?");
		}while(confirm!=JOptionPane.YES_OPTION);
		Character ch = new Character(cf.finalStats, cf.finalName, cf.finalWeight,cf.finalHeight,cf.finalRace,cf.finalClass);
		ch.writeCharacterToFile();
		//ch.printCharacter();
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CreateCharacter c = new CreateCharacter();
		try {
			c.createCharacter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
