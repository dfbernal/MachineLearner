package com.sciera.knowledgefactory;

import java.util.ArrayList;
import java.util.Iterator;

import com.sciera.logicalobjects.Condition;

public class Rule 
{
	String name;
	private KnowledgeBase kb;
	private ArrayList<Condition> antecedents = new ArrayList<Condition>();
	private ArrayList<Condition> consequents = new ArrayList<Condition>();
	
	public Rule(Rule r)
	{
		this.name = r.getName();
		this.antecedents = r.getAntecedents();
		this.consequents = r.getConsequents();
		this.kb = r.kb;
	}
	
	public Rule(String id)
	{
		this.name = id;
	}
	
	public void setKnowledgeBase(KnowledgeBase kb)
	{
		this.kb = kb;
	}
	
	public void addAntecedent(Condition antecedent)
	{
		antecedents.add(antecedent);
	}
	
	public void addConsequent(Condition consequent)
	{
		consequents.add(consequent);
	}
	
	public boolean isChild(Rule childRule)
	{
		ArrayList<Condition> parentConditions = new ArrayList<Condition>(this.consequents);
		ArrayList<Condition> childConditions = new ArrayList<Condition>(childRule.getAntecedents());

		if (kb != null)
		{
			for (Condition c : kb.getKnowledgeBase())
			{
				Iterator<?> itr = childConditions.iterator();
				
				while(itr.hasNext())
				{
					Condition childC = (Condition) itr.next();
					
					if (c.getFailedMatches(childC).isEmpty())
					{
						childConditions.remove(childC);
					}
				}
			}
		}
		
		// The rule fires directly from the KB therefore the rule is not a child
		if (childConditions.isEmpty())
			return false;
		
		for (Condition c : childConditions)
		{
			Iterator<?> itr = parentConditions.iterator();
			
			while(itr.hasNext())
			{
				Condition parentC = (Condition) itr.next();
				
				if (c.getFailedMatches(parentC).isEmpty())
				{
					parentConditions.remove(parentC);
				}
			}
		}
		
		return parentConditions.isEmpty();
	}
	
	public boolean isParent(Rule parentRule)
	{
		ArrayList<Condition> parentConditions = new ArrayList<Condition>(parentRule.getConsequents());
		ArrayList<Condition> childConditions = new ArrayList<Condition>(this.getAntecedents());
		
		if (kb != null)
		{
			for (Condition c : kb.getKnowledgeBase())
			{
				ArrayList<Condition> temp = new ArrayList<Condition>();
				Iterator<?> itr = childConditions.iterator();
				
				while(itr.hasNext())
				{
					Condition childC = (Condition) itr.next();
					
					if (c.getFailedMatches(childC).isEmpty())
					{
						temp.add(childC);
					}
				}
				
				childConditions.removeAll(temp);
			}
		}
		
		// The rule fires directly from the KB therefore the rule is not a child
		if (childConditions.isEmpty())
			return false;
		
		for (Condition c : childConditions)
		{
			ArrayList<Condition> temp = new ArrayList<Condition>();
			Iterator<?> itr = parentConditions.iterator();
			
			while(itr.hasNext())
			{
				Condition parentC = (Condition) itr.next();
				
				if (c.getFailedMatches(parentC).isEmpty())
				{
					temp.add(parentC);
				}
			}
			
			parentConditions.removeAll(temp);
		}
		
		return parentConditions.isEmpty();
	}

	public ArrayList<Condition> getAntecedents()
	{
		return antecedents;
	}
	
	public ArrayList<Condition> getConsequents()
	{
		return consequents;
	}
	
	public String getName()
	{
		return name;
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
		
		return s;
	}
}
