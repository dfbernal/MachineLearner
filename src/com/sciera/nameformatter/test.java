//package com.sciera.nameformatter;
//
//import java.io.BufferedReader;
//import java.io.DataInputStream;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//
//import org.apache.lucene.queryParser.ParseException;
//
//import com.sciera.analyzers.AnalyzerException;
//import com.sciera.analyzers.LuceneAnalyzer;
//import com.sciera.analyzers.NameAnalyzer;
//import com.sciera.utilities.Timer;
//
//public class test 
//{
//	public static void main(String args[]) throws IOException, ParseException, AnalyzerException, ClassNotFoundException, InstantiationException, IllegalAccessException
//	{
//		String file = "short.txt";
//		FileInputStream fstream = new FileInputStream(file);
//		
//		DataInputStream in = new DataInputStream(fstream);
//		BufferedReader br = new BufferedReader(new InputStreamReader(in));
//		
//		LuceneAnalyzer fi = new LuceneAnalyzer("data/First_Names2.txt");
//		LuceneAnalyzer la = new LuceneAnalyzer("data/Last_Names2.txt");
//		NameAnalyzer.initializeAnalyzers(fi, la);
//		
//		ArrayList<String> rules = new ArrayList<String>();
//		rules.add("rules/Strict.txt");
//		
//		ArrayList<String> goals = new ArrayList<String>();
//		goals.add("lastFirst");
//		goals.add("sizeIs1");
//		goals.add("firstName");
//		goals.add("neighbor0LastNameAt0");
//		goals.add("neighbor1LastNameAt0");
//		goals.add("neighborLastName");
//		
//		PreProcessor pre = new MaricopaSifter();
//		Sifter.initialize();
//		RulesClassifier.initialize(rules, goals);
//		
//		Timer t = new Timer();
//		
//		String strLine;
//		while ((strLine = br.readLine()) != null) {
//			ArrayList<NameString> names = new ArrayList<NameString>();
//			ArrayList<Marble> marbles = new ArrayList<Marble>();
//			
//	    	String[] temp1 = strLine.split("[|]");
//	    	//String property = temp1[0];
//	    	String strName = temp1[1];
//	    	
//	    	String[] temp2 = strName.split("(AND\b)|/|&");
//
//	    	
//	    	// Sets up a list of all the names identified on this line
//	    	for (int j = 0; j < temp2.length; j++)
//	    	{
//	    		NameString newName = new NameString(temp2[j]);
//	    		names.add(newName);
//	    	}
//		
//	    	for (NameString s : names)
//			{
//				marbles.add(new Marble(s));
//			}
//	    	
//			for (Marble m : marbles)
//			{
//				for (Marble nbr : marbles)
//				{
//					if (!m.equals(nbr))
//					{
//						m.addNeighbor(nbr);
//					}
//				}
//			}
//			
//			
//			t.start();
//			
//			pre.execute(marbles);
//			t.stop();
//			
//			//System.out.println(marbles.toString());
//			//System.out.println(t.timePeek("Milliseconds"));
//			
//			t.start();
//			
//			if (marbles.get(0).getPPLabel().equals("Name"))
//				RulesClassifier.execute(marbles);
//			
//			t.stop();
//			//System.out.println(marbles.toString());
//			//System.out.println(t.timePeek("Milliseconds"));
//			
//			for (Marble m : marbles)
//			{
//				if (m.getCLabel() != null)
//					System.out.println(Formatter.getFormat(m));
//			}
//		}
//		
//		br.close();
//	}
//}
