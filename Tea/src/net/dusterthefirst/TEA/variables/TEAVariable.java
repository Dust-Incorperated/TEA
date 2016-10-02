package net.dusterthefirst.TEA.variables;

import java.util.HashMap;

import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;

import net.dusterthefirst.TEA.Main;
import net.dusterthefirst.TEA.logger.TEALogger;

public abstract class TEAVariable{
	
	protected Class<? extends TEAVariable> instance = this.getClass();
	protected HashMap<String, Object> variables = new HashMap<>();
	protected Main main = Main.getPlugin(Main.class);
	protected Server server = main.getServer();
	protected FileConfiguration config = main.getConfig();
	
	private TEALogger logger = new TEALogger();
	
	public abstract boolean onReload();
	public abstract Object get(String value);
	
	protected TEAVariable(){
		
	}
	
	protected void log(String log){
		logger.log(log, "Variable Initializer");
	}
	
	protected void error(String log){
		logger.error(log, "Variable Initializer");
	}
	
	protected void debug(String log){
		logger.debug(log);
	}
	
	protected void logDebugInfo(){
		logger.logDebugInfo();
	}
	
	@Override
	public String toString() {
		return variables.toString();
	}
	
	public static Class<?>[] getClasses(){
		return TEAVariable.class.getClasses();
	}
}
