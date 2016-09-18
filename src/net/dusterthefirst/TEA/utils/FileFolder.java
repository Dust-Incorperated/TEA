package net.dusterthefirst.TEA.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class FileFolder  {
	
	private String src;
	HashMap<String, File> files = new HashMap<>();

	public FileFolder(String src){
		this.src = src;
		makeDir(src);
	}
	
	public void addFile(String name){
		files.put(name, new File(src + name));
	}
	
	public File getFile(String name){
		return files.get(name);
	}
	
	public void setFile(String name, File file){
		files.put(name, file);
	}
	
	public void saveAll(){
		for(Map.Entry<String, File> f : files.entrySet()){
			try {
				f.getValue().createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void saveFile(String name){
		try {
			files.get(name).createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void makeDir(String path){
		File file = new File(path);
		file.mkdirs();
	}
	
	public void deleteFile(String name){
		files.get(name).delete();
	}
	
	public boolean exists(String name){
		return files.get(name).exists();
	}
	
	public void setTextContents(String name, String input){
		String[] inp = input.split(";");
		int x = 0;
		for(String s : inp){
			if(s.startsWith(" ")){
				inp[x] = s.substring(1);
			}
			x++;
		}
		
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
			files.get(name).createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getSrc(){
		return src;
	}
	
	@Override
	public String toString() {
		return files.toString();
	}
	
}