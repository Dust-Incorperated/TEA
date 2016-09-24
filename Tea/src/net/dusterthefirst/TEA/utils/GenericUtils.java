package net.dusterthefirst.TEA.utils;

public class GenericUtils {

	public static String StacktraceToString(StackTraceElement[] stackTrace){
		String out = "\n\r";
		for(StackTraceElement e : stackTrace){
			out += e.toString() + "\n\r";
		}
		return out;
	}
	
}
