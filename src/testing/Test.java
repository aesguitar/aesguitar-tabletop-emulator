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
import org.xml.sax.SAXException;

public class Test{
	public static ArrayList<String> inputs = new ArrayList<String>();
	public static String[] xmlSections = {"Race","Class","Name","Stats","Attributes"};
	public static String[] statsList = {"Level","HP","Weight","Height"};
	
	
	public static void main(String[] args){
	
	
	}
}
