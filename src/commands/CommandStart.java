package commands;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;

import lg.LgPlayer;
import lg.TitleManager;
import lg.main;

public class CommandStart implements CommandExecutor
{
    private main main;
    private TitleManager tm;
    
    public CommandStart(main main) {
        this.main = main;
        this.tm = new TitleManager();
    }
    
    public boolean onCommand(final CommandSender sender, Command cmd, String msg, String[] arg) {
        System.out.println("start");
        
        this.main.config.setPartieState("PLAYING");
        this.main.getPlayers().forEach((key, value) -> {
        	
            String role_nom = value.getRole().getNom();
            ArrayList<ItemStack> items = value.getRole().getItems();
            Player j = Bukkit.getPlayer(key);
            
         
            
            // add slots de coeur et pierre Loki si pas certains rôles
            
			if(value.getRole().getCamp().equals("§6Solo§f")) {
				 j.sendMessage("§6>§f--------------------------------------§6<\n§fVous êtes " + role_nom + ".\nVous devez gagner §6seul§f.\n" + value.getRole().getDesc() + "§6====================================================\n");
			} else {
				if (value.getRole().getMates() != null) j.sendMessage("§6====================================================\n§fVous êtes " + role_nom + ".\nVous devez gagner avec les " + value.getRole().getCamp() + ".\n" + value.getRole().getDesc() + "§6====================================================§f");
				else j.sendMessage("§6>>§f§m--------------------------------------§6<<\n§fVous êtes " + role_nom + ".\nVous devez gagner avec les " + value.getRole().getCamp() + ".\n" + value.getRole().getDesc() + "\n§6>>§f§m--------------------------------------§6<<\n");
			}
            
			for(int i = 0; i < value.getRole().getEffetsPerma().size(); i++) {
				j.addPotionEffect(value.getRole().getEffetsPerma().get(i));
			}
			
			if(value.getRole().getNom().equals("Loup-Garou Blanc")) {
				j.setMaxHealth(30);
			}
            
            return;
        });
        
        // soeur, lg, mercenaire pseudos mates / ange choix
        ArrayList<UUID> soeurs = this.main.config.getPlayersByRole("Soeur");
        ArrayList<UUID> mercenaires = this.main.config.getPlayersByRole("Mercenaire");
        ArrayList<UUID> assassins = this.main.config.getPlayersByRole("Assassin");
        ArrayList<UUID> anges = this.main.config.getPlayersByRole("Ange");
        ArrayList<LgPlayer> loups = this.main.config.getPlayersCamp("§4Loup-Garou§f");
        for (UUID mercenaire : mercenaires) {
			String desc = "Voici l'identité de l'assassin : ";
			for (UUID uuid : assassins) {
				desc += Bukkit.getOfflinePlayer(uuid).getName() + " ";
			}
			Player merc = Bukkit.getPlayer(mercenaire);
			merc.sendMessage(desc);
		}
        
        for (UUID soeur : soeurs) {
			String desc = "Voici l'identité de votre soeur : ";
			for (UUID uuid : soeurs) {
				if(!uuid.equals(soeur)) desc += Bukkit.getOfflinePlayer(uuid).getName() + " ";
			}
			Player soeu = Bukkit.getPlayer(soeur);
			soeu.sendMessage(desc);
		}
        
        String listelg = "§c";
		for (LgPlayer lgp : loups) {
			Player lg = (Player) Bukkit.getPlayer(lgp.getUuid());
			listelg += lg.getDisplayName() + ", ";
		}
		
		for (LgPlayer lgp : loups) {
			lgp.getPlayer().sendMessage("§4Voici la liste des loups-garou :");
			lgp.getPlayer().sendMessage(listelg);
		}
		
		for (UUID ange : anges) {
			Player ang = Bukkit.getPlayer(ange);
			ang.sendMessage(("Vous pouvez choisir votre destinée en cliquant sur ce message blabla."));
			ang.spigot().sendMessage(tm.getClickableCommand(ang, "§1Ange Gardien§f", "lg ange gardien"));
			ang.spigot().sendMessage(tm.getClickableCommand(ang, "§4Ange Déchu§f", "lg ange dechu"));
		}
        
        
        
        
        
    return true;
    }
}