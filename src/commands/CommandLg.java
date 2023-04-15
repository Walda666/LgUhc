package commands;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import lg.main;

public class CommandLg implements CommandExecutor
{
    private main main;
    
    public CommandLg(main main) {
        this.main = main;
    }
    
    public boolean onCommand(final CommandSender sender, Command cmd, String msg, String[] arg) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player)sender;
        
        String[] args = Arrays.copyOfRange(arg, 1, arg.length);
        
        switch(arg[0]) {
	        case "voir": 
	        	CmdVoir comvoir = new CmdVoir(player, args, main);
	        	comvoir.run();
	        	break;
        
			case "kit": 
				CmdKit comkit = new CmdKit(player, args, main);
				comkit.run();
				break;
        
		    case "vote": 
				CmdVote comvote = new CmdVote(player, args, main);
				comvote.run();
				break;
			
		    case "pan": 
				CmdPan compan = new CmdPan(player, args, main);
				compan.run();
				break;
				
		    case "citoyen": 
				CmdCitoyen comcitoyen = new CmdCitoyen(player, args, main);
				comcitoyen.run();
				break;
				
		    case "confesser": 
				CmdConfesser comconfess = new CmdConfesser(player, args, main);
				comconfess.run();
				break;
				
		    case "salvater": 
				CmdSalvater comsalvater = new CmdSalvater(player, args, main);
				comsalvater.run();
				break;
				
		    case "flairer": 
				CmdFlairer comflairer = new CmdFlairer(player, args, main);
				comflairer.run();
				break;
				
		    case "ressuciter": 
				CmdRessuciter comrez = new CmdRessuciter(player, args, main);
				comrez.run();
				break;
			
		    case "infecter": 
				CmdInfecter cominfect = new CmdInfecter(player, args, main);
				cominfect.run();
				break;
				
		    case "love": 
		    	if(this.main.config.isCoupleAleatoire()) {
		    		player.sendMessage("§cVous ne pouvez pas choisir le couple. Vous en aurez connaissance à 25mn de jeu");
		    	} else {
					CmdLove comlove = new CmdLove(player, args, main);
					comlove.run();
		    	}
		    	break;
				
		    case "modele": 
				CmdModele commodele = new CmdModele(player, args, main);
				commodele.run();
				break;
				
		    case "mouton": 
				CmdMouton commouton = new CmdMouton(player, args, main);
				commouton.run();
				break;
				
		    case "s": 
		    	CmdSoeurChat commsoeurchat = new CmdSoeurChat(player, args, main);
				commsoeurchat.run();
				break;
        
		    case "ange": 
		    	CmdAnge comange = new CmdAnge(player, args, main);
				comange.run();
				break;
        
		    case "role": 
		    	CmdRole comrole = new CmdRole(player, args, main);
				comrole.run();
				break;
				
		    case "comparer": 
		    	CmdComparer comcomp = new CmdComparer(player, args, main);
		    	comcomp.run();
				break;
			
		    case "compo": 
		    	CmdCompo comcompo = new CmdCompo(player, args, main);
				comcompo.run();
				break;
        }
     
        return true;
        
    }
}
