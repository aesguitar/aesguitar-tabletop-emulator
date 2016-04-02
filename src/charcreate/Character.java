package charcreate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import util.Dice;
import util.UF;

public class Character {
	/*roll 4d6 for stats*/
	//private final int WIZARD = 0, FIGHTER = 1, ROGUE = 2, MONK = 3;
	private final ArrayList<CharClass> classList = new ArrayList<CharClass>();
	private int charClass = -1;
	private int level = 0;
	private float experience = 0;
	private float hitpoints = 0;
	//private Inventory
	private int[] stats = new int[6];
	private String[] statsList = {"Strength","Dexterity","Constitution","Intelligence","Wisdom","Charisma"};
	private String name = "";

	public Character()
	{
		for(int i = 0; i < 6; i++)
			stats[i] = 0;

		createCharacter();
		printCharacter();
	};

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
				System.out.print(statsList[i] + " = ");
				stats[i] = Dice.rollSum(4, 6, 0, 3);
			}
			System.out.print("Keep stats? (yes/no)\t");
			rr = in.nextLine();
			System.out.println();
		}while(rr.contains("n"));
		
		
		
		in.close();
	}

	private void printClassList()
	{
		for(int i = 0; i < classList.size(); i++)
			System.out.printf("%d: %s\n", i+1, classList.get(i));
	}

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
	
	private void printStats()
	{
		System.out.printf("Character Stats:\n");
		for(int i = 0; i < 5; i++)
		{
			System.out.printf("%s = %d\n", statsList[i],stats[i]);
		}
	}
	public void printCharacter()
	{
		System.out.printf("Name:\t%s\nClass:\t%s\nLevel:\t%d\nExperience:\t%f\n\n", name, classList.get(charClass),level, experience);
		printStats();
		
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Character c = new Character();

	}

}
