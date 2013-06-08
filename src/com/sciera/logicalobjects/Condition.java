package com.sciera.logicalobjects;

import java.util.Map;

public abstract class Condition
{
	protected Frame descriptor;
	private boolean condition;
	
	public Condition(Frame descriptor, boolean condition)
	{
		this.descriptor = descriptor;
		this.condition = condition;
	}

	public Frame getDescriptor()
	{
		return descriptor;
	}
	
	public boolean getCondition()
	{
		return condition;
	}
	
	public boolean equals(Object o)
	{
		Condition comp = (Condition) o;
		
		return this.descriptor.equals(comp.getDescriptor()) && this.condition == comp.getCondition();
	}
	
	public Map<String, Object> getFailedMatches(Condition comp)
	{
		if (this.condition == comp.getCondition())
			return this.descriptor.removeAllValues(comp.getDescriptor());
		
		return null;
	}
	
	public String toString()
	{
		String temp = "";
		
		temp += "Descriptor: " + descriptor.toString() + " Condition: " + condition;
		
		return temp;
	}
}
