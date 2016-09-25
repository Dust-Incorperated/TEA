package net.dusterthefirst.TEA.types;

public class Variable {

	private Object value;
	private final String name;
	public Variable(String name, Object value) {
		this.name = name;
		this.value = value;
	}
	
	public Variable(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
	public Object getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return "\"" + name + " = " + value + "\"";
	}
}