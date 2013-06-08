package com.sciera.utilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.sciera.logicalobjects.Frame;
import com.sciera.logicalobjects.Frameable;

@SuppressWarnings("serial")
public class FrameList extends ArrayList<Frame> implements Frameable
{
	public FrameList()
	{
		super();
	}
	
	public FrameList(FrameList fl)
	{
		super(fl);
	}
	
	public boolean containsPartialMatch(Frame f)
	{
		for (Frame curr : this)
		{
			if (curr.removeAllValues(f).isEmpty())
				return true;
		}
		
		return false;
	}
	
	public boolean containsAllPartialMatches(FrameList fl)
	{
		FrameList list1 = new FrameList(this);
		FrameList list2 = new FrameList(fl);
		
		boolean allFound = true;
		int i = 0;
		
		while (i < list1.size() && allFound)
		{
			int j = 0;
			boolean found = false;
			
			while (j < list2.size() && found == false)
			{
				if (list1.get(i).removeAllValues(list2.get(j)).isEmpty())
				{
					found = true;
					list2.remove(list2.get(j));
				}
				
				j++;
			}
			
			if (!found)
				allFound = false;
			
			i++;
		}
		
		return allFound;
	}
	
	public boolean equals(FrameList fl)
	{
		ArrayList<Frame> a = new ArrayList<Frame>(this);
		ArrayList<Frame> b = new ArrayList<Frame>(fl);
		ArrayList<Frame> temp = new ArrayList<Frame>(this);
		
		a.removeAll(b);
		b.removeAll(temp);
		
		return a.isEmpty() && b.isEmpty();
	}
	
	public String toString()
	{
		Set<Object> references = new HashSet<Object>();
		return toStringHelper(this, references);
	}

	public String toStringHelper(Frameable f, Set<Object> references) {
		String ret = "List[";
		
		if (!references.contains(f))
		{
			references.add(f);
			for (Frame fr : this)
			{
				if (this.indexOf(fr) < this.size()-1)
					ret += fr.toStringHelper(fr, references) + " ";
				else
					ret += fr.toStringHelper(fr, references);
			}
		}
		
		return ret +"]";
	}
}
