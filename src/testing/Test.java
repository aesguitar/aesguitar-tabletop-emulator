package testing;

import java.util.ArrayList;
import java.util.Iterator;

public class Test{
	public static ArrayList<String> inputs = new ArrayList<String>();

	public static void main(String[] args){
		//RaceList rl = new RaceList(new File("race-list.txt"));
		
		Iterator<String> it = inputs.iterator();
		while(it.hasNext())
			System.out.println(it.next());
	}
}