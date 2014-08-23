package me.johnnywoof;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CheckRunnable implements Runnable{
	
	@Override
	public void run() {
		
		long num = Value.getSpaceLeftInMB();
		
		if(num <= Value.space){
			
			if(Value.warn){
				
				for(Player p : Bukkit.getOnlinePlayers()){
					
					if(p.hasPermission("spacewarning.notify") || p.isOp()){
						
						if(!Value.silents.contains(p.getUniqueId())){
						
							p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.STRIKETHROUGH + "---------------------------------------");
							p.sendMessage(ChatColor.RED + "WARNING! The space on the drive is low!");
							p.sendMessage(ChatColor.RED + "Space left on drive: " + num + " MB (" + (num / 1024) + " GB)");
							p.sendMessage(ChatColor.RED + "To hide this message, please do /spacewarning silent");
							p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.STRIKETHROUGH + "---------------------------------------");
						
						}
						
					}
					
				}
				
			}
			
			Bukkit.getLogger().warning(ChatColor.DARK_RED + "" + ChatColor.STRIKETHROUGH + "---------------------------------------");
			Bukkit.getLogger().warning(ChatColor.RED + "WARNING! The space on the drive is low!");
			Bukkit.getLogger().warning(ChatColor.RED + "Space left on drive: " + num + " MB (" + (num / 1024) + " GB)");
			Bukkit.getLogger().warning(ChatColor.RED + "To hide this message, please do /spacewarning silent");
			Bukkit.getLogger().warning(ChatColor.DARK_RED + "" + ChatColor.STRIKETHROUGH + "---------------------------------------");
			
			if(Value.shutdown){
				
				for(Player p : Bukkit.getOnlinePlayers()){
					
					p.kickPlayer(ChatColor.RED + "Server has shutdown due to not enough space left on the drive.");
					
				}
				
				Bukkit.getLogger().severe("[SpaceWarning] Server is shutting down due to only " + num + " MB left on the drive!");
				
				if(!Value.multicraft) {
				
					Bukkit.getServer().shutdown();
				
				} else {
					
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "builtin:stop");
					
				}
				
			}
			
		}
		
	}

}
