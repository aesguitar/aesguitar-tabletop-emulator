package main;
/**
 * Sample Skeleton for 'test_tab.fxml' Controller Class
 */

import java.io.File;

import JFXEvents.JavaFXStat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import main.Character;
import util.UF;

public class LoadCharController {

	private Character ch;
	private ObservableList<JavaFXStat> data = FXCollections.observableArrayList(); 
	private File charFile;
	public int Id;

	@FXML // fx:id="pane1"
	private GridPane pane1; // Value injected by FXMLLoader

	@FXML // fx:id="loadChar"
	private Button loadChar; // Value injected by FXMLLoader

	@FXML // fx:id="classLbl"
	private Label classLbl; // Value injected by FXMLLoader

	@FXML // fx:id="charName"
	private TextField charName; // Value injected by FXMLLoader

	@FXML // fx:id="nameLbl"
	private Label nameLbl; // Value injected by FXMLLoader

	@FXML // fx:id="charClass"
	private TextField charClass; // Value injected by FXMLLoader

	@FXML // fx:id="raceLbl"
	private Label raceLbl; // Value injected by FXMLLoader

	@FXML // fx:id="charRace"
	private TextField charRace; // Value injected by FXMLLoader

	@FXML // fx:id="charLevel"
	private TextField charLevel; // Value injected by FXMLLoader

	@FXML // fx:id="charHeight"
	private TextField charHeight; // Value injected by FXMLLoader

	@FXML // fx:id="charWeight"
	private TextField charWeight; // Value injected by FXMLLoader

	@FXML // fx:id="charHP"
	private TextField charHP; // Value injected by FXMLLoader

	@FXML // fx:id="statsTable"
	private TableView<JavaFXStat> statsTable; // Value injected by FXMLLoader

	@SuppressWarnings("rawtypes")
	@FXML // fx:id="name"
	private TableColumn name; // Value injected by FXMLLoader

	@SuppressWarnings("rawtypes")
	@FXML // fx:id="value"
	private TableColumn value; // Value injected by FXMLLoader

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void initialize()
	{
		pane1.requestFocus();
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		value.setCellValueFactory(new PropertyValueFactory<>("value"));

		charFile = MainController.fc.showOpenDialog(null);

		try {
			ch = new Character(charFile);
			setStatsFields();
			
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		loadChar.addEventHandler(Event.ANY, new EventHandler(){
			public void handle(Event e)
			{
				//System.out.println(e.getEventType().getName());
				if(e.getEventType().equals(MouseEvent.MOUSE_CLICKED))
				{
					//System.out.println(e.getEventType().getName());
					charFile = MainController.fc.showOpenDialog(null);
					if(charFile != null)
					{
						try {
							ch = new Character(charFile);
							setStatsFields();
							MainController.updateLists(Id, ch);;
							

						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
		MainController.addToLists(ch);
	}
	private void setStatsFields()
	{

		if(ch != null)
		{
			int[] stats = ch.getAttrs();
			if(!data.isEmpty())
				data.clear();

			for(int i = 0; i < stats.length; i++)
			{
				data.add(new JavaFXStat(UF.attrList[i], stats[i]));
			}

			statsTable.setItems(data);
			//statsTable.getColumns().addAll(name, value);

			charName.setText(ch.getName());
			charClass.setText(ch.getCl().getName());
			charRace.setText(ch.getRace().getName());
			charLevel.setText(Integer.toString(ch.getLevel()));
			charWeight.setText(Double.toString(ch.getWeight()));
			charHeight.setText(Double.toString(ch.getHeight()));
			charHP.setText(Integer.toString(ch.getHP()));
		}
	}
}


