package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class RaceList{ //An ArrayList containing all available races in the game (from a file)

	private ArrayList<Race> racelist = new ArrayList<Race>();
	private File listLoc;

	//Requires the location of the race-list file
	public RaceList(File race_list)
	{
		listLoc = race_list;
	}

	//Builds the list of races
	public void buildList() throws IdConflictException, ParseException
	{
		Scanner in = null;
		//ArrayList<Race> rlist = new ArrayList<Race>();
		int lineNumber = 1;
		int[] b = new int[6];
		int id = 0;
		String rname = "";

		try {
			in = new Scanner(listLoc);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lineNumber++;
		String line = in.nextLine().trim();
		String tmp = line.trim();
		while(in.hasNextLine())
		{		

			//System.out.println(tmp);
			if(!tmp.startsWith("#") && !tmp.equalsIgnoreCase(""))
			{
				if(tmp.trim().startsWith("{"))
				{

					for(int i = 0; i < b.length; i++)
						b[i] = 0;

					String ids = tmp.replaceAll("\\{", "").replaceAll("\\}", "");
					id = Integer.parseInt(ids);
					
					//System.out.printf("id = %d\n", id);
					tmp = in.nextLine().trim();
					lineNumber++;
					//System.out.println(tmp);
					while(tmp.startsWith("r:") || tmp.startsWith("#"))
					{
						if(tmp.startsWith("#"))
						{
							tmp = in.nextLine().trim();
							lineNumber++;
							continue;
						}
						tmp = tmp.replaceAll("r:", "");
						if(tmp.startsWith("name"))
						{
							tmp = tmp.replaceFirst("name","").trim().replace("=", "").trim().replaceAll("\"", "");
							rname = tmp;
							if(!isIdUnique(id))
								throw new IdConflictException("Race ID conflict: " + id + "; " + rname + " and " + get(id).getName() + ". Line number " + (lineNumber-2));
							//System.out.printf("name = %s\n", rname);
						}
						else if(tmp.startsWith("bonus"))
						{
							//System.out.println(tmp);
							tmp = tmp.replaceFirst("bonus","").trim().replace("=", "").trim().replaceAll("\"", "").replaceAll(",", "");
							tmp = tmp.toLowerCase();
							if(tmp.startsWith("str"))
							{
								tmp = tmp.replaceAll("strength", "");
								b[0] = Integer.parseInt(tmp);
							}
							else if(tmp.startsWith("dex"))
							{
								tmp = tmp.replaceAll("dexterity", "");
								b[1] = Integer.parseInt(tmp);
							}
							else if(tmp.startsWith("con"))
							{
								tmp = tmp.replaceAll("constitution", "");
								b[2] = Integer.parseInt(tmp);
							}
							else if(tmp.startsWith("int"))
							{
								tmp = tmp.replaceAll("intelligence", "");
								b[3] = Integer.parseInt(tmp);
							}
							else if(tmp.startsWith("wis"))
							{
								tmp = tmp.replaceAll("wisdom", "");
								b[4] = Integer.parseInt(tmp);
							}
							else if(tmp.startsWith("cha"))
							{
								tmp = tmp.replaceAll("charisma", "");
								b[5] = Integer.parseInt(tmp);
							}
							else
							{
								throw new ParseException("Unknown bonus: \"" + tmp.substring(0, tmp.length()-1) + "\" at line " + (lineNumber-1), lineNumber);
							}
						}
						else
							throw new ParseException("Unknown Parameter: " + line.replaceAll("r:", ""), lineNumber);
						if(in.hasNextLine())
							{tmp = in.nextLine().trim();lineNumber++;}
					}
					/*if(!isIdUnique(id))
						throw new IdConflictException("Race ID conflict: " + id + "; " + rname + " and " + get(id).getName() + ". Line number " + (lineNumber-5));*/
					racelist.add(new Race(id, rname, Arrays.copyOf(b, b.length)));
				}
				else if(tmp.trim().startsWith("r:"))
				{
					throw new ParseException("ID missing at line " + (lineNumber-2) + ".",lineNumber);
				}
			}
			else{
				line = in.nextLine();
				tmp =line;
				lineNumber++;
			}
		}
		/*System.out.printf("Race list: \n\n");
		Iterator<Race> it = racelist.iterator();
		while(it.hasNext())
		{it.next().printRace(); System.out.println();}*/

	}
	
	public Race get(String name)
	{
		Iterator<Race> it = racelist.iterator();
		while(it.hasNext())
		{
			Race r = it.next();
			if(name.equalsIgnoreCase(r.getName()))
			{
				return r;
			}
		}
		return null;
	}
	
	//returns the race with the specific race id
	public Race get(int raceId)
	{
		for(int i = 0; i < racelist.size(); i++)
		{
			if(racelist.get(i).getID() == raceId)
				return racelist.get(i);
		}
		
		return null;
	}
	
	//returns the race list as an ArrayList
	public ArrayList<Race> getRaceList()
	{
		return racelist;
	}

	private boolean isIdUnique(int id)
	{
		return (get(id) == null);			
	}
	
	public int getRaceId(String name)
	{
		Iterator<Race> it = racelist.iterator();
		while(it.hasNext())
		{
			Race r = it.next();
			if(name.equalsIgnoreCase(r.getName()))
			{
				return r.getID();
			}
		}
		return -1;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RaceList r = null;
		Timer t = new Timer();
		t.start();
		r = new RaceList(new File("race-list.txt"));
		try {
			r.buildList();
		} catch (IdConflictException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t.end();
		System.out.printf("Elapsed time: %f\n\n", t.difference()/1000.0);
		Iterator<Race> i = r.getRaceList().iterator();
		while(i.hasNext())
			{i.next().printRace();
		System.out.println("");}
		/**/
	}

}
