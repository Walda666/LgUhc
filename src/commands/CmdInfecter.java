package commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import lg.LgPlayer;
import lg.main;
import roles.LgRole;

public class CmdInfecter {
	private Player player;
	private String[] args;
	private main main;
	
	public CmdInfecter(Player player, String[] args, main main) {
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
				if(!main.getPlayers().get(player.getUniqueId()).getRole().getNom().equals("Infect Père des Loups")) {
					player.sendMessage("Erreur : Vous n'êtes pas Infect Père des Loups");
				} else {
					if(main.getPlayers().get(player.getUniqueId()).getRole().getNbUses() < 1) {
						player.sendMessage("Erreur : Vous ne pouvez utiliser ce pouvoir qu'une fois dans la partie");
					} else {
						if(!main.getPlayers().get(player.getUniqueId()).getRole().isCanUse()) {
							player.sendMessage("Erreur : Trop tard cheh");
						} else {
							Player target = Bukkit.getPlayer(args[0]);
							if(target == null) {
								player.sendMessage("Erreur : Le joueur est déconnecté");
							} else {
								if(!main.getPlayers().containsKey(target.getUniqueId())) {
									player.sendMessage("Erreur : Ce joueur est déjà mort");
								} else {
									// enfin
									LgPlayer ap = main.getPlayers().get(target.getUniqueId());
									// tp loin
									target.sendMessage("§7L'infect Père des Loups à décidé de vous réssuciter ! Vous êtes loups blabla !");
									target.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999, 0, false, false));
									ap.getRole().getEffetsPerma().add(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 0, false, false));
									ap.getRole().setCamp("§4Loup-Garou§f");
									target.getInventory().setHelmet(ap.getCasque());
									target.getInventory().setChestplate(ap.getPlastron());
									target.getInventory().setLeggings(ap.getJambes());
									target.getInventory().setBoots(ap.getBottes());
									for (ItemStack item : ap.getStuff()) {
										target.getInventory().addItem(item);
									}
									ap.setRez(true);
									Bukkit.getScheduler().runTaskLater(main, () -> { ap.setRez(false); }, 600L);
									
									player.sendMessage("§7Vous venez d'infecter " + target.getDisplayName() + ".");
									this.main.getPlayers().get(player.getUniqueId()).getRole().use();
									this.main.getPlayers().get(player.getUniqueId()).getRole().setTarget(target.getUniqueId());
									// affiche liste loup
    								// prévenir les loups
									ArrayList<LgPlayer> loups = this.main.config.getPlayersCamp("§4Loup-Garou§f"); 
    								String listelg = "§c";
    								for (LgPlayer lgp : loups) {
										Player lg = lgp.getPlayer();
										Player lgoffline = (Player) Bukkit.getOfflinePlayer(lgp.getUuid());
										listelg += lgoffline.getDisplayName() + ", ";
										if(lg != null && (!lg.getUniqueId().equals(target.getUniqueId()))) lg.sendMessage("§4Un joueur a rejoint votre camp. Faites /lg role pour découvrir son pseudo");
									}
    								target.sendMessage("§4Voici la liste des loups-garou :");
    								target.sendMessage(listelg);
								}
							}
						}
					}
				}
			}
		}
		
	}

}
