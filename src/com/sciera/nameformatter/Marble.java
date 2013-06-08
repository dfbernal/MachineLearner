package com.sciera.nameformatter;

import java.util.ArrayList;

public class Marble
{
	private ArrayList<Marble> neighbors = new ArrayList<Marble>();
	private String preProcessorLabel;
	private String classifierLabel;
	private String postProcessorLabel;
	private NameString descriptor;

	public Marble(NameString n)
	{
		descriptor = n;
	}
	
	public void addNeighbor(Marble nbr)
	{
		neighbors.add(nbr);
	}
	
	public ArrayList<Marble> getNeighbors()
	{
		return neighbors;
	}
	
	public void setDescriptor(NameString n)
	{
		descriptor = n;
	}
	
	public NameString getDescriptor()
	{
		return descriptor;
	}
	
	public void setPPLabel(String s)
	{
		preProcessorLabel = s;
	}
	
	public void setCLabel(String s)
	{
		classifierLabel = s;
	}
	
	public String getPPLabel()
	{
		return preProcessorLabel;
	}
	
	public String getCLabel()
	{
		return classifierLabel;
	}
	
	public void setPostLabel(String s)
	{
		postProcessorLabel = s;
	}
	
	public String getPostPLabel()
	{
		return postProcessorLabel;
	}
	
	public String toString()
	{
		return preProcessorLabel + " " + classifierLabel + " " + postProcessorLabel;
	}
}
