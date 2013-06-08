//package com.sciera.knowledgefactory;
//
//import java.io.BufferedReader;
//import java.io.DataInputStream;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import com.sciera.logicalobjects.Frame;
//import com.sciera.logicalobjects.FrameCondition;
//import com.sciera.logicalobjects.GoalCondition;
//import com.sciera.logicalobjects.StringFrame;
//import com.sciera.utilities.FrameList;
//
//public class RuleInterpreter 
//{
//	public static void initializeFactory(KnowledgeFactory factory, String file) throws IOException
//	{
//		// Read in file
//		FileInputStream fstream = new FileInputStream(file);
//		
//		DataInputStream in = new DataInputStream(fstream);
//		BufferedReader br = new BufferedReader(new InputStreamReader(in));
//		
//		String rules = "";
//		ArrayList<String> ruleStr = new ArrayList<String>();
//		
//		String strLine;
//		while ((strLine = br.readLine()) != null) {
//			rules += strLine;
//		}
//		
//		br.close();
//		
//		Pattern p = Pattern.compile("<Rule>(.*?)</Rule>", Pattern.DOTALL);
//		Matcher matcher = p.matcher(rules);
//		
//		while (matcher.find())
//		{
//			ruleStr.add(matcher.group(1));
//		}
//		
//		for (String rule : ruleStr)
//		{
//			String nameStr = "";
//			String conditionStr = "";
//			String resultStr = "";
//			ArrayList<String> frameStr = new ArrayList<String>();
//			
//			p = Pattern.compile("<Name>(.*?)</Name>", Pattern.DOTALL);
//			matcher = p.matcher(rule);
//			
//			while (matcher.find())
//			{
//				nameStr = (matcher.group(1));
//			}
//			
//			Rule r = new Rule(nameStr);
//			
//			// Find the condition string
//			p = Pattern.compile("<When>(.*?)</When>", Pattern.DOTALL);
//			matcher = p.matcher(rule);
//			
//			while (matcher.find())
//			{
//				conditionStr = (matcher.group(1));
//			}
//			
//			// Find the frames
//			p = Pattern.compile("<Frame>(.*?)</Frame>", Pattern.DOTALL);
//			matcher = p.matcher(conditionStr);
//			
//			while (matcher.find())
//			{
//				frameStr.add(matcher.group(1));
//			}
//			
//			// For each of the frames in the condition
//			for (String s : frameStr)
//			{
//				s = s.trim();
//				FrameCondition c;
//				String[] arr = s.split(":=");
//				
//				if (arr.length == 1)
//				{
//					if (s.substring(0,1).equals("!"))
//					{
//						s = s.substring(1, s.length());
//						c = new FrameCondition(processParameters(s), false);
//					}
//					else
//					{
//						c = new FrameCondition(processParameters(s), true);
//					}
//				}
//				else
//				{
//					if (arr[1].substring(0,1).equals("!"))
//					{
//						arr[1] = arr[1].substring(1, s.length());
//						c = new FrameCondition(processParameters(arr[1]), false);
//					}
//					else
//					{
//						c = new FrameCondition(processParameters(arr[1]), true);
//					}
//					
//					r = new UpdateRule(r);
//					((UpdateRule) r).putVariable(c, arr[0]);
//				}
//				
//				r.addAntecedent(c);
//			}
//			
//			frameStr.clear();
//			
//			// Find the result string
//			p = Pattern.compile("<Then>(.*?)</Then>", Pattern.DOTALL);
//			matcher = p.matcher(rule);
//			
//			while (matcher.find())
//			{
//				resultStr = (matcher.group(1));
//			}
//			
//			// Find the frames
//			p = Pattern.compile("<Frame>(.*?)</Frame>", Pattern.DOTALL);
//			matcher = p.matcher(resultStr);
//			
//			while (matcher.find())
//			{
//				frameStr.add(matcher.group(1));
//			}
//			
//			// For each of the frames in the condition
//			for (String s : frameStr)
//			{
//				s = s.trim();
//				FrameCondition c;
//				
//				if (s.substring(0,1).equals("!"))
//				{
//					s = s.substring(1, s.length());
//					c = new FrameCondition(processParameters(s), false);
//				}
//				else
//				{
//					c = new FrameCondition(processParameters(s), true);
//				}
//				
//				r.addConsequent(c);
//			}
//			
//			// Find the goals
//			p = Pattern.compile("<Goal>(.*?)</Goal>", Pattern.DOTALL);
//			matcher = p.matcher(resultStr);
//			
//			while (matcher.find())
//			{
//				frameStr.add(matcher.group(1));
//			}
//			
//			// For each of the goals in the condition
//			for (String s : frameStr)
//			{
//				s = s.trim();
//				GoalCondition c;
//				
//				if (s.substring(0,1).equals("!"))
//				{
//					s = s.substring(1, s.length());
//					StringFrame pmString = new StringFrame("Goal", s);
//					c = new GoalCondition(pmString, false);
//				}
//				else
//				{
//					StringFrame pmString = new StringFrame("Goal", s);
//					c = new GoalCondition(pmString, true);
//				}
//				
//				r.addConsequent(c);
//			}
//			
//			r.setKnowledgeBase(factory.getKnowledgeBase());
//			factory.addRule(r);
//			System.out.println(r);
//		}
//	}
//	
//	private static Frame processParameters(String params)
//	{
//		Frame f = new Frame(params.substring(0, params.indexOf(":")));
//		int itr = params.indexOf(":")+1;
//		int head = 0;
//		int frameCount = 0;
//		String state = "check";
//		String tempSlot = "";
//		Object tempValue = null;
//
//		while (itr < params.length())
//		{
//			char curr = params.charAt(itr);
//			
//			if (state.equals("detect"))
//			{	
//				if (curr == '(')
//				{
//					frameCount++;
//				}
//				else if (curr == ')')
//				{
//					frameCount--;
//				}
//				
//				if (frameCount == 0)
//				{
//					tempValue = processParameters(params.substring(head, itr+1));
//					state = "processValues";
//				}
//			}
//			else if (state.equals("detectList"))
//			{
//				if (curr == ']')
//				{
//					tempValue = processList(params.substring(head, itr+1));
//					state = "processValues";
//				}
//			}
//			else if (state.equals("check"))
//			{
//				if (curr == '(')
//				{
//					state = "processSlots";
//				}
//			}
//			else if (state.equals("processSlots"))
//			{
//				if (curr == '=')
//				{
//					head = itr+1;
//					tempValue = "";
//					state = "processValues";
//				}
//				else
//				{
//					tempSlot += curr;
//				}
//			}
//			else if (state.equals("processValues"))
//			{
//				if (curr == ' ')
//				{
//					f.addFrameKnowledge(tempSlot, tempValue);
//					tempValue = "";
//					tempSlot = "";
//					head = itr+1;
//					state = "processSlots";
//				}
//				else if (curr == '[')
//				{
//					state = "detectList";
//				}
//				else if (curr == ':')
//				{
//					state = "detect";
//				}
//				else if (curr == ')')
//				{
//					f.addFrameKnowledge(tempSlot, tempValue);
//					tempValue = "";
//					tempSlot = "";
//					head = itr+1;
//					state = "exit";
//				}
//				else
//				{
//					tempValue = (String) tempValue + curr;
//				}
//			}
//			else if (curr == ':' && state.equals("idle"))
//			{
//				f = new Frame(params.substring(head, itr));
//				state = "check";
//			}
//			
//			itr++;
//		}
//		
//		return f;
//	}
//	
//	private static FrameList processList(String params)
//	{
//		FrameList list = new FrameList();
//		params = params.replaceAll("[\\[\\]]", "");
//		String[] frames = params.split(" ");
//		
//		for (String s : frames)
//		{
//			list.add(processParameters(s));
//		}
//		
//		return list;
//	}
//}
