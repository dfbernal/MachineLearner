package com.sciera.nameformatter;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.lucene.queryParser.ParseException;

import com.sciera.analyzers.AnalyzerException;
import com.sciera.analyzers.LuceneAnalyzer;

public class Sifter 
{
	ResultSet results;
	Statement stmt;
	private static LuceneAnalyzer fAnlzr;
	private static LuceneAnalyzer dicAnlzr;
	private static LuceneAnalyzer dnstyAnlzr;
	private static LuceneAnalyzer trustAnlzr;
	private static LuceneAnalyzer keyWordAnalyzer;
	private static ArrayList<String> triggers;
	
	public static void initialize() throws IOException
	{
		fAnlzr  = new LuceneAnalyzer("data/First_Names2.txt");
		dicAnlzr  = new LuceneAnalyzer("data/corncob_lowercase.txt");
		dnstyAnlzr  = new LuceneAnalyzer("data/FirstAndLastNames.txt");
		trustAnlzr  = new LuceneAnalyzer("data/TrustTriggers.txt");
		keyWordAnalyzer  = new LuceneAnalyzer("data/NoNameTriggers.txt");
		
		FileInputStream triggerFile  = new FileInputStream("data/NoNameTriggers.txt");
		triggers = new ArrayList<String>();
		
		// Get the object of DataInputStream
		DataInputStream in = new DataInputStream(triggerFile);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    
		String strLine;

		while ((strLine = br.readLine()) != null)   {
			triggers.add(strLine);
		}
		
		br.close();
	}
	
	/* This function takes in an array of names and commands, it then executes those commands on the names and modifies
	 * the SiftType variable of the name object.
	 * Parameters: Name to be modified, Commands to be executed
	 * Results: SiftType variable is modified.
	 */
	public static void sift(ArrayList<Marble> names, String siftCommand) throws IOException, ParseException, AnalyzerException
	{
		checkAnalyzers();
		
		String[] commands = siftCommand.split("[ ]");
		
		for (Marble n : names)
		{
			if (n.getPPLabel() == null)
			{
				n.setPPLabel("Neutral");
			}
		}

		// For each of the commands given
		for (int i = 0; i < commands.length; i++)
		{
			// For each of the names given
			for (Marble n : names)
			{
				// Execute First Name Presence
				if (commands[i].equals("firstNamePresence"))
				{
					firstNamePresenceSift(n);
				}
				// Execute Name Density
				else if (commands[i].equals("nameDensity"))
				{
					nameDensitySift(n);
				}
				// Execute Keyword Finder
				else if (commands[i].equals("keyWordFind"))
				{
					keyWordPresenceSift(n);
				}
				// Execute Trust Bias
				else if (commands[i].equals("trustBias"))
				{
					trustBias(n);
				}
				// Execute Keyword Accurate
				else if (commands[i].equals("keyWordAccurate"))
				{
					keyWordAccurate(n);
				}
				// Execute Upward Neighbor Bias
				else if (commands[i].equals("upwardNeighborBias"))
				{
					upwardNeighborBias(n);
				} 
				// Execute Downward Neighbor Bias
				else if (commands[i].equals("downwardNeighborBias"))
				{
					downwardNeighborBias(n);
				}
				// Execute Middle Name Bias
				else if (commands[i].equals("middleNameBias"))
				{
					middleNameBias(n);
				}
				// Execute Numerical Find
				else if (commands[i].equals("numericalFind"))
				{
					numericalFind(n);
				} 
				// Execute Vowel Bias
				else if (commands[i].equals("vowelBias"))
				{
					vowelBias(n);
				}
				// Execute Dictionary Find
				else if (commands[i].equals("dictionaryFind"))
				{
					dictionaryFind(n);
				} 
				// The command was not found in this list
				else
					System.out.println("Command not found");
			}
		}
	}
	
	private static void downwardNeighborBias(Marble n) throws IOException, ParseException
	{
		boolean found = false;
		
		for (Marble nbr : n.getNeighbors())
    	{
    		if (nbr.getPPLabel().equals("Unknown"))
    		{
    			found = true;
    		}
    	}
		
		if (found)
		{
			n.setPPLabel("Unknown");
		}
	}
	
	private static void upwardNeighborBias(Marble n) throws IOException, ParseException
	{
		boolean found = false;
		
		for (Marble nbr : n.getNeighbors())
    	{
    		if (nbr.getPPLabel().equals("Name"))
    		{
    			found = true;
    		}
    	}
		
		if (found)
		{
			n.setPPLabel("Name");;
		}
	}
	
	private static void keyWordPresenceSift(Marble n) throws IOException, ParseException
	{
		boolean found = false;
		
		for (String w : n.getDescriptor().getWords())
    	{
			if (w.length() > 1)
			{
	    		if (keyWordAnalyzer.analyze(w) > 0)
	    		{
	    			found = true;
	    		}
			}
    	}
		
		if (found)
		{
			n.setPPLabel("Unknown");
		}
	}
	
	private static void trustBias(Marble n) throws IOException, ParseException
	{
		boolean found = false;
		
		for (String w : n.getDescriptor().getWords())
    	{
			if (w.length() > 1)
			{
	    		if (trustAnlzr.analyze(w) > 0)
	    		{
	    			found = true;
	    		}
			}
    	}
		
		if (found)
		{
			n.setPPLabel("Name");;
		}
		else
		{
			n.setPPLabel("Unknown");
		}
	}
	
	private static void keyWordAccurate(Marble n) throws IOException, ParseException
	{		
		String w = "";
    	for (int i = 0; i < n.getDescriptor().getWords().size(); i++)
    	{
    		if (i == n.getDescriptor().getWords().size()-1)
    			w += n.getDescriptor().getWords().get(i);
    		else
    			w += n.getDescriptor().getWords().get(i) + " ";
    	}
    	
    	boolean found = false;
    	
    	for (String s : triggers)
    	{
    		if (w.matches(".*\\s(" + s + ")\\n*\\s*"))
    		{
    			found = true;
    		}
    	}
    	
    	if (found)
		{
			n.setPPLabel("Unknown");
		}
	}
	
	private static void firstNamePresenceSift(Marble n) throws IOException, ParseException
	{	 
    	// If the name has only one word then perform a check on that word
    	if (n.getDescriptor().getWords().size() == 0)
    	{
    		n.setPPLabel("Unknown");
    	}
    	else if (n.getDescriptor().getWords().size() == 1)
    	{
    		String w = n.getDescriptor().getWords().get(0);
    		if (fAnlzr.analyze(w) > 0)
    		{
    			n.setPPLabel("Name");;
    			//System.out.println(n);
    		}
    		else
    			n.setPPLabel("Neutral");
    	}
    	// Otherwise perform the check on the second word of the name
    	else
    	{
    		String w = n.getDescriptor().getWords().get(1);
    		
    		if (fAnlzr.analyze(w) > 0)
    		{
    			n.setPPLabel("Name");;
    			//System.out.println(n);
    		}
    		else
    			n.setPPLabel("Neutral");
    	}
	}
	
	private static void middleNameBias(Marble n) throws IOException, ParseException
	{
		boolean found = false;
		boolean all = true;
		
		ArrayList<String> words = n.getDescriptor().getWords();
		for (String s : words)
		{
			if ((s.length() > 2))
			{
				all = false;
			}
		}
		
		if (words.size() > 1 && !all)
		{
			String w = words.get(words.size()-1);
			if (w.length() == 1 && w.matches("[\\w]") && !w.matches("[\\d]"))
			{
				found = true;
			}
		}
		
		if (found)
		{
			n.setPPLabel("Name");;
		}
		else if (all)
		{
			n.setPPLabel("Unknown");
		}
	}
	
	private static void vowelBias(Marble n) throws IOException, ParseException
	{
		boolean found = false;
		
		ArrayList<String> words = n.getDescriptor().getWords();
		if (words.size() > 0)
		{
			for (String w : words)
			{
				if (w.length() > 1 && !w.matches(".*[aeiou]+.*") && !w.equalsIgnoreCase("JR") 
						&& !w.equalsIgnoreCase("SR") && !w.equalsIgnoreCase("MD")
						&& !w.equalsIgnoreCase("DR"))
				{
					found = true;
				}
			}
		}
		
		if (found)
		{
			n.setPPLabel("Unknown");
		}
	}
	
	private static void numericalFind(Marble n) throws IOException, ParseException
	{
		boolean found = false;
		
		ArrayList<String> words = n.getDescriptor().getWords();
		if (words.size() > 0)
		{
			for (String w : words)
			{
				if (w.matches(".*\\d+.*"))
				{
					found = true;
				}
			}
		}
		
		if (found)
		{
			n.setPPLabel("Unknown");
		}
	}
	
	private static void dictionaryFind(Marble n) throws IOException, ParseException
	{
		boolean found = false;
		
		for (String w : n.getDescriptor().getWords())
		{
			if (dicAnlzr.analyze(w) > 0)
			{
				found = true;
			}
		}
		
		if (found)
		{
			n.setPPLabel("Unknown");
		}
	}
	
	private static void nameDensitySift(Marble n) throws IOException, ParseException
	{
		double totalWords = n.getDescriptor().getWords().size();
		double totalHit = 0;
		double result = 0;
		
    	for (String w : n.getDescriptor().getWords())
    	{
    		if (w.length() > 1)
    		{
    			if (dnstyAnlzr.analyze(w) > 0)
	    		{
	    			totalHit++;
	    		}
    		}
    	}
    	
    	result = totalHit/totalWords;
    	
    	if (result > .9)
    	{
    		n.setPPLabel("Name");;
    	}
    	else if (result > .65)
    	{
    		n.setPPLabel("Neutral");
    	}
    	else
    	{
    		n.setPPLabel("Unknown");
    	}
	}
	
	private static void checkAnalyzers() throws AnalyzerException
	{
		if (fAnlzr == null || dicAnlzr == null || dnstyAnlzr == null || trustAnlzr == null || triggers == null)
			throw new AnalyzerException();
	}
}