package util;

import java.util.List;

//Usefule functions
public class UF {
	
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

	}

}
