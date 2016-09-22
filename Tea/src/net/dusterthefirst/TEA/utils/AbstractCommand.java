package net.dusterthefirst.TEA.utils;

/*
 * The MIT License
 *
 * Copyright 2013 Goblom.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import java.lang.reflect.Field;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

/**
 * For a How-To on how to use AbstractCommand see this post @ http://forums.bukkit.org/threads/195990/
 * 
 * @author Goblom
 */
public abstract class AbstractCommand implements CommandExecutor, TabExecutor {
    
    protected String command;
    protected String description;
    protected List<String> alias;
    protected String usage;
    protected String permMessage;

    protected static CommandMap cmap;
    
    public AbstractCommand(String command, String usage, String description, String permissionMessage, List<String> aliases) {
        this.command = command.toLowerCase();
        this.usage = usage;
        this.description = description;
        this.permMessage = permissionMessage;
        this.alias = aliases;
    }
    
    public void register() {
        ReflectCommand cmd = new ReflectCommand(this.command);
        if (this.alias != null) cmd.setAliases(this.alias);
        if (this.description != null) cmd.setDescription(this.description);
        if (this.usage != null) cmd.setUsage(this.usage);
        if (this.permMessage != null) cmd.setPermissionMessage(this.permMessage);
        getCommandMap().register("", cmd);
        cmd.setExecutor(this);
    }
    
    final CommandMap getCommandMap() {
        if (cmap == null) {
            try {
                final Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                f.setAccessible(true);
                cmap = (CommandMap) f.get(Bukkit.getServer());
                return getCommandMap();
            } catch (Exception e) { e.printStackTrace(); }
        } else if (cmap != null) { return cmap; }
        return getCommandMap();
    }
    
    private final class ReflectCommand extends Command {
        private AbstractCommand exe = null;
        protected ReflectCommand(String command) { super(command); }
        public void setExecutor(AbstractCommand exe) { this.exe = exe; }
        @Override public boolean execute(CommandSender sender, String commandLabel, String[] args) {
            if (exe != null) { return exe.onCommand(sender, this, commandLabel, args); }
            return false;
        }
        
        @Override  public List<String> tabComplete(CommandSender sender, String alais, String[] args) {
            if (exe != null) { return exe.onTabComplete(sender, this, alais, args); }
            return null;
        }
    }
    
    public abstract boolean onCommand(CommandSender sender, Command cmd, String label, String[] args);
    
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return null;
    }

    public void addAlias(String alias){
    	this.alias.add(alias);
    }
    
	public void addAlias(String[] aliases){
		for(String s : aliases){
			this.alias.add(s);
		}
	}
	
	public void removeAlias(String alias){
		this.alias.remove(alias);
	}
	
	public List<String> getAliases(){
		return alias;
	}

	public void setUsage(String usage){
		this.usage = usage;
	}
	
	public String getUsage(){
		return this.usage;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public void setPermMessage(String permMessage){
		this.permMessage = permMessage;
	}
	
	public String getPermMessage(){
		return this.permMessage;
	}
	
	@Override
	public String toString() {
		return "\r\nCommand = " + this.command 
				+ "\r\nDescription = " + this.description 
				+ "\r\nAliases = " + this.alias.toString() 
				+ "\r\nUsage = " + this.usage 
				+ "\r\nPermission Message = " + this.permMessage;
	}
	
}