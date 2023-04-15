package commands;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import lg.LgPlayer;
import lg.main;
import roles.Berger;
import roles.LgRole;

public class CmdSoeurChat {
	private Player player;
	private String[] args;
	private main main;
	
	public CmdSoeurChat(Player player, String[] args, main main) {
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
				if(!main.getPlayers().get(player.getUniqueId()).getRole().getNom().equals("Soeur")) {
					player.sendMessage("Erreur : Vous n'êtes pas Soeur");
				} else {
					if(main.getPlayers().get(player.getUniqueId()).getRole().getNbUses() < 1) {
						player.sendMessage("Erreur : Vous ne pouvez envoyer des message que le jour");
					} else {
						if(args.length < 1) {
							player.sendMessage("Erreur : il faut mettre un message");
						} else {
							String msg = args[0];
							for(int i = 1; i < args.length; i++) msg += " " + args[i];
							// enfin
							 ArrayList<UUID> soeurs = this.main.config.getPlayersByRole("Soeur");
						        for (UUID s : soeurs) {
						        	Player soeur = Bukkit.getPlayer(s);
						        	if(s != null) {
						        		soeur.sendMessage("§7[soeurs] " + player.getDisplayName() + " » " + msg);
						        	}
						        }
						}
					}
				}
			}
		}
		
	}

}
