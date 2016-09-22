package net.dusterthefirst.TEA;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;

import net.dusterthefirst.TEA.logger.TEALogger;
import net.dusterthefirst.TEA.res.FileContents;
import net.dusterthefirst.TEA.utils.FileFolder;
import net.md_5.bungee.api.ChatColor;

public class FileManager {
	
	//Makes A New Tea Logger Instance
	static TEALogger logger;
	
	//Sets Data Folder Path To Null
	static File data = null;

	//Makes An ArrayList Of All File Managers That Need Saving
	static ArrayList<FileFolder> fileManagers = new ArrayList<FileFolder>();
	
	//Creates All File Managers
	//HOLDS ALL SCRIPTS
	public static FileFolder scriptManager = null;
	//HOLDS ALL APIS
	//TODO public static FileFolder APIManager = null;
	//HOLDS ALL INFO FILES
	public static FileFolder infoManager =null;
	//HOLDS ALL FILES IN THE SRC DIR
	public static FileFolder srcManager = null;
	//HOLDS ALL RESOURCES FOR PLUGINS
	//TODO public static FileFolder resManager = null;
	
	//Adds All Resource Files To Their File Managers
	static void makeFiles() {
		//Adds FileManagers That Need To Be Saved To The FileManagers Array List
		fileManagers.add(infoManager);
		fileManagers.add(srcManager);
		
		//Created All The Info Files
		srcManager.addFile(".README");
		srcManager.addFile("Example.TEA");
		infoManager.addFile("Tea For Two.INFO");
		infoManager.addFile("Making Your First Plugin.INFO");
		infoManager.addFile("APIs.TAPI");
		
		//Writes To All Files
		srcManager.setTextContents(".README", FileContents.README);
		srcManager.setTextContents("Example.TEA", FileContents.EXAMPLE);
		
		//Saves All Files In The File Managers In The ArrayList
		saveAll(fileManagers);
	}
	
	//Initializes All File Managers To The Plugins DataFolder
	public static void init(File dataFolder, String infoColor, String errColor) {
		//Sets Logger Colors
		logger = new TEALogger(errColor, infoColor);
		//Initializes The Data Folder
		data = dataFolder;
		//Initializes All Managers
		scriptManager = new FileFolder(getDataFolder() + "/Scripts/");
		//TODO APIManager = new FileFolder(getDataFolder() + "/APIs/");
		infoManager = new FileFolder(getDataFolder() + "/INFO/");
		srcManager = new FileFolder(getDataFolder() + "/");
		//TODO resManager = new FileFolder(getDataFolder() + "/res/");
	}
	
	//Gets The DataFolder
	private static File getDataFolder(){
		return data;
	}
	
	//Saves All Files In All file Managers
	private static void saveAll(ArrayList<FileFolder> fileManagers){
		//Loops Through All FileManagers  In The File Manager
		for(FileFolder f : fileManagers){
			//Saves All Files
			f.saveAll();
		}
	}

	//Gets All Files From The Scripts, Resources And APIs Folder
	public static void getFiles() {
		File script = new File(scriptManager.getSrc());
		//TODO File res = new File(resManager.getSrc());
		//TODO File API = new File(APIManager.getSrc());
		getFilesInDir(script, scriptManager, "TEA");
		//TODO getFilesInDir(res, resManager, "");
		//TODO getFilesInDir(API, APIManager, "TAPI");
	}
	
	//Gets All Files In The Given Directory
	private static void getFilesInDir(File dir, FileFolder manager, String ext){
		//Checks That The Given File Is A Directory
		if(dir.isDirectory()){
			//Loops Through All The Files In The Directory
			for(File f : dir.listFiles()){
				//Gets Extension
				String extt = FilenameUtils.getExtension(f.getAbsolutePath()).toUpperCase();
				//Checks To See If The Extension Ends With The Given Extension
				if (extt.endsWith(ext)) {
					//Adds The File To The File Manager
					manager.setFile(f.getName(), f);
				//If Not,
				}else{
					//Logs An Error
					logger.error(ChatColor.RED + "Found A ." + extt + " File In The " + dir.getName() + " Folder", "Loader");
				}
			}
		}
	}
	
}
