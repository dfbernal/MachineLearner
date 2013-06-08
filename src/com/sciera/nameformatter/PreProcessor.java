package com.sciera.nameformatter;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.queryParser.ParseException;

import com.sciera.analyzers.AnalyzerException;

public interface PreProcessor 
{
	public void execute(ArrayList<Marble> marbles) throws IOException, ParseException, AnalyzerException;
}
