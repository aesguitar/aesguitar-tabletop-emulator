package testing;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.WindowConstants;

import charcreate.CreationForm;
import util.Character;
import util.ClassList;
import util.IdConflictException;
import util.RaceList;

public class Test{
	public static ArrayList<String> inputs = new ArrayList<String>();

	public static void main(String[] args){
		RaceList rl = new RaceList(new File("race-list.txt"));
		ClassList cl = new ClassList(new File("class-list.txt"));
		try {
			rl.buildList();
		} catch (IdConflictException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			cl.buildList();
		} catch (IdConflictException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CreationForm cf = new CreationForm(rl,cl);
		cf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		cf.setVisible(true);
		Character ch = new Character(cf.finalStats, cf.finalName, cf.finalWeight,cf.finalHeight,cf.finalRace,cf.finalClass);
		try {
			ch.writeCharacterToFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String fileName = ch.getName().replaceAll(" ", "_").concat("-data.txt");
		Character readTest = new Character(new File(fileName));
		readTest.printCharacter();
		System.exit(0);
	}
}