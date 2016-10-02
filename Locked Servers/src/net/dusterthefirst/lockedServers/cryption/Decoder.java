package net.dusterthefirst.lockedServers.cryption;

public class Decoder {

	public static String decode(String input){
		
		String[] split = stringToArray(flip(input));
		String string = hexToStr(split);
		
		return string;
	}
	
	public static String flip(String inp){
		return new StringBuilder(inp).reverse().toString();
	}

	private static String hexToStr(String[] split) {
		String out = "";
		for(int x = 0; x < split.length; x++){
			
			String str =  split[x];
			String values = "0123456789ABCDEF";
			int num = 0;
			num += values.indexOf(str.charAt(0)) * 16;
			num += values.indexOf(str.charAt(1));
			out += (char) num;
		}
		return out;
	}

	private static String[] stringToArray(String input) {
		int n = input.length();
		String[] split = new String[n/2];		
		for(int x = 0; x < n; x += 2) {
			String temp = "";
			temp = temp + input.charAt(x) + input.charAt(x+1);
			split[x/2] = temp;
		}
		return split;
	}
	
}
