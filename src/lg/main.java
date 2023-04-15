package lg;

import org.bukkit.plugin.PluginManager;
import org.bukkit.event.Listener;

import roles.Ancien;
import roles.Ange;
import roles.Assassin;
import roles.Berger;
import roles.Citoyen;
import roles.Confesseur;
import roles.Corbeau;
import roles.Cupidon;
import roles.Detective;
import roles.EnfantSauvage;
import roles.Ermite;
import roles.IdiotDuVillage;
import roles.InfectPereDesLoups;
import roles.LgRole;
import roles.LoupGarouBlanc;
import roles.LoupGarouCraintif;
import roles.LoupGarouFeutre;
import roles.LoupGarouGrimeur;
import roles.LoupGarouKaboom;
import roles.LoupGarouMystique;
import roles.Mercenaire;
import roles.MontreurDours;
import roles.PetitGarcon;
import roles.PetiteFille;
import roles.Renard;
import roles.Salvateur;
import roles.Soeur;
import roles.Sorciere;
import roles.Voleur;
import roles.Voyante;

import org.bukkit.plugin.Plugin;
import task.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Random;

import org.bukkit.plugin.java.JavaPlugin;

import commands.CommandAssign;
import commands.CommandConfig;
import commands.CommandDecoreco;
import commands.CommandFinishstuff;
import commands.CommandLg;
import commands.CommandStart;
import commands.CommandTest;
import commands.CommandUhcStart;
import listeners.PlayerListeners;

public class main extends JavaPlugin
{
	private HashMap<UUID, LgPlayer> players;
	private HashMap<String, Integer> events;
	private HashMap<UUID, UUID> damagers;
    ArrayList<LgRole> compo;
    public ArrayList<UUID> hosts;
    public ArrayList<Location> spawns;
    private TimerTask task;
    public ScoreboardManager sm;
    public Location worldspawn;
    public LgConfig config;
    public Scenarios scenarios;
    public int actuTp;
    private ArrayList<String> eventsActives;
    
    public main() {
        this.players = new HashMap<UUID, LgPlayer>();
        this.spawns = new ArrayList<>();
        this.compo = new ArrayList<LgRole>();
        compo.add(new MontreurDours());
        
        this.hosts = new ArrayList<>();
        this.config = new LgConfig(this);
        this.damagers = new HashMap<UUID, UUID>();
        this.events = new HashMap<String, Integer>();
        this.scenarios = new Scenarios();
        this.eventsActives = new ArrayList<>();
    }
    
    public void onEnable() {
       
        // int rnd = new Random().nextInt(rolesSafeAlive.size());
        /*
        ArrayList<String> gestri = new ArrayList<>();
        ArrayList<String> result = new ArrayList<>();
        result.add("role du gars");
        gestri.add("un");
        gestri.add("deux");
        gestri.add("trois");
        gestri.add("quatre");
        gestri.add("cinq");
        gestri.add("cinq");
        gestri.add("cinq");
        gestri.add("sixe");
        Collections.shuffle(gestri);
        Collections.shuffle(gestri);
        Collections.shuffle(gestri);
        boolean good = false;
        int compteur = 0;
        for (String it : gestri) {
			if(it.equals("cinq") && !good) {
				result.add(it + " choisi");
				good = true;
			} else if(compteur < 2) {
				result.add(it + " rd");
				compteur++;
			}
		}
        System.out.println(result);
        */
        
        this.getCommand("test").setExecutor((CommandExecutor)new CommandTest(this));
        this.getCommand("assign").setExecutor((CommandExecutor)new CommandAssign(this));
        this.getCommand("start").setExecutor((CommandExecutor)new CommandStart(this));
        this.getCommand("lg").setExecutor((CommandExecutor)new CommandLg(this));
        this.getCommand("uhcstart").setExecutor((CommandExecutor)new CommandUhcStart(this));
        this.getCommand("config").setExecutor((CommandExecutor)new CommandConfig(this));
        this.getCommand("decoreco").setExecutor((CommandExecutor)new CommandDecoreco(this));
        this.getCommand("finishstuff").setExecutor((CommandExecutor)new CommandFinishstuff(this));
        this.task = new TimerTask(this);
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents((Listener)new PlayerListeners(this), (Plugin)this);
        sm = new ScoreboardManager(this);
        sm.updateAllTime();
        Location centre = new Location(Bukkit.getWorld("world"), 0, 100, 0);
        Bukkit.getWorld("world").getWorldBorder().setCenter(centre);
    }
    
    public void onDisable() {
        System.out.println("Ferme ");
        this.players.clear();
    }
    
    public HashMap<UUID, LgPlayer> getPlayers() {
        return this.players;
    }
    
    public HashMap<UUID, UUID> getDamagers() {
		return damagers;
	}

	public ArrayList<LgRole> getCompo() {
        return this.compo;
    }

    
    public TimerTask getTask() {
    	return this.task;
    }

	public HashMap<String, Integer> getEvents() {
		return events;
	}

	public void setEvents(HashMap<String, Integer> events) {
		this.events = events;
	}

	public ArrayList<String> getEventsActives() {
		return eventsActives;
	}
	
	public HashMap<String, Integer> getCompoTriee() {
    	ArrayList<LgRole> clone = (ArrayList<LgRole>) this.compo.clone();
    	HashMap<String, Integer> finale = new LinkedHashMap<>();
    	ArrayList<String> explorateurs = new ArrayList<>();
    	ArrayList<String> animaux = new ArrayList<>();
    	ArrayList<String> braconniers = new ArrayList<>();
    	ArrayList<String> solos = new ArrayList<>();
    	for(LgRole role : clone) {
    		String camp = role.getCamp();
    		if(camp.equals("§fVillageois§f")) explorateurs.add("§a" + role.getNom() + "§f");
    		if(camp.equals("§4Loup-Garou§f")) animaux.add("§4" + role.getNom() + "§f");
    		if(camp.equals("§fSolo§f")) solos.add("§6" + role.getNom() + "§f");
    	}
    	Collections.sort(explorateurs);
    	Collections.sort(animaux);
    	Collections.sort(solos);
    
    	for(String role: explorateurs) {
    		if(finale.containsKey(role)) finale.replace(role, finale.get(role) + 1);
    		else finale.put(role, 1);
    	}
    	for(String role: animaux) {
    		if(finale.containsKey(role)) finale.replace(role, finale.get(role) + 1);
    		else finale.put(role, 1);
    	}
    	for(String role: solos) {
    		if(finale.containsKey(role)) finale.replace(role, finale.get(role) + 1);
    		else finale.put(role, 1);
    	}
    	return finale;
	}   
}
