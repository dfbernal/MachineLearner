package com.sciera.logicalobjects;


public class GoalCondition extends Condition
{
	public GoalCondition(StringFrame descriptor, boolean condition) {
		super(descriptor, condition);
	}
	
	public StringFrame getDescriptor()
	{
		return (StringFrame) descriptor;
	}
	
	public boolean equals(Object o)
	{
		GoalCondition comp = (GoalCondition) o;
		
		return this.equals(comp);
	}
	
	public boolean equals(GoalCondition g)
	{
		return (this.getDescriptor()).equals(g.getDescriptor());
	}
}
