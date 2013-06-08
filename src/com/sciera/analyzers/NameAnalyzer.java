package com.sciera.analyzers;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.queryParser.ParseException;




public class NameAnalyzer
{
	public static ArrayList<String> newLastNames = new ArrayList<String>();
	public static ArrayList<String> newFirstNames = new ArrayList<String>();
	static LuceneAnalyzer firstNameAnalyzer;
	static LuceneAnalyzer lastNameAnalyzer;
	
	public static void initializeAnalyzers(LuceneAnalyzer fi, LuceneAnalyzer la)
	{
		firstNameAnalyzer = fi;
		lastNameAnalyzer = la;
	}
	
	public static String retrieveTitle(ArrayList<String> words)
	{
		for (int i = 0; i < words.size(); i++)
		{
			String w = words.get(i);
			if (w.equals("md"))
			{
				words.remove(w);
				return "md";
			}
			else if (w.equals("dr"))
			{
				words.remove(w);
				return "dr";
			}
			else if (w.equals("ii"))
			{
				words.remove(w);
				return "ii";
			}
			else if (w.equals("iii"))
			{
				words.remove(w);
				return "iii";
			}
			else if (w.equals("iv"))
			{
				words.remove(w);
				return "iv";
			}
		}
		
		return null;
	}
	
	public static String retrieveModifier(ArrayList<String> words)
	{
		for (int i = 0; i < words.size(); i++)
		{
			String w = words.get(i);
			if (w.equals("jr"))
			{
				words.remove(w);
				return "jr";
			}
			else if (w.equals("sr"))
			{
				words.remove(w);
				return "sr";
			}
		}
		
		return null;
	}
	
	/* Returns a boolean matrix indicating where a last name exists in a string array
	 * Ex. String [John][Smith][F]
	 * Returns: [1][0][0]
	 */
	public static boolean[] getFirstNameMatrix(ArrayList<String> words) throws IOException, ParseException, AnalyzerException
	{
		checkAnalyzers();
		
		boolean[] fnArr = new boolean[words.size()];
		
		for (String s : words)
		{
			fnArr[words.indexOf(s)] = firstNameAnalyzer.analyze(s) > 0;
		}
		
		return fnArr;
	}
	
	/* Returns a boolean matrix indicating where a last name exists in a string array
	 * Ex. String [John][Smith][F]
	 * Returns: [0][1][0]
	 */
	public static boolean[] getLastNameMatrix(ArrayList<String> words) throws IOException, ParseException, AnalyzerException
	{
		checkAnalyzers();
		
		boolean[] fnArr = new boolean[words.size()];
		
		for (String s : words)
		{
			fnArr[words.indexOf(s)] = lastNameAnalyzer.analyze(s) > 0;
		}
		
		return fnArr;
	}
	
	/* Returns a boolean matrix indicating where a middle initial exists in a string array
	 * Ex. String [John][Smith][F]
	 * Returns: [0][0][1]
	 */
	public static boolean[] getMiddleInitialMatrix(ArrayList<String> words) throws IOException, ParseException
	{
		boolean[] fnArr = new boolean[words.size()];
		
		for (String s : words)
		{
			fnArr[words.indexOf(s)] = (s.length() == 1);
		}
		
		return fnArr;
	}
	
	private static void checkAnalyzers() throws AnalyzerException
	{
		if (firstNameAnalyzer == null || lastNameAnalyzer == null)
			throw new AnalyzerException();
	}
}