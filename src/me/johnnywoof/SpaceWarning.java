package me.johnnywoof;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class SpaceWarning extends JavaPlugin{
	
	public void onEnable(){
		
		this.reload();
		
	}
	
	public void onDisable(){
		
		this.getServer().getScheduler().cancelTasks(this);
		
		this.getLogger().info("[SpaceWarning] Disabled!");
		
	}
	
	public void reload(){
		
		this.getServer().getScheduler().cancelTasks(this);
		
		if(!getDataFolder().exists()){
			getDataFolder().mkdir();
		}
		
		if(!new File(this.getDataFolder() + File.separator + "config.yml").exists()){
		
			this.saveDefaultConfig();
		
		}
		
		this.reloadConfig();
		
		FileConfiguration config = this.getConfig();
		
		Value.shutdown = config.getBoolean("shutdown");
		Value.multicraft = config.getBoolean("multicraft");
		Value.warn = config.getBoolean("warn-players");
		Value.space = config.getLong("space-left");
		
		this.getServer().getScheduler().runTaskTimer(this, new CheckRunnable(), 10, (20 * config.getInt("check-interval")));
		
		config = null;
		
		this.getLogger().info("[SpaceWarning] Space detection: " + Value.space + " MB");
		this.getLogger().info("[SpaceWarning] Shutdown server: " + Value.shutdown);
		this.getLogger().info("[SpaceWarning] Using multicraft: " + Value.multicraft);
		this.getLogger().info("[SpaceWarning] Warn players with permission: " + Value.warn);
		this.getLogger().info("[SpaceWarning] Loaded and ready.");
		
	}
	
	public boolean onCommand(final CommandSender sender, Command cmd, String commandLabel, String[] args) {
			
			if(args.length <= 0){
			
				if(sender.hasPermission("spacewarning.check") || sender.isOp()){
				
					if(System.getProperty("os.name") != null){
						sender.sendMessage(ChatColor.GREEN + "OS: " + System.getProperty("os.name").toString().toLowerCase());
					}else{
						sender.sendMessage(ChatColor.GREEN + "OS: null");
					}
					sender.sendMessage(ChatColor.GREEN + "System Architecture: " + System.getProperty("os.arch").toString().toLowerCase());
					sender.sendMessage(ChatColor.GREEN + "System Version: " + System.getProperty("os.version").toString().toLowerCase());
					sender.sendMessage(ChatColor.GREEN + "System Username: " + System.getProperty("user.name").toString().toLowerCase());
					sender.sendMessage(ChatColor.GREEN + "Java Version: " + System.getProperty("java.version").toString().toLowerCase());
					sender.sendMessage(ChatColor.GREEN + "Available Processors: " + Runtime.getRuntime().availableProcessors());
					sender.sendMessage(ChatColor.GREEN + "Total Ram: " + (Runtime.getRuntime().maxMemory() / 1048576) + "MB");
					sender.sendMessage(ChatColor.GREEN + "Version: " + this.getServer().getBukkitVersion().toString().toLowerCase());
					long num = Value.getSpaceLeftInMB();
					sender.sendMessage(ChatColor.GREEN + "Disk Space Left: " + (num) + "MB (" + (num / 1024) + "GB)");

				}else{
					
					sender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
					
				}
				
			}else{
				
				if(args[0].equalsIgnoreCase("silent")){
					
					if(sender.hasPermission("spacewarning.silent") || sender.isOp()){
					
						boolean silent = Value.silents.contains(sender.getName());
						
						if(silent){
							
							Value.silents.remove(sender.getName());
							sender.sendMessage(ChatColor.RED + "Alerts are no longer hidden for you.");
							
						}else{
							
							Value.silents.add(sender.getName());
							sender.sendMessage(ChatColor.RED + "Alerts are now hidden for you.");
							
						}
					
					}else{
						
						sender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
						
					}
					
				}else if(args[0].equalsIgnoreCase("reload")){
					
					if(sender.hasPermission("spacewarning.reload") || sender.isOp()){
						
						this.reload();
						sender.sendMessage(ChatColor.GREEN + "Configuration has been reloaded.");
						
					}else{
						
						sender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
						
					}
					
				}
				
			}
	
		return true;
		
	}
	
}
