package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import util.Dice;
import util.Timer;
import util.UF;

public class ClassList {
	
	private ArrayList<Class> classlist = new ArrayList<Class>();
	private File listLoc;

	//Requires the location of the Class-list file
	public ClassList(File Class_list)
	{
		listLoc = Class_list;
	}

	//Builds the list of Classes
	public void buildList() throws ParserConfigurationException, IOException, SAXException 
	{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(listLoc);

		//optional, but recommended
		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();
		
		NodeList nl = doc.getElementsByTagName("Class");
		
		for(int i = 0; i < nl.getLength(); i++)
		{
			Element e = (Element)nl.item(i);
			int tid = Integer.parseInt(e.getAttribute("id"));
			String name = elementValue(e,"Name",0);
			Dice hd = new Dice(elementValue(e,"Hit_Dice",0));
			classlist.add(new Class(tid,name,hd));		
		}
		
		
		//System.out.println(nl.getLength());
	}
	
	private String elementValue(Element e, String tag, int index)
	{
		return e.getElementsByTagName(tag).item(index).getTextContent();
	}
	
	public Class get(String name)
	{
		Iterator<Class> it = classlist.iterator();
		while(it.hasNext())
		{
			Class r = it.next();
			if(name.equalsIgnoreCase(r.getName()))
			{
				return r;
			}
		}
		return null;
	}
	
	//returns the Class with the specific Class id
	public Class get(int ClassId)
	{
		for(int i = 0; i < classlist.size(); i++)
		{
			if(classlist.get(i).getId() == ClassId)
				return classlist.get(i);
		}
		
		return null;
	}
	
	//returns the Class list as an ArrayList
	public ArrayList<Class> getClassList()
	{
		return classlist;
	}

	private boolean isIdUnique(int id)
	{
		return (get(id) == null);			
	}
	
	public int getClassId(String name)
	{
		Iterator<Class> it = classlist.iterator();
		while(it.hasNext())
		{
			Class r = it.next();
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
		Iterator<Class> i = classlist.iterator();
		while(i.hasNext())
			names.add(i.next().getName());
		
		return names;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClassList r = null;
		Timer t = new Timer();
		t.start();
		r = new ClassList(UF.classLoc);
		try {
			r.buildList();
		} catch (ParserConfigurationException | IOException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t.end();
		System.out.printf("Elapsed time: %f\n\n", t.difference()/1000.0);
		Iterator<Class> i = r.getClassList().iterator();
		while(i.hasNext())
			{i.next().printClass();
		System.out.println("");}
		/**/
	}

}
