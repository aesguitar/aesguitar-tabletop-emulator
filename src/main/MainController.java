package main;


import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.Scanner;

import JFXEvents.FocusGainedEvent;
import JFXEvents.FocusLostEvent;
import JFXEvents.JFXCustomEvents;
import JFXEvents.JavaFXStat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import util.UF;
import main.Character;
import javafx.scene.paint.Color;

public class MainController {
	private Character ch;
	private FileChooser fc = new FileChooser();
	private File charFile;
	private ObservableList<JavaFXStat> data = FXCollections.observableArrayList(); 

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private MenuItem openFile;

	@FXML
	private TextField charName, charClass, charRace, charLevel, charHeight, charWeight, charHP;

	@FXML
	private GridPane pane1;

	@FXML
	private Label nameLbl, classLbl, raceLbl;

	@FXML
	private Button loadChar;

	@FXML
	private Tab charTab;

	@FXML
	private TableView<JavaFXStat> statsTable;

	@FXML
	private TableColumn name,  value;


	@SuppressWarnings("unchecked")
	public void initialize()
	{
		fc.setInitialDirectory(UF.currDir);
		pane1.requestFocus();
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		value.setCellValueFactory(new PropertyValueFactory<>("value"));

		loadChar.addEventHandler(Event.ANY, new EventHandler(){
			public void handle(Event e)
			{
				//System.out.println(e.getEventType().getName());
				if(e.getEventType().equals(MouseEvent.MOUSE_CLICKED))
				{
					//System.out.println(e.getEventType().getName());
					charFile = fc.showOpenDialog(null);
					if(charFile != null)
					{
						ch = new Character(charFile);
						setStatsFields();
					}
				}
			}
		});

		openFile.addEventHandler(Event.ANY, new EventHandler()
		{
			public void handle(Event e)
			{
				if(e.getEventType().equals(ActionEvent.ANY))
				{
					//System.out.println(e.getEventType().getName());
					charFile = fc.showOpenDialog(null);
					if(charFile != null)
					{
						ch = new Character(charFile);
						setStatsFields();
					}
				}
			}
		});

	}

	@SuppressWarnings("unchecked")
	private void setStatsFields()
	{

		if(ch != null)
		{
			int[] stats = ch.getStats();
			for(int i = 0; i < stats.length; i++)
			{
				data.add(new JavaFXStat(UF.statsList[i], stats[i]));
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
