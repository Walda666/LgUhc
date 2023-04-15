package commands;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import lg.LgPlayer;
import lg.main;
import roles.Berger;
import roles.LgRole;

public class CmdAnge {
	private Player player;
	private String[] args;
	private main main;
	
	public CmdAnge(Player player, String[] args, main main) {
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
				if(!main.getPlayers().get(player.getUniqueId()).getRole().getNom().equals("Ange")) {
					player.sendMessage("Erreur : Vous n'êtes pas Ange");
				} else {
					if(main.getPlayers().get(player.getUniqueId()).getRole().getNbUses() < 1) {
						player.sendMessage("Erreur : Vous avez déjà choisi votre forme. /lg role pour plus d'informations");
					} else {
						LgRole ap = this.main.getPlayers().get(player.getUniqueId()).getRole();
						// target random
						ArrayList<LgPlayer> alive = new ArrayList<>();
						for (Map.Entry mapentry : this.main.getPlayers().entrySet()) {
							LgPlayer lgp = (LgPlayer) mapentry.getValue();
							if(!lgp.getPlayer().getUniqueId().equals(player.getUniqueId())) alive.add((LgPlayer) mapentry.getValue());
						}
	
						Random r = new Random();
						LgPlayer target = alive.get(r.nextInt(alive.size()));
						ap.setTarget(target.getUuid());
						this.main.getPlayers().get(target.getUuid()).setCibleAnge(true);
						
						if(args[0].equals("gardien")) {
							ap.use();
							ap.setType(0);
							target.getPlayer().sendMessage("Votre destin est lié à celui d'un ange qui devra vous protéger.");
							player.sendMessage("Vous venez de choisir Ange Gardien ! Votre protégé est " + target.getPlayer().getDisplayName());
							player.sendMessage("S'il meurt vous retomberez à 10 coeurs permanents et devrez gagner seul. Vous disposez de la commande /lg regen pour donner Régénération à votre protégé pendant 1 minute.");
							player.setMaxHealth(30);
						}
						
						if(args[0].equals("dechu")) {
							ap.use();
							ap.setType(1);
							player.sendMessage("Vous venez de choisir Ange Déchu ! Votre cible est " + target.getPlayer().getDisplayName());
							player.sendMessage("Si vous parvenez à la tuer, vous gagnerez 3 coeurs permanents, pour un total de 15");
							player.setMaxHealth(24);
						}						
					}
				}
			}
		}
		
	}

}
