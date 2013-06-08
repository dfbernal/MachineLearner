package com.sciera.logicalobjects;

public class StringFrame extends Frame
{
	String str;
	
	public StringFrame(String value) {
		super("n/a");
		
		str = value;
	}
	
	public StringFrame(String frameName, String value)
	{
		super(frameName);
		
		str = value;
	}
	
	public String getStringDescriptor()
	{
		return str;
	}
	
	public boolean equals(Object o)
	{
		StringFrame s = (StringFrame) o;
		return super.equals(s) && this.str.equals(s.str);
	}
	
	public boolean equals(Frame o)
	{
		StringFrame s = (StringFrame) o;
		return super.equals(s) && this.str.equals(s.str);
	}

	public String toString()
	{
		return super.getName() + "{" + str +"}";
	}
}
