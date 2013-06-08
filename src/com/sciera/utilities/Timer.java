package com.sciera.utilities;

public class Timer 
{
	long start;
	long end;
	
	public void start()
	{
		start = System.currentTimeMillis();
	}
	
	public void stop()
	{
		end = System.currentTimeMillis();
	}
	
	public void clear()
	{
		start = 0;
		end = 0;
	}
	
	public String timePeek(String timeSpecifier)
	{
		double total = (double)end - (double)start;
		
		if (timeSpecifier.equals("Hours"))
		{
			return String.valueOf(total/3600*60);
		}
		else if (timeSpecifier.equals("Minutes"))
		{
			return String.valueOf(total/3600);
		}
		else if (timeSpecifier.equals("Seconds"))
		{
			return String.valueOf(total/1000);
		}
		else if (timeSpecifier.equals("Milliseconds"))
		{
			return String.valueOf(total);
		}
		
		return null;
	}
}
