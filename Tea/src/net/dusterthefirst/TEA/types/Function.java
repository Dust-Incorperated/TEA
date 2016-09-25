package net.dusterthefirst.TEA.types;

import java.util.List;

import com.google.common.collect.Lists;

public class Function {

	private List<String> code;
	private List<Variable> variables;
	
	private final String name;
	public Function(String name, List<String> code, List<Variable> variables) {
		this.name = name;
		this.code = code;
		this.variables = variables;
	}

	public Function(String name) {
		this(name, Lists.<String>newArrayList(), Lists.<Variable>newArrayList());
	}
	
	public String getName() {
		return name;
	}
	
	public List<String> getCode() {
		return code;
	}
	
	public List<Variable> getVariables() {
		return variables;
	}
}