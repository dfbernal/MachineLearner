//package com.sciera.nameformatter;
//import java.io.IOException;
//import java.util.ArrayList;
//
//import org.apache.lucene.queryParser.ParseException;
//
//import com.sciera.analyzers.AnalyzerException;
//
//public class RulesClassifier
//{
//	static int numFiles = 0;
//	
//	public static void initialize(ArrayList<String> rules, ArrayList<String> goals) throws IOException
//	{
//		KnowledgeEngine.initializeFactories(rules);
//		numFiles = rules.size();
//		KnowledgeEngine.setGoals(goals);
//	}
//	
//	public static void execute(ArrayList<Marble> marbles) throws IOException, ParseException, AnalyzerException, ClassNotFoundException, InstantiationException, IllegalAccessException
//	{
//		ArrayList<NameString> names = new ArrayList<NameString>();
//		
//		for (Marble m : marbles)
//		{
//			names.add(m.getDescriptor());
//		}
//		
//		// Set up knowledge frames inside the knowledge engine
//	    KnowledgeEngine.initiateKnowledge(names);
//	    
//		for (Marble m : marbles)
//		{
//			int fIndex = 0;
//    		boolean found = false;
//	    	String formatFound = null;
//	    	while (!found && fIndex < numFiles)
//	    	{
//	    		KnowledgeEngine.switchFactory(fIndex);
//	    		formatFound = KnowledgeEngine.fireRules(m.getDescriptor());
//	    		
//	    		if (formatFound != null)
//	    			found = true;
//
//	    		fIndex++;
//	    	}
//	    	
//	    	m.setCLabel(formatFound);
//		}
//	}
//}
