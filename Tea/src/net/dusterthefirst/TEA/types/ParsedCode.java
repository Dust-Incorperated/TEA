package net.dusterthefirst.TEA.types;

import java.util.ArrayList;
import java.util.HashMap;

public class ParsedCode {

	public HashMap<String, Variable> variables;
	public HashMap<String, Function> functions;
	public ArrayList<Flow> flows;
	public ArrayList<String> codes;
	
	@Override
	public String toString() {
		return "\n\r" + variables.toString() + "\n\r" + functions.toString() + "\n\r" + flows.toString() + "\n\r" + codes.toString();
	}

}
