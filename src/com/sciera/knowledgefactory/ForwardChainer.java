package com.sciera.knowledgefactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import com.sciera.logicalobjects.Condition;

public class ForwardChainer 
{
	private ArrayList<Rule> ruleSet = new ArrayList<Rule>();
	private ArrayList<Condition> knowledgeBase = new ArrayList<Condition>();
	private Condition goal;
	
	public void addRule(Rule r)
	{
		ruleSet.add(r);
	}
	
	public void addKnowledge(Condition l)
	{
		knowledgeBase.add(l);
	}
	
	public ArrayList<Rule> forwardChain()
	{
		for (Rule rs : getStartingPoints())
		{
			ArrayList<Rule> temp = BFS(rs);
			if (temp != null)
				return temp;
		}
		
		return null;
	}
	
	private ArrayList<Rule> BFS(Rule start)
	{
		Queue<ArrayList<Rule>> queue = new LinkedList<ArrayList<Rule>>();
		queue.add(new ArrayList<Rule>(Arrays.asList(start)));

		while (!queue.isEmpty())
		{
			ArrayList<Rule> path = queue.poll();
			
			for (Condition l : path.get(path.size()-1).getConsequents())
			{
				if (isGoal(l))
				{
					return path;
				}
			}
			for (Rule r : ruleSet)
			{
				if (path.get(path.size()-1).isChild(r))
				{
					ArrayList<Rule> newPath = new ArrayList<Rule>(path);
					newPath.add(r);
					queue.add(newPath);
				}
			}
		}
		
		return null;
	}
	
	public void setGoal(Condition l)
	{
		goal = l;
	}
	
	private boolean isGoal(Condition l)
	{
		if (l.equals(goal))
			return true;
		
		return false;
	}
	
	private ArrayList<Rule> getStartingPoints()
	{
		ArrayList<Rule> startingPoints = new ArrayList<Rule>();
		
		for (Rule r : ruleSet)
		{
			boolean containsOne = false;
			int i = 0;
			ArrayList<Condition> kC = knowledgeBase;
			
			while (i < kC.size() && !containsOne)
			{
				boolean containsAll = true;
				int j = 0;
				ArrayList<Condition> ruleAntecedents = r.getAntecedents();
				
				while(j < ruleAntecedents.size() && containsAll)
				{
					if (!ruleAntecedents.get(j).getFailedMatches(kC.get(i)).isEmpty())
						containsAll = false;
					
					j++;
				}
				
				if (containsAll)
					containsOne = true;
				
				i++;
			}
			
			if (containsOne)
			{
				startingPoints.add(r);
			}
		}
		
		return startingPoints;
	}
}
