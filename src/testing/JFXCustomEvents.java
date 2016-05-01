package testing;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;

public class JFXCustomEvents {
	
	public static Control addFocusListener(Control tf)
	{
		 tf.focusedProperty().addListener(new ChangeListener<Boolean>() {
	        	public void changed(ObservableValue<? extends Boolean> ov, Boolean t1, Boolean t2)
				{
					if(t1)
						tf.fireEvent(new FocusLostEvent(FocusLostEvent.eventType));
					else
						tf.fireEvent(new FocusGainedEvent(FocusGainedEvent.eventType));
				}
	        });
		 
		 return tf;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
