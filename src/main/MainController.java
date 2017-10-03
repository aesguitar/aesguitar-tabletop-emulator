package main;

/**
 * TODO
 * Migrate these testing classes to main
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;

import util.UF;
import main.Character;
import JFXEvents.ObservableCharacter;

public class MainController {

	public static FileChooser fc = new FileChooser();
	private static ObservableList<ObservableCharacter> loadedChars = FXCollections.observableArrayList(
			(ObservableCharacter oc) -> new Observable[]{oc.getName(), oc.getCl(), oc.getHeight(), oc.getWeight(), oc.getRace(), oc.getAttrs()[0],
					oc.getAttrs()[1],oc.getAttrs()[2],oc.getAttrs()[3],oc.getAttrs()[4],oc.getAttrs()[5],oc.getLevel(), oc.getHitpoints() } );
	private static ObservableList<ObservableCharacter> charNames = FXCollections.observableArrayList(
			(ObservableCharacter oc) -> new Observable[]{oc.getName()});
	private int tabInd = 0;


	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private MenuItem openFile, newChar;

	@FXML
	private TabPane tabPane;


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void initialize()
	{
		fc.setInitialDirectory(UF.currDir);

		openFile.addEventHandler(Event.ANY, new EventHandler()
		{
			public void handle(Event e)
			{
				Tab tmpTab = new Tab("New tab");
				tabPane.getTabs().add(tabInd, tmpTab);
				FXMLLoader tmpLoader = new FXMLLoader(getClass().getResource("loadChar.fxml"));
				try {
					tmpTab.setContent(tmpLoader.load());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				//tmpTab.setText(Integer.toString(tabInd));
				LoadCharController tlcc = tmpLoader.getController();
				tlcc.Id = tabInd;
				tmpTab.setOnClosed(new EventHandler<Event>() {

					@Override
					public void handle(Event arg0) {
						removeFromLists(tlcc.Id);
						//tabPane.getTabs().remove(tlcc.Id);
					}
					
				});
				tabInd++;
			}
		});

		charNames.addListener(new ListChangeListener<ObservableCharacter>() {
			@Override
			public void onChanged(Change<? extends ObservableCharacter> arg0) {
				while(arg0.next())
				{
					if(arg0.wasAdded())
					{
						//ObservableList<Tab> tmpL = tabPane.getTabs();
						System.out.printf("Tab was added. Number of tabs: %d\n", tabPane.getTabs().size());
						tabPane.getTabs().get(tabPane.getTabs().size()-1).setText(charNames.get(tabPane.getTabs().size()-1).getName().get());
					}
					else if(arg0.wasUpdated())
					{
						System.out.printf("Tab %d was updated.\n", arg0.getFrom());
						//System.out.printf("getFrom: %d;\tgetTo: %d\n", arg0.getFrom(), arg0.getTo());
						tabPane.getTabs().get(arg0.getFrom()).setText(charNames.get(arg0.getFrom()).getName().get());
					}
					else if(arg0.wasPermutated())
						System.out.println("Tab was permutated.");
					else if(arg0.wasReplaced())
						System.out.println("Tab was replaced.");
					else if(arg0.wasRemoved())
						System.out.printf("Tab %d was removed. Number of tabs: %d\n", arg0.getFrom(), tabPane.getTabs().size());
					nothing();
				}

			}
		});
	}

	public static void addToLists(Character ch)
	{
		loadedChars.add(new ObservableCharacter(ch));
		charNames.add(new ObservableCharacter(ch));
	}

	public static void updateLists(int index, Character ch)
	{
		loadedChars.get(index).update(ch);
		charNames.get(index).update(ch);
	}

	public static void removeFromLists(int index)
	{
		loadedChars.remove(index);
		charNames.remove(index);
	}
	
	private void nothing()
	{}
}
