package com.sciera.nameformatter;

import com.sciera.logicalobjects.Frame;

public class test3 
{
	public static void main(String args[])
	{
		String s = "n:=Frame:()";
		String s2 = "Frame:()";
		
		String[] arr = s2.split(":=");
		
		if (arr.length == 1)
		{
			System.out.println("ok");
		}
		else
		{
			
		}
		
		for (String str : arr)
		{
			System.out.println(str);
		}
		
		Frame soc = new Frame("Man");
		soc.put("name", "Socrates");
		
		
	}
}
