package com.sciera.analyzers;

@SuppressWarnings("serial")
public class AnalyzerException extends Exception
{
	public String getMessage()
	{
		return "Analyzer not initialized";
	}
}