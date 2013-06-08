package com.sciera.knowledgefactory;

import java.util.ArrayList;

import com.sciera.logicalobjects.Condition;
import com.sciera.logicalobjects.GoalCondition;

public class KnowledgeFactory {
		private ArrayList<Rule> rules = new ArrayList<Rule>();
		private KnowledgeBase kb;
		private ArrayList<GoalCondition> goals;
		
		public KnowledgeFactory(KnowledgeBase kb)
		{
			this.kb = kb;
		}
		
		public void addRule(Rule rule)
		{
			rules.add(rule);
		}
		
		public void setKnowledgeBase(KnowledgeBase kb)
		{
			this.kb = kb;
		}
		
		public KnowledgeBase getKnowledgeBase()
		{
			return kb;
		}

		public void setGoals(ArrayList<GoalCondition> goals)
		{
			this.goals = goals;
		}
		
		public ArrayList<Rule> getRulePath()
		{
			ForwardChainer fc = new ForwardChainer();
			ArrayList<Condition> tempGoals = new ArrayList<Condition>(goals);
			
			for (Condition l : kb.getKnowledgeBase())
			{
				fc.addKnowledge(l);
			}
			
			for (Rule r : rules)
			{
				fc.addRule(r);
			}
			
			boolean found = false;
			ArrayList<Rule> path = null;
			
			while (!found && !tempGoals.isEmpty())
			{
				fc.setGoal(tempGoals.get(0));
				path = fc.forwardChain();
				
				if (path != null)
					found = true;
				
				tempGoals.remove(tempGoals.get(0));
			}
			
			return path;
		}
}
