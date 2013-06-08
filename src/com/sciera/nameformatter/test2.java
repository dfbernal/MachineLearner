//package com.sciera.nameformatter;
//
//import com.sciera.logicalobjects.Frame;
//import com.sciera.utilities.FrameList;
//
//public class test2 
//{
//	public static void main(String args[])
//	{
//		Frame f1 = new Frame("Dog");
//		Frame f2 = new Frame("Dog");
//		Frame f3 = new Frame("Owner");
//		Frame f4 = new Frame("Owner");
//		
//		f1.addFrameKnowledge("legs", 4);
//		f2.addFrameKnowledge("legs", 4);
//		f1.addFrameKnowledge("owner", f3);
//		f2.addFrameKnowledge("owner", f4);
//		f3.addFrameKnowledge("hasDog", f1);
//		//f3.addFrameKnowledge("job", "salesman");
//		f4.addFrameKnowledge("hasDog", f2);
//		
//		System.out.println(f1.equals(f2));
//		
//		FrameList t1 = new FrameList();
//		t1.add(f1);
//		t1.add(new Frame("Cat"));
//		t1.add(new Frame("Sandwhich"));
//		
//		FrameList t2 = new FrameList();
//		t2.add(f2);
//		t2.add(new Frame("Cat"));
//		t2.add(new Frame("Sandwhich"));
//		
//		System.out.println(t1.containsAllPartialMatches(t2));
//	}
//}
