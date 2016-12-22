package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Scanner;

import javax.swing.JOptionPane;
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

import util.FileIO;
import util.UF;

public class Character {
	private RaceList rli = new RaceList(UF.raceLoc);
	private ClassList cli = new ClassList(UF.classLoc);
	private int[] attrs = new int[6];
	private String name = "";
	private Class cl = null;
	private Race race = null;
	private double weight = 0;
	private double height = 0;
	int level = 0;
	double experience = 0;
	int hitpoints = 0;

	public Character(int[] stats, String name, double weight, double height, Race race, Class cl)
	{
		this.setAttrs(stats);
		this.setName(name);
		this.setWeight(weight);
		this.setHeight(height);
		this.setRace(race);
		this.setCl(cl);
		level = 1;
		hitpoints = cl.getDie().getNumSides() + (int)Math.floorDiv(stats[2]-10, 2);
		//System.out.println(hitpoints);

	}

	public Character(File data) throws Exception
	{

		rli.buildList();
		cli.buildList();
		
		readCharXML(data);
	}

	/**
	 *Writes the character information to XML
	 * @throws Exception 
	 **/
	public void writeCharacterToFile() throws Exception
	{
		Path fileName = Paths.get(name.concat("-data.xml").replaceAll(" ", "_"));

		if(Files.exists(fileName))
		{
			int conf = JOptionPane.showConfirmDialog(null, "File already exists. Overwrite?");
			if(conf == JOptionPane.OK_OPTION)
			{
				FileIO.deleteFile(fileName);
				writeCharXML(fileName.toFile());
			}
			else
				JOptionPane.showMessageDialog(null, "No file operation completed.");
		}
		else
		{
			writeCharXML(fileName.toFile());
		}



	}
	private void writeCharXML(File fileName) throws Exception
	{
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("character");
			doc.appendChild(rootElement);


			for(int i = 0; i < UF.xmlSections.length; i++)
			{
				Element e = doc.createElement(UF.xmlSections[i]);
				rootElement.appendChild(e);
				if(UF.xmlSections[i].equalsIgnoreCase("stats"))
				{
					for(int j = 0; j < UF.statsList.length; j++)
					{
						Element se = doc.createElement(UF.statsList[j]);
						e.appendChild(se);
						appendTextNode(se,doc,getStatValueSTR(UF.statsList[j]));
					}
				}
				else if(UF.xmlSections[i].equalsIgnoreCase("attributes"))
				{
					for(int j = 0; j < 6; j++)
					{
						Element ta = doc.createElement(UF.attrList[j]);
						e.appendChild(ta);
						appendTextNode(ta,doc,Integer.toString(getAttrs()[j]));
					}
				}
				else
				{
					switch(i){
					case 0:
						appendTextNode(e,doc,race.getName());
						break;
					case 1:
						appendTextNode(e,doc,cl.getName());
						break;
					case 2:
						appendTextNode(e,doc,name);
						break;
					default:
						throw new Exception("Unknown value: " + UF.xmlSections[i]);
					}
				}

			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(fileName);

			// Output to console for testing
			//StreamResult result = new StreamResult(System.out);

			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.transform(source, result);


			System.out.printf("%s written to file.\n",fileName.getAbsolutePath());
			//System.out.println("Location: " + result.getSystemId());

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	private void appendTextNode(Element e, Document doc, String data)
	{
		e.appendChild(doc.createTextNode(data));
	}

	private void readCharXML(File cf) throws Exception
	{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(cf);

		//optional, but recommended
		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();

		NodeList nl = doc.getElementsByTagName("character");
		//System.out.printf("Number of nodes: %d\n",nl.getLength());
		Node n = nl.item(0);
		Element e = (Element)n;
		for(int i = 0; i < UF.xmlSections.length; i++)
		{
			String elem = null;
			if(UF.xmlSections[i].equalsIgnoreCase("stats"))
			{
				for(int j = 0; j < UF.statsList.length; j++)
				{
					elem = elementValue(e,UF.statsList[j],0);
					setStatValueSTR(UF.statsList[j],elem);
				}
			}
			else if(UF.xmlSections[i].equalsIgnoreCase("Attributes"))
			{
				for(int j = 0; j < UF.attrList.length; j++)
				{
					elem = elementValue(e,UF.attrList[j],0);
					attrs[j]=Integer.parseInt(elem);
				}
			}
			else{
				elem = elementValue(e,UF.xmlSections[i],0);
				switch(i){
				case 0: 
					race = rli.get(elem);
					break;
				case 1: 
					cl = cli.get(elem);
					break;
				case 2: 
					name = elem;
					break;
				default:
					throw new Exception("Unknown value: " + UF.xmlSections[i]);
				}
			}
		}
	}

	private String elementValue(Element e, String tag, int index)
	{
		return e.getElementsByTagName(tag).item(index).getTextContent();
	}




	public void printCharacter()
	{
		System.out.printf("Name:\t%s\nWeight:\t%f\nHeight:\t%f\n"
				+ "Level:\t%d\n" + "Hitpoints:\t%d\n" + "Race:\t%s\nClass\t%s\nStats:", name,weight,height,level, hitpoints, race.getName(),cl.getName());
		for(int i = 0; i < 6; i++)
		{
			System.out.printf("\t%s = %d\n", UF.attrList[i],attrs[i]);
		}
	}


	/**
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(double height) {
		this.height = height;
	}

	/**
	 * @return the stats
	 */
	public int[] getAttrs() {
		return attrs;
	}

	/**
	 * @param stats the stats to set
	 */
	public void setAttrs(int[] stats) {
		this.attrs = stats;
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
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	public int getLevel() {
		// TODO Auto-generated method stub
		return level;
	}

	public int getHP()
	{
		return hitpoints;
	}

	public void setHP(int val)
	{
		hitpoints = val;
	}

	public String getStatValueSTR(String valName)
	{
		if(valName.equalsIgnoreCase("weight"))
			return Double.toString(weight);
		else if(valName.equalsIgnoreCase("height"))
			return Double.toString(height);
		else if (valName.equalsIgnoreCase("level"))
			return Integer.toString(level);
		else if (valName.equalsIgnoreCase("hp") || valName.equalsIgnoreCase("hitpoints"))
			return Integer.toString(hitpoints);
		else
			return null;

	}
	public void setStatValueSTR(String valName, String value) throws Exception
	{
		if(valName.equalsIgnoreCase("weight"))
			weight = Double.parseDouble(value);
		else if(valName.equalsIgnoreCase("height"))
			height = Double.parseDouble(value);
		else if (valName.equalsIgnoreCase("level"))
			level = Integer.parseInt(value);
		else if (valName.equalsIgnoreCase("hp") || valName.equalsIgnoreCase("hitpoints"))
			hitpoints = Integer.parseInt(value);
		else
			throw new Exception("Unknown stat: " + valName);

	}
}
