package commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import lg.main;

public class CmdConfesser {
	private Player player;
	private String[] args;
	private main main;
	
	public CmdConfesser(Player player, String[] args, main main) {
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
				if(!main.getPlayers().get(player.getUniqueId()).getRole().getNom().equals("Confesseur")) {
					player.sendMessage("Erreur : Vous n'êtes pas Confesseur");
				} else {
					if(main.getPlayers().get(player.getUniqueId()).getRole().getNbUses() < 1) {
						player.sendMessage("Erreur : Vous ne pouvez utiliser ce pouvoir qu'une seule fois dans la partie");
					} else {
						if(args.length != 1) {
							player.sendMessage("Erreur : il faut mettre le pseudo du joueur à confesser");
						} else {
							Player target = Bukkit.getPlayer(args[0]);
							if(target == null) {
								player.sendMessage("Erreur : Joueur inconnu");
							} else {
								if(!main.getPlayers().containsKey(target.getUniqueId())) {
									player.sendMessage("Erreur : Ce joueur n'est pas dans la partie");
								} else {
									// enfin
									String role = main.getPlayers().get(target.getUniqueId()).getRole().getNom();
									player.sendMessage("§7Le joueur §f" + target.getDisplayName() + " §7est §f" + role + ".");
									player.sendMessage("§7Il a effectué §b" + main.getPlayers().get(target.getUniqueId()).getKill() + "§7 kill(s)" + ".");
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
