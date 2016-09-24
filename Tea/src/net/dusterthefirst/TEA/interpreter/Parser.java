package net.dusterthefirst.TEA.interpreter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.dusterthefirst.TEA.logger.TEALogger;
import net.dusterthefirst.TEA.types.Flow;
import net.dusterthefirst.TEA.types.Function;
import net.dusterthefirst.TEA.types.ParsedCode;
import net.dusterthefirst.TEA.types.Variable;
import net.md_5.bungee.api.ChatColor;

public class Parser {
	
	private static final Pattern functionRegex = Pattern.compile("function\\s*\\w+\\s*\\(\\s*\\w+\\s*(,\\s*\\w*)*\\s*\\)");
	private static final Pattern variableRegex = Pattern.compile("var\\s*(\\w+(\\s*=\\s*((\"(.*\\s*)\")|([0-9]*)))*)");
	private static final Pattern conditionalRegex = Pattern.compile("(while|if)\\s*\\(.+\\)");
	/* I am not sure how the "for" loop is going to be syntaxed. I shall write a RegEx when necessary */

	//Makes Variables hashMap
	static HashMap<String, Variable> variables = new HashMap<>();
	//Makes Functions HashMap
	static HashMap<String, Function> functions = new HashMap<>();
	//Holds All Flow Things
	static ArrayList<Flow> flows = new ArrayList<>();
	//Holds All Lines Of Code Thats Not One Of The Above
	static ArrayList<String> codes = new ArrayList<>();
	//Makes Logger
	static TEALogger logger;
	
	//Parses The Given File
	public static ParsedCode parse(File f, String infoColor, String errColor){
		//Makes New Logger
		logger = new TEALogger();
		//Gets Lines From File
		ArrayList<String> lines = readFile(f);
		//Sets The Working Line To 1
		int line = 1;
		//Loops Through All Lines And Parses Variables
		for(String s : lines){
			Matcher variableMatcher = variableRegex.matcher(s);
			Matcher functionMatcher = functionRegex.matcher(s);
			Matcher conditionalMatcher = conditionalRegex.matcher(s);
			
			// Attempt to parse a variable
			if (variableMatcher.matches()){
				parseVariable(s, line, f);
			}
			
			// Attempt to parse a conditional (if, while, for)
			else if (conditionalMatcher.matches()){
				parseLoop(conditionalMatcher.group(1), s, line, f, lines);
			}
			
			// Attempt to parse a function
			else if (functionMatcher.matches()){
				parseFunction(s, line, f, lines);
			}
			
			// If all else fails, render it as a piece of code
			else{
				codes.add(s);
			}
			
			//Sets Working Line To the Current Line +1
			line++;
		}
		
		//Returns The Parsed Code
		ParsedCode parsedCode = new ParsedCode();
		parsedCode.variables = variables;
		parsedCode.functions = functions;
		parsedCode.flows = flows;
		parsedCode.codes = codes;
		return parsedCode;
	}
	
	private static void parseLoop(String string, String s, int line, File f, ArrayList<String> lines) {
		logger.log(ChatColor.YELLOW + "Coming Soon", "Parser");
	}

	//Parses A Variable
	private static void parseVariable(String s, int line, File f) {
		s = s.trim().toLowerCase();
		try{
			//Creates A New Variable
			Variable var = new Variable();
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
				//Sets The Variables Name To The Item Before The = Sign
				var.name = split[0];
				//Checks If Value Is A String
				if(split[1].startsWith("\"") && split[1].endsWith("\"")){
					//Sets Value = To String Value
					var.value = split[1];
				//If Not A String
				}else{
					//It Will Attempt To Parse The Integer
					try {
						//Parses Integer And Sets The Variables Value To The Integer
						var.value = Integer.parseInt(split[1]);
					//Catches Any Exceptions
					} catch (Exception e) {
						//Logs An Error
						logger.error(ChatColor.RED + "ERROR IN " + f.getName() + ", AT LINE " + line, f.getName());
						logger.error(ChatColor.RED + "Canot Parse Integer", f.getName());
					}
				}
				//Logs The Variables Value And Name
				logger.log("Variable " + var.name + " Found, With A Value Of, " + var.value, f.getName());
				//Adds The Variable To The Variables Hash Map
				variables.put(var.name, var);
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
	private static void parseFunction(String s, int line, File f, ArrayList<String> lines) {
		s = s.trim().toLowerCase();
		//Catches If There Is An Error
		try {
			//Creates A New Function
			Function function = new Function();
			//Removes The 'Function' From The Line
			s = s.replace("function ", "");
			//Splits The Function By Name And Values
			String[] split = s.split("\\(", 2);
			//Sets Name Of The Function
			function.name = split[0];
			//Splits The Variables
			String[] vars = split[1].split(",");
			//Loops Through All Variables
			for(String var : vars){
				//removes Whitespace
				var = var.trim();
				//New Variable
				Variable varr = new Variable();
				//Removes End )
				if(var.endsWith(")")){
					var = var.replace(")", "");
				}
				//Sets Variables name
				varr.name = var;
				//Sets Value To Null
				varr.value = null;
				//Adds Variables To The Function
				function.variables.add(varr);
			}
			//Loops Through All Lines UNDER The Function Declaration
			for(String line1 : lines.subList(line, lines.size())){
				line1 = line1.trim();
				//Checks If The Line Is End
				if(line1.equalsIgnoreCase("end")){
					break;
				}
				//Adds Current Line To The Code
				function.code.add(line1);
			}
			//Adds The Function To The Function Hash Map
			functions.put(function.name, function);
			//Logs Finding Of The Function
			logger.log("Function " + function.name + ", And A Value Of " + function.code.toString() + ", And Variables Set As " + function.variables.toString(), f.getName());
		} catch (Exception e) {
			logger.error(ChatColor.RED + "Error In Declairing A Function At Line " + line, f.getName());
		}
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
