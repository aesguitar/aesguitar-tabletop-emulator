package testing;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.WindowConstants;

import charcreate.CreationForm;
import main.Character;
import main.ClassList;
import main.IdConflictException;
import main.RaceList;
import util.UF;

public class Test{
	public static ArrayList<String> inputs = new ArrayList<String>();

	public static void main(String[] args){
		RaceList rl = new RaceList(UF.raceLoc);
		ClassList cl = new ClassList(UF.classLoc);
		
		
		try {
			cl.buildList();
			rl.buildList();
		} catch (IdConflictException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Character tc = new Character(new int[] {1,2,3,4,5,6}, "Alex Scott", 112.34, 2.56, rl.get(1), cl.get(1));
		tc.printCharacter();
		try {
			tc.writeCharacterToFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}