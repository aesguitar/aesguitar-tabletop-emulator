package testing;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class JFXStatsTableRow {
	
	private SimpleStringProperty name;
	private SimpleIntegerProperty base;
	private SimpleIntegerProperty modifier;
	private SimpleIntegerProperty finalVal;
	
	public JFXStatsTableRow(String name, int base, int modifier, int finalVal)
	{
		this.name = new SimpleStringProperty(name);
		this.base = new SimpleIntegerProperty(base);
		this.modifier = new SimpleIntegerProperty(modifier);
		this.finalVal = new SimpleIntegerProperty(base + modifier);
	}

	public String getName()
	{
		return name.get();
	}
	
	public void setName(String name)
	{
		this.name.set(name);
	}
	
	public Integer getModifier()
	{
		return modifier.get();
	}
	
	public void setModifier(int val)
	{
		modifier.set(val);
	}
	
	public Integer getFinalVal()
	{
		finalVal.set(base.get()+modifier.get());;
		return finalVal.get();
	}
	
	public Integer getBase()
	{
		return base.get();
	}
	
	public void setBase(int val)
	{
		base.set(val);
	}
	
	
	
	
}
