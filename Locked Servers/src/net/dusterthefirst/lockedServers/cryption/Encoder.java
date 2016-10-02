package net.dusterthefirst.lockedServers.cryption;

public class Encoder {
		
	public static String encode(String input){

		char[] array;
		int[] dec;
		String in16;
		
		array = stringToArray(input);
		dec = arrayToInt(array);
		in16 = intToHex(dec);
		
		return flip(in16);
	}
	
	public static String flip(String inp){
		return new StringBuilder(inp).reverse().toString();
	}
	
	public static String intToHex(int[] inp){
		String output = "", hex;
		int counter = 0;
		int l = inp.length;
		while(counter < l){
			hex = toHex(inp[counter]);
			output = output + hex;
			counter++;
		}	
		return output.toUpperCase();
	}
	
	public static int[] arrayToInt(char[] inp){
		int[] output;
		int inDec;
		int counter = 0, l;
		l = inp.length;
		output = new int[l];
		while(counter <= l-1){
			inDec = (int) inp[counter];
			output[counter] = inDec;
			counter++;
		}
		return output;
	}
	
	public static char[] stringToArray(String inp){
		return inp.toCharArray();
	}
	
	public static String toHex(int inp){
		return Integer.toHexString(inp);
	}
	
}