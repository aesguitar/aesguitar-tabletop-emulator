package JFXEvents;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;

public class FocusLostEvent extends Event{
	
	public static final EventType eventType = new EventType("Focus lost");

	public FocusLostEvent(EventType<? extends Event> eventType) {
		super(eventType);
		// TODO Auto-generated constructor stub
	}

	
}
