package com.sciera.knowledgefactory;

import java.util.HashMap;
import java.util.Map;

import com.sciera.logicalobjects.Condition;

public class UpdateRule extends Rule
{
	private Map<Condition, String> variableMap = new HashMap<Condition, String>();
	
	public UpdateRule(Rule r)
	{
		super(r);
	}
	
	public UpdateRule(String id)
	{
		super(id);
	}
	
	public void putVariable(Condition key, String variable)
	{
		variableMap.put(key, variable);
	}
	
	public String toString()
	{
		String s = "";
		
		s += name + "\n";
		
		for (Condition a : getAntecedents())
		{
			s += "Antecedents: " + a.toString() + "\n";
		}
		
		for (Condition a : getConsequents())
		{
			s += "Consequents: " + a.toString() + "\n";
		}
		
		s += "Saved Variables: ";
		
		for (Condition a : getAntecedents())
		{
			s += variableMap.get(a) + "\n";
		}
		
		return s;
	}
}
