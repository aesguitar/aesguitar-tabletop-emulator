package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dice {
	private static Random ran = new Random();

	//Rolls n m-sided dice with a modifier and a rule for which dice to keep 
	public static int rollLoudSum(String rollCmd)
	{
		rollCmd = rollCmd.replaceAll(" ", "");
		Pattern p = Pattern.compile("[0-9]+");
		Matcher m = p.matcher(rollCmd);
		rollCmd = rollCmd.trim();
		int numDice = 0, numSides = 0, modifier = 0, rule = 0;
		if(rollCmd.contains("d"))
		{
			if(m.find())
				numDice = Integer.parseInt(m.group());
			else 
				return 0;

			if(m.find())
				numSides = Integer.parseInt(m.group());
			else
				return 0;

			if(rollCmd.substring(m.end(), m.end()+1).equalsIgnoreCase("m")&&!m.hitEnd())
			{
				if(m.find())
					modifier = Integer.parseInt(m.group());
				else
					System.out.println("m not found.");
			}
			if(rollCmd.substring(m.end(), m.end()+1).equalsIgnoreCase("r")&&!m.hitEnd())
			{
				if(m.find())
					rule = Integer.parseInt(m.group());
				else
					return 0;
			}	
			//System.out.println(rule);
			return rollLoudSum(numDice, numSides, modifier, rule);
		}
		else
			return 0;
	}
	public static int rollLoudSum(int numDice, int numSides, int modifier, int rule)
	{
		int value = 0;
		ArrayList<Integer> rolls = new ArrayList<Integer>();
		ArrayList<Integer> filtered = new ArrayList<Integer>();
		for(int i = 0; i < numDice; i++)
		{
			int r = ran.nextInt(numSides)+1;
			rolls.add(r);
			filtered.add(r);
		}


		Collections.sort(filtered);
		System.out.print("Rolls = " );
		UF.printList(rolls);


		if(Math.abs(rule)<numDice && rule != 0)
		{
			//System.out.println("Trimming rolls.");
			if(rule < 0)
				for(int i = 0; i < numDice + rule; i++)
					filtered.remove(filtered.size()-1);
			else
				for(int i = 0; i < numDice - rule; i++)
					filtered.remove(0);
		}
		
		System.out.print("Kept = ");
		UF.printList(filtered);

		for(int i = 0; i < filtered.size(); i++)
		{
			System.out.print(filtered.get(i) + " + ");
			value += filtered.get(i);
		}
		value += modifier;
		System.out.println(modifier + " = " + value + "\n");
		return value;
	}

	//command of form #d# m# r#
	//m is optional, d and r are not
	public static int rollSum(String rollCmd)
	{
		rollCmd = rollCmd.replaceAll(" ", "");
		Pattern p = Pattern.compile("[0-9]+");
		Matcher m = p.matcher(rollCmd);
		rollCmd = rollCmd.trim();
		int numDice = 0, numSides = 0, modifier = 0, rule = 0;
		if(rollCmd.contains("d"))
		{
			if(m.find())
				numDice = Integer.parseInt(m.group());
			else 
				return 0;

			if(m.find())
				numSides = Integer.parseInt(m.group());
			else
				return 0;

			if(rollCmd.substring(m.end(), m.end()+1).equalsIgnoreCase("m")&&!m.hitEnd())
			{
				if(m.find())
					modifier = Integer.parseInt(m.group());
			}
			if(rollCmd.substring(m.end(), m.end()+1).equalsIgnoreCase("r")&&!m.hitEnd())
			{
				if(m.find())
					rule = Integer.parseInt(m.group());
			}	
			System.out.println(rule);
			return rollSum(numDice, numSides, modifier, rule);
		}
		else
			return 0;
	}
	public static int rollSum(int numDice, int numSides, int modifier, int rule)
	{
		int value = 0;
		ArrayList<Integer> rolls = new ArrayList<Integer>();
		ArrayList<Integer> filtered = new ArrayList<Integer>();
		for(int i = 0; i < numDice; i++)
		{
			int r = ran.nextInt(numSides)+1;
			rolls.add(r);
			filtered.add(r);
		}


		Collections.sort(filtered);
		//System.out.print("Rolls = " );
		//UF.printList(rolls);


		if(Math.abs(rule)<numDice && rule != 0)
		{
			if(rule < 0)
				for(int i = 0; i < numDice + rule; i++)
					filtered.remove(filtered.size()-1);
			else
				for(int i = 0; i < numDice - rule; i++)
					filtered.remove(0);
		}
		//System.out.print("Kept = ");
		//UF.printList(filtered);

		for(int i = 0; i < filtered.size(); i++)
		{
			//System.out.print(filtered.get(i) + " + ");
			value += filtered.get(i);
		}
		value += modifier;
		//System.out.println(modifier + " = " + value + "");
		return value;
	}



	public static void main(String[] args) {
		System.out.println(rollLoudSum("14d6 m0 r3"));

	}

}
