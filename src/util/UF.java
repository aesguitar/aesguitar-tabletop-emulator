package util;

import java.awt.Color;
import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

//Usefule functions and variables
public class UF {
	public static final String[] statsList = {"Strength","Dexterity","Constitution","Intelligence","Wisdom","Charisma"};//A list of the stats in order
	public static final File raceLoc = new File("race-list.txt");
	public static final File classLoc = new File("class-list.txt");
	public static final Color lightCoral = new Color(240, 128, 128);
	private static final Pattern  regExp = Pattern.compile("[\\x00-\\x20]*[+-]?(((((\\p{Digit}+)(\\.)?((\\p{Digit}+)?)([eE][+-]?(\\p{Digit}+))?)|(\\.((\\p{Digit}+))([eE][+-]?(\\p{Digit}+))?)|(((0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+)))[pP][+-]?(\\p{Digit}+)))[fFdD]?))[\\x00-\\x20]*");
	//Checks if a given string is an integer
	public static boolean isInt(String str)
	{
		if (str == null) {
			return false;
		}
		int length = str.length();
		if (length == 0) {
			return false;
		}
		int i = 0;
		if (str.charAt(0) == '-') {
			if (length == 1) {
				return false;
			}
			i = 1;
		}
		for (; i < length; i++) {
			char c = str.charAt(i);
			if (c < '0' || c > '9') {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isFloat(String str)
	{
		return regExp.matcher(str).matches();
	}

	//prints the contents of a List l
	public static void printList(List l)
	{
		System.out.print("[");
		if(l.size()>0)
		{
			for(int i = 0; i < l.size()-1; i++)
				System.out.print(l.get(i) + ", ");
			System.out.print(l.get(l.size()-1));
		}
		System.out.print("]\n");
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(isFloat("1.642d"));
	}

}
