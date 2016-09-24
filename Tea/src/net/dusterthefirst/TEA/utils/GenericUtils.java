package net.dusterthefirst.TEA.utils;

import net.md_5.bungee.api.ChatColor;

public class GenericUtils {

	public static String StacktraceToString(StackTraceElement[] stackTrace){
		String out = "\n\r" + ChatColor.RED + "[TEA]:" + ChatColor.RESET;
		for(StackTraceElement e : stackTrace){
			out += e.toString() + "\n\r" + ChatColor.RED + "[TEA]:" + ChatColor.RESET;
		}
		return out;
	}
	
}
