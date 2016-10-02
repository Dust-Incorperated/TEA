package net.dusterthefirst.TEA.variables;

public class Variables {

	public static void getAllVariables() {
		Class<?>[] classes = TEAVariable.getClasses();
		System.out.println(classes.length);
		
	}
	
}
