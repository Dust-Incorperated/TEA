package net.dusterthefirst.lockedServers.utils;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

public class Config {

	private YamlConfiguration config;
	private File file;
	
	public Config(File file, YamlConfiguration config) {
		this.file = file;
		this.config = config;
	}
	
	public File getFile() {
		return file;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
	public YamlConfiguration getConfig() {
		return config;
	}
	
	public void setConfig(YamlConfiguration config) {
		this.config = config;
	}
	
}
