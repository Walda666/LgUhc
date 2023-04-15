package commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import lg.main;
import roles.LgRole;

public class CmdVoir {
	private Player player;
	private String[] args;
	private main main;
	
	public CmdVoir(Player player, String[] args, main main) {
		this.player = player;
		this.args = args;
		this.main = main;
	}
	
	public void run() {
		// 6. erreurs possibles : r�les pas annonc�s / pas en jeu / pas voyante / pas d'utilisation / pas bon nombre d'arguments / pas de joueur � ce pseudo // joueur mort
		if(main.config.getPartieState() != "PLAYING") {
			player.sendMessage("Erreur : les r�les n'ont pas encore �t� annonc�s");
		} else {
			if(!main.getPlayers().containsKey(player.getUniqueId())) {
				player.sendMessage("Erreur : vous n'�tes pas en jeu.");
			} else {
				if(!main.getPlayers().get(player.getUniqueId()).getRole().getNom().equals("Voyante") && !main.getPlayers().get(player.getUniqueId()).getRole().getNom().equals("Voyante Bavarde")) {
					player.sendMessage("Erreur : Vous n'�tes pas Voyante");
				} else {
					if(main.getPlayers().get(player.getUniqueId()).getRole().getNbUses() < 1) {
						player.sendMessage("Erreur : Vous ne pouvez utiliser ce pouvoir qu'une fois par jour");
					} else {
						if(args.length != 1) {
							player.sendMessage("Erreur : il faut mettre le pseudo du joueur � espionner");
						} else {
							Player target = Bukkit.getPlayer(args[0]);
							if(target == null) {
								player.sendMessage("Erreur : Joueur inconnu");
							} else {
								if(!main.getPlayers().containsKey(target.getUniqueId())) {
									player.sendMessage("Erreur : Ce joueur n'est pas dans la partie");
								} else {
									// enfin
									LgRole role = main.getPlayers().get(target.getUniqueId()).getRole();
									String rolenom = role.getFacade() == null ? role.getNom() : role.getFacade();
									if(main.getPlayers().get(player.getUniqueId()).getRole().getNom().equals("Voyante")) player.sendMessage("�7Le joueur �f" + target.getDisplayName() + "�7 est �f" + rolenom + ".");
									else Bukkit.broadcastMessage("�7La voyante bavarde a espionn� un joueur qui est �f" + rolenom + ".");
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
