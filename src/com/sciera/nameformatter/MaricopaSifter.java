package com.sciera.nameformatter;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.queryParser.ParseException;

import com.sciera.analyzers.AnalyzerException;

public class MaricopaSifter implements PreProcessor
{
	public void execute(ArrayList<Marble> names) throws IOException, ParseException, AnalyzerException
	{
		Sifter.sift(names, "firstNamePresence keyWordFind numericalFind downwardNeighborBias upwardNeighborBias");
		
		if (names.get(0).getPPLabel().equals("Neutral"))
			Sifter.sift(names, "middleNameBias keyWordAccurate vowelBias downwardNeighborBias dictionaryFind upwardNeighborBias");
		
		for (Marble n : names)
		{
			if (n.getPPLabel().equals("Neutral"))
				n.setPPLabel("Unknown");
			else if (n.getPPLabel().equals("Unknown"))
				n.setPPLabel("Neutral");
		}
	}
}
