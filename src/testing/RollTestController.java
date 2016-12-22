package testing;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import util.UF;

public class RollTestController {
	
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;
	
	@FXML
	private TextField field1, field2, field3, field4, field5, field6;
	
	@FXML
	private ComboBox<String> stat1,stat2,stat3,stat4,stat5,stat6;
	
	@FXML
	private Button roll,accept,cancel;
	
	public void initialize()
	{
		ObservableList<String> statNames = FXCollections.observableArrayList(new ArrayList<String>(Arrays.asList(UF.attrList)));
		System.out.println("First stat:" + statNames.get(0));
		stat1.setItems(statNames);
		stat2.setItems(statNames);
		stat3.setItems(statNames);
		stat4.setItems(statNames);
		stat5.setItems(statNames);
		stat6.setItems(statNames);
	}

}
