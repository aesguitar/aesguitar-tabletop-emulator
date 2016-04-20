package main;

public class Class {
	private int id = -1;
	private String name = "";
	public Class(int id, String name)
	{
		this.setId(id);
		this.setName(name);
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public static Class defaultClass()
	{
		return new Class(-1, "");
	}
	
	public void printClass() {
		// TODO Auto-generated method stub
		System.out.println("ID: " + id + "\nName: " + name);
	}
}
