package JFXEvents;

import javafx.beans.property.*;
import main.Character;

public class ObservableCharacter {
	
	private SimpleStringProperty name, race, cl;
	private SimpleIntegerProperty level, hitpoints;
	private SimpleIntegerProperty[] attrs = new SimpleIntegerProperty[6];
	private SimpleDoubleProperty weight, height;
	
	public ObservableCharacter(Character ch)
	{
		name = toSSP(ch.getName());
		race = toSSP(ch.getRace().getName());
		cl = toSSP(ch.getCl().getName());
		level = toSIP(ch.getLevel());
		hitpoints = toSIP(ch.getHP());
		
		for(int i = 0; i < 6; i++)
		{
			attrs[i] = toSIP(ch.getAttrs()[i]);
		}
		
		weight = toSDP(ch.getWeight());
		height = toSDP(ch.getHeight());
	}
	
	public void update(Character ch)
	{
		name.set(ch.getName());
		race.set(ch.getRace().getName());
		cl.set(ch.getCl().getName());
		level.set(ch.getLevel());
		hitpoints.set(ch.getHP());
		
		for(int i = 0; i < 6; i++)
		{
			attrs[i].set(ch.getAttrs()[i]);
		}
		
		weight.set(ch.getWeight());
		height.set(ch.getHeight());
	}
	
	
	private SimpleStringProperty toSSP(String s)
	{
		return new SimpleStringProperty(s);
	}
	
	private SimpleIntegerProperty toSIP(int i)
	{
		return new SimpleIntegerProperty(i);
	}
	
	private SimpleDoubleProperty toSDP(double d)
	{
		return new SimpleDoubleProperty(d);
	}
	
	public SimpleStringProperty getName()
	{
		return name;
	}
	
	public SimpleStringProperty getRace()
	{
		return race;
	}
	
	public SimpleStringProperty getCl()
	{
		return cl;
	}
	
	public SimpleIntegerProperty getLevel()
	{
		return level;
	}
	
	public SimpleIntegerProperty getHitpoints()
	{
		return hitpoints;
	}
	public SimpleIntegerProperty[] getAttrs()
	{
		return attrs;
	}
	
	public SimpleDoubleProperty getWeight()
	{
		return weight;
	}
	
	public SimpleDoubleProperty getHeight()
	{
		return height;
	}
	
}
