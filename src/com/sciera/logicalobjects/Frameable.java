package com.sciera.logicalobjects;

import java.util.Set;

public interface Frameable 
{
	public String toStringHelper(Frameable f, Set<Object> references);
}
