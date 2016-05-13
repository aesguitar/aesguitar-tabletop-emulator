package util;

public class Stat {
	
	String name;
	int value;
	
	public Stat(String name, int value)
	{
		this.name = name;
		this.value = value;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public String getName()
	{
		return name;
	}

}
