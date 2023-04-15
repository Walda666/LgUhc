package commands;

import java.util.ArrayList;
import java.util.Map;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import lg.LgPlayer;
import lg.main;
import roles.LgRole;

public class CommandAssign implements CommandExecutor
{
    private main main;
    
    public CommandAssign(main main) {
        this.main = main;
    }
    
    public boolean onCommand(final CommandSender sender, Command cmd, String msg, String[] arg) {
        System.out.println("assign");
        
        ArrayList<LgRole> compooo = this.main.getCompo();
        ArrayList<LgRole> compo = (ArrayList<LgRole>) compooo.clone();
        ArrayList<LgRole> compoRandom = new ArrayList<>();
        int random = 0;
        
        for(int i = compo.size()-1; i >= 0; i--) {
        	random = (int) (Math.random() * (compo.size()));
        	compoRandom.add(compo.get(random));
        	compo.remove(random);
        }
        
        int compteur = 0;
        
        for (final Map.Entry mapentry : this.main.getPlayers().entrySet()) {
            final LgPlayer ap = (LgPlayer) mapentry.getValue();
            if(ap.getPlayer() == null) {
            	this.main.getPlayers().values().remove(ap);
            } else {
	            if (ap.getPlayer().getGameMode().equals((Object)GameMode.SPECTATOR)) {
	                this.main.getPlayers().values().remove(ap);	
	            } else {
	                ap.setRole(compoRandom.get(compteur));
	                ++compteur;
	            }
            }
        }
        System.out.println(this.main.getCompo());
        System.out.println(this.main.getPlayers());
        
        return true;
    }
}