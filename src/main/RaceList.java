package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import util.Dice;
import util.Timer;
import util.UF;

public class RaceList{ //An ArrayList containing all available races in the game (from a file)

	private ArrayList<Race> racelist = new ArrayList<Race>();
	private File listLoc;

	//Requires the location of the race-list file
	public RaceList(File race_list)
	{
		listLoc = race_list;
	}

	//Builds the list of races
	public void buildList() throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(listLoc);

		//optional, but recommended
		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();

		NodeList nl = doc.getElementsByTagName("Race");

		for(int i = 0; i < nl.getLength(); i++)
		{
			Element e = (Element)nl.item(i);
			int tid = Integer.parseInt(e.getAttribute("id"));
			String name = elementValue(e,"Name",0);
			int[] mods = new int[6];
			for(int j = 0; j < 6; j++)
			{
				mods[j] = Integer.parseInt(elementValue(e,UF.attrList[j],0));
			}
			racelist.add(new Race(tid,name,mods));			
		}


		System.out.println(nl.getLength());

	}
	private String elementValue(Element e, String tag, int index)
	{
		return e.getElementsByTagName(tag).item(index).getTextContent();
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
			if(racelist.get(i).getId() == raceId)
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
				return r.getId();
			}
		}
		return -1;
	}

	public ArrayList<String> getListOfNames()
	{
		ArrayList<String> names = new ArrayList<String>();
		Iterator<Race> i = racelist.iterator();
		while(i.hasNext())
			names.add(i.next().getName());

		return names;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RaceList r = null;
		Timer t = new Timer();
		t.start();
		r = new RaceList(UF.raceLoc);
		try {
			r.buildList();
		} catch (ParserConfigurationException | SAXException | IOException e) {
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
