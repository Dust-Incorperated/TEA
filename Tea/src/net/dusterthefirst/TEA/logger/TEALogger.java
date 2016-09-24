package net.dusterthefirst.TEA.logger;

import java.io.PrintStream;

import org.bukkit.ChatColor;

import net.dusterthefirst.TEA.Main;

public class TEALogger {
	
	//Stores The Output Colors
	private static String infoColor, errColor, debugColor;
	//Console Output
	public PrintStream out = null;

	public static void setColors(String infoColor, String errColor, String debugColor){
		TEALogger.infoColor = ChatColor.translateAlternateColorCodes('&', infoColor);
		TEALogger.errColor = ChatColor.translateAlternateColorCodes('&', errColor);
		TEALogger.debugColor = ChatColor.translateAlternateColorCodes('&', debugColor);
	}
	
	//Makes A TEALogger With A Custom PrintStream
	public TEALogger(PrintStream out) {
		this.out = out;
	}
	
	//Makes A TEALogger With The Default Output
	public TEALogger() {
		this.out = System.out;
	}

	//Logs Contents Out To The Console
	public void log(String contents, String filename) {
		//Creates The Line
		String output = infoColor + "[TEA][" + removeExtension(filename) + "][Info]: " + ChatColor.RESET + contents;
		//Sends The Line To The Console
		Main.sendConsoleAMsg(output);
	}
	
	//Logs Contents Out To The Console
	public void debug(String contents) {
		//Creates The Line
		String output = debugColor + "[TEA][Debug]: " + ChatColor.RESET + contents;
		//Sends The Line To The Console
		Main.sendConsoleAMsg(output);
	}

	//Logs Contents Out To The Error Console
	public void error(String contents, String filename) {
		//Creates The Line
		String output = errColor + "[TEA][" + removeExtension(filename) + "]" + "[Error]: " + ChatColor.RESET + contents;
		//Sends The Line To The Console
		Main.sendConsoleAMsg(output);
	}
	
	//Removes The Extension Of A File
	private String removeExtension(String inp){
		//Splits The Input By A .
		String[] input = inp.split("\\.");
		//Checks To See That The File Does Have An Extension
		if(input.length == 2){
			//If It Does It Returns The Part Before The .
			return input[0];
		}else{
			//If Not, It Will Return The Whole String
			return inp;
		}
	}

	public void logDebugInfo() {
		//Logs A Message
		this.debug(ChatColor.LIGHT_PURPLE + "System Info For Debugging:");
		
		 /* Total number of processors or cores available to the JVM */
		this.debug(ChatColor.LIGHT_PURPLE + "Available processors: " + 
	        Runtime.getRuntime().availableProcessors());

	    /* Total amount of free memory available to the JVM */
		this.debug(ChatColor.LIGHT_PURPLE + "Free memory: " + 
	        Runtime.getRuntime().freeMemory()/1000000 + "MB");

	    /* This will return Long.MAX_VALUE if there is no preset limit */
	    long maxMemory = Runtime.getRuntime().maxMemory()/1000000;
	    /* Maximum amount of memory the JVM will attempt to use */
	    this.debug(ChatColor.LIGHT_PURPLE + "Maximum memory: " + 
	        (maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory) + "MB");

	    /* Total memory currently available to the JVM */
	    this.debug(ChatColor.LIGHT_PURPLE + "Total memory available to JVM: " + 
	        Runtime.getRuntime().totalMemory()/1000000 + "MB");
	}
	
}
