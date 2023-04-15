package commands;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import lg.main;

public class CmdCitoyen {
	private Player player;
	private String[] args;
	private main main;
	
	public CmdCitoyen(Player player, String[] args, main main) {
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
				if(!main.getPlayers().get(player.getUniqueId()).getRole().getNom().equals("Citoyen")) {
					player.sendMessage("Erreur : Vous n'êtes pas Citoyen");
				} else {
					if(main.getPlayers().get(player.getUniqueId()).getRole().getNbUses() < 1) {
						player.sendMessage("Erreur : Vous ne pouvez utiliser ce pouvoir que deux fois dans la partie");
					} else {
						if(!main.getPlayers().get(player.getUniqueId()).getRole().isCanUse()) {
							player.sendMessage("Erreur : Il est trop tard pour utiliser ce pouvoir");
						} else {
							// enfin
							this.main.getPlayers().get(player.getUniqueId()).getRole().use();
							player.sendMessage("Liste des joueurs ayant votés ce jour ci :");
							player.sendMessage("");
							for (Map.Entry mapentry : this.main.config.getVotes().entrySet()) {
								Player p = (Player) Bukkit.getOfflinePlayer((UUID) mapentry.getKey());
								Player t = (Player) Bukkit.getOfflinePlayer((UUID) mapentry.getValue());
								player.sendMessage("§7" + p.getDisplayName() + " => " + t.getDisplayName());
							}
						}
					}	
				}
			}
		}
		
	}

}
