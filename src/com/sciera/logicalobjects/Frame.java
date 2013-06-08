package com.sciera.logicalobjects;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.sciera.utilities.FrameList;

public class Frame implements Frameable
{
	private String frameName;
	private Map<String, Object> frameKnowledge = new HashMap<String, Object>();
	
	public Frame(String frameName)
	{
		this.frameName = frameName;
	}

	public Map<String, Object> getFrameKnowledge() {
		return frameKnowledge;
	}

	public void put(String slot, Object value) {
		frameKnowledge.put(slot, value);
	}
	
	public String getName()
	{
		return frameName;
	}
	
	public Object getValue(String slot)
	{
		return frameKnowledge.get(slot);
	}
	
	public Map<String, Object> removeAllValues(Frame b)
	{
		Set<Object> references = new HashSet<Object>();
		return removeAllValuesHelper(this,b,references);
	}
	
	private Map<String, Object> removeAllValuesHelper(Frame a, Frame b, Set<Object> references) {
		Map<String, Object> joinedKB = new HashMap<String, Object>();
		
	    Collection<?> c1 = a.getFrameKnowledge().keySet();	    
	    
	    Iterator<?> itr = c1.iterator();
	    
	    while (itr.hasNext())
	    {
	    	String key = (String) itr.next();
	    	Object leftValue = a.getFrameKnowledge().get(key);
			Object rightValue = b.getFrameKnowledge().get(key);
			
			if (rightValue == null)
				joinedKB.put(key, leftValue);
			else
			{
		    	if (!references.contains(a.getFrameKnowledge().get(key)))
				{
	    			references.add(a);
			    	if (leftValue instanceof Frame && rightValue instanceof Frame)
			    	{
			    		if (!((Frame)leftValue).removeAllValuesHelper((Frame)leftValue, (Frame)rightValue, references).isEmpty()){
			    			joinedKB.put(key, leftValue);
			    		}
			    	}
			    	else if (leftValue instanceof FrameList && rightValue instanceof FrameList)
			    	{
			    		if (!((FrameList)leftValue).containsAllPartialMatches((FrameList)rightValue))
			    			joinedKB.put(key, leftValue);
			    	}
			    	else if (leftValue instanceof Frame && rightValue instanceof FrameList)
			    	{
			    		FrameList leftList = new FrameList();
			    		leftList.add((Frame)leftValue);
			    		if (!leftList.containsAllPartialMatches((FrameList)rightValue))
			    			joinedKB.put(key, leftValue);
			    	}
			    	else if (leftValue instanceof FrameList && rightValue instanceof Frame)
			    	{
			    		FrameList rightList = new FrameList();
			    		rightList.add((Frame)rightValue);
			    		if (!((FrameList)leftValue).containsAllPartialMatches(rightList))
			    			joinedKB.put(key, leftValue);
			    	}
					else
					{
						if (!leftValue.equals(rightValue)){
							joinedKB.put(key, leftValue);
						}
					}
				}
			}
	    }
	    
	    return joinedKB;
	}
	
	public boolean equals(Frame b)
	{
		Set<Object> references = new HashSet<Object>();
		
		return equalsHelper(this,b,references);
	}
	
	private boolean equalsHelper(Frame a, Frame b, Set<Object> references)
	{
		// Checks if the frame objects are of the same type
		if (a.getName().equals(b.getName()))
		{
			/* Now the goal is to compare all the slots and their values and see if the two frames have the same
			 * slot-value pairs.
			 */
			boolean allValuesEqual = true;
			/*
		      Get Collection of values contained in HashMap
		    */
		    Collection<?> c = a.getFrameKnowledge().keySet();
		    Collection<?> cComp = b.getFrameKnowledge().keySet();
		    
		    if (c.containsAll(cComp) && cComp.containsAll(c))
		    {
		    	//Obtain an Iterator for Collection
			    Iterator<?> itr = c.iterator();
			   
			    // For each of the values in the frame knowledge iterate through them checking for equality
			    while(itr.hasNext() && allValuesEqual)
			    {
		    		String key = (String) itr.next();
		    		if (!references.contains(a.getFrameKnowledge().get(key)))
					{
		    			references.add(a);
		    			// Create instance objects so that java is able to interpret the polymorphic equals method correctly
		    			Object o = a.getFrameKnowledge().get(key);
		    			Object o2 = b.getFrameKnowledge().get(key);
		    			
		    			if (o instanceof Frame && o2 instanceof Frame)
		    				allValuesEqual = equalsHelper((Frame)o, (Frame)o2, references);
		    			else
		    				allValuesEqual = o.equals(o2);
					}
			    }
		    }
		    else
		    {
		    	allValuesEqual = false;
		    }

		    return allValuesEqual;
		}

		return false;
	}
		
	public String toString()
	{
		Set<Object> tempReferences = new HashSet<Object>();
		return toStringHelper(this, tempReferences);
	}

	public String toStringHelper(Frameable f, Set<Object> references) {
		Frame a = (Frame) f;
		Collection<?> c = a.getFrameKnowledge().keySet();
		String temp = a.getName() + "{";
		//Obtain an Iterator for Collection
	    Iterator<?> itr = c.iterator();
	   
	    // For each of the values in the frame knowledge iterate through them checking for equality
	    while(itr.hasNext())
	    {
    		String key = (String) itr.next();
    		Object leftValue = a.getFrameKnowledge().get(key);

    		if (itr.hasNext())
    		{
	    		if (!references.contains(a.getFrameKnowledge().get(key)))
				{
	    			references.add(a);
	    			
	    			if (a.getFrameKnowledge().get(key) instanceof Frameable)
	    				temp += "Slot:" + key + " Value:" + 
	    						((Frameable) leftValue).toStringHelper((Frameable)leftValue, references) + ",";
	    			else
	    				temp += "Slot:" + key + " Value:" + a.getFrameKnowledge().get(key) + ",";
				}
    		}
    		else
	    	{
	    		if (!references.contains(a.getFrameKnowledge().get(key)))
				{
	    			references.add(a);
	    			
	    			if (a.getFrameKnowledge().get(key) instanceof Frameable)
	    				temp += "Slot:" + key + " Value:" + ((Frameable) leftValue).toStringHelper((Frameable)leftValue, references);
	    			else
	    				temp += "Slot:" + key + " Value:" + a.getFrameKnowledge().get(key);
				}
	    	}
	    }
		
		return temp + "}";
	}
}
