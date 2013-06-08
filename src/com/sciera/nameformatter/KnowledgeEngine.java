//package com.sciera.nameformatter;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.lucene.queryParser.ParseException;
//
//import com.sciera.analyzers.AnalyzerException;
//import com.sciera.analyzers.NameAnalyzer;
//import com.sciera.knowledgefactory.KnowledgeBase;
//import com.sciera.knowledgefactory.KnowledgeFactory;
//import com.sciera.knowledgefactory.Rule;
//import com.sciera.knowledgefactory.RuleInterpreter;
//import com.sciera.logicalobjects.Condition;
//import com.sciera.logicalobjects.Frame;
//import com.sciera.logicalobjects.FrameCondition;
//import com.sciera.logicalobjects.GoalCondition;
//import com.sciera.logicalobjects.StringFrame;
//import com.sciera.utilities.FrameList;
//
//public class KnowledgeEngine {
//	private static Map<NameString, FrameCondition> allFrames = new HashMap<NameString, FrameCondition>();
//	private static ArrayList<GoalCondition> goals = new ArrayList<GoalCondition>();
//	private static ArrayList<KnowledgeFactory> factories = new ArrayList<KnowledgeFactory>();
//	private static KnowledgeFactory currentFactory;
//	private static KnowledgeBase kBase;
//	
//	public static void initializeFactories(ArrayList<String> files) throws IOException
//	{
//		kBase = new KnowledgeBase();
//		
//		for (String s : files)
//		{
//			KnowledgeFactory kFact = new KnowledgeFactory(kBase);
//			kFact.setGoals(goals);
//			RuleInterpreter.initializeFactory(kFact, s);
//			factories.add(kFact);
//		}
//	}
//	
//	public static void switchFactory(int key)
//	{
//		currentFactory = factories.get(key);
//	}
//	
//	public static void setGoals(ArrayList<String> goalStr)
//	{
//		for (String s : goalStr)
//		{
//			StringFrame pmString = new StringFrame("Goal", s);
//			GoalCondition g = new GoalCondition(pmString, true);
//			
//			goals.add(g);
//		}
//	}
//	
//	public static void initiateKnowledge(ArrayList<NameString> names) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, ParseException, AnalyzerException
//	{
//		ArrayList<Frame> frames = new ArrayList<Frame>();
//		
//		int index = 0;
//		// Sets up the names in the knowledge base
//    	for (NameString n : names)
//    	{
//    		Frame f = new Frame("Name");
//    		f.addFrameKnowledge("index", String.valueOf(index));
//    		f.addFrameKnowledge("size", String.valueOf(n.getWords().size()));
//    		
//    		// Get analysis matrices
//    		boolean[] fmatrix = NameAnalyzer.getFirstNameMatrix(n.getWords());
//    		boolean[] lmatrix = NameAnalyzer.getLastNameMatrix(n.getWords());
//    		boolean[] mimatrix = NameAnalyzer.getMiddleInitialMatrix(n.getWords());
//    		
//    		// For each name array set up proper logic
//    		for (int i = 0; i < n.getWords().size(); i++)
//    		{
//    			Frame sub = new Frame("SubName");
//    			sub.addFrameKnowledge("firstname", String.valueOf(fmatrix[i]));
//    			sub.addFrameKnowledge("lastname", String.valueOf(lmatrix[i]));
//    			sub.addFrameKnowledge("middle", String.valueOf(mimatrix[i]));
//    			f.addFrameKnowledge("word" + i, sub);
//    		}
//    		
//    		frames.add(f);
//    		FrameCondition c = new FrameCondition(f, true);
//    		allFrames.put(n, c);
//    		index++;
//    	}
//    	
//    	// Sets up a neighbor for each frame
//    	for (Frame f : frames)
//    	{
//    		FrameList nbrList = new FrameList();
//    		for (Frame nbr : frames)
//    		{
//    			if (!f.equals(nbr))
//    			{
//    				nbrList.add(nbr);
//    			}
//    		}
//    		
//    		f.addFrameKnowledge("neighbor", nbrList);
//    	}
//    	
//    	
//
//	}
//	
//	public static String fireRules(NameString n)
//	{
//		kBase.insert(allFrames.get(n));
//		ArrayList<Rule> rulePath = currentFactory.getRulePath();
//		kBase.getKnowledgeBase().clear();
//		
//		if (rulePath != null)
//		{
//			String firedRules = "";
//			
//			Rule goalRule = rulePath.get(rulePath.size()-1);
//			
//			ArrayList<Condition> goalCondition = goalRule.getConsequents();
//			
//			// For each of the goals in the engine find which ones fired at the end of analysis
//			for (GoalCondition g : goals)
//			{
//				for (Condition c : goalCondition)
//				{
//					GoalCondition g2 = (GoalCondition) c;
//					if (g2.equals(g))
//					{
//						firedRules += g2.getDescriptor().getStringDescriptor();
//					}
//				}
//			}
//			
//			return firedRules;
//		}
//		else
//			return null;
//	}
//}
