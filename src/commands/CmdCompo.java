package commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import lg.main;
import roles.LgRole;

public class CmdCompo {
	private Player player;
	private String[] args;
	private main main;
	
	public CmdCompo(Player player, String[] args, main main) {
		this.player = player;
		this.args = args;
		this.main = main;
	}
	
	public void run() {
		if(this.main.config.isCompocache()) player.sendMessage("§cLa composition de la partie est cachée !");
		else {
			 HashMap<String, Integer> compoTriee = this.main.getCompoTriee();
		        String toSend = "";
		        
		       
		        for (final Map.Entry mapentry : compoTriee.entrySet()) {
		        	toSend += "§f- " + mapentry.getKey();
		        	if((int) mapentry.getValue() > 1) toSend += " (x" + mapentry.getValue() + ")";
		        	toSend += "\n\n";
		        }
		        player.sendMessage(toSend);
		}
		
	}
}
