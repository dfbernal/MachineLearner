package com.sciera.nameformatter;

import java.util.ArrayList;

public class NameString 
{
	private String[] formattedName = new String[6];
	private ArrayList<String> words;
	private boolean[] firstArray;
	private boolean[] lastArray;
	private boolean[] MIArray;
	private int index;
	
	public NameString()
	{
		words = new ArrayList<String>();
	}
	
	public NameString(String str)
	{
		this();
		
		str = str.replaceAll("[()]", "");
		
		String[] newWords = str.split("[ ]");
		
		for (int i = 0; i < newWords.length; i++)
		{
			if (!newWords[i].equals("") && !newWords[i].equals("AND") && !newWords[i].equals("OR")
					&& !newWords[i].equals("-") && !newWords[i].substring(0,1).equals("*") && !newWords[i].equals("+")
					&& !newWords[i].toUpperCase().equals("NOT"))
			{
				words.add(newWords[i].toLowerCase());
			}
		}
	}

	public String[] getFormat()
	{
		return formattedName;
	}
	
	public void setFormat(String[] format)
	{
		formattedName = format;
	}
	
	public void setIndex(int num)
	{
		index = num;
	}
	
	public int getIndex()
	{
		return index;
	}
	
	public ArrayList<String> getWords()
	{
		return words;
	}
	
	public boolean[] getFirstNameArray()
	{
		return firstArray;
	}

	public void setFirstNameArray(boolean[] firstArr)
	{
		firstArray = firstArr;
	}
	
	public boolean[] getLastNameArray()
	{
		return lastArray;
	}
	
	public void setLastNameArray(boolean[] lastArr)
	{
		lastArray = lastArr;
	}
	
	public boolean[] getMiddleInitialArray()
	{
		return MIArray;
	}
	
	public void setMiddleInitialAray(boolean[] miarr)
	{
		MIArray = miarr;
	}
	
	public String toString()
	{
		String str = "";
		
		str += words.toString();
//		str += siftType + "\n";
//		
//		for (int i = 0; i < formattedName.length; i++)
//		{
//			if (i == formattedName.length-1)
//			{
//				str += formattedName[i] + "\n";
//			}
//			else
//				str += formattedName[i] + "|";
//		}
		
		return str;
	}
}
