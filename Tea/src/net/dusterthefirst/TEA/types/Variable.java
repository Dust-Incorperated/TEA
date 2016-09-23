package net.dusterthefirst.TEA.types;

public class Variable {

	public Object value;
	public String name;
	
	@Override
	public String toString() {
		return name + " = " + value;
	}

}
