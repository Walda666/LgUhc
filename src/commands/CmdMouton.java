package commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import lg.main;
import roles.Berger;
import roles.LgRole;

public class CmdMouton {
	private Player player;
	private String[] args;
	private main main;
	
	public CmdMouton(Player player, String[] args, main main) {
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
				if(!main.getPlayers().get(player.getUniqueId()).getRole().getNom().equals("Berger")) {
					player.sendMessage("Erreur : Vous n'êtes pas Berger");
				} else {
					if(main.getPlayers().get(player.getUniqueId()).getRole().getNbUses() < 1) {
						player.sendMessage("Erreur : Vous ne pouvez utiliser ce pouvoir que deux fois dans la partie");
					} else {
						if(args.length != 1) {
							player.sendMessage("Erreur : il faut mettre le pseudo du joueur sur qui mettre un mouton");
						} else {
							Player target = Bukkit.getPlayer(args[0]);
							if(target == null) {
								player.sendMessage("Erreur : Joueur inconnu");
							} else {
								if(!main.getPlayers().containsKey(target.getUniqueId())) {
									player.sendMessage("Erreur : Ce joueur n'est pas dans la partie");
								} else {
									// enfin
									Berger role = (Berger) main.getPlayers().get(player.getUniqueId()).getRole();
									if(role.getNbUses() == 2) role.setMouton1(target.getDisplayName());
									if(role.getNbUses() == 1) role.setMouton2(target.getDisplayName());
									player.sendMessage("§7Vous venez de poser un mouton sur §f" + target.getDisplayName() + ".");
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
