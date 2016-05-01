package testing;

import java.io.IOException;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestMain2Main extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("TestMain2.fxml"));
		Scene scene = new Scene(root);
		
		primaryStage.setTitle("Test Window");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		primaryStage.focusedProperty().addListener(new ChangeListener<Boolean>() {

			public void changed(ObservableValue<? extends Boolean> ov, Boolean t1, Boolean t2)
			{
				if(t2)
					System.out.println(primaryStage.focusedProperty().getName());
					
			}
			
		});
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
