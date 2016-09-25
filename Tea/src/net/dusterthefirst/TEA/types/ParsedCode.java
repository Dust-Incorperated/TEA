package net.dusterthefirst.TEA.types;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ParsedCode {

	private final Map<String, Variable> variables;
	private final Map<String, Function> functions;
	private final List<Flow> flows;
	private final List<String> codes;
	
	public ParsedCode(Map<String, Variable> variables, Map<String, Function> functions, List<Flow> flows, List<String> codes) {
		this.variables = variables;
		this.functions = functions;
		this.flows = flows;
		this.codes = codes;
	}
	
	public ParsedCode() {
		this(Maps.<String, Variable>newHashMap(), 
			Maps.<String, Function>newHashMap(), 
			Lists.<Flow>newArrayList(), 
			Lists.<String>newArrayList()
		);
	}
	
	public Map<String, Variable> getVariables() {
		return variables;
	}
	
	public Map<String, Function> getFunctions() {
		return functions;
	}
	
	public List<Flow> getFlows() {
		return flows;
	}
	
	public List<String> getCodes() {
		return codes;
	}
	
	@Override
	public String toString() {
		return "\n\r" + variables.toString() + "\n\r" + functions.toString() + "\n\r" + flows.toString() + "\n\r" + codes.toString();
	}
}