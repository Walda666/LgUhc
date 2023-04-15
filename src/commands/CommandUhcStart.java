package commands;

import java.util.Map.Entry;
import java.util.Random;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import lg.LgConfig;
import lg.LgPlayer;
import lg.main;
import task.TaskTp;

public class CommandUhcStart implements CommandExecutor
{
    private main main;
    
    public CommandUhcStart(main main) {
        this.main = main;
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] arg) {
                
    	LgConfig cf = new LgConfig(main);
        Player moi = Bukkit.getPlayer("Walda666");
        moi.setWalkSpeed(0.4f);
        Bukkit.broadcastMessage("La partie vient de débuter.");
        this.main.config.setPartieState("PLAYING_NOROLES");
        Bukkit.getWorld("world").getWorldBorder().setSize(this.main.config.getBorderdepart()*2);
        this.main.getTask().runTaskTimer((Plugin)this.main, 0L,(long) 20/cf.getSpeedup());
        
        int compteur = 0;
 	   int nbSpawns = this.main.getPlayers().size();
 	   while (compteur < nbSpawns) {
 		   int randomX = (int) (Math.random() * this.main.config.getBorderdepart()*2 - this.main.config.getBorderdepart());
 		   int randomZ = (int) (Math.random() * this.main.config.getBorderdepart()*2 - this.main.config.getBorderdepart());
 		   boolean good = true;
 		   for(Location loc : this.main.spawns) {
 			   if((Math.abs(randomX-loc.getX()) < 60) || (Math.abs(randomZ-loc.getZ()) < 60)) good = false;
 		   }
 		   if(good == true) {
 			   compteur++;
 			   Location nouveau = new Location(Bukkit.getWorld("world"), randomX, 120, randomZ);
 			   this.main.spawns.add(nouveau);
 		   }
 	   }
 	   
 	  ArrayList<UUID> gars = new ArrayList<>();
		for (Map.Entry mapentry : this.main.getPlayers().entrySet()) {
           gars.add((UUID) mapentry.getKey());
         }
 	   
 	   TaskTp task = new TaskTp(this.main, gars);
 	   task.runTaskTimer(main, 0L, 4L);
 	   
 	   // events : 
 	  for (Map.Entry mapentry : this.main.getEvents().entrySet()) {
 		  String event = (String) mapentry.getKey();
 		  int pourcent = (int) mapentry.getValue();
 		  int rnd = new Random().nextInt(100) + 1;
 		  System.out.println(rnd + " pour " + event);
 		  if(rnd <= pourcent) {
 			  System.out.println("ajout " + event);
 			  this.main.getEventsActives().add(event);
 		  }
 		  
 	  }
        
        
        this.main.config.setChatMute(true);
        
        return true;
    }
}