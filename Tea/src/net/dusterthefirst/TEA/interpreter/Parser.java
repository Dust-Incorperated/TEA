package net.dusterthefirst.TEA.interpreter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.dusterthefirst.TEA.logger.TEALogger;
import net.dusterthefirst.TEA.types.Flow;
import net.dusterthefirst.TEA.types.Function;
import net.dusterthefirst.TEA.types.ParsedCode;
import net.dusterthefirst.TEA.types.Variable;
import net.md_5.bungee.api.ChatColor;

public class Parser {
	
	private static final Pattern functionRegex = Pattern.compile("function\\s*\\w+\\s*\\(\\s*\\w*\\s*(,\\s*\\w*)*\\s*\\)");
	private static final Pattern variableRegex = Pattern.compile("var\\s*(\\w+(\\s*=\\s*((\"(.*\\s*)\")|([0-9]*)))*)");
	private static final Pattern conditionalRegex = Pattern.compile("(while|if)\\s+.+");
	/* I am not sure how the "for" loop is going to be syntaxed. I shall write a RegEx when necessary */

	//Makes Logger
	static TEALogger logger = new TEALogger();
	
	//Parses The Given File
	public static ParsedCode parse(File f){
		Map<String, Variable> variables = new HashMap<>();
		Map<String, Function> functions = new HashMap<>();
		List<Flow> flows = new ArrayList<>();
		List<String> codes = new ArrayList<>();
		
		//Gets Lines From File
		ArrayList<String> lines = readFile(f);
		//Sets The Working Line To 1
		int line = 1;
		int linen = 0;
		//Loops Through All Lines And Parses Variables
		for(String s : lines){
			//Skips Lines In Loops Or Functions
			if(line <= linen){
				logger.log("Skipping Line " + line, "Parser");
				line++;
			}else{
				logger.log("Parsing Line " + line, "Parser");
				s = s.replaceAll("\\s+", "").toLowerCase();
				Matcher variableMatcher = variableRegex.matcher(s);
				Matcher functionMatcher = functionRegex.matcher(s);
				Matcher conditionalMatcher = conditionalRegex.matcher(s);
				
				// Attempt to parse a variable
				if (variableMatcher.matches()){
					parseVariable(variableMatcher.group(), line, f, variables);
				}
				
				// Attempt to parse a conditional (if, while, for)
				else if (conditionalMatcher.matches()){
					//Shifts Reader To Bottom Of Loop
					linen = parseLoop(conditionalMatcher.group(1), conditionalMatcher.group(), line, f, lines);
				}
				
				// Attempt to parse a function
				else if (functionMatcher.matches()){
					//Shifts Reader To Bottom Of Function
					linen = parseFunction(functionMatcher.group(), line, f, lines, functions);
				}
				
				// If all else fails, render it as a piece of code
				else{
					codes.add(s);
				}
				
				//Sets Working Line To the Current Line +1
				line++;
			}
		}
		
		//Returns The Parsed Code
		return new ParsedCode(variables, functions, flows, codes);
	}
	
	private static int parseLoop(String string, String s, int line, File f, ArrayList<String> lines) {
		logger.log(ChatColor.YELLOW + "Coming Soon", "Parser");
		logger.log(ChatColor.LIGHT_PURPLE + "stuff: " + string + "\n\rS:" + s , "Parser");
		return line;
	}

	//Parses A Variable
	private static void parseVariable(String s, int line, File f, Map<String, Variable> variables) {
		s = s.trim().toLowerCase();
		try{
			//Removes The Var From The Line
			s = s.replaceFirst("var ", "");
			//Splits The Line By The = Sign
			String[] split = s.split("=", 2);
			//Loops Through All The Split Line
			for(int i = 0; i < split.length; i++){
				//Removes All Leading And Trailing Whitespace
				split[i] = split[i].trim();
			}
			//Checks If The Variable Has A Value And A Name
			if(split.length == 2){
				Variable variable = new Variable(split[0]);
				//Checks If Value Is A String
				if(split[1].startsWith("\"") && split[1].endsWith("\"")){
					//Sets Value = To String Value
					variable.setValue(split[1]);
				//If Not A String
				}else{
					//It Will Attempt To Parse The Integer
					try {
						//Parses Integer And Sets The Variables Value To The Integer
						variable.setValue(Integer.parseInt(split[1]));
					//Catches Any Exceptions
					} catch (Exception e) {
						//Logs An Error
						logger.error(ChatColor.RED + "ERROR IN " + f.getName() + ", AT LINE " + line, f.getName());
						logger.error(ChatColor.RED + "Canot Parse Integer", f.getName());
					}
				}
				//Logs The Variables Value And Name
				logger.log("Variable " + variable.getName() + " Found, With A Value Of, " + variable.getValue(), f.getName());
				//Adds The Variable To The Variables Hash Map
				variables.put(variable.getName(), variable);
			//If The Var Doesn't Have A Name Or A Value
			}else{
				//Logs An Error
				logger.error(ChatColor.RED + "ERROR IN " + f.getName() + ", AT LINE " + line, f.getName());
				logger.error(ChatColor.RED + "Variable Missing Name Or Value", f.getName());
			}
		} catch (Exception e) {
			logger.error(ChatColor.RED + "Error In Declairing A Variable At Line " + line, f.getName());
		}
	}

	//Parses A Function
	private static int parseFunction(String s, int line, File f, ArrayList<String> lines, Map<String, Function> functions) {
		s = s.trim().toLowerCase();
		//Catches If There Is An Error
		try {
			//Removes The 'Function' From The Line
			s = s.replaceFirst("function", "");
			//Splits The Function By Name And Values
			String[] split = s.split("\\(", 2);
			
			Function function = new Function(split[0]);
			
			//Splits The Variables
			String[] vars = split[1].split(",");
			//Loops Through All Variables
			for(String var : vars){
				//removes Whitespace
				var = var.trim();
				//Removes End )
				if(var.endsWith(")")){
					var = var.replace(")", "");
				}
				//Creates a new variable
				Variable variable = new Variable(var, null);
				//Adds Variables To The Function
				function.getVariables().add(variable);
			}
			//Loops Through All Lines UNDER The Function Declaration
			for(String line1 : lines.subList(line, lines.size())){
				line1 = line1.trim();
				//Checks If The Line Is End
				if(line1.equalsIgnoreCase("end")){
					break;
				}
				//Adds Current Line To The Code
				function.getCode().add(line1);
				line++;
			}
			//Adds The Function To The Function Hash Map
			functions.put(function.getName(), function);
			//Logs Finding Of The Function
			logger.log("End Line:" + line + " Function " + function.getName() + ", And A Value Of " + function.getCode().toString() + ", And Variables Set As " + function.getVariables().toString(), f.getName());
		} catch (Exception e) {
			logger.error(ChatColor.RED + "Error In Declairing A Function At Line " + line, f.getName());
		}
		return line + 1;
	}
	
	//Reads The Given
	private static ArrayList<String> readFile(File f){
		//Makes A New ArrayList To Store The File Contents
		ArrayList<String> out = new ArrayList<String>();
		//Does Some Magic To Read The File
		try (BufferedReader br = new BufferedReader(new FileReader(f))){
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				out.add(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Returns The ArrayList Of The File Contents
		return out;
	}
}
