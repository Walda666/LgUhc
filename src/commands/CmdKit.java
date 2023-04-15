package commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import lg.main;

public class CmdKit {
	private Player player;
	private String[] args;
	private main main;
	
	public CmdKit(Player player, String[] args, main main) {
		this.player = player;
		this.args = args;
		this.main = main;
	}
	
	public void run() {
		if(main.config.getPartieState() != "PLAYING") {
			player.sendMessage("Erreur : les rôles n'ont pas encore été annoncés");
		} else {
			if(!main.getPlayers().containsKey(player.getUniqueId())) {
				player.sendMessage("Erreur : vous n'êtes pas en jeu");
			} else {
				if(main.getPlayers().get(player.getUniqueId()).getKit()) player.sendMessage("Erreur : vous avez déjà récupéré votre kit");
				else {
					ArrayList<ItemStack> items = main.getPlayers().get(player.getUniqueId()).getRole().getItems();
					if(items.size() == 0) player.sendMessage("Erreur : vous ne possédez pas de kit");
					else {
						for (ItemStack it : items) {
							player.getInventory().addItem(it);
						}
						player.sendMessage("§7Vous venez de récupérer vos items");
						main.getPlayers().get(player.getUniqueId()).setKit(true);
					}
				}
			}
		}
	}

}
