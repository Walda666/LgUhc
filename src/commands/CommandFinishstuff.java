package commands;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import lg.LgConfig;
import lg.LgPlayer;
import lg.main;

public class CommandFinishstuff implements CommandExecutor
{
    private main main;
    
    public CommandFinishstuff(main main) {
        this.main = main;
    }
    
    
    public boolean onCommand(final CommandSender sender, Command cmd, String msg, String[] arg) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player p = (Player)sender;
        LgConfig cf = this.main.config;
        for(ItemStack it : p.getInventory()) {
        	cf.getItemsStart().add(it);
        }
        
        p.getInventory().clear();
        ItemStack netherstar = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta meta = netherstar.getItemMeta();
        meta.setDisplayName("§3Configuration");
        netherstar.setItemMeta(meta);
        p.getInventory().setItem(4, netherstar);
        
       return true;
       
       
    }
}
