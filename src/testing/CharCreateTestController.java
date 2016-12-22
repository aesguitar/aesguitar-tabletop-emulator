package testing;

import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

import JFXEvents.FocusLostEvent;
import JFXEvents.JFXCustomEvents;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.ClassList;
import main.IdConflictException;
import main.RaceList;
import util.UF;

public class CharCreateTestController {

	private RaceList rl;
	private ClassList cl;
	private ObservableList<JFXStatsTableRow> data = FXCollections.observableArrayList();

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane mainPane;

	@FXML
	private Button save, cancel, rollBtn;

	@FXML
	private TextField nameField, heightField, weightField;

	@FXML
	private ComboBox<String> raceField, classField;

	@FXML
	private Label errorLbl;

	@FXML
	private TableView<JFXStatsTableRow> statsTable;

	@FXML
	private TableColumn nameCol, baseCol, modifierCol, finalCol;

	@SuppressWarnings("unchecked")
	@FXML
	void initialize() {

		rl = new RaceList(UF.raceLoc);
		cl = new ClassList(UF.classLoc);

		try {
			rl.buildList();
			cl.buildList();
		} catch (IdConflictException | ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ObservableList<String> orl = FXCollections.observableArrayList(rl.getListOfNames());
		raceField.setItems(orl);

		ObservableList<String> ocl = FXCollections.observableArrayList(cl.getListOfNames());
		classField.setItems(ocl);

		heightField = (TextField) JFXCustomEvents.addFocusListener(heightField);
		weightField = (TextField) JFXCustomEvents.addFocusListener(weightField);


		cancel.addEventHandler(Event.ANY, new EventHandler() {
			public void handle(Event e)
			{
				if(e.getEventType().equals(MouseEvent.MOUSE_CLICKED))
				{
					//System.out.println("Cancel clicked.");
					Stage st = ((Stage) cancel.getScene().getWindow());
					st.close();
				}
			}
		});

		raceField.addEventHandler(Event.ANY, new EventHandler()
		{
			public void handle(Event e)
			{
				if(e.getEventType().equals(ActionEvent.ACTION))
				{
					System.out.println("Race selected: " + raceField.getValue());
					int[] modifiers = rl.get(raceField.getValue()).getBonus();
					for(int i = 0; i < 6; i++)
					{
						data.get(i).setModifier(modifiers[i]);
					}

					statsTable.refresh();
				}
			}
		});

		classField.addEventHandler(Event.ANY, new EventHandler()
		{
			public void handle(Event e)
			{
				if(e.getEventType().equals(ActionEvent.ACTION))
				{
					cl.get(classField.getValue()).printClass();
				}
			}
		});

		heightField.addEventHandler(Event.ANY, new EventHandler()
		{
			public void handle(Event e)
			{
				if(e.getEventType().equals(FocusLostEvent.eventType))
					if(!UF.isFloat(heightField.getText()))
					{
						heightField.setStyle("-fx-text-fill: red;");
						if(!errorLbl.getText().contains("Height must be a number."))
							errorLbl.setText(errorLbl.getText().concat("Height must be a number. "));
					}
					else
					{
						if(UF.isInt(heightField.getText()))
							heightField.setText(heightField.getText().trim().concat(".0"));
						heightField.setStyle("-fx-text-fill: black;");
						errorLbl.setText(errorLbl.getText().replaceAll("Height must be a number. ", ""));
					}
			}
		});

		weightField.addEventHandler(Event.ANY, new EventHandler()
		{
			public void handle(Event e)
			{
				if(e.getEventType().equals(FocusLostEvent.eventType))
					if(!UF.isFloat(weightField.getText()))
					{
						weightField.setStyle("-fx-text-fill: red;");
						if(!errorLbl.getText().contains("Weight must be a number."))
							errorLbl.setText(errorLbl.getText().concat("Weight must be a number. "));
					}
					else
					{
						if(UF.isInt(weightField.getText()))
							weightField.setText(weightField.getText().trim().concat(".0"));
						weightField.setStyle("-fx-text-fill: black;");
						errorLbl.setText(errorLbl.getText().replaceAll("Weight must be a number. ", ""));
					}
			}
		});

		mainPane.addEventHandler(Event.ANY, new EventHandler()
		{
			public void handle(Event e)
			{
				//System.out.println(e.getTarget());
				if(e.getTarget().equals(mainPane) && e.getEventType().equals(MouseEvent.MOUSE_CLICKED))
					mainPane.requestFocus();
			}
		});

		rollBtn.addEventHandler(Event.ANY, new EventHandler()
		{
			public void handle(Event e)
			{
				if(e.getEventType().equals(MouseEvent.MOUSE_CLICKED))
				{
					
				}
			}
		});

		initializeStatsTable();
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		baseCol.setCellValueFactory(new PropertyValueFactory<>("base"));
		modifierCol.setCellValueFactory(new PropertyValueFactory<>("modifier"));
		finalCol.setCellValueFactory(new PropertyValueFactory<>("finalVal"));

	}

	public void initializeStatsTable()
	{
		for(int i = 0; i < 6; i++)
		{
			data.add(new JFXStatsTableRow(UF.attrList[i], 0, 0, 0));
		}

		statsTable.setItems(data);
	}
}
