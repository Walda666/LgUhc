package commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import lg.main;

public class CmdPan {
	private Player player;
	private String[] args;
	private main main;
	
	public CmdPan(Player player, String[] args, main main) {
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
				if(!main.getPlayers().get(player.getUniqueId()).getRole().getNom().equals("Chasseur")) {
					player.sendMessage("Erreur : Vous n'êtes pas Chasseur");
				} else {
					if(main.getPlayers().get(player.getUniqueId()).getRole().getNbUses() < 1) {
						player.sendMessage("Erreur : Vous ne pouvez utiliser ce pouvoir qu'une seule fois");
					} else {
						if(args.length != 1) {
							player.sendMessage("Erreur : il faut mettre le pseudo du joueur sur qui tirer");
						} else {
							Player target = Bukkit.getPlayer(args[0]);
							if(target == null) {
								player.sendMessage("Erreur : Joueur inconnu");
							} else {
								if(!main.getPlayers().containsKey(target.getUniqueId())) {
									player.sendMessage("Erreur : Ce joueur n'est pas dans la partie");
								} else {
									// enfin
									target.setHealth(target.getHealth() / 2);
									Bukkit.broadcastMessage("§7Le chasseur a tiré sur §f" + target.getDisplayName() + "§7 ! Celui ci perd la moitié de sa vie");
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
