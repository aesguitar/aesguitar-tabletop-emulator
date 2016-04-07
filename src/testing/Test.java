package testing;

import java.awt.EventQueue;
import java.io.File;
import java.text.ParseException;

import charcreate.CreateCharacter;
import charcreate.CreationForm;
import util.IdConflictException;
import util.RaceList;

public class Test extends CreateCharacter{

	@Override
	public void createCharacter()
	{
		EventQueue.invokeLater(new Runnable() {
			public void run()
			{
				CreationForm t = new CreationForm(rl);
				t.frmCharacterCreation.setVisible(true);
			}
		});
	}

	public static void main(String[] args){
		RaceList rl = new RaceList(new File("race-list.txt"));
		Test t = new Test();
		t.createCharacter();
	}
}