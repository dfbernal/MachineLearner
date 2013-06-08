package com.sciera.nameformatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Formatter 
{
	public static String getFormat(Marble m)
	{
		String label = m.getCLabel();
		String format = "";
		ArrayList<String> words = m.getDescriptor().getWords();
		Map<Integer, ArrayList<String>> nbrWords = new HashMap<Integer, ArrayList<String>>();
		
		for (Marble nbr : m.getNeighbors())
		{
			nbrWords.put(nbr.getDescriptor().getIndex(), nbr.getDescriptor().getWords());
		}
		
		switch (label)
		{
			case "lastFirst" : format = words.get(0) + "|" + words.get(1);
				break;
			case "neighbor0LastNameAt0" : format = nbrWords.get(0).get(0) + "|" + words.get(0);
				break;
		}
		
		return format;
	}
}
