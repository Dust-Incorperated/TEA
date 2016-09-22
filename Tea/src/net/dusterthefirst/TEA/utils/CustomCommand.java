package net.dusterthefirst.TEA.utils;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CustomCommand extends AbstractCommand{
	
	public CustomCommand(String command, String usage, String description, String permissionMessage, List<String> aliases) {
		super(command, usage, description, permissionMessage, aliases);
		
	}

	public void setOnCommand(){
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {	
		return false;
	}
	
}
