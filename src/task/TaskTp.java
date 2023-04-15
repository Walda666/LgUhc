package task;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import lg.main;

public class TaskTp extends BukkitRunnable {
	private main main;
	private ArrayList<UUID> gars;
	
	public TaskTp(main main, ArrayList<UUID> gars) {
		this.main = main;
		this.gars = gars;
	}
	
	public void run() {
		if(this.main.actuTp < gars.size()) {
			Player p = Bukkit.getPlayer(gars.get(this.main.actuTp));
			if(p != null) {
				p.teleport(this.main.spawns.get(this.main.actuTp));
				Bukkit.broadcastMessage("§7Téléportation de §6" + p.getDisplayName() + " §7[§f" + (main.actuTp+1) + "§7/§f" + gars.size() + "§7]");
				System.out.println("tp" + p.getDisplayName());
				p.setLevel(0);
				p.setMaxHealth(20);
				p.getInventory().clear();
				for (ItemStack it : this.main.config.getItemsStart()) {
					p.getInventory().addItem(it);
				}
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999, 10, false, false));
				p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 99999, 255, false, false));
				p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 99999, 0, false, false));
				
			}
			
			this.main.actuTp++;
		} else if(this.main.actuTp == gars.size()){
			for (UUID uuid : gars) {
				Player p = Bukkit.getPlayer(uuid);
				if(p != null) {
					p.removePotionEffect(PotionEffectType.BLINDNESS);
					p.removePotionEffect(PotionEffectType.SLOW);
					p.removePotionEffect(PotionEffectType.JUMP);
				}
			}
			Bukkit.broadcastMessage("Bon chance");
			this.main.actuTp++;
			return;
		} else {
			return;
		}
	}
	
}
