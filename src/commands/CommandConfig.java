package commands;

import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

import lg.InventairesConfig;
import lg.TitleManager;
import lg.main;
import roles.Ancien;

public class CommandConfig implements CommandExecutor
{
    private main main;
    
    public CommandConfig(main main) {
        this.main = main;
    }
    
    public ItemStack createItem(Material material, String nom) {
        ItemStack it = new ItemStack(material, 1);
        ItemMeta metaIt = it.getItemMeta();
        metaIt.setDisplayName(nom);
        it.setItemMeta(metaIt);
        return it;
    }
    
    public boolean onCommand(final CommandSender sender, Command cmd, String msg, String[] arg) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player)sender;
        

        InventairesConfig ivcf = new InventairesConfig(main);
        player.openInventory(ivcf.getConfig());
     
        
       // Bukkit.broadcastMessage("§6>>§f§m--------------------------------------§6<<\n§fVous");
        
        
       TitleManager tm = new TitleManager();
      
       return true;
       
       
    }
}
