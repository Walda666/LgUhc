package commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import lg.main;

public class CmdSalvater {
	private Player player;
	private String[] args;
	private main main;
	
	public CmdSalvater(Player player, String[] args, main main) {
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
				if(!main.getPlayers().get(player.getUniqueId()).getRole().getNom().equals("Salvateur")) {
					player.sendMessage("Erreur : Vous n'êtes pas Salvateur");
				} else {
					if(main.getPlayers().get(player.getUniqueId()).getRole().getNbUses() < 1) {
						player.sendMessage("Erreur : Vous ne pouvez utiliser ce pouvoir qu'une seule fois par épisode");
					} else {
						if(args.length != 1) {
							player.sendMessage("Erreur : il faut mettre le pseudo du joueur à salvater");
						} else {
							Player target = Bukkit.getPlayer(args[0]);
							if(target == null) {
								player.sendMessage("Erreur : Joueur inconnu");
							} else {
								if(!main.getPlayers().containsKey(target.getUniqueId())) {
									player.sendMessage("Erreur : Ce joueur n'est pas dans la partie");
								} else {
									if(main.getPlayers().get(player.getUniqueId()).getRole().getTarget() != null) {
										if(main.getPlayers().get(player.getUniqueId()).getRole().getTarget().equals(target.getUniqueId())) {
											player.sendMessage("Vous ne pouvez pas salvater la même personne deux jours de suite");
										} else {
											// enfin
											target.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*60*20, 0, false, false));
											this.main.getPlayers().get(target.getUniqueId()).setSalvate(true);
											player.sendMessage("§7Vous avez mis la salvation sur §f" + target.getDisplayName() + "§7. Il possèdera les effets Résistance et No Fall pendant 20 minutes.");
											target.sendMessage("§aVous venez de recevoir la salvation ! Vous possédez les effets Résistance et No Fall pendant 20 minutes");
											this.main.getPlayers().get(player.getUniqueId()).getRole().use();
											this.main.getPlayers().get(player.getUniqueId()).getRole().setTarget(target.getUniqueId());
										}
									} else {
										target.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*60*20, 0, false, false));
										this.main.getPlayers().get(target.getUniqueId()).setSalvate(true);
										player.sendMessage("§7Vous avez mis la salvation sur §f" + target.getDisplayName() + "§7. Il possèdera les effets Résistance et No Fall pendant 20 minutes.");
										target.sendMessage("§aVous venez de recevoir la salvation ! Vous possédez les effets Résistance et No Fall pendant 20 minutes");
										this.main.getPlayers().get(player.getUniqueId()).getRole().use();
										this.main.getPlayers().get(player.getUniqueId()).getRole().setTarget(target.getUniqueId());
									}
								}
							}
						}
					}
				}
			}
		}
		
	}

}
