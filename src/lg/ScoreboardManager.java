package lg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import utils.scoreboard.SidebarManager;

public class ScoreboardManager extends SidebarManager {

	private main plugin;
	private ConcurrentHashMap scoreboards;
	private boolean alternate;
	private boolean alternate2;
	private ArrayList<String> tableauCompoRole;
	private ArrayList<Integer> tableauCompoNb;
	
	public ScoreboardManager(main plugin) {
		this.plugin = plugin;
		this.scoreboards = new ConcurrentHashMap<>();
		this.tableauCompoNb = new ArrayList<Integer>();
		this.tableauCompoRole = new ArrayList<String>();
		this.alternate = true;
		this.alternate2 = true;
	}
	
	@Override
	public void build(Player p, SidebarEditor e) {
		
		if(plugin.config.getPartieState() == "PLAYING") {
			tableauCompoNb.clear();
			tableauCompoRole.clear();
		
			
			if(alternate && alternate2) {
				sendGameTitle(e);
				sendGameScoreboard(p, e);
				return;
			}
			
			if(alternate && !alternate2) {
				sendLobbyTitle(e);
				sendCompo1Scoreboard(p, e);
				return;
			} 
			if(!alternate && !alternate2) {
				sendLobbyTitle(e);
				sendCompo2Scoreboard(p, e);
				return;
			}
			
			if(!alternate && alternate2) {
				sendLobbyTitle(e);
				sendGameScoreboard(p, e);
				return;
			}
		}
		
		
		if(plugin.config.getPartieState() == "WAITING") {
			sendLobbyTitle(e);
			sendLobbyScoreboard(p, e);
			return;
		}
		
		if(plugin.config.getPartieState() == "END") {
			sendLobbyTitle(e);
			sendEndScoreboard(p, e);
			return;
		}
		
		sendGameTitle(e);
				
				sendGameScoreboard(p, e);
			//}
		//}
	}
	
	public void sendLobbyTitle(SidebarEditor e) {
		String titleLobby = "LG [test] #1";
		e.setTitle(titleLobby);
	}
	
	public void sendGameTitle(SidebarEditor e) {
		String titleGame = "LG [test] #1";
		e.setTitle(titleGame);
	}
	
	public void sendKillTitle(SidebarEditor e) {
		String titleGame = "Kills";
		e.setTitle(titleGame);
	}
	
	public void sendLobbyScoreboard(Player p, SidebarEditor e) {
		e.clear();
		
		int nbJoueurs = 0;
		for (Player pl : Bukkit.getOnlinePlayers()) {
			if(pl.getGameMode() != GameMode.SPECTATOR) nbJoueurs++;
		}
		
		this.sendLobbyTitle(e);
		e.add("§8§m------------");
		if(plugin.hosts.size() > 0) {
			Player pl = Bukkit.getPlayer(plugin.hosts.get(0));
			if(pl == null) e.add(("§fHost: §c" + Bukkit.getOfflinePlayer(plugin.hosts.get(0)).getName()));
				e.add("§fHost: §c" + pl.getDisplayName());
		}
		else e.add("§fHost: §c Personne");
		e.add("");
		e.add("§fJoueurs: §c" + nbJoueurs);
		e.add("§8§m------------");
	}
	
	public void sendGameScoreboard(Player p, SidebarEditor e) {
		e.clear();
		this.sendGameTitle(e);
		String desc = "§fTimer: §c";
		if(plugin.getTask().getHeure() == 0) {
			if(plugin.getTask().getMinute() < 10) desc += "0";
			desc += plugin.getTask().getMinute() + ":";
			if(plugin.getTask().getSeconde() < 10) desc += "0";
			desc += plugin.getTask().getSeconde();
		}
		else {
			desc += "0" + plugin.getTask().getHeure() + ":";
			if(plugin.getTask().getMinute() < 10) desc += "0";
			desc += plugin.getTask().getMinute() + ":";
			if(plugin.getTask().getSeconde() < 10) desc += "0";
			desc += plugin.getTask().getSeconde();
		}
		e.add("§8§m------------");
			e.add("§fEpisode §c"+plugin.getTask().getJour());
			e.add("");
			e.add(desc);			
			e.add("");
			int killsPlayer = this.plugin.getPlayers().get(p.getUniqueId()).getKill();
			e.add("Kills : " + killsPlayer);
			return;
	}
	
	public void sendCompo2Scoreboard(Player p, SidebarEditor e) {
		e.clear();
		this.sendGameTitle(e);
		if(tableauCompoRole.size() % 2 == 0) {
			for(int i = 0; i < tableauCompoRole.size() / 2; i++) e.add(tableauCompoRole.get(i) + " (" + tableauCompoNb.get(i) + ")");
			} else {
			for(int i = 0; i < tableauCompoRole.size() / 2 +1; i++) e.add(tableauCompoRole.get(i) + " (" + tableauCompoNb.get(i) + ")");
			}
			return;
	}
	
	public void sendCompo1Scoreboard(Player p, SidebarEditor e) {
		e.clear();
		this.sendGameTitle(e);
		if(tableauCompoRole.size() % 2 == 0) {
		for(int i = (tableauCompoRole.size() / 2); i < tableauCompoRole.size(); i++) e.add(tableauCompoRole.get(i) + " (" + tableauCompoNb.get(i) + ")");
		} else {
			for(int i = (tableauCompoRole.size() / 2) +1; i < tableauCompoRole.size(); i++) e.add(tableauCompoRole.get(i) + " (" + tableauCompoNb.get(i) + ")");
		}
			return;
	}
	
	public void sendEndScoreboard(Player p, SidebarEditor e) {
		e.clear();
		
		
	}
	
	public void alternateScoreboard() {
		this.alternate = !alternate;
	}
	
	public void alternateScoreboard2() {
		this.alternate2 = !alternate2;
	}
  
  public void updateAllTime() {
		plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, new Runnable() {
			
			public void run() {
				
				update();
			}
			
		}, 0L, 5L);
	}
}