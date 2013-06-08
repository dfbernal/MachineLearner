package com.sciera.knowledgefactory;

import java.util.ArrayList;

import com.sciera.logicalobjects.FrameCondition;

public class KnowledgeBase
{
	private ArrayList<FrameCondition> kb = new ArrayList<FrameCondition>();
	
	public void insert(FrameCondition knowledge)
	{
		kb.add(knowledge);
	}
	
	public ArrayList<FrameCondition> getKnowledgeBase()
	{
		return kb;
	}
}

