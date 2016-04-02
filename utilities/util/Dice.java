package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Dice {
	// rule is a number indicating whether to take the highest or lowest dice thrown
	// for example, a rule of -3 indicates taking the lowest three dice for the sum
	private static Random ran = new Random();

	@SuppressWarnings("null")
	public static int rollLoudSum(int numDice, int numSides, int modifier, int rule)
	{
		int value = 0;
		ArrayList<Integer> rolls = new ArrayList<Integer>();
		ArrayList<Integer> filtered = new ArrayList<Integer>();
		for(int i = 0; i < numDice; i++)
		{
			int r = ran.nextInt(numSides-1)+1;
			rolls.add(r);
			filtered.add(r);
		}

		
		Collections.sort(filtered);
		System.out.print("Rolls = " );
		UF.printList(rolls);

		
		if(Math.abs(rule)<numDice && rule != 0)
		{
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
	public static int rollSum(int numDice, int numSides, int modifier, int rule)
	{
		int value = 0;
		ArrayList<Integer> rolls = new ArrayList<Integer>();
		ArrayList<Integer> filtered = new ArrayList<Integer>();
		for(int i = 0; i < numDice; i++)
		{
			int r = ran.nextInt(numSides-1)+1;
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
			System.out.print(filtered.get(i) + " + ");
			value += filtered.get(i);
		}
		value += modifier;
		System.out.println(modifier + " = " + value + "");
		return value;
	}
	
	

	public static void main(String[] args) {
		rollLoudSum(4,6,1,-3);
		rollLoudSum(4,6,1,3);
		rollLoudSum(8,20,1,-3);
		rollLoudSum(8,20,1,3);

	}

}
