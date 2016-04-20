package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Scanner;

import util.FileIO;
import util.UF;

public class Character {
	private int[] stats = new int[6];
	private String name = "";
	private Class cl = null;
	private Race race = null;
	private float weight = 0;
	private float height = 0;
	
	public Character(int[] stats, String name, float weight, float height, Race race, Class cl)
	{
		this.setStats(stats);
		this.setName(name);
		this.setWeight(weight);
		this.setHeight(height);
		this.setRace(race);
		this.setCl(cl);
	}
	
	public Character(File data)
	{
		RaceList rli = new RaceList(UF.raceLoc);
		ClassList cli = new ClassList(UF.classLoc);
		try {
			rli.buildList();
			cli.buildList();
		} catch (IdConflictException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Scanner in = new Scanner(data);
			name = in.nextLine().trim();
			weight = in.nextFloat();
			height = in.nextFloat();
			race = rli.get(in.nextInt());
			cl = cli.get(in.nextInt());
			int i = 0;
			in.useDelimiter("\\s*,\\s*");
			while(in.hasNext())
			{
				String test = in.next();
				//System.out.println("test = " + test);
				stats[i] = Integer.parseInt(test.trim());
				i++;
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *Writes the character information to file in this order:
	 *Name
	 *Weight
	 *Height
	 *Race ID
	 *Class ID
	 *Stats (comma separated) 
	 * @throws IOException 
	 **/
	public void writeCharacterToFile() throws IOException
	{
		String to_write = name + "\n" + Float.toString(weight) + 
							"\n" + Float.toString(height) + "\n"
							+ race.getID() + "\n" + cl.getId() +
							"\n" + stats[0] + "," + stats[1] + "," +
							stats[2] + "," + stats[3] + "," +
							stats[4] + "," + stats[5];
		Path fileName = Paths.get(name.concat("-data.txt").replaceAll(" ", "_"));
		
		FileIO.createFile(fileName);
		FileIO.writeToFile(fileName, to_write);
	}
	
	public void printCharacter()
	{
		System.out.printf("Name:\t%s\nWeight:\t%f\nHeight:\t%f\n"
				+ "Race:\t%s\nClass\t%s\nStats:", name,weight,height,race.getName(),cl.getName());
		for(int i = 0; i < 6; i++)
		{
			System.out.printf("\t%s = %d\n", UF.statsList[i],stats[i]);
		}
	}
	

	/**
	 * @return the height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	/**
	 * @return the stats
	 */
	public int[] getStats() {
		return stats;
	}

	/**
	 * @param stats the stats to set
	 */
	public void setStats(int[] stats) {
		this.stats = stats;
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

	/**
	 * @return the cl
	 */
	public Class getCl() {
		return cl;
	}

	/**
	 * @param cl the cl to set
	 */
	public void setCl(Class cl) {
		this.cl = cl;
	}

	/**
	 * @return the race
	 */
	public Race getRace() {
		return race;
	}

	/**
	 * @param race the race to set
	 */
	public void setRace(Race race) {
		this.race = race;
	}

	/**
	 * @return the weight
	 */
	public float getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(float weight) {
		this.weight = weight;
	}
}
