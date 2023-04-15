package commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import lg.main;

public class CmdModele {
	private Player player;
	private String[] args;
	private main main;
	
	public CmdModele(Player player, String[] args, main main) {
		this.player = player;
		this.args = args;
		this.main = main;
	}
	
	public void run() {
		if(main.config.getPartieState() != "PLAYING") {
			player.sendMessage("Erreur : les r�les n'ont pas encore �t� annonc�s");
		} else {
			if(!main.getPlayers().containsKey(player.getUniqueId())) {
				player.sendMessage("Erreur : vous n'�tes pas en jeu.");
			} else {
				if(!main.getPlayers().get(player.getUniqueId()).getRole().getNom().equals("Enfant Sauvage")) {
					player.sendMessage("Erreur : Vous n'�tes pas Enfant Sauvage");
				} else {
					if(main.getPlayers().get(player.getUniqueId()).getRole().getNbUses() < 1) {
						player.sendMessage("Erreur : Vous avez d�j� choisi votre mod�le (/lg role)");
					} else {
						if(args.length != 1) {
							player.sendMessage("Erreur : il faut mettre le pseudo du mod�le voulu");
						} else {
							Player target = Bukkit.getPlayer(args[0]);
							if(target == null) {
								player.sendMessage("Erreur : Joueur inconnu");
							} else {
								if(!main.getPlayers().containsKey(target.getUniqueId())) {
									player.sendMessage("Erreur : Ce joueur n'est pas dans la partie");
								} else {
									// enfin
									player.sendMessage("�7Votre mod�le a �t� enregistr�. Vous pouvez le consulter � tout moment avec la commande /lg role");
									this.main.getPlayers().get(player.getUniqueId()).getRole().use();
									this.main.getPlayers().get(player.getUniqueId()).getRole().setTarget(target.getUniqueId());
									this.main.getPlayers().get(target.getUniqueId()).setModeleEs(true);	
								}
							}
						}
					}
				}
			}
		}
		
	}

}
