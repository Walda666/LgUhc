package commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import lg.main;

public class CmdVote {
	private Player player;
	private String[] args;
	private main main;
	
	public CmdVote(Player player, String[] args, main main) {
		this.player = player;
		this.args = args;
		this.main = main;
	}
	
	public void run() {
		if(!main.config.isVote()) {
			player.sendMessage("Erreur : ce n'est pas le moment de voter");
		} else {
			if(!main.getPlayers().containsKey(player.getUniqueId())) {
				player.sendMessage("Erreur : vous n'êtes pas en jeu.");
			} else {
				if(main.config.containsVote(player.getUniqueId())) {
					player.sendMessage("Erreur : Vous ne pouvez voter qu'une fois par jour");
				} else {
					if(args.length != 1) {
						player.sendMessage("Erreur : il faut mettre le pseudo du joueur à voter");
					} else {
						Player target = Bukkit.getPlayer(args[0]);
						if(target == null) {
							player.sendMessage("Erreur : Joueur inconnu");
						} else {
							if(!main.getPlayers().containsKey(target.getUniqueId())) {
								player.sendMessage("Erreur : Ce joueur n'est pas dans la partie");
							} else {
								// enfin
								this.main.config.addVote(player.getUniqueId(), target.getUniqueId());
								player.sendMessage("§7Vous venez de voter pour §f" + target.getDisplayName() + ".");
							}
						}
					}
				}
			}
		}
	}		
}