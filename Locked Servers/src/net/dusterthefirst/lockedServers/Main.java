package net.dusterthefirst.lockedServers;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftInventoryPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.dusterthefirst.lockedServers.update.Checker;
import net.dusterthefirst.lockedServers.utils.AnvilGUI;
import net.dusterthefirst.lockedServers.utils.Config;

public class Main extends JavaPlugin implements Listener{
	
	//DONT FORGET THE PLUGIN.YML
	
	//Id Of The Plugin Found On The Spigot Url
	private final String ID = "26676";
	//Download Page
	private final String DOWNLOADURL = "YourMOM.COM";
	
	public static String loginMessage;
	public static String loginIncorrect;
	public static String loginHelp;
	
	public static boolean OPEnabled;
	public static String OPKick;
	
	public static int loginAttempts;
	public static String loginKick;
	
	public static HashMap<String, Config> inventories = new HashMap<>();
	
	@Override
	public void onEnable(){
		//Does Intit Stuff
		getServer().getPluginManager().registerEvents(this, this);
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		
		getConfigValues();
		getPassword();
		setPassword("Hello World");
		
		//Timed Update Warn
		new BukkitRunnable(){
			@Override
			public void run(){
				//Checks For Update
				tellUp();
			}
		}.runTaskTimer(this, 200L, 72000L);
	}
	
	
	private void setPassword(String string) {
		// TODO Auto-generated method stub
		
	}


	private void getPassword() {
		// TODO Auto-generated method stub
		
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onJoin(PlayerJoinEvent e){
		try {
			Config config = inventories.get(e.getPlayer().getUniqueId());
			YamlConfiguration yml = config.getConfig();
			ItemStack[] storage = new ItemStack[yml.getList("storage").size()];
			for(int x = 0; x < yml.getList("storage").size(); x++){
				//copy for all
				storage[x] = (ItemStack) yml.getList("storage").get(x);
			}
			List<?> armor = yml.getList("armor");
			List<?> extra = yml.getList("offhand");
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onLeave(PlayerQuitEvent e){
		try{
			File file = new File(this.getDataFolder() + "/inventories", e.getPlayer().getUniqueId() + ".yml");
			file.getParentFile().mkdir();
			file.createNewFile();
			YamlConfiguration config = new YamlConfiguration();
			config.load(file);
			config.set("storage", e.getPlayer().getInventory().getStorageContents());
			config.set("armor", e.getPlayer().getInventory().getArmorContents());
			config.set("offhand", e.getPlayer().getInventory().getExtraContents());
			config.set("location", e.getPlayer().getLocation());
			config.save(file);
			Config inv = new Config(file, config);
			inventories.put(e.getPlayer().getUniqueId().toString(), inv);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void openAnvil(Player player){
		  AnvilGUI gui = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler(){
			  @Override
			  public void onAnvilClick(AnvilGUI.AnvilClickEvent event){
				  if(event.getSlot() == AnvilGUI.AnvilSlot.OUTPUT){
					  event.setWillClose(true);
					  event.setWillDestroy(true);
					   
					  player.sendMessage(event.getName());
				  } else {
					  event.setWillClose(false);
					  event.setWillDestroy(false);
				  }
			  }
		  });

		  ItemStack passItem = new ItemStack(Material.NAME_TAG);
		  ItemMeta passMeta = passItem.getItemMeta();
		  passMeta.setDisplayName(ChatColor.GREEN + "Password");
		  passMeta.setLore(new ArrayList<String>(
				    Arrays.asList(ChatColor.YELLOW + "Change This Name To The Server Password")));
		  passItem.setItemMeta(passMeta);
		  gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, passItem);
		  gui.open();
	}
	
	private void getConfigValues() {
		loginMessage = getConfig().getString("Login.Welcome");
		loginIncorrect = getConfig().getString("Login.Incorrect");
		loginHelp = getConfig().getString("Login.Help");
		
		OPEnabled = getConfig().getBoolean("OP Only.Enabled");
		OPKick = getConfig().getString("OP Only.Kick Message");
		
		loginAttempts = getConfig().getInt("Login Attempts.Amount");
		loginKick = getConfig().getString("Login Attempts.Kick Message");
	}
	
	@Override
	public void onDisable() {
		
		//Checks For Update
		tellUp();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.isOp()){
			sender.sendMessage(ChatColor.RED + "You Need To Be OP To Use This Command");
		}
		return true;
	}
	
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
