package testing;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;

public class FocusGainedEvent extends Event{
	
	public static final EventType eventType = new EventType("Focus gained");

	public FocusGainedEvent(EventType<? extends Event> eventType) {
		super(eventType);
		// TODO Auto-generated constructor stub
	}

	
}
