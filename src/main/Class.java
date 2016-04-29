package main;

import util.Dice;

public class Class {
	private int id = -1;
	private String name = "";
	private Dice die;
	public Class(int id, String name, Dice die)
	{
		this.setId(id);
		this.setName(name);
		this.setDie(die);
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
		return new Class(-1, "", new Dice("0d0m0r0"));
	}
	
	public void printClass() {
		// TODO Auto-generated method stub
		System.out.println("ID: " + id + "\nName: " + name + "\nHit Dice: " + die);
	}
	/**
	 * @return the die
	 */
	public Dice getDie() {
		return die;
	}
	/**
	 * @param die the die to set
	 */
	public void setDie(Dice die) {
		this.die = die;
	}
}
