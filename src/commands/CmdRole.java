package commands;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import lg.LgPlayer;
import lg.main;
import roles.LgRole;

public class CmdRole {
	private Player player;
	private String[] args;
	private main main;
	
	public CmdRole(Player player, String[] args, main main) {
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
				LgPlayer ap = this.main.getPlayers().get(player.getUniqueId());
				LgRole ar = ap.getRole();
				player.sendMessage("Vous êtes " + ar.getNom() + ". Vous devez gagner avec les " + ar.getCamp() + ". " + ar.getDesc());
				ArrayList<UUID> soeurs = this.main.config.getPlayersByRole("Soeur");
		        ArrayList<UUID> mercenaires = this.main.config.getPlayersByRole("Mercenaire");
		        ArrayList<UUID> assassins = this.main.config.getPlayersByRole("Assassin");
		        ArrayList<UUID> anges = this.main.config.getPlayersByRole("Ange");
		        ArrayList<LgPlayer> loups = this.main.config.getPlayersCamp("§4Loup-Garou§f");
		        
		        if(ar.getNom().equals("Mercenaire")) {
		        	String desc = "Voici l'identité de l'assassin : ";
					for (UUID uuid : assassins) {
						desc += Bukkit.getOfflinePlayer(uuid).getName() + " ";
					}
					player.sendMessage(desc);
		        }
		        
		        if(ar.getNom().equals("Soeur")) {
		        	for (UUID soeur : soeurs) {
			        	String desc = "Voici l'identité de votre soeur : ";
						for (UUID uuid : soeurs) {
							if(!uuid.equals(soeur)) desc += Bukkit.getOfflinePlayer(uuid).getName() + " ";
						}
						player.sendMessage(desc);
			        }
		        }
		        	
	        	if(ar.getNom().equals("Ange")) {
	        		String target = Bukkit.getOfflinePlayer(ar.getTarget()).getName();
	        		if(ar.getType() == 0) {
	        			player.sendMessage("Vous avez choisi Ange Gardien. Votre protégé est " + target + ".");
	        		}
	        		if(ar.getType() == 1) {
	        			player.sendMessage("Vous avez choisi Ange Déchu. Votre cible est " + target + ".");
	        		}
	        	}
		        	
	        	if(ar.getNom().equals("Enfant Sauvage")) {
	        		String target = Bukkit.getOfflinePlayer(ar.getTarget()).getName();
	        		player.sendMessage("Votre modèle est " + target + ".");
	        	}
		        	
		        	
	        	String listelg = "§c";
	    		for (LgPlayer lgp : loups) {
	    			Player lg = Bukkit.getPlayer(lgp.getUuid());
	    			listelg += lg.getName() + ", ";
	    		}
	    		if(ar.getCamp().equals("§4Loup-Garou§f")) {
	    			player.sendMessage("§4Voici la liste des loups-garou :");
	    			player.getPlayer().sendMessage(listelg);
	    		}
		        
				if(ap.getCouple() != null) player.sendMessage("§d ♥ Vous êtes en couple avec " + Bukkit.getOfflinePlayer(ap.getCouple()).getName());
				if(ap.isCibleAnge()) player.sendMessage("Vous êtes lié à l'ange.");
			}
		}
	}
	
}
