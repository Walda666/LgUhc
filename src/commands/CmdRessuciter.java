package commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import lg.LgPlayer;
import lg.main;
import roles.LgRole;

public class CmdRessuciter {
	private Player player;
	private String[] args;
	private main main;
	
	public CmdRessuciter(Player player, String[] args, main main) {
		this.player = player;
		this.args = args;
		this.main = main;
	}
	
	public void run() {
		if(main.config.getPartieState() != "PLAYING") {
			player.sendMessage("Erreur : les rôles n'ont pas encore été annoncés");
		} else {
			if(!main.getPlayers().containsKey(player.getUniqueId())) {
				player.sendMessage("Erreur : vous n'êtes pas en jeu.");
			} else {
				if(!main.getPlayers().get(player.getUniqueId()).getRole().getNom().equals("Sorcière")) {
					player.sendMessage("Erreur : Vous n'êtes pas Sorcière");
				} else {
					if(main.getPlayers().get(player.getUniqueId()).getRole().getNbUses() < 1) {
						player.sendMessage("Erreur : Vous ne pouvez utiliser ce pouvoir qu'une fois dans la partie");
					} else {
						if(!main.getPlayers().get(player.getUniqueId()).getRole().isCanUse()) {
							player.sendMessage("Erreur : Trop tard cheh");
						} else {
							Player target = Bukkit.getPlayer(args[0]);
							if(target == null) {
								player.sendMessage("Erreur : Le joueur est déconnecté");
							} else {
								if(!main.getPlayers().containsKey(target.getUniqueId())) {
									player.sendMessage("Erreur : Ce joueur est déjà mort");
								} else {
									// enfin
									LgPlayer ap = main.getPlayers().get(target.getUniqueId());
									// tp loin
									target.sendMessage("Vous venez d'être réssucité par la sorcière !");
									target.getInventory().setHelmet(ap.getCasque());
									target.getInventory().setChestplate(ap.getPlastron());
									target.getInventory().setLeggings(ap.getJambes());
									target.getInventory().setBoots(ap.getBottes());
									for (ItemStack item : ap.getStuff()) {
										target.getInventory().addItem(item);
									}
									ap.setRez(true);
									Bukkit.getScheduler().runTaskLater(main, () -> { ap.setRez(false); }, 600L);
									
									player.sendMessage("§7Vous venez de réssuciter §f" + target.getDisplayName() + ".");
									this.main.getPlayers().get(player.getUniqueId()).getRole().use();
								}
							}
						}
					}
				}
			}
		}
		
	}

}
