package commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import lg.main;
import roles.LgRole;

public class CmdComparer {
	private Player player;
	private String[] args;
	private main main;
	
	public CmdComparer(Player player, String[] args, main main) {
		this.player = player;
		this.args = args;
		this.main = main;
	}
	
	public void run() {
		// 6. erreurs possibles : rôles pas annoncés / pas en jeu / pas voyante / pas d'utilisation / pas bon nombre d'arguments / pas de joueur à ce pseudo // joueur mort
		if(main.config.getPartieState() != "PLAYING") {
			player.sendMessage("Erreur : les rôles n'ont pas encore été annoncés");
		} else {
			if(!main.getPlayers().containsKey(player.getUniqueId())) {
				player.sendMessage("Erreur : vous n'êtes pas en jeu.");
			} else {
				if(!main.getPlayers().get(player.getUniqueId()).getRole().getNom().equals("Détective")) {
					player.sendMessage("Erreur : Vous n'êtes pas Détective");
				} else {
					if(main.getPlayers().get(player.getUniqueId()).getRole().getNbUses() < 1) {
						player.sendMessage("Erreur : Vous ne pouvez utiliser ce pouvoir qu'une fois par jour");
					} else {
						if(args.length != 2) {
							player.sendMessage("Erreur : il faut mettre les pseudos des deux joueurs à comparer");
						} else {
							Player target = Bukkit.getPlayer(args[0]);
							Player target2 = Bukkit.getPlayer(args[1]);
							if(target == null || target2 == null) {
								player.sendMessage("Erreur : Un ou plusieurs des joueurs inconnus");
							} else {
								if(!main.getPlayers().containsKey(target.getUniqueId()) || !main.getPlayers().containsKey(target2.getUniqueId())) {
									player.sendMessage("Erreur : Un ou plusieurs des joueurs ne sont pas dans la partie");
								} else {
									// enfin
									String camp1 = this.main.getPlayers().get(target.getUniqueId()).getRole().getFacade() == null ? this.main.getPlayers().get(target.getUniqueId()).getRole().getCamp() : "§fVillageois§f";
									String camp2 = this.main.getPlayers().get(target2.getUniqueId()).getRole().getFacade() == null ? this.main.getPlayers().get(target2.getUniqueId()).getRole().getCamp() : "§fVillageois§f";
									String reponse = camp1.equals(camp2) ? "§a" + target.getDisplayName() + " et " + target2.getDisplayName() + " font parti du même camp !" : "§c" + target.getDisplayName() + " et " + target2.getDisplayName() + " ne font pas parti du même camp !";
									player.sendMessage(reponse);
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
