package commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import lg.main;
import roles.LgRole;

public class CmdFlairer {
	private Player player;
	private String[] args;
	private main main;
	
	public CmdFlairer(Player player, String[] args, main main) {
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
				if(!main.getPlayers().get(player.getUniqueId()).getRole().getNom().equals("Renard")) {
					player.sendMessage("Erreur : Vous n'êtes pas Renard");
				} else {
					if(main.getPlayers().get(player.getUniqueId()).getRole().getNbUses() < 1) {
						player.sendMessage("Erreur : Vous ne pouvez utiliser ce pouvoir que trois fois dans la partie");
					} else {
						if(args.length != 1) {
							player.sendMessage("Erreur : il faut mettre le pseudo du joueur à flairer");
						} else {
							Player target = Bukkit.getPlayer(args[0]);
							if(target == null) {
								player.sendMessage("Erreur : Joueur inconnu");
							} else {
								if(!main.getPlayers().containsKey(target.getUniqueId())) {
									player.sendMessage("Erreur : Ce joueur n'est pas dans la partie");
								} else {
									boolean isArround = false;
									List<Entity> nearList = (List<Entity>)player.getNearbyEntities(10.0, 120.0, 10.0);
			        	        	for (Entity entity : nearList) {
			        	        		if (entity instanceof Player) {
			        	        			if(entity.getUniqueId().equals(target.getUniqueId())) isArround = true;
			        	        		}
			        	        	}
									if(!isArround) {
										player.sendMessage("Erreur : Le joueur ciblé doit être dans un rayon de 10 blocs autour de vous");
									} else {
										// enfin
										LgRole role = main.getPlayers().get(target.getUniqueId()).getRole();
										String desc = "§aCe joueur n'appartient pas au camp des Loup-Garous";
										if(role.getCamp() == "§4Loup-Garou§f" && role.getFacade() == null) {
											desc = "§4Ce joueur appartient au camp des Loup-Garous";
										}
										player.sendMessage(desc);
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

}
