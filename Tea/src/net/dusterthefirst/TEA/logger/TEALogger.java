package net.dusterthefirst.TEA.logger;

import java.io.PrintStream;

import org.bukkit.ChatColor;

import net.dusterthefirst.TEA.Main;

public class TEALogger {
	
	//Stores The Output Colors
	private String infoColor, errColor;
	//Console Output
	public PrintStream out = null;

	//Makes A TEALogger With A Custom PrintStream
	public TEALogger(PrintStream out, String errColor, String infoColor) {
		this.out = out;
		this.errColor = ChatColor.translateAlternateColorCodes('&', errColor);
		this.infoColor =  ChatColor.translateAlternateColorCodes('&', infoColor);
	}
	
	//Makes A TEALogger With The Default Output
	public TEALogger(String errColor, String infoColor) {
		this.out = System.out;
		this.errColor = ChatColor.translateAlternateColorCodes('&', errColor);
		this.infoColor =  ChatColor.translateAlternateColorCodes('&', infoColor);
	}

	//Logs Contents Out To The Console
	public void log(String contents, String filename) {
		//Creates The Line
		String output = infoColor + "[TEA][" + removeExtension(filename) + "][Info]: " + ChatColor.RESET + contents;
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
	
}
