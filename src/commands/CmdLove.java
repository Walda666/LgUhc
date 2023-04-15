package commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import lg.main;
import roles.LgRole;

public class CmdLove {
	private Player player;
	private String[] args;
	private main main;
	
	public CmdLove(Player player, String[] args, main main) {
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
				if(!main.getPlayers().get(player.getUniqueId()).getRole().getNom().equals("Cupidon")) {
					player.sendMessage("Erreur : Vous n'êtes pas Cupidon");
				} else {
					if(main.getPlayers().get(player.getUniqueId()).getRole().getNbUses() < 1) {
						player.sendMessage("Erreur : Vous ne pouvez utiliser ce pouvoir qu'une seule fois");
					} else {
						if(args.length != 2) {
							player.sendMessage("Erreur : il faut mettre les pseudos des deux joueurs à mettre en couple");
						} else {
							if(args[0].equals(args[1])) {
								player.sendMessage("Erreur : " + args[0] + " ne doit pas être une personne si narcissique ! Mettez-là avec un autre personne qu'elle même");
							}
							Player target = Bukkit.getPlayer(args[0]);
							Player target2 = Bukkit.getPlayer(args[1]);
							if(target == null || target2 == null) {
								player.sendMessage("Erreur : Un ou plusieurs des joueurs inconnus");
							} else {
								if(!main.getPlayers().containsKey(target.getUniqueId()) || !main.getPlayers().containsKey(target2.getUniqueId())) {
									player.sendMessage("Erreur : Un ou plusieurs des joueurs ne sont pas dans la partie");
								} else {
									// enfin									
									target.sendMessage("§dVous êtes amoureux de " + target2.getDisplayName() + " ! §1Si l'un d'entre vous meurt, l'aure ne pourra supporter cette souffrance et se suicidera immédiatement...");
									target.sendMessage("§1Vous possédez une boussole pointant constamment vers votre amoureux");
									target.getInventory().addItem(new ItemStack(Material.COMPASS, 1));
									main.getPlayers().get(target.getUniqueId()).setCouple(target2.getUniqueId());
									
									target2.sendMessage("§dVous êtes amoureux de " + target.getDisplayName() + " ! §1Si l'un d'entre vous meurt, l'aure ne pourra supporter cette souffrance et se suicidera immédiatement...");
									target2.sendMessage("§1Vous possédez une boussole pointant constamment vers votre amoureux");
									target2.getInventory().addItem(new ItemStack(Material.COMPASS, 1));
									main.getPlayers().get(target2.getUniqueId()).setCouple(target.getUniqueId());
									
									this.main.getPlayers().get(player.getUniqueId()).getRole().use();
									player.sendMessage("§7Vos flèches d'amour viennent de toucher §f" + target.getDisplayName() + "§7 et §f" + target2.getDisplayName() + "§7. Si l'un d'eux vient à mourir, il emportera l'autre avec lui dans sa tombe.");
								}
							}
						}
					}
				}
			}
		}
		
	}

}
