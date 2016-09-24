package net.dusterthefirst.TEA;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.dusterthefirst.TEA.interpreter.Parser;
import net.dusterthefirst.TEA.logger.TEALogger;
import net.dusterthefirst.TEA.types.ParsedCode;
import net.dusterthefirst.TEA.update.Checker;
import net.dusterthefirst.TEA.utils.GenericUtils;
import net.dusterthefirst.TEA.utils.internal.TEACmd;

public class Main extends JavaPlugin implements Listener{
	
	//DONT FORGET THE PLUGIN.YML
	
	//Id Of The Plugin Found On The Spigot Url
	private final String ID = "29401";
	//Download Page
	private final String DOWNLOADURL = "https://www.spigotmc.org/resources/tea.29401/";
	
	@Override
	public void onEnable(){
		//Does Intit Stuff
		getServer().getPluginManager().registerEvents(this, this);
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		
		//Gets Colors For Console
		String infoColor = getConfig().getString("Console.InfoColor");
		String errColor = getConfig().getString("Console.ErrorColor");
		String debugColor = getConfig().getString("Console.DebugColor");
		//Sets Color For The Console
		TEALogger.setColors(infoColor, errColor, debugColor);
		
		//Makes Logger Instance
		TEALogger logger = new TEALogger();
		//Logs All The Debug Info
		logger.logDebugInfo();
		
		try {
			
			//Makes And Registers The TEA Command
			TEACmd teaCmd = new TEACmd();
			teaCmd.register();
			
			//MAKES AND GETS ALL FILES
			FileManager.init(this.getDataFolder(), infoColor, errColor);
			FileManager.makeFiles();
			FileManager.getFiles();
			
			//Loops Through Each File In The Scripts Folder
			for(File f: FileManager.scriptManager.getFiles()){
				//Parses It
				ParsedCode parsed = Parser.parse(f);
				System.out.println(parsed);
			}
			
		} catch (Throwable e) {
			String trace = GenericUtils.StacktraceToString(e.getStackTrace());
			logger.error(ChatColor.RED + "OH NOES!!! TEA CRASHED", "Loader");
			logger.error(ChatColor.RED + "There Has Been An Uncaught Exeption In TEA", "Loader");
			logger.error(ChatColor.RED + "Make Sure You Are Running The Latest Version Of TEA", "Loader");
			logger.error(ChatColor.RED + "PLEASE SEND THE BELOW INFORMATION BETWEEN THE LINES TO THE DEVELOPER THROUGH THE LINK BELOW", "logger");
			logger.error(ChatColor.RED + "https://github.com/DusterTheFirst/TEA/issues", "Loader");
			logger.error(ChatColor.RED + "-----------------------------------", "Loader");
			logger.error(ChatColor.YELLOW + trace, "Loader");
			logger.logDebugInfo();
			logger.error(ChatColor.RED + "-----------------------------------", "Loader");
		}
		
		//TODO Remove TEST
		//ABSRACT COMMAND TEST
//		CustomCommand command = new CustomCommand(DOWNLOADURL, DOWNLOADURL, DOWNLOADURL, DOWNLOADURL, new ArrayList<String>(21));
//		sendConsoleAMsg(ChatColor.GREEN + command.toString());
//		command.addAlias("yomamma");
//		command.addAlias("dis is cool");
//		command.setPermMessage("You Just Cant");
//		sendConsoleAMsg(ChatColor.DARK_GREEN + command.toString());
//		command.register();
		
		
		//Timed Update Warn
			new BukkitRunnable(){
				@Override
				public void run(){
					//Checks For Update
					tellUp();
				}
			}.runTaskTimer(this, 200L, 72000L);
	}

	@Override
	public void onDisable() {
		
		//Checks For Update
		tellUp();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		return false;
	}
	
	//Sends A Message To The Console, With Chat Colors
	public static boolean sendConsoleAMsg(String msg) {
        try {
            Bukkit.getServer().getConsoleSender().sendMessage(msg);
            return true;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }
	
	//Tells User If Update Needed
	public void tellUp(){
		if(Checker.needUpdate(ID, getDescription().getVersion())){
			sendConsoleAMsg(ChatColor.RED + "The Version Of " + getDescription().getName() + " You Are Running Is Out Of Date. There Is A New Version Available At " + DOWNLOADURL);
		}else
			sendConsoleAMsg(ChatColor.GREEN + "The Version Of " + getDescription().getName() + " You Are Running Is Up To Date");
	}

	
}
