package testing;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class TestMain2Controller {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField testField1;

	@FXML
	private Button button1;

	@FXML
	private Pane pane;


	@SuppressWarnings("unchecked")
	@FXML
	void initialize() {
		assert testField1 != null : "fx:id=\"testField1\" was not injected: check your FXML file 'TestMain2.fxml'.";
		assert button1 != null : "fx:id=\"button1\" was not injected: check your FXML file 'TestMain2.fxml'.";

		testField1 = (TextField) JFXCustomEvents.addFocusListener(testField1);
		
		testField1.addEventHandler(Event.ANY, new EventHandler() {
			@Override
			public void handle(Event event) {
				//System.out.println(event.getEventType());
				if(event.getEventType().equals(FocusLostEvent.eventType))
					testField1.setText("Focus Lost.");
				else if(event.getEventType().equals(FocusGainedEvent.eventType))
					testField1.setText("Focus Gained.");
			}    	   
		});

		button1.addEventHandler(Event.ANY, new EventHandler() {
			@Override
			public void handle(Event event) {
				if(event.getEventType().equals(MouseEvent.MOUSE_CLICKED))
					testField1.setText("Button clicked.");
			}
		});

		pane.addEventHandler(Event.ANY, new EventHandler(){

			@Override
			public void handle(Event event) {
				if(event.getEventType().equals(MouseEvent.MOUSE_CLICKED))
					pane.requestFocus();

			}

		});

	}
}
