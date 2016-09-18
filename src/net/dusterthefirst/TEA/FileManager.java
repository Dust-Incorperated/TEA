package net.dusterthefirst.TEA;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;

import net.dusterthefirst.TEA.res.FileContents;
import net.dusterthefirst.TEA.utils.FileFolder;
import net.md_5.bungee.api.ChatColor;

public class FileManager {
	
	static File data = null;

	static ArrayList<FileFolder> fileManagers = new ArrayList<FileFolder>();
	
	static FileFolder scriptManager = null;
	static FileFolder APIManager = null;
	static FileFolder pluginManager = null;
	static FileFolder extentionManager = null;
	static FileFolder infoManager =null;
	static FileFolder srcManager = null;
	
	static void makeFiles() {
		//Adds Filemanagers To The Filemanagers Variable
		fileManagers.add(infoManager);
		fileManagers.add(srcManager);
		
		//Adds All Files
		srcManager.addFile(".README");
		srcManager.addFile("Example.TEA");
		infoManager.addFile("Tea For Two.INFO");
		infoManager.addFile("Making Your First Plugin.INFO");
		infoManager.addFile("APIs.TAPI");
		infoManager.addFile("Plugins.JAR");
		infoManager.addFile("Extentions.TBAG");
		
		//Writes To All Files
		srcManager.setTextContents(".README", FileContents.README);
		srcManager.setTextContents("Example.TEA", FileContents.EXAMPLE);
		
		//Saves All Files
		saveAll(fileManagers);
	}
	
	public static void init(File dataFolder) {
		data = dataFolder;
		scriptManager = new FileFolder(getDataFolder() + "/Scripts/");
		APIManager = new FileFolder(getDataFolder() + "/APIs/");
		pluginManager = new FileFolder(getDataFolder() + "/Plugins/");
		extentionManager = new FileFolder(getDataFolder() + "/Extentions/");
		infoManager = new FileFolder(getDataFolder() + "/INFO/");
		srcManager = new FileFolder(getDataFolder() + "/");
	}
	
	private static File getDataFolder(){
		return data;
	}
	
	//Saves All Files In All file Managers
	private static void saveAll(ArrayList<FileFolder> fileManagers){
		for(FileFolder f : fileManagers){
			f.saveAll();
		}
	}

	
	public static void getFiles() {
		File script = new File(scriptManager.getSrc());
		File API = new File(APIManager.getSrc());
		File plugin = new File(pluginManager.getSrc());
		File extention = new File(extentionManager.getSrc());
		getFilesInDir(script, scriptManager, "TEA");
		getFilesInDir(API, APIManager, "TAPI");
		getFilesInDir(plugin, pluginManager, "JAR");
		getFilesInDir(extention, extentionManager, "TBAG");
		System.out.println(scriptManager.toString());
		System.out.println(APIManager.toString());
		System.out.println(pluginManager.toString());
		System.out.println(extentionManager.toString());
	}
	
	private static void getFilesInDir(File dir, FileFolder manager, String ext){
		if(dir.isDirectory()){
			for(File f : dir.listFiles()){
				String extt = FilenameUtils.getExtension(f.getAbsolutePath()).toUpperCase();
				if (extt.endsWith(ext)) {
					manager.setFile(f.getName(), f);
				}else{
					Main.sendConsoleAMsg(ChatColor.DARK_RED + "Found A ." + extt + " File In The " + dir.getName() + " Folder");
				}
			}
		}
	}
	
}
