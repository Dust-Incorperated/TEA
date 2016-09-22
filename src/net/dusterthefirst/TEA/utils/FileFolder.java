package net.dusterthefirst.TEA.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FileFolder  {
	
	//File Folder Source
	private String src;
	//list Of Files And Names
	HashMap<String, File> files = new HashMap<>();

	//Creates A New File Folder At The Specified Source
	public FileFolder(String src){
		this.src = src;
		//Creates The Directory, If Id Doesn't Exist
		makeDir(src);
	}
	
	//Adds A New File To The File Folder
	public void addFile(String name){
		//Adds A File With Then Name Of The Given Name, And The Source Of The Source Folder + The Given Name
		files.put(name, new File(src + name));
	}
	
	//Returns The File With The Specified Name
	public File getFile(String name){
		//Returns File
		return files.get(name);
	}
	
	//Adds A Pre-Determined File To The List
	public void setFile(String name, File file){
		files.put(name, file);
	}
	
	//Saves All Files In The File Folder
	public void saveAll(){
		//Loops Through All Files In The File Folder
		for(Map.Entry<String, File> f : files.entrySet()){
			try {
				//Trys To Save Them
				f.getValue().createNewFile();
			} catch (IOException e) {
				//If It Can't, It Will Print Out The Stack Trace
				e.printStackTrace();
			}
		}
	}
	
	//Saves A Specified File
	public void saveFile(String name){
		try {
			//Trys To Save The File
			files.get(name).createNewFile();
		} catch (IOException e) {
			//If It Can't, It Will Print The Stack Trace
			e.printStackTrace();
		}
	}
	
	//Makes The Directory
	private void makeDir(String path){
		//Makes A New File With The File Folders Path
		File file = new File(path);
		//Makes Directories Of That File
		file.mkdirs();
	}
	
	//Deletes The File With The Specifies Name
	public void deleteFile(String name){
		files.get(name).delete();
	}
	
	//Checks To See If The File Exists
	public boolean exists(String name){
		return files.get(name).exists();
	}
	
	//Sets Text Contents Of The File To The String Given
	public void setTextContents(String name, String input){
		//Splits Each Line By The ; Character
		String[] inp = input.split(";");
		//Sets Line To 0
		int x = 0;
		//Loops Through All Lines
		for(String s : inp){
			//If They Start With A Space It Will Remove That Space
			if(s.startsWith(" ")){
				inp[x] = s.substring(1);
			}
			//Adds One TO The Line
			x++;
		}
		
		//Does Some Writing Magic
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(files.get(name), "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		for(String s : inp){
			writer.println(s);
		}
		writer.close();
		try {
			//Trys To Save The File
			files.get(name).createNewFile();
		} catch (IOException e) {
			//If It Can't, It Will Print Out The Stack Trace
			e.printStackTrace();
		}
	}
	
	//Returns The Source Dir
	public String getSrc(){
		return src;
	}
	
	//Changes How #toString() Works
	@Override
	public String toString() {
		return files.toString();
	}
	
	//Gets The Files In The Folder
	public ArrayList<File> getFiles(){
		//Makes A List To Store The Files
		ArrayList<File> out = new ArrayList<File>();
		//Loops Through All Files And Adds Them To The Output
		for(Map.Entry<String, File> f : files.entrySet()){
			out.add(f.getValue());
		}
		//Returns The Output
		return out;
	}
	
}