package commands;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import lg.LgPlayer;
import lg.main;

public class CommandDecoreco implements CommandExecutor
{
    private main main;
    
    public CommandDecoreco(main main) {
        this.main = main;
    }
    
    
    public boolean onCommand(final CommandSender sender, Command cmd, String msg, String[] arg) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player)sender;
        
        
        this.main.getPlayers().clear();
        for(Player p : Bukkit.getOnlinePlayers()) {
        	 this.main.getPlayers().put(player.getUniqueId(), new LgPlayer(player));
        	 this.main.sm.join(player);
        }

        
     
       return true;
       
       
    }
}
