package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class ClassList {
	
	private ArrayList<Class> classlist = new ArrayList<Class>();
	private File listLoc;

	//Requires the location of the Class-list file
	public ClassList(File Class_list)
	{
		listLoc = Class_list;
	}

	//Builds the list of Classes
	public void buildList() throws IdConflictException, ParseException
	{
		Scanner in = null;
		//ArrayList<Class> rlist = new ArrayList<Class>();
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
					while(tmp.startsWith("c:") || tmp.startsWith("#"))
					{
						if(tmp.startsWith("#"))
						{
							tmp = in.nextLine().trim();
							lineNumber++;
							continue;
						}
						tmp = tmp.replaceAll("c:", "");
						if(tmp.startsWith("name"))
						{
							tmp = tmp.replaceFirst("name","").trim().replace("=", "").trim().replaceAll("\"", "");
							rname = tmp;
							if(!isIdUnique(id))
								throw new IdConflictException("Class ID conflict: " + id + "; " + rname + " and " + get(id).getName() + ". Line number " + (lineNumber-2));
							//System.out.printf("name = %s\n", rname);
						}
						else
							throw new ParseException("Unknown Parameter: " + line.replaceAll("r:", ""), lineNumber);
						if(in.hasNextLine())
							{tmp = in.nextLine().trim();lineNumber++;}
					}
					/*if(!isIdUnique(id))
						throw new IdConflictException("Class ID conflict: " + id + "; " + rname + " and " + get(id).getName() + ". Line number " + (lineNumber-5));*/
					classlist.add(new Class(id, rname));
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
		/*System.out.printf("Class list: \n\n");
		Iterator<Class> it = Classlist.iterator();
		while(it.hasNext())
		{it.next().printClass(); System.out.println();}*/

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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClassList r = null;
		Timer t = new Timer();
		t.start();
		r = new ClassList(new File("class-list.txt"));
		try {
			r.buildList();
		} catch (IdConflictException | ParseException e) {
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
