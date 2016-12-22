package testing;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.WindowConstants;

import charcreate.CreationForm;
import main.Character;
import main.ClassList;
import main.IdConflictException;
import main.RaceList;
import util.UF;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Test{
	public static ArrayList<String> inputs = new ArrayList<String>();
	public static String[] xmlSections = {"Race","Class","Name","Stats","Attributes"};
	public static String[] statsList = {"Level","HP","Weight","Height"};
	public static void main(String[] args){
		RaceList rl = new RaceList(UF.raceLoc);
		ClassList cl = new ClassList(UF.classLoc);


		try {
			cl.buildList();
			rl.buildList();
		} catch (IdConflictException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Character tc = new Character(new int[] {1,2,3,4,5,6}, "Alex Scott", 112.34, 2.56, rl.get(1), cl.get(1));
		tc.printCharacter();
		System.out.println();
		try {
			tc.writeCharacterToFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void writeCharXML(Character c)
	{
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("character");
			doc.appendChild(rootElement);


			for(int i = 0; i < xmlSections.length; i++)
			{
				Element e = doc.createElement(xmlSections[i]);
				rootElement.appendChild(e);
				if(xmlSections[i].equalsIgnoreCase("stats"))
				{
					for(int j = 0; j < statsList.length; j++)
					{
						Element se = doc.createElement(statsList[j]);
						e.appendChild(se);
						appendTextNode(e,doc,c.getStatValueSTR(statsList[j]));
					}
				}
				else if(xmlSections[i].equalsIgnoreCase("attributes"))
				{
					for(int j = 0; j < 6; j++)
					{
						Element ta = doc.createElement(UF.attrList[j]);
						e.appendChild(ta);
						appendTextNode(e,doc,Integer.toString(c.getAttrs()[j]));
					}
				}
				else
				{
					appendTextNode(e,doc,xmlSections[i]);
				}

			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("file.xml"));

			// Output to console for testing
			//StreamResult result = new StreamResult(System.out);

			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.transform(source, result);


			//System.out.println("File saved!");
			//System.out.println("Location: " + result.getSystemId());

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
	
	private static void appendTextNode(Element e, Document doc, String data)
	{
		e.appendChild(doc.createTextNode(data));
	}
	
	public static void readCharXML(File cf)
	{
		try{
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
			for(int i = 0; i < xmlSections.length; i++)
			{
				String elem = null;
				if(xmlSections[i].equalsIgnoreCase("stats"))
				{
					for(int j = 0; j < statsList.length; j++)
					{
						elem = elementValue(e,statsList[j],0);
						System.out.printf("%s = %s\n",statsList[j],elem);
					}
				}
				else if(xmlSections[i].equalsIgnoreCase("Attributes"))
				{
					for(int j = 0; j < UF.attrList.length; j++)
					{
						elem = elementValue(e,UF.attrList[j],0);
						System.out.printf("%s = %s\n",UF.attrList[j],elem);
					}
				}
				else{
					elem = elementValue(e,xmlSections[i],0);
					System.out.printf("%s = %s\n",xmlSections[i],elem);
				}


			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String elementValue(Element e, String tag, int index)
	{
		return e.getElementsByTagName(tag).item(index).getTextContent();
	}
}
