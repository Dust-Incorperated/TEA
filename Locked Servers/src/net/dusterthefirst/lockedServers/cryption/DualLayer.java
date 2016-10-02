package net.dusterthefirst.lockedServers.cryption;

public class DualLayer{

	public static String encode(String input){
		input = Encoder.encode(input);
		input = new StringBuilder(input).reverse().toString();
		input = Encoder.encode(input);
		return input;
	}
	
	public static String decode(String input){
		input = Decoder.decode(input);
		input = new StringBuilder(input).reverse().toString();
		input = Decoder.decode(input);
		return input;
	}
	
}
