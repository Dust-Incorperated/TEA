package net.dusterthefirst.TEA.utils.internal;

import java.io.File;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.google.common.collect.Lists;

import net.dusterthefirst.TEA.FileManager;
import net.dusterthefirst.TEA.utils.AbstractCommand;

/** The core TEA command.
 * <br>
 * <br>Functionality for /tea skripts <reload|list> provided by 2008Choco
 * @author Parker Hawke - 2008Choco
 */
public class TEACmd extends AbstractCommand {

	public TEACmd() {
		super("tea", 
			"/tea <scripts>", 
			"The main TEA command to manage TEA scripts", 
			"You do not have sufficient privileges to run this command", 
			Lists.<String>newArrayList()
		);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length >= 1){
			if (args[0].equalsIgnoreCase("scripts")){
				if (args.length >= 2){
					if (args[1].equalsIgnoreCase("reload")){
						FileManager.getFiles();
						// Not sure whether a prefix is set or not. a Main#sendMessage() method is suggested here
						sender.sendMessage(ChatColor.GREEN + "All TEA scripts have been reloaded");
					}
					
					else if (args[1].equalsIgnoreCase("list")){
						List<File> scripts = FileManager.scriptManager.getFiles();
						if (scripts.size() == 0){
							sender.sendMessage(ChatColor.RED + "No TEA scripts are currently loaded");
							return true;
						}
						
						for (File file : scripts)
							sender.sendMessage((file.exists() ? ChatColor.GREEN : ChatColor.RED) + file.getName());
					}
					
					else{
						sender.sendMessage(ChatColor.RED + "Unknown parameter (\"" + args[1] + "\")! " + ChatColor.GRAY + "Expected: <reload|list>");
					}
					
					return true;
				}
				
				else{
					sender.sendMessage(ChatColor.RED + "Missing parameter! " + ChatColor.GRAY + "Expected: <reload|list>");
				}
			}
			
			else{
				sender.sendMessage(this.getUsage());
			}
		}else{
			sender.sendMessage(this.getUsage());
		}
		return true;
	}
}