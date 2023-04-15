package lg;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import roles.Ancien;
import roles.Ange;
import roles.Assassin;
import roles.Berger;
import roles.Chasseur;
import roles.ChienLoup;
import roles.Citoyen;
import roles.Confesseur;
import roles.Corbeau;
import roles.Cupidon;
import roles.EnfantSauvage;
import roles.InfectPereDesLoups;
import roles.LoupFantome;
import roles.LoupGarou;
import roles.LoupGarouBlanc;
import roles.LoupGarouKaboom;
import roles.LoupGarouMystique;
import roles.Mercenaire;
import roles.Mineur;
import roles.MontreurDours;
import roles.Nezuko;
import roles.PetitGarcon;
import roles.PetiteFille;
import roles.Renard;
import roles.Salvateur;
import roles.SimpleVillageois;
import roles.Soeur;
import roles.Sorciere;
import roles.VilainPetitLoup;
import roles.VillageoisVillageois;
import roles.Voleur;
import roles.Voyante;
import roles.VoyanteBavarde;

public class InventairesConfig {
	
	private Inventory configUhc, configLg, scenarios, eventsAlea, composition, config;
	private main main;
	private ArrayList<String> loreActive, loreDesactive;
	private LgConfig cf;
	private Scenarios sc;
	
	public InventairesConfig(main main) {
		this.configUhc = Bukkit.createInventory((InventoryHolder)null, 18, "§8Config Uhc");
		this.composition = Bukkit.createInventory((InventoryHolder)null, 54, "§8Composition");
		this.configLg = Bukkit.createInventory((InventoryHolder)null, 18, "§8Config Lg");
		this.scenarios = Bukkit.createInventory((InventoryHolder)null, 18, "§8Scenarios");
		this.eventsAlea = Bukkit.createInventory((InventoryHolder)null, 18, "§8Events aleatoires");
		this.config = Bukkit.createInventory((InventoryHolder)null, 9, "§8Config");
		this.main = main;
		this.cf = main.config;
		this.sc = main.scenarios;
		this.loreActive = new ArrayList<>();
		this.loreDesactive = new ArrayList<>();
		loreActive.add("Activé");
		loreDesactive.add("Désactivé");
	}	
	
	
	public Inventory getConfigUhc() {
		ArrayList<String> all = new ArrayList<>();
		all.add(Integer.toString(cf.getDlimit()) + " diamants");
		configUhc.setItem(0, this.createItemLore(Material.DIAMOND, "Diamond Limit", all));
		all.clear();
		all.add(cf.getBorderdepart() + " blocs");
		configUhc.setItem(1, this.createItemLore(Material.DIRT, "Border initiale", all));
		all.clear();
		all.add(cf.getTimerRoles() + "mn");
		configUhc.setItem(2, this.createItemLore(Material.WATCH, "Temps Rôles", all));
		all.clear();
		all.add(cf.getTimerMeetup() + "mn");
		configUhc.setItem(3, this.createItemLore(Material.WATCH, "Temps Meetup", all));
		configUhc.setItem(4, this.createItemLore(Material.GOLDEN_APPLE, "Gapple Kill", loreActive));
		configUhc.setItem(5, this.createIt(Material.DIRT, "Inventaire Départ"));
		all.clear();
		all.add(cf.getPearlsmax() + " pearls");
		configUhc.setItem(6, this.createItemLore(Material.ENDER_PEARL, "Pearls Max/Inventaire", all));
		all.clear();
		all.add("LETSGOOOO");
		configUhc.setItem(7, this.createItemLore(Material.SLIME_BALL, "START", all));
		all.clear();
		all.add(cf.getSpeedup() + " speed");
		configUhc.setItem(8, this.createItemLore(Material.COMMAND, "Speedup (only debug)", all));
		configUhc.setItem(17, this.createIt(Material.CACTUS, "Retour"));
		return configUhc;
	}



	public Inventory getConfigLg() {
		ArrayList<String> all = new ArrayList<>();
		String txt = cf.isAutorez() ? "Activé" : "Désactivé";
		all.add(txt);
		configLg.setItem(0, this.createItemLore(Material.POTION, "Autorez Sorcière", all));
		all.clear();
		txt = cf.isResisoeur() ? "Activé" : "Désactivé";
		all.add(txt);
		configLg.setItem(1, this.createItemLore(Material.DIRT, "Résistance Soeurs", all));
		all.clear();
		txt = cf.isAngenormal() ? "Activé" : "Désactivé";
		all.add(txt);
		configLg.setItem(2, this.createItemLore(Material.DIRT, "Ange Normal", all));
		all.clear();
		all.add(cf.getRayonKaboom() + "");
		configLg.setItem(3, this.createItemLore(Material.TNT, "Rayon Kaboom", all));
		all.clear();
		all.add("Jour " + cf.getSpeedup());
		configLg.setItem(4, this.createItemLore(Material.DIRT, "Début Votes", all));
		all.clear();
		all.add("Jour " + cf.getSpeedup());
		configLg.setItem(5, this.createItemLore(Material.DIRT, "Début Grrrr", all));
		all.clear();
		txt = cf.isCompocache() ? "Activé" : "Désactivé";
		all.add(txt);
		configLg.setItem(6, this.createItemLore(Material.DIRT, "Compo Cachée", all));
		all.clear();
		txt = cf.isCoupleAleatoire() ? "Activé" : "Désactivé";
		all.add(txt);
		configLg.setItem(7, this.createItemLore(Material.COMPASS, "Couple Aléatoire", all));
		all.clear();
		all.add(cf.getDaycycle() + "mn");
		configLg.setItem(8, this.createItemLore(Material.WATCH, "Cycle Jour/Nuit", all));
		all.clear();
		txt = cf.isSolitaire() ? "Activé" : "Désactivé";
		all.add(txt);
		configLg.setItem(9, this.createItemLore(Material.DIRT, "Loup-Garou Solitaire", all));
		configLg.setItem(17, this.createIt(Material.CACTUS, "Retour"));
		return configLg;
	}



	public Inventory getScenarios() {
		ArrayList<String> all = new ArrayList<>();
		String txt = sc.isCutclean() ? "Activé" : "Désactivé";
		all.add(txt);
		scenarios.setItem(0, this.createItemLore(Material.FURNACE, "Cut Clean", all));
		all.clear();
		txt = sc.isHasteyboys() ? "Activé" : "Désactivé";
		all.add(txt);
		scenarios.setItem(1, this.createItemLore(Material.DIAMOND_PICKAXE, "Hastey Boys", all));
		all.clear();
		txt = sc.isSafeminers() ? "Activé" : "Désactivé";
		all.add(txt);
		scenarios.setItem(2, this.createItemLore(Material.LAVA_BUCKET, "SafeMiners", all));
		all.clear();
		txt = sc.isAllcobble() ? "Activé" : "Désactivé";
		all.add(txt);
		scenarios.setItem(3, this.createItemLore(Material.COBBLESTONE, "AllCobble", all));
		all.clear();
		txt = sc.isBetazombie() ? "Activé" : "Désactivé";
		all.add(txt);
		scenarios.setItem(4, this.createItemLore(Material.FEATHER, "BetaZombie", all));
		all.clear();
		txt = sc.isDiamondlimit() ? "Activé" : "Désactivé";
		all.add(txt);
		scenarios.setItem(5, this.createItemLore(Material.DIAMOND, "DiamondLimit", all));
		scenarios.setItem(17, this.createIt(Material.CACTUS, "Retour"));
		return scenarios;
	}



	public Inventory getEventsAlea() {
		ArrayList<String> all = new ArrayList<>();
		String txt = this.main.getEvents().containsKey("Exposed") ? "Activé à " + this.main.getEvents().get("Exposed") + "%" : "Désactivé";
		all.add(txt);
		eventsAlea.setItem(0, this.createItemLore(Material.DIRT, "Exposed", all));
		all.clear();
		txt = this.main.getEvents().containsKey("Rumeurs") ? "Activé à " + this.main.getEvents().get("Rumeurs") + "%" : "Désactivé";
		all.add(txt);
		eventsAlea.setItem(1, this.createItemLore(Material.DIRT, "Rumeurs", all));
		all.clear();
		txt = this.main.getEvents().containsKey("Bénédiction") ? "Activé à " + this.main.getEvents().get("Bénédiction") + "%" : "Désactivé";
		all.add(txt);
		eventsAlea.setItem(2, this.createItemLore(Material.DIRT, "Bénédiction", all));
		all.clear();
		txt = this.main.getEvents().containsKey("Maire") ? "Activé à " + this.main.getEvents().get("Maire") + "%" : "Désactivé";
		all.add(txt);
		eventsAlea.setItem(3, this.createItemLore(Material.DIRT, "Maire", all));
		all.clear();
		txt = this.main.getEvents().containsKey("NotAllLoups") ? "Activé à " + this.main.getEvents().get("NotAllLoups") + "%" : "Désactivé";
		all.add(txt);
		eventsAlea.setItem(4, this.createItemLore(Material.DIRT, "NotAllLoups", all));
		all.clear();
		txt = this.main.getEvents().containsKey("OursMalLéché") ? "Activé à " + this.main.getEvents().get("OursMalLéché") + "%" : "Désactivé";
		all.add(txt);
		eventsAlea.setItem(5, this.createItemLore(Material.DIRT, "OursMalLéché", all));
		eventsAlea.setItem(17, this.createIt(Material.CACTUS, "Retour"));
		return eventsAlea;
	}



	public Inventory getComposition() {
		String nbRoles = String.valueOf(this.main.getCompo().size());
		composition.setItem(0, this.createItem(Material.DIRT, "Ancien", this.main.config.nbInCompo(new Ancien())));
		composition.setItem(1, this.createItem(Material.DIRT, "Ange", this.main.config.nbInCompo(new Ange())));
		composition.setItem(2, this.createItem(Material.DIRT, "Assassin", this.main.config.nbInCompo(new Assassin())));
		composition.setItem(3, this.createItem(Material.DIRT, "Berger", this.main.config.nbInCompo(new Berger())));
		composition.setItem(4, this.createItem(Material.DIRT, "Chasseur", this.main.config.nbInCompo(new Chasseur())));
		composition.setItem(5, this.createItem(Material.DIRT, "(X) Chien-Loup", this.main.config.nbInCompo(new ChienLoup())));
		composition.setItem(6, this.createItem(Material.DIRT, "Citoyen", this.main.config.nbInCompo(new Citoyen())));
		composition.setItem(7, this.createItem(Material.DIRT, "Confesseur", this.main.config.nbInCompo(new Confesseur())));
		composition.setItem(8, this.createItem(Material.DIRT, "Corbeau", this.main.config.nbInCompo(new Corbeau())));
		composition.setItem(9, this.createItem(Material.DIRT, "Cupidon", this.main.config.nbInCompo(new Cupidon())));
		composition.setItem(10, this.createItem(Material.DIRT, "Enfant Sauvage", this.main.config.nbInCompo(new EnfantSauvage())));
		composition.setItem(11, this.createItem(Material.DIRT, "Infect Père des Loups", this.main.config.nbInCompo(new InfectPereDesLoups())));
		composition.setItem(12, this.createItem(Material.DIRT, "Loup Fantôme", this.main.config.nbInCompo(new LoupFantome())));
		composition.setItem(13, this.createItem(Material.DIRT, "Loup-Garou", this.main.config.nbInCompo(new LoupGarou())));
		composition.setItem(14, this.createItem(Material.DIRT, "Loup-Garou Blanc", this.main.config.nbInCompo(new LoupGarouBlanc())));
		composition.setItem(15, this.createItem(Material.DIRT, "Loup-Garou Kaboom", this.main.config.nbInCompo(new LoupGarouKaboom())));
		composition.setItem(16, this.createItem(Material.DIRT, "Loup-Garou Mystique", this.main.config.nbInCompo(new LoupGarouMystique())));
		composition.setItem(17, this.createItem(Material.DIRT, "Mercenaire", this.main.config.nbInCompo(new Mercenaire())));
		composition.setItem(18, this.createItem(Material.DIRT, "Mineur", this.main.config.nbInCompo(new Mineur())));
		composition.setItem(19, this.createItem(Material.DIRT, "Montreur d'Ours", this.main.config.nbInCompo(new MontreurDours())));
		composition.setItem(20, this.createItem(Material.DIRT, "(X) Nezuko", this.main.config.nbInCompo(new Nezuko())));
		composition.setItem(21, this.createItem(Material.DIRT, "Petite Fille", this.main.config.nbInCompo(new PetiteFille())));
		composition.setItem(22, this.createItem(Material.DIRT, "Petit Garçon", this.main.config.nbInCompo(new PetitGarcon())));
		composition.setItem(23, this.createItem(Material.DIRT, "Renard", this.main.config.nbInCompo(new Renard())));
		composition.setItem(24, this.createItem(Material.DIRT, "Salvateur", this.main.config.nbInCompo(new Salvateur())));
		composition.setItem(25, this.createItem(Material.DIRT, "Simple Villageois", this.main.config.nbInCompo(new SimpleVillageois())));
		composition.setItem(26, this.createItem(Material.DIRT, "Soeur", this.main.config.nbInCompo(new Soeur())));
		composition.setItem(27, this.createItem(Material.DIRT, "Sorcière", this.main.config.nbInCompo(new Sorciere())));
		composition.setItem(28, this.createItem(Material.DIRT, "Vilain Petit Loup", this.main.config.nbInCompo(new VilainPetitLoup())));
		composition.setItem(29, this.createItem(Material.DIRT, "Villageois Villageois", this.main.config.nbInCompo(new VillageoisVillageois())));
		composition.setItem(30, this.createItem(Material.DIRT, "Voleur", this.main.config.nbInCompo(new Voleur())));
		composition.setItem(31, this.createItem(Material.DIRT, "Voyante", this.main.config.nbInCompo(new Voyante())));
		composition.setItem(32, this.createItem(Material.DIRT, "Voyante Bavarde", this.main.config.nbInCompo(new VoyanteBavarde())));
		composition.setItem(51, this.createItem(Material.BARRIER, "Clear compo", 1));
		composition.setItem(52, this.createItem(Material.GLASS, nbRoles, Integer.parseInt(nbRoles)));
		composition.setItem(53, this.createIt(Material.CACTUS, "Retour"));
		return composition;
	}



	public Inventory getConfig() {
		config.setItem(0, this.createIt(Material.DIRT, "§8Config Uhc"));
		config.setItem(1, this.createIt(Material.DIRT, "§8Scenarios"));
        config.setItem(4, this.createIt(Material.DIRT, "§8Composition"));
        config.setItem(5, this.createIt(Material.DIRT, "§8Events aleatoires"));
        config.setItem(8, this.createIt(Material.DIRT, "§8Config Lg"));
		return config;
	}


	// chiffre pour compo
    public ItemStack createItem(Material material, String nom, int nb) {
        ItemStack it = new ItemStack(material, 1);
        ItemMeta metaIt = it.getItemMeta();
        metaIt.setDisplayName(nom);
        it.setAmount(nb);
        it.setItemMeta(metaIt);
        return it;
    }
    
    // basique
    public ItemStack createIt(Material material, String nom) {
        ItemStack it = new ItemStack(material, 1);
        ItemMeta metaIt = it.getItemMeta();
        metaIt.setDisplayName(nom);
        it.setItemMeta(metaIt);
        return it;
    }
    
    // short pour menu fleché
    public ItemStack createItemStain(Material material, String nom, short id) {
        ItemStack it = new ItemStack(material, 1, id);
        ItemMeta metaIt = it.getItemMeta();
        metaIt.setDisplayName(nom);
        it.setItemMeta(metaIt);
        return it;
    }
    
    // avec lore
    public ItemStack createItemLore(Material material, String nom, ArrayList<String> lore) {
        ItemStack it = new ItemStack(material, 1);
        ItemMeta metaIt = it.getItemMeta();
        metaIt.setDisplayName(nom);
        metaIt.setLore(lore);
        it.setItemMeta(metaIt);
        return it;
    }
    
    
    public Inventory createMenuFleches(ItemStack it, int first, int second, int third) {
    	Inventory inv = Bukkit.createInventory((InventoryHolder)null, 18, "§8Edit " + it.getItemMeta().getDisplayName());
    	ItemStack rouge = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
    	ItemStack verte = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
    	
		inv.setItem(1, this.createItemStain(Material.STAINED_GLASS_PANE, "-" + second, (short) 14));
		inv.setItem(2, this.createItemStain(Material.STAINED_GLASS_PANE, "-" + first, (short) 14));
		
		inv.setItem(4, it);
		
		inv.setItem(6, this.createItemStain(Material.STAINED_GLASS_PANE, "+" + first, (short) 13));
		inv.setItem(7, this.createItemStain(Material.STAINED_GLASS_PANE, "+" + second, (short) 13));
		
		inv.setItem(13, this.createIt(Material.CACTUS, "Retour"));
		
		return inv;
    }

}
