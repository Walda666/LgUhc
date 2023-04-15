package listeners;

import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import java.util.Iterator;
import java.util.List;

import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import commands.CommandAssign;
import commands.CommandStart;
import commands.CommandUhcStart;

import java.util.Map;
import java.util.Set;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArrow;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Zombie;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;

import lg.InventairesConfig;
import lg.LgConfig;
import lg.LgPlayer;
import lg.Scenarios;
import lg.TitleManager;
import lg.main;
import net.minecraft.server.v1_8_R3.EntityArrow;
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
import roles.LgRole;
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

import org.bukkit.event.Listener;

public class PlayerListeners implements Listener
{
    private main main;
    private TitleManager tm;
    private InventairesConfig invconfigs;
    
    public PlayerListeners(main main) {
        this.main = main;
        this.tm = new TitleManager();
        this.invconfigs = new InventairesConfig(main);
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        this.main.sm.join(player);
        if (!this.main.getPlayers().containsKey(event.getPlayer().getUniqueId()) && this.main.config.getPartieState() == "WAITING") {
            this.main.getPlayers().put(event.getPlayer().getUniqueId(), new LgPlayer(event.getPlayer()));
        }
        if(player.getDisplayName().equals("Walda666")) {
        	 ItemStack netherstar = new ItemStack(Material.NETHER_STAR, 1);
             ItemMeta meta = netherstar.getItemMeta();
             meta.setDisplayName("§3Configuration");
             netherstar.setItemMeta(meta);
             player.getInventory().setItem(4, netherstar);
        }
        
        if(this.main.config.getPartieState() == "WAITING") player.setGameMode(GameMode.SURVIVAL);
    }
    
    
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        this.main.sm.leave(player);
    }
    
    // menu compo
    @EventHandler
    public void onClick(InventoryClickEvent event) {
    	LgConfig cf = this.main.config;
    	Inventory inv = event.getInventory();
    	Player p = (Player) event.getWhoClicked();
    	ItemStack current = event.getCurrentItem();
    	ArrayList<String> loreActive = new ArrayList<>();
		ArrayList<String> loreDesactive = new ArrayList<>();
		loreActive.add("Activé");
		loreDesactive.add("Désactivé");
    	if (inv.getName() == "§8Composition" && current != null) {
    		if(current.hasItemMeta()) {
	    		event.setCancelled(true);
	            if (current != null) {
	            	if (current.getItemMeta().getDisplayName().equals("Ancien")) {
		            	if(event.getClick() == ClickType.LEFT) {
			            	this.main.getCompo().add(new Ancien());
			            	event.getInventory().getItem(0).setAmount(event.getInventory().getItem(0).getAmount() +1);
		            	}
		            	if(event.getClick() == ClickType.RIGHT) {
		            		this.main.config.removeCompo(new Ancien());
			            	event.getInventory().getItem(0).setAmount(event.getInventory().getItem(0).getAmount() -1);
		            	}
		            }
		            
	            	if (current.getItemMeta().getDisplayName().equals("Ange")) {
		            	if(event.getClick() == ClickType.LEFT) {
			            	this.main.getCompo().add(new Ange());
			            	event.getInventory().getItem(1).setAmount(event.getInventory().getItem(1).getAmount() +1);
		            	}
		            	if(event.getClick() == ClickType.RIGHT) {
		            		this.main.config.removeCompo(new Ange());
			            	event.getInventory().getItem(1).setAmount(event.getInventory().getItem(1).getAmount() -1);
		            	}
		            }
		            
	            	if (current.getItemMeta().getDisplayName().equals("Assassin")) {
		            	if(event.getClick() == ClickType.LEFT) {
			            	this.main.getCompo().add(new Assassin());
			            	event.getInventory().getItem(2).setAmount(event.getInventory().getItem(2).getAmount() +1);
		            	}
		            	if(event.getClick() == ClickType.RIGHT) {
		            		this.main.config.removeCompo(new Assassin());
			            	event.getInventory().getItem(2).setAmount(event.getInventory().getItem(2).getAmount() -1);
		            	}
		            }
		            
	            	if (current.getItemMeta().getDisplayName().equals("Berger")) {
		            	if(event.getClick() == ClickType.LEFT) {
			            	this.main.getCompo().add(new Berger());
			            	event.getInventory().getItem(3).setAmount(event.getInventory().getItem(3).getAmount() +1);
		            	}
		            	if(event.getClick() == ClickType.RIGHT) {
		            		this.main.config.removeCompo(new Berger());
			            	event.getInventory().getItem(3).setAmount(event.getInventory().getItem(3).getAmount() -1);
		            	}
		            }
		            
	            	if (current.getItemMeta().getDisplayName().equals("Chasseur")) {
		            	if(event.getClick() == ClickType.LEFT) {
			            	this.main.getCompo().add(new Chasseur());
			            	event.getInventory().getItem(4).setAmount(event.getInventory().getItem(4).getAmount() +1);
		            	}
		            	if(event.getClick() == ClickType.RIGHT) {
		            		this.main.config.removeCompo(new Chasseur());
			            	event.getInventory().getItem(4).setAmount(event.getInventory().getItem(4).getAmount() -1);
		            	}
		            }
		            
	            	if (current.getItemMeta().getDisplayName().equals("Chien-Loup")) {
		            	if(event.getClick() == ClickType.LEFT) {
			            	this.main.getCompo().add(new ChienLoup());
			            	event.getInventory().getItem(5).setAmount(event.getInventory().getItem(5).getAmount() +1);
		            	}
		            	if(event.getClick() == ClickType.RIGHT) {
		            		this.main.config.removeCompo(new ChienLoup());
			            	event.getInventory().getItem(5).setAmount(event.getInventory().getItem(5).getAmount() -1);
		            	}
		            }
		            
	            	if (current.getItemMeta().getDisplayName().equals("Citoyen")) {
		            	if(event.getClick() == ClickType.LEFT) {
			            	this.main.getCompo().add(new Citoyen());
			            	event.getInventory().getItem(6).setAmount(event.getInventory().getItem(6).getAmount() +1);
		            	}
		            	if(event.getClick() == ClickType.RIGHT) {
		            		this.main.config.removeCompo(new Citoyen());
			            	event.getInventory().getItem(6).setAmount(event.getInventory().getItem(6).getAmount() -1);
		            	}
		            }
		            
	            	if (current.getItemMeta().getDisplayName().equals("Confesseur")) {
		            	if(event.getClick() == ClickType.LEFT) {
			            	this.main.getCompo().add(new Confesseur());
			            	event.getInventory().getItem(7).setAmount(event.getInventory().getItem(7).getAmount() +1);
		            	}
		            	if(event.getClick() == ClickType.RIGHT) {
		            		this.main.config.removeCompo(new Confesseur());
			            	event.getInventory().getItem(7).setAmount(event.getInventory().getItem(7).getAmount() -1);
		            	}
		            }
		            
	            	if (current.getItemMeta().getDisplayName().equals("Corbeau")) {
		            	if(event.getClick() == ClickType.LEFT) {
			            	this.main.getCompo().add(new Corbeau());
			            	event.getInventory().getItem(8).setAmount(event.getInventory().getItem(8).getAmount() +1);
		            	}
		            	if(event.getClick() == ClickType.RIGHT) {
		            		this.main.config.removeCompo(new Corbeau());
			            	event.getInventory().getItem(8).setAmount(event.getInventory().getItem(8).getAmount() -1);
		            	}
		            }
		            
	            	if (current.getItemMeta().getDisplayName().equals("Cupidon")) {
		            	if(event.getClick() == ClickType.LEFT) {
			            	this.main.getCompo().add(new Cupidon());
			            	event.getInventory().getItem(9).setAmount(event.getInventory().getItem(9).getAmount() +1);
		            	}
		            	if(event.getClick() == ClickType.RIGHT) {
		            		this.main.config.removeCompo(new Cupidon());
			            	event.getInventory().getItem(9).setAmount(event.getInventory().getItem(9).getAmount() -1);
		            	}
		            }
		            
	            	if (current.getItemMeta().getDisplayName().equals("Enfant Sauvage")) {
		            	if(event.getClick() == ClickType.LEFT) {
			            	this.main.getCompo().add(new EnfantSauvage());
			            	event.getInventory().getItem(10).setAmount(event.getInventory().getItem(10).getAmount() +1);
		            	}
		            	if(event.getClick() == ClickType.RIGHT) {
		            		this.main.config.removeCompo(new EnfantSauvage());
			            	event.getInventory().getItem(10).setAmount(event.getInventory().getItem(10).getAmount() -1);
		            	}
		            }
		            
	            	if (current.getItemMeta().getDisplayName().equals("Infect Père des Loups")) {
		            	if(event.getClick() == ClickType.LEFT) {
			            	this.main.getCompo().add(new InfectPereDesLoups());
			            	event.getInventory().getItem(11).setAmount(event.getInventory().getItem(11).getAmount() +1);
		            	}
		            	if(event.getClick() == ClickType.RIGHT) {
		            		this.main.config.removeCompo(new InfectPereDesLoups());
			            	event.getInventory().getItem(11).setAmount(event.getInventory().getItem(11).getAmount() -1);
		            	}
		            }
		            
	            	if (current.getItemMeta().getDisplayName().equals("Loup Fantôme")) {
		            	if(event.getClick() == ClickType.LEFT) {
			            	this.main.getCompo().add(new LoupFantome());
			            	event.getInventory().getItem(12).setAmount(event.getInventory().getItem(12).getAmount() +1);
		            	}
		            	if(event.getClick() == ClickType.RIGHT) {
		            		this.main.config.removeCompo(new LoupFantome());
			            	event.getInventory().getItem(12).setAmount(event.getInventory().getItem(12).getAmount() -1);
		            	}
		            }
		            
	            	if (current.getItemMeta().getDisplayName().equals("Loup-Garou")) {
		            	if(event.getClick() == ClickType.LEFT) {
			            	this.main.getCompo().add(new LoupGarou());
			            	event.getInventory().getItem(13).setAmount(event.getInventory().getItem(13).getAmount() +1);
		            	}
		            	if(event.getClick() == ClickType.RIGHT) {
		            		this.main.config.removeCompo(new LoupGarou());
			            	event.getInventory().getItem(13).setAmount(event.getInventory().getItem(13).getAmount() -1);
		            	}
		            }
		            
	            	if (current.getItemMeta().getDisplayName().equals("Loup-Garou Blanc")) {
		            	if(event.getClick() == ClickType.LEFT) {
			            	this.main.getCompo().add(new LoupGarouBlanc());
			            	event.getInventory().getItem(14).setAmount(event.getInventory().getItem(14).getAmount() +1);
		            	}
		            	if(event.getClick() == ClickType.RIGHT) {
		            		this.main.config.removeCompo(new LoupGarouBlanc());
			            	event.getInventory().getItem(14).setAmount(event.getInventory().getItem(14).getAmount() -1);
		            	}
		            }
	            	
	            	if (current.getItemMeta().getDisplayName().equals("Loup-Garou Kaboom")) {
		            	if(event.getClick() == ClickType.LEFT) {
			            	this.main.getCompo().add(new LoupGarouKaboom());
			            	event.getInventory().getItem(15).setAmount(event.getInventory().getItem(15).getAmount() +1);
		            	}
		            	if(event.getClick() == ClickType.RIGHT) {
		            		this.main.config.removeCompo(new LoupGarouKaboom());
			            	event.getInventory().getItem(15).setAmount(event.getInventory().getItem(15).getAmount() -1);
		            	}
		            }
		            
	            	if (current.getItemMeta().getDisplayName().equals("Loup-Garou Mystique")) {
		            	if(event.getClick() == ClickType.LEFT) {
			            	this.main.getCompo().add(new LoupGarouMystique());
			            	event.getInventory().getItem(16).setAmount(event.getInventory().getItem(16).getAmount() +1);
		            	}
		            	if(event.getClick() == ClickType.RIGHT) {
		            		this.main.config.removeCompo(new LoupGarouMystique());
			            	event.getInventory().getItem(16).setAmount(event.getInventory().getItem(16).getAmount() -1);
		            	}
		            }
		            
	            	if (current.getItemMeta().getDisplayName().equals("Mercenaire")) {
		            	if(event.getClick() == ClickType.LEFT) {
			            	this.main.getCompo().add(new Mercenaire());
			            	event.getInventory().getItem(17).setAmount(event.getInventory().getItem(17).getAmount() +1);
		            	}
		            	if(event.getClick() == ClickType.RIGHT) {
		            		this.main.config.removeCompo(new Mercenaire());
			            	event.getInventory().getItem(17).setAmount(event.getInventory().getItem(17).getAmount() -1);
		            	}
		            }
		            
	            	if (current.getItemMeta().getDisplayName().equals("Mineur")) {
		            	if(event.getClick() == ClickType.LEFT) {
			            	this.main.getCompo().add(new Mineur());
			            	event.getInventory().getItem(18).setAmount(event.getInventory().getItem(18).getAmount() +1);
		            	}
		            	if(event.getClick() == ClickType.RIGHT) {
		            		this.main.config.removeCompo(new Mineur());
			            	event.getInventory().getItem(18).setAmount(event.getInventory().getItem(18).getAmount() -1);
		            	}
		            }
		            
	            	if (current.getItemMeta().getDisplayName().equals("Montreur d'Ours")) {
		            	if(event.getClick() == ClickType.LEFT) {
			            	this.main.getCompo().add(new MontreurDours());
			            	event.getInventory().getItem(19).setAmount(event.getInventory().getItem(19).getAmount() +1);
		            	}
		            	if(event.getClick() == ClickType.RIGHT) {
		            		this.main.config.removeCompo(new MontreurDours());
			            	event.getInventory().getItem(19).setAmount(event.getInventory().getItem(19).getAmount() -1);
		            	}
		            }
		            
	            	if (current.getItemMeta().getDisplayName().equals("Nezuko")) {
		            	if(event.getClick() == ClickType.LEFT) {
			            	this.main.getCompo().add(new Nezuko());
			            	event.getInventory().getItem(20).setAmount(event.getInventory().getItem(20).getAmount() +1);
		            	}
		            	if(event.getClick() == ClickType.RIGHT) {
		            		this.main.config.removeCompo(new Nezuko());
			            	event.getInventory().getItem(20).setAmount(event.getInventory().getItem(20).getAmount() -1);
		            	}
		            }
		            
	            	if (current.getItemMeta().getDisplayName().equals("Petite Fille")) {
		            	if(event.getClick() == ClickType.LEFT) {
			            	this.main.getCompo().add(new PetiteFille());
			            	event.getInventory().getItem(21).setAmount(event.getInventory().getItem(21).getAmount() +1);
		            	}
		            	if(event.getClick() == ClickType.RIGHT) {
		            		this.main.config.removeCompo(new PetiteFille());
			            	event.getInventory().getItem(21).setAmount(event.getInventory().getItem(21).getAmount() -1);
		            	}
		            }
		            
	            	if (current.getItemMeta().getDisplayName().equals("Petit Garçon")) {
		            	if(event.getClick() == ClickType.LEFT) {
			            	this.main.getCompo().add(new PetitGarcon());
			            	event.getInventory().getItem(22).setAmount(event.getInventory().getItem(22).getAmount() +1);
		            	}
		            	if(event.getClick() == ClickType.RIGHT) {
		            		this.main.config.removeCompo(new PetitGarcon());
			            	event.getInventory().getItem(22).setAmount(event.getInventory().getItem(22).getAmount() -1);
		            	}
		            }
		            
	            	if (current.getItemMeta().getDisplayName().equals("Renard")) {
		            	if(event.getClick() == ClickType.LEFT) {
			            	this.main.getCompo().add(new Renard());
			            	event.getInventory().getItem(23).setAmount(event.getInventory().getItem(23).getAmount() +1);
		            	}
		            	if(event.getClick() == ClickType.RIGHT) {
		            		this.main.config.removeCompo(new Renard());
			            	event.getInventory().getItem(23).setAmount(event.getInventory().getItem(23).getAmount() -1);
		            	}
		            }
		            
	            	if (current.getItemMeta().getDisplayName().equals("Salvateur")) {
		            	if(event.getClick() == ClickType.LEFT) {
			            	this.main.getCompo().add(new Salvateur());
			            	event.getInventory().getItem(24).setAmount(event.getInventory().getItem(24).getAmount() +1);
		            	}
		            	if(event.getClick() == ClickType.RIGHT) {
		            		this.main.config.removeCompo(new Salvateur());
			            	event.getInventory().getItem(24).setAmount(event.getInventory().getItem(24).getAmount() -1);
		            	}
		            }
		            
	            	if (current.getItemMeta().getDisplayName().equals("Simple Villageois")) {
		            	if(event.getClick() == ClickType.LEFT) {
			            	this.main.getCompo().add(new SimpleVillageois());
			            	event.getInventory().getItem(25).setAmount(event.getInventory().getItem(25).getAmount() +1);
		            	}
		            	if(event.getClick() == ClickType.RIGHT) {
		            		this.main.config.removeCompo(new SimpleVillageois());
			            	event.getInventory().getItem(25).setAmount(event.getInventory().getItem(25).getAmount() -1);
		            	}
		            }
		            
	            	if (current.getItemMeta().getDisplayName().equals("Soeur")) {
		            	if(event.getClick() == ClickType.LEFT) {
			            	this.main.getCompo().add(new Soeur());
			            	event.getInventory().getItem(26).setAmount(event.getInventory().getItem(26).getAmount() +1);
		            	}
		            	if(event.getClick() == ClickType.RIGHT) {
		            		this.main.config.removeCompo(new Soeur());
			            	event.getInventory().getItem(26).setAmount(event.getInventory().getItem(26).getAmount() -1);
		            	}
		            }
		            
	            	if (current.getItemMeta().getDisplayName().equals("Sorcière")) {
		            	if(event.getClick() == ClickType.LEFT) {
			            	this.main.getCompo().add(new Sorciere());
			            	event.getInventory().getItem(27).setAmount(event.getInventory().getItem(27).getAmount() +1);
		            	}
		            	if(event.getClick() == ClickType.RIGHT) {
		            		this.main.config.removeCompo(new Sorciere());
			            	event.getInventory().getItem(27).setAmount(event.getInventory().getItem(27).getAmount() -1);
		            	}
		            }
		            
	            	if (current.getItemMeta().getDisplayName().equals("Vilain Petit Loup")) {
		            	if(event.getClick() == ClickType.LEFT) {
			            	this.main.getCompo().add(new VilainPetitLoup());
			            	event.getInventory().getItem(28).setAmount(event.getInventory().getItem(28).getAmount() +1);
		            	}
		            	if(event.getClick() == ClickType.RIGHT) {
		            		this.main.config.removeCompo(new VilainPetitLoup());
			            	event.getInventory().getItem(28).setAmount(event.getInventory().getItem(28).getAmount() -1);
		            	}
		            }
		            
	            	if (current.getItemMeta().getDisplayName().equals("Villageois Villageois")) {
		            	if(event.getClick() == ClickType.LEFT) {
			            	this.main.getCompo().add(new VillageoisVillageois());
			            	event.getInventory().getItem(29).setAmount(event.getInventory().getItem(29).getAmount() +1);
		            	}
		            	if(event.getClick() == ClickType.RIGHT) {
		            		this.main.config.removeCompo(new VillageoisVillageois());
			            	event.getInventory().getItem(29).setAmount(event.getInventory().getItem(29).getAmount() -1);
		            	}
		            }
		            
	            	if (current.getItemMeta().getDisplayName().equals("Voleur")) {
		            	if(event.getClick() == ClickType.LEFT) {
			            	this.main.getCompo().add(new Voleur());
			            	event.getInventory().getItem(30).setAmount(event.getInventory().getItem(30).getAmount() +1);
		            	}
		            	if(event.getClick() == ClickType.RIGHT) {
		            		this.main.config.removeCompo(new Voleur());
			            	event.getInventory().getItem(30).setAmount(event.getInventory().getItem(30).getAmount() -1);
		            	}
		            }
		            
	            	if (current.getItemMeta().getDisplayName().equals("Voyante")) {
		            	if(event.getClick() == ClickType.LEFT) {
			            	this.main.getCompo().add(new Voyante());
			            	event.getInventory().getItem(31).setAmount(event.getInventory().getItem(31).getAmount() +1);
		            	}
		            	if(event.getClick() == ClickType.RIGHT) {
		            		this.main.config.removeCompo(new Voyante());
			            	event.getInventory().getItem(31).setAmount(event.getInventory().getItem(31).getAmount() -1);
		            	}
		            }
		            
	            	if (current.getItemMeta().getDisplayName().equals("Voyante Bavarde")) {
		            	if(event.getClick() == ClickType.LEFT) {
			            	this.main.getCompo().add(new VoyanteBavarde());
			            	event.getInventory().getItem(32).setAmount(event.getInventory().getItem(32).getAmount() +1);
		            	}
		            	if(event.getClick() == ClickType.RIGHT) {
		            		this.main.config.removeCompo(new VoyanteBavarde());
			            	event.getInventory().getItem(32).setAmount(event.getInventory().getItem(32).getAmount() -1);
		            	}
		            }
	            	
	            	if (current.getItemMeta().getDisplayName().equals("Clear compo")) {
	            		this.main.getCompo().clear();
	            		for(int i = 0; i < 33; i++) {
	            			event.getInventory().getItem(i).setAmount(0);
	            		}
	            	}
            	}
            }
    	}
    	
    	// sous menu config
    	if (inv.getName() == "§8Config" && current != null) {
    		if(current.hasItemMeta()) {
	    		event.setCancelled(true);
	            if (current != null) {
	            	if (current.getItemMeta().getDisplayName().equals("§8Config Uhc")) {
	                    p.openInventory(invconfigs.getConfigUhc());
	            	}
	            	
					if (current.getItemMeta().getDisplayName().equals("§8Composition")) {
				       p.openInventory(invconfigs.getComposition());
					}
					
					if (current.getItemMeta().getDisplayName().equals("§8Config Lg")) {
	                    p.openInventory(invconfigs.getConfigLg());
					}
					
					if (current.getItemMeta().getDisplayName().equals("§8Scenarios")) {
	                    p.openInventory(invconfigs.getScenarios());
					}
					
					if (current.getItemMeta().getDisplayName().equals("§8Events aleatoires")) {
	                    p.openInventory(invconfigs.getEventsAlea());
					}
	            }
            }
    	}
    	// actions sous menu config
    	if (inv.getName() == "§8Config Lg" && current != null) {
    		if(current.hasItemMeta()) {
	    		event.setCancelled(true);
	            if (current != null) {
					if (current.getItemMeta().getDisplayName().equals("Autorez Sorcière")) {
						cf.setAutorez(!cf.isAutorez());
						if(cf.isAutorez()) {
							ItemMeta meta = current.getItemMeta();
							meta.setLore(loreActive);
							inv.getItem(0).setItemMeta(meta);
						} else {
							ItemMeta meta = current.getItemMeta();
							meta.setLore(loreDesactive);
							inv.getItem(0).setItemMeta(meta);
						}
					}
					if (current.getItemMeta().getDisplayName().equals("Résistance Soeurs")) {
						cf.setResisoeur(!cf.isResisoeur());
						if(cf.isResisoeur()) {
							ItemMeta meta = current.getItemMeta();
							meta.setLore(loreActive);
							inv.getItem(1).setItemMeta(meta);
						} else {
							ItemMeta meta = current.getItemMeta();
							meta.setLore(loreDesactive);
							inv.getItem(1).setItemMeta(meta);
						}
					}
					if (current.getItemMeta().getDisplayName().equals("Ange Normal")) {
						cf.setAngenormal(!cf.isAngenormal());
						if(cf.isAngenormal()) {
							ItemMeta meta = current.getItemMeta();
							meta.setLore(loreActive);
							inv.getItem(2).setItemMeta(meta);
						} else {
							ItemMeta meta = current.getItemMeta();
							meta.setLore(loreDesactive);
							inv.getItem(2).setItemMeta(meta);
						}
					}
					if (current.getItemMeta().getDisplayName().equals("Rayon Kaboom")) {
						p.openInventory(createMenuFleches(current, 1, 10, Integer.toString(cf.getRayonKaboom())));
					}
					if (current.getItemMeta().getDisplayName().equals("Début Votes")) {
						p.openInventory(createMenuFleches(current, 1, 10, "Jour " + Integer.toString(cf.getJourVote())));
					}
					if (current.getItemMeta().getDisplayName().equals("Début Grrrr")) {
						p.openInventory(createMenuFleches(current, 1, 10, "Jour " + Integer.toString(cf.getJourMo())));
					}
					if (current.getItemMeta().getDisplayName().equals("Compo Cachée")) {
						cf.setCompocache(!cf.isCompocache());
						if(cf.isCompocache()) {
							ItemMeta meta = current.getItemMeta();
							meta.setLore(loreActive);
							inv.getItem(6).setItemMeta(meta);
						} else {
							ItemMeta meta = current.getItemMeta();
							meta.setLore(loreDesactive);
							inv.getItem(6).setItemMeta(meta);
						}
					}
					if (current.getItemMeta().getDisplayName().equals("Couple Aléatoire")) {
						cf.setCoupleAleatoire(!cf.isCoupleAleatoire());
						if(cf.isCoupleAleatoire()) {
							ItemMeta meta = current.getItemMeta();
							meta.setLore(loreActive);
							inv.getItem(7).setItemMeta(meta);
						} else {
							ItemMeta meta = current.getItemMeta();
							meta.setLore(loreDesactive);
							inv.getItem(7).setItemMeta(meta);
						}
					}
					if (current.getItemMeta().getDisplayName().equals("Cycle Jour/Nuit")) {
						p.openInventory(createMenuFleches(current, 5, 5, Integer.toString(cf.getDaycycle()) + "mn"));
					}
					if (current.getItemMeta().getDisplayName().equals("Loup-Garou Solitaire")) {
						cf.setSolitaire(!cf.isSolitaire());
						if(cf.isSolitaire()) {
							ItemMeta meta = current.getItemMeta();
							meta.setLore(loreActive);
							inv.getItem(9).setItemMeta(meta);
						} else {
							ItemMeta meta = current.getItemMeta();
							meta.setLore(loreDesactive);
							inv.getItem(9).setItemMeta(meta);
						}
					}
	            }
            }
    	}
    	
    	if (inv.getName() == "§8Events aleatoires" && current != null) {
    		if(current.hasItemMeta()) {
	    		event.setCancelled(true);
	            if (current != null) {
					if (current.getItemMeta().getDisplayName().equals("Exposed")) {
						if(event.getClick() == ClickType.LEFT) {
							if(this.main.getEvents().containsKey("Exposed")) this.main.getEvents().remove("Exposed");
							else this.main.getEvents().put("Exposed", 50);
						} else {
							if(!this.main.getEvents().containsKey("Exposed")) this.main.getEvents().put("Exposed", 50);
							p.openInventory(this.createMenuFleches(current, 1, 10, this.main.getEvents().get("Exposed").toString() + "%"));
						}
					}
					if (current.getItemMeta().getDisplayName().equals("Rumeurs")) {
						if(event.getClick() == ClickType.LEFT) {
							if(this.main.getEvents().containsKey("Rumeurs")) this.main.getEvents().remove("Rumeurs");
							else this.main.getEvents().put("Rumeurs", 50);
						} else {
							if(!this.main.getEvents().containsKey("Rumeurs")) this.main.getEvents().put("Rumeurs", 50);
							p.openInventory(this.createMenuFleches(current, 1, 10, this.main.getEvents().get("Rumeurs").toString() + "%"));
						}
					}
					if (current.getItemMeta().getDisplayName().equals("Bénédiction")) {
						if(event.getClick() == ClickType.LEFT) {
							if(this.main.getEvents().containsKey("Bénédiction")) this.main.getEvents().remove("Bénédiction");
							else this.main.getEvents().put("Bénédiction", 50);
						} else {
							if(!this.main.getEvents().containsKey("Bénédiction")) this.main.getEvents().put("Bénédiction", 50);
							p.openInventory(this.createMenuFleches(current, 1, 10, this.main.getEvents().get("Bénédiction").toString() + "%"));
						}
					}
					if (current.getItemMeta().getDisplayName().equals("Maire")) {
						if(event.getClick() == ClickType.LEFT) {
							if(this.main.getEvents().containsKey("Maire")) this.main.getEvents().remove("Maire");
							else this.main.getEvents().put("Maire", 50);
						} else {
							if(!this.main.getEvents().containsKey("Maire")) this.main.getEvents().put("Maire", 50);
							p.openInventory(this.createMenuFleches(current, 1, 10, this.main.getEvents().get("Maire").toString() + "%"));
						}
					}
					if (current.getItemMeta().getDisplayName().equals("NotAllLoups")) {
						if(event.getClick() == ClickType.LEFT) {
							if(this.main.getEvents().containsKey("NotAllLoups")) this.main.getEvents().remove("NotAllLoups");
							else this.main.getEvents().put("NotAllLoups", 50);
						} else {
							if(!this.main.getEvents().containsKey("NotAllLoups")) this.main.getEvents().put("NotAllLoups", 50);
							p.openInventory(this.createMenuFleches(current, 1, 10, this.main.getEvents().get("NotAllLoups").toString() + "%"));
						}
					}
					if (current.getItemMeta().getDisplayName().equals("OursMalLéché")) {
						if(event.getClick() == ClickType.LEFT) {
							if(this.main.getEvents().containsKey("OursMalLéché")) this.main.getEvents().remove("OursMalLéché");
							else this.main.getEvents().put("OursMalLéché", 50);
						} else {
							if(!this.main.getEvents().containsKey("OursMalLéché")) this.main.getEvents().put("OursMalLéché", 50);
							p.openInventory(this.createMenuFleches(current, 1, 10, this.main.getEvents().get("OursMalLéché").toString() + "%"));
						}
					}
				}
            }
    	}
    	
    	if (inv.getName() == "§8Scenarios" && current != null) {
    		if(current.hasItemMeta()) {
	    		event.setCancelled(true);
	            if (current != null) {
	            	Scenarios sc = this.main.scenarios;
	            	if (current.getItemMeta().getDisplayName().equals("Cut Clean")) {
	            		sc.setCutclean(!sc.isCutclean());
	            		if(sc.isCutclean()) {
							ItemMeta meta = current.getItemMeta();
							meta.setLore(loreActive);
							inv.getItem(0).setItemMeta(meta);
						} else {
							ItemMeta meta = current.getItemMeta();
							meta.setLore(loreDesactive);
							inv.getItem(0).setItemMeta(meta);
						}
	            	}
	            	if (current.getItemMeta().getDisplayName().equals("Hastey Boys")) {
	            		sc.setHasteyboys(!sc.isHasteyboys());
	            		if(sc.isHasteyboys()) {
							ItemMeta meta = current.getItemMeta();
							meta.setLore(loreActive);
							inv.getItem(1).setItemMeta(meta);
						} else {
							ItemMeta meta = current.getItemMeta();
							meta.setLore(loreDesactive);
							inv.getItem(1).setItemMeta(meta);
						}
	            	}
	            	if (current.getItemMeta().getDisplayName().equals("SafeMiners")) {
	            		sc.setSafeminers(!sc.isSafeminers());
	            		if(sc.isSafeminers()) {
							ItemMeta meta = current.getItemMeta();
							meta.setLore(loreActive);
							inv.getItem(2).setItemMeta(meta);
						} else {
							ItemMeta meta = current.getItemMeta();
							meta.setLore(loreDesactive);
							inv.getItem(2).setItemMeta(meta);
						}
	            	}
	            	if (current.getItemMeta().getDisplayName().equals("AllCobble")) {
	            		sc.setAllcobble(!sc.isAllcobble());
	            		if(sc.isAllcobble()) {
							ItemMeta meta = current.getItemMeta();
							meta.setLore(loreActive);
							inv.getItem(3).setItemMeta(meta);
						} else {
							ItemMeta meta = current.getItemMeta();
							meta.setLore(loreDesactive);
							inv.getItem(3).setItemMeta(meta);
						}
	            	}
	            	if (current.getItemMeta().getDisplayName().equals("BetaZombie")) {
	            		sc.setBetazombie(!sc.isBetazombie());
	            		if(sc.isBetazombie()) {
							ItemMeta meta = current.getItemMeta();
							meta.setLore(loreActive);
							inv.getItem(4).setItemMeta(meta);
						} else {
							ItemMeta meta = current.getItemMeta();
							meta.setLore(loreDesactive);
							inv.getItem(4).setItemMeta(meta);
						}
	            	}
	            	if (current.getItemMeta().getDisplayName().equals("DiamondLimit")) {
	            		sc.setDiamondlimit(!sc.isDiamondlimit());
	            		if(sc.isDiamondlimit()) {
							ItemMeta meta = current.getItemMeta();
							meta.setLore(loreActive);
							inv.getItem(5).setItemMeta(meta);
						} else {
							ItemMeta meta = current.getItemMeta();
							meta.setLore(loreDesactive);
							inv.getItem(5).setItemMeta(meta);
						}
	            	}
				}
    		}
    	}
    	
    	if (inv.getName() == "§8Config Uhc" && current != null) {
    		if(current.hasItemMeta()) {
	    		event.setCancelled(true);
	            if (current != null) {
				if (current.getItemMeta().getDisplayName().equals("Diamond Limit")) {
					p.openInventory(this.createMenuFleches(current, 1, 10, Integer.toString(cf.getDlimit() ) + " diamants"));
				}
				if (current.getItemMeta().getDisplayName().equals("Border initiale")) {
					p.openInventory(this.createMenuFleches(current, 10, 100, Integer.toString(cf.getBorderdepart()) + "blocs"));
				}
				if (current.getItemMeta().getDisplayName().equals("Temps Rôles")) {
					p.openInventory(this.createMenuFleches(current, 1, 10, Integer.toString(cf.getTimerRoles())  + "mn"));
				}
				if (current.getItemMeta().getDisplayName().equals("Temps Meetup")) {
					p.openInventory(this.createMenuFleches(current, 1, 10, Integer.toString(cf.getTimerMeetup()) + "mn"));
				}
				if (current.getItemMeta().getDisplayName().equals("Gapple Kill")) {
					cf.setGapplekill(!cf.isGapplekill());
					if(cf.isGapplekill()) {
						ItemMeta meta = current.getItemMeta();
						meta.setLore(loreActive);
						inv.getItem(4).setItemMeta(meta);
					} else {
						ItemMeta meta = current.getItemMeta();
						meta.setLore(loreDesactive);
						inv.getItem(4).setItemMeta(meta);
					}
				}
				if (current.getItemMeta().getDisplayName().equals("Inventaire Départ")) {
					p.getInventory().clear();
					for (ItemStack it : cf.getItemsStart()) {
						p.getInventory().addItem(it);
					}
					p.sendMessage("§7Ajouter les items dans votre inventaire puis faites §f/finishstuff §7pour finaliser l'edition de l'inventaire de départ.");
					p.closeInventory();
					p.setGameMode(GameMode.CREATIVE);
				}
				if (current.getItemMeta().getDisplayName().equals("Pearls Max/Inventaire")) {
					p.openInventory(this.createMenuFleches(current, 1, 10, Integer.toString(cf.getPearlsmax()) + " pearls"));
				}
				if (current.getItemMeta().getDisplayName().equals("START")) {
					CommandUhcStart ca = new CommandUhcStart(main);
		        	ca.onCommand(null, null, null, null);
				}
				if (current.getItemMeta().getDisplayName().equals("Speedup (only debug)")) {
					p.openInventory(this.createMenuFleches(current, 1, 10, Integer.toString(cf.getSpeedup()) + " speeeed"));
				}
	          }
    		}
    	}
    	
    	//Retour :
    		if (current != null && current.hasItemMeta()) {
    			String name = event.getInventory().getName();
    			if(current.getItemMeta().getDisplayName().equals("Retour")) {
    				if(event.getInventory().getName().equals("§8Config Uhc") || event.getInventory().getName().equals("§8Scenarios") || event.getInventory().getName().equals("§8Config Lg") || event.getInventory().getName().equals("§8Composition") || event.getInventory().getName().equals("§8Events aleatoires")) {
    					event.setCancelled(true);
    					p.openInventory(invconfigs.getConfig());
    				}
    			if(name.equals("§8Edit Diamond Limit") || name.equals("§8Edit Border initiale") || name.equals("§8Edit Temps Rôles") || name.equals("§8Edit Temps Meetup") || name.equals("§8Edit Pearls Max/Inventaire")) {
    				event.setCancelled(true);
    				p.openInventory(invconfigs.getConfigUhc());
    			}
    			
    			if(name.equals("§8Edit OursMalLéché") || name.equals("§8Edit Rumeurs") || name.equals("§8Edit Bénédiction") || name.equals("§8Edit Exposed") || name.equals("§8Edit NotAllLoups") || name.equals("§8Edit Maire")) {
    				event.setCancelled(true);
    				p.openInventory(invconfigs.getEventsAlea());
    			}
    			
    			if(name.equals("§8Edit Début Votes") || name.equals("§8Edit Rayon Kaboom") || name.equals("§8Edit Début Grrrr") || name.equals("§8Edit Cycle Jour/Nuit")) {
    				event.setCancelled(true);
    				p.openInventory(invconfigs.getConfigLg());
    			}
    		}
    	}
    		
    	// all menu +/-
    	if(inv.getName().equals("§8Edit Diamond Limit") && current != null && current.hasItemMeta()) {
    		String txt = current.getItemMeta().getDisplayName();
    		event.setCancelled(true);
    		if(txt.equals("-1") || txt.equals("-10") || txt.equals("+1") || txt.equals("+10")) {
    			cf.setDlimit(cf.getDlimit() + Integer.parseInt(txt));
    			inv.setItem(22, this.createIt(Material.PAPER, cf.getDlimit() + " diamants"));
    			
    		}
    	}
    	
    	if(inv.getName().equals("§8Edit Border initiale") && current != null && current.hasItemMeta()) {
    		String txt = current.getItemMeta().getDisplayName();
    		event.setCancelled(true);
    		if(txt.equals("-10") || txt.equals("-100") || txt.equals("+10") || txt.equals("+100")) {
    			cf.setBorderdepart(cf.getBorderdepart() + Integer.parseInt(txt));
    			inv.setItem(22, this.createIt(Material.PAPER, cf.getBorderdepart() + " blocs"));
    		}
    	}
    	
    	if(inv.getName().equals("§8Edit Temps Rôles") && current != null && current.hasItemMeta()) {
    		String txt = current.getItemMeta().getDisplayName();
    		event.setCancelled(true);
    		if(txt.equals("-1") || txt.equals("-10") || txt.equals("+1") || txt.equals("+10")) {
    			cf.setTimerRoles(cf.getTimerRoles() + Integer.parseInt(txt));
    			inv.setItem(22, this.createIt(Material.PAPER, cf.getTimerRoles() + "mn"));
    		}
    	}
    	
    	if(inv.getName().equals("§8Edit Temps Meetup") && current != null && current.hasItemMeta()) {
    		String txt = current.getItemMeta().getDisplayName();
    		event.setCancelled(true);
    		if(txt.equals("-1") || txt.equals("-10") || txt.equals("+1") || txt.equals("+10")) {
    			cf.setTimerMeetup(cf.getTimerMeetup() + Integer.parseInt(txt));
    			inv.setItem(22, this.createIt(Material.PAPER, Integer.toString(cf.getTimerMeetup()) + "mn"));
    		}
    	}
    	
    	if(inv.getName().equals("§8Edit Pearls Max/Inventaire") && current != null && current.hasItemMeta()) {
    		String txt = current.getItemMeta().getDisplayName();
    		event.setCancelled(true);
    		if(txt.equals("-1") || txt.equals("-10") || txt.equals("+1") || txt.equals("+10")) {
    			cf.setPearlsmax(cf.getPearlsmax() + Integer.parseInt(txt));
    			inv.setItem(22, this.createIt(Material.PAPER, Integer.toString(cf.getPearlsmax()) + " pearls"));
    		}
    	}
    	
    	
    	if(inv.getName().equals("§8Edit OursMalLéché") && current != null && current.hasItemMeta()) {
    		String txt = current.getItemMeta().getDisplayName();
    		event.setCancelled(true);
    		if(txt.equals("-1") || txt.equals("-10") || txt.equals("+1") || txt.equals("+10")) {
    			int newValue = this.main.getEvents().get("OursMalLéché") + Integer.parseInt(txt);
    			this.main.getEvents().put("OursMalLéché", newValue);
    			inv.setItem(22, this.createIt(Material.PAPER, newValue + "%"));
    		}
    	}
    	
    	if(inv.getName().equals("§8Edit Exposed") && current != null && current.hasItemMeta()) {
    		String txt = current.getItemMeta().getDisplayName();
    		event.setCancelled(true);
    		if(txt.equals("-1") || txt.equals("-10") || txt.equals("+1") || txt.equals("+10")) {
    			int newValue = this.main.getEvents().get("Exposed") + Integer.parseInt(txt);
    			this.main.getEvents().put("Exposed", newValue);
    			inv.setItem(22, this.createIt(Material.PAPER, newValue + "%"));
    		}
    	}
    	
    	if(inv.getName().equals("§8Edit Rumeurs") && current != null && current.hasItemMeta()) {
    		String txt = current.getItemMeta().getDisplayName();
    		event.setCancelled(true);
    		if(txt.equals("-1") || txt.equals("-10") || txt.equals("+1") || txt.equals("+10")) {
    			int newValue = this.main.getEvents().get("Rumeurs") + Integer.parseInt(txt);
    			this.main.getEvents().put("Rumeurs", newValue);
    			inv.setItem(22, this.createIt(Material.PAPER, newValue + "%"));
    		}
    	}
    	
    	if(inv.getName().equals("§8Edit Bénédiction") && current != null && current.hasItemMeta()) {
    		String txt = current.getItemMeta().getDisplayName();
    		event.setCancelled(true);
    		if(txt.equals("-1") || txt.equals("-10") || txt.equals("+1") || txt.equals("+10")) {
    			int newValue = this.main.getEvents().get("Bénédiction") + Integer.parseInt(txt);
    			this.main.getEvents().put("Bénédiction", newValue);
    			inv.setItem(22, this.createIt(Material.PAPER, newValue + "%"));
    		}
    	}
    	
    	if(inv.getName().equals("§8Edit Maire") && current != null && current.hasItemMeta()) {
    		String txt = current.getItemMeta().getDisplayName();
    		event.setCancelled(true);
    		if(txt.equals("-1") || txt.equals("-10") || txt.equals("+1") || txt.equals("+10")) {
    			int newValue = this.main.getEvents().get("Maire") + Integer.parseInt(txt);
    			this.main.getEvents().put("Maire", newValue);
    			inv.setItem(22, this.createIt(Material.PAPER, newValue + "%"));
    		}
    	}
    	
    	if(inv.getName().equals("§8Edit NotAllLoups") && current != null && current.hasItemMeta()) {
    		String txt = current.getItemMeta().getDisplayName();
    		event.setCancelled(true);
    		if(txt.equals("-1") || txt.equals("-10") || txt.equals("+1") || txt.equals("+10")) {
    			int newValue = this.main.getEvents().get("NotAllLoups") + Integer.parseInt(txt);
    			this.main.getEvents().put("NotAllLoups", newValue);
    			inv.setItem(22, this.createIt(Material.PAPER, newValue + "%"));
    		}
    	}
    	
    	if(inv.getName().equals("§8Edit Rayon Kaboom") && current != null && current.hasItemMeta()) {
    		String txt = current.getItemMeta().getDisplayName();
    		event.setCancelled(true);
    		if(txt.equals("-1") || txt.equals("-10") || txt.equals("+1") || txt.equals("+10")) {
    			cf.setRayonKaboom(cf.getRayonKaboom()+ Integer.parseInt(txt));
    			inv.setItem(22, this.createIt(Material.PAPER, Integer.toString(cf.getRayonKaboom())));
    		}
    	}
    	
    	if(inv.getName().equals("§8Edit Début Grrrr") && current != null && current.hasItemMeta()) {
    		String txt = current.getItemMeta().getDisplayName();
    		event.setCancelled(true);
    		if(txt.equals("-1") || txt.equals("-10") || txt.equals("+1") || txt.equals("+10")) {
    			cf.setJourMo(cf.getJourMo()+ Integer.parseInt(txt));
    			inv.setItem(22, this.createIt(Material.PAPER, "Jour " + Integer.toString(cf.getJourMo())));
    		}
    	}
    	
    	if(inv.getName().equals("§8Edit Début Votes") && current != null && current.hasItemMeta()) {
    		String txt = current.getItemMeta().getDisplayName();
    		event.setCancelled(true);
    		if(txt.equals("-1") || txt.equals("-10") || txt.equals("+1") || txt.equals("+10")) {
    			cf.setJourVote(cf.getJourVote()+ Integer.parseInt(txt));
    			inv.setItem(22, this.createIt(Material.PAPER, "Jour " + Integer.toString(cf.getJourVote())));
    		}
    	}
    	
    	if(inv.getName().equals("§8Edit Cycle Jour/Nuit") && current != null && current.hasItemMeta()) {
    		String txt = current.getItemMeta().getDisplayName();
    		event.setCancelled(true);
    		if(txt.equals("-5") || txt.equals("+5")) {
    			cf.setDaycycle(cf.getDaycycle() + Integer.parseInt(txt));
    			inv.setItem(22, this.createIt(Material.PAPER, Integer.toString(cf.getDaycycle()) + "mn"));
    		}
    	}
    	
    	
    	
    	
    	
    	
    }
    
    
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
    	Player player = event.getPlayer();
    	ItemStack it = event.getItem();
    	if(player.getItemInHand() != null && event.getItem() != null) {
	    	if(event.getItem().getType().equals(Material.NETHER_STAR) && event.getItem().hasItemMeta()) {
	    		if(event.getItem().getItemMeta().getDisplayName().equals("§3Configuration")) {
	    			player.openInventory(invconfigs.getConfig());
	    		}
	    	}
    	}
    }
	    
	    @EventHandler
	    public void onPvp(EntityDamageByEntityEvent event) {
	    	// update damager pour le kill
	    	if(event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
    			Player p = (Player) event.getEntity();
    			Player degateur = (Player) event.getDamager();
    			this.main.getDamagers().put(p.getUniqueId(), degateur.getUniqueId());
	    	}
	    	if(event.getEntity() instanceof Player && event.getDamager() instanceof Arrow) {
    			Player p = (Player) event.getEntity();
    			Entity shooter = (Entity) ((Arrow) event.getDamager()).getShooter();
    			if(shooter instanceof Player) {
	    			Player degateur = (Player) shooter;
	    			this.main.getDamagers().put(p.getUniqueId(), degateur.getUniqueId());
	    		}
	    	}
	    }
	 // click player infos spec
    @EventHandler
    public void onInteracta(PlayerInteractAtEntityEvent event) {
    	Player player = event.getPlayer();
    	if(player.getGameMode().equals(GameMode.SPECTATOR)) {
	    	if(event.getRightClicked().getType().equals(EntityType.PLAYER)) {
	    		Player jspec = (Player) event.getRightClicked();
	    		Collection<PotionEffect> effets = jspec.getActivePotionEffects();
	    		List<String> effetsp = new ArrayList<>();
	    		for(PotionEffect pe : effets) {
	    			effetsp.add( "- " + pe.getType().getName() + " " + pe.getAmplifier()+1);
	    		}
	    		if(effets.size() == 0) effetsp.add("§dAucun effets");
	    		ItemStack item = new ItemStack(Material.POTION);
	    		ItemMeta itemM = item.getItemMeta();
	    		itemM.setDisplayName("Effets :");
	    		itemM.setLore(effetsp);
	    		item.setItemMeta(itemM);
	    		
	    		final Inventory inv = Bukkit.createInventory((InventoryHolder)null, 54, "§8Inventaire" + jspec.getDisplayName());
	    		if(jspec.getInventory().getHelmet() != null) inv.setItem(0, jspec.getInventory().getHelmet());
	    		if(jspec.getInventory().getChestplate() != null) inv.setItem(1, jspec.getInventory().getChestplate());
	    		if(jspec.getInventory().getLeggings() != null) inv.setItem(2, jspec.getInventory().getLeggings());
	    		if(jspec.getInventory().getBoots() != null) inv.setItem(3, jspec.getInventory().getBoots());
	    		inv.setItem(5, item);
	    		inv.setItem(6, this.createIt(Material.EXP_BOTTLE, "Lvl " + jspec.getLevel()));
	    		inv.setItem(7, this.createIt(Material.RED_ROSE, "Vie : " + jspec.getHealth()));
	    		if(this.main.getPlayers().containsKey(jspec.getUniqueId()) && this.main.config.getPartieState() == "PLAYING") inv.setItem(8, this.createIt(Material.PAPER, this.main.getPlayers().get(jspec.getUniqueId()).getRole().getNom()));
	    		else inv.setItem(8, this.createIt(Material.PAPER, "Roles annoncées à " + this.main.config.getTimerRoles() + "mn"));
	    		for(int i = 0; i < 36; i++) {
	    			if(jspec.getInventory().getItem(i) != null) inv.setItem(i+18, jspec.getInventory().getItem(i));
	    		}
	    		player.openInventory(inv);
	    	}
    	}
    }
    
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
    	if(event.getEntity() instanceof Player) {
    		Player p = (Player) event.getEntity();
    		
    		// invincibilité !:
    		if(!this.main.config.getPartieState().equals("PLAYING")) {
    			event.setCancelled(true);
    		}
    		
    		// salvation
    		if(event.getCause().equals(DamageCause.FALL) && this.main.getPlayers().containsKey(p.getUniqueId())) {
	    		if(this.main.getPlayers().get(p.getUniqueId()).isSalvate()) {
	    			event.setCancelled(true);
	    		}
	    	}
    		
    		// border tp
    		if(event.getCause().equals(DamageCause.SUFFOCATION)) {
    			Location loc = p.getLocation();
    			double border = this.main.getTask().getBorder();    			
    			
    			if(border/2 < loc.getX()) {
    				event.setCancelled(true);
    				Location nvloc = new Location(p.getWorld(), border/2-1, loc.getY(), loc.getZ());
    				p.teleport(nvloc);
    			}
    			
    			if((border/2)*-1 > loc.getX()) {
    				event.setCancelled(true);
    				Location nvloc = new Location(p.getWorld(), border/2-1, loc.getY(), loc.getZ());
    				p.teleport(nvloc);
    			}
    			
    			if(border/2 < loc.getZ()) {
    				event.setCancelled(true);
    				Location nvloc = new Location(p.getWorld(), loc.getX(), loc.getY(), border/2-1);
    				p.teleport(nvloc);
    			}
    			
    			if((border/2)*-1 > loc.getZ()) {
    				event.setCancelled(true);
    				Location nvloc = new Location(p.getWorld(), loc.getX(), loc.getY(), border/2-1);
    				p.teleport(nvloc);
    			}
    		}
    		// safeminers 
    		if(this.main.scenarios.isSafeminers()) {
	    		if(p.getLocation().getY() < 32) event.setDamage(event.getDamage() / 2);
	    		if(event.getCause().equals(DamageCause.FIRE_TICK) && p.getLocation().getY() < 32) event.setCancelled(true);
	    		if((p.getLocation().getBlock().getType().equals(Material.LAVA) || p.getLocation().getBlock().getType().equals(Material.STATIONARY_LAVA)) && p.getLocation().getY() < 32) event.setCancelled(true);
    		}
    	}
    }
    

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
    	Player p = (Player) event.getEntity();
    	
    	ArrayList<ItemStack> stuff = new ArrayList<ItemStack>();
    	ItemStack casque = p.getInventory().getHelmet();
    	ItemStack plastron = p.getInventory().getChestplate();
    	ItemStack jambes= p.getInventory().getLeggings();
    	ItemStack bottes = p.getInventory().getBoots();
    	
		for (ItemStack it : p.getInventory()) {
			if(it != null) stuff.add(it);
		}
		LgPlayer ap = this.main.getPlayers().get(p.getUniqueId());
		ap.setCasque(casque);
		ap.setPlastron(plastron);
		ap.setJambes(jambes);
		ap.setBottes(bottes);
		ap.setStuff(stuff);
		
		Location loc = p.getLocation();
		event.getDrops().clear();
		event.setDeathMessage("");
		Bukkit.getScheduler().runTaskLater(main, () -> { event.getEntity().spigot().respawn(); }, 10L);
		// tp lobby si besoin
    		Bukkit.getScheduler().runTaskLater(main, () -> {
    			System.out.println("debut 1.5sec");
	    		// Ancien
	    		if(main.getPlayers().get(p.getUniqueId()).getRole().getNom().equals("Ancien") && main.getPlayers().get(p.getUniqueId()).getRole().getNbUses() > 0) {
	    			if(this.main.getDamagers().containsKey(p.getUniqueId())) {
	    			LgPlayer tueur = this.main.getPlayers().get(this.main.getDamagers().get(p.getUniqueId()));
		    			if(tueur.getRole().getCamp().equals("§4Loup-Garou§f")) {
			    	    	if(main.getPlayers().containsKey(p.getUniqueId())) {
			    			// TP RANDOM MAP + give stuff + armure
			    			for (ItemStack it : stuff) {
								p.getInventory().addItem(it);
							}
			    			p.getInventory().setHelmet(casque);
			    			p.getInventory().setChestplate(plastron);
			    			p.getInventory().setLeggings(jambes);
			    			p.getInventory().setBoots(bottes);
			    			
			    			main.getPlayers().get(p.getUniqueId()).setRez(true);
			    			Bukkit.getScheduler().runTaskLater(main, () -> { ap.setRez(false); }, 600L);
			    			p.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
			    			p.sendMessage("§7Vous venez de réssuciter grâce à votre pouvoir. Vous perdez cependant votre effet de Résistance");
			    			ap.getRole().use();
			    			}
		    			} else {
		    				tueur.getPlayer().sendMessage("§7Vous venez de tuer l'Ancien. Par conséquent vous perdez vos pouvoirs");
		    				tueur.getRole().setNbUses(0);
		    				tueur.getRole().setCanUse(false);
		    			}
	    			}
	    		}
	    		
	    		// Idiot
	    		if(main.getPlayers().get(p.getUniqueId()).getRole().getNom().equals("Idiot Du Village") && main.getPlayers().get(p.getUniqueId()).getRole().getNbUses() > 0) {
	    			if(this.main.getDamagers().containsKey(p.getUniqueId())) {
	    			LgPlayer tueur = this.main.getPlayers().get(this.main.getDamagers().get(p.getUniqueId()));
		    			if(!tueur.getRole().getCamp().equals("§4Loup-Garou§f")) {
			    	    	if(main.getPlayers().containsKey(p.getUniqueId())) {
			    			// TP RANDOM MAP + give stuff + armure
			    			for (ItemStack it : stuff) {
								p.getInventory().addItem(it);
							}
			    			p.getInventory().setHelmet(casque);
			    			p.getInventory().setChestplate(plastron);
			    			p.getInventory().setLeggings(jambes);
			    			p.getInventory().setBoots(bottes);
			    			
			    			main.getPlayers().get(p.getUniqueId()).setRez(true);
			    			Bukkit.getScheduler().runTaskLater(main, () -> { ap.setRez(false); }, 600L);
			    			p.setMaxHealth(16);
			    			Bukkit.broadcastMessage("Le joueur " + p.getDisplayName() + " qui est Idiot du Village a été assassiné de sang froid par un villageois.");
			    			ap.getRole().use();
			    			}
		    			}
	    			}
	    		}
    			
    		if(!main.getPlayers().get(p.getUniqueId()).isRez()) {
    			// propose ipdl (tp + setrez si commande)
    			for (UUID uuid : this.main.config.getPlayersByRole("Infect Père des Loups")) {
					Player soso = Bukkit.getPlayer(uuid);
					if(this.main.getPlayers().get(uuid).getRole().getNbUses() > 0) {
						this.main.getPlayers().get(uuid).getRole().setCanUse(true);
						if(soso != null) tm.sendClickableCommand(soso, "§6" + p.getDisplayName() + " est mort. Vous avez 6 secondes pour cliquer sur ce message si vous souhaitez l'infecter.", "lg infecter " + p.getDisplayName());
					}
				}
    			System.out.println("propose ipdl");
    			Bukkit.getScheduler().runTaskLater(main, () -> {
    				// ipdl ne peut PLUS utiliser
    				for (UUID uuid : this.main.config.getPlayersByRole("Infect Père des Loups")) {
    					if(this.main.getPlayers().get(uuid).getRole().getNbUses() > 0) {
    						Player soso = Bukkit.getPlayer(uuid);
	    					this.main.getPlayers().get(uuid).getRole().setCanUse(false);
    					}
    				}
    				
    				if(!main.getPlayers().get(p.getUniqueId()).isRez()) {
    					// propose sorcière (tp + setrez si commande)
    					System.out.println("propose soso");
    					for (UUID uuid : this.main.config.getPlayersByRole("Sorcière")) {
    						Player soso = Bukkit.getPlayer(uuid);
    						if(this.main.getPlayers().get(uuid).getRole().getNbUses() > 0) {
    							boolean tupeux = true;
    							if(!this.main.config.isAutorez()) {
    								if(uuid.equals(p.getUniqueId())) tupeux = false;
    							}
    							if(tupeux) {
		    						this.main.getPlayers().get(uuid).getRole().setCanUse(true);
		    						if(soso != null) tm.sendClickableCommand(soso, "§6" + p.getDisplayName() + " est mort. Vous avez 6 secondes pour cliquer sur ce message si vous souhaitez le réssuciter.", "lg ressuciter " + p.getDisplayName());
		    					}
    						}
    					}
    				}
    					Bukkit.getScheduler().runTaskLater(main, () -> {
    						// sorcière ne peut PLUS utiliser
    	    				for (UUID uuid : this.main.config.getPlayersByRole("Sorcière")) {
    	    					Player soso = Bukkit.getPlayer(uuid);
    	    					this.main.getPlayers().get(uuid).getRole().setCanUse(false);
    	    				}
    	    				
    						if(main.getPlayers().get(p.getUniqueId()).isRez()) {
    							// si rez : pas de processus de mort
    							//main.getPlayers().get(p.getUniqueId()).setRez(false);
    						} else {
    							// si mort >
	    							mortFinale(ap, p, stuff, casque, plastron, jambes, bottes, loc);
    						}
    					}, 120L);
    			}, 120L);
    		}
    		}, 30L);
    	}
    		
    		//
    		// pretresse/pivot ou ermite craintif
    		//event.setDeathMessage(p.getDisplayName() + " est mort. Il était " +  main.getPlayers().get(p.getUniqueId()).getRole().getNom());
    		
    
    
    
    
    // Autre
    		
    @EventHandler
    public void onDrop(final PlayerDropItemEvent event) {
    	Player player = event.getPlayer();
    	if(event.getItemDrop().getItemStack().hasItemMeta()) {
    		if(event.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals("§3Configuration")) {
    			event.setCancelled(true);
    		}
    	}
    }
    
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
    	Player player = event.getPlayer();
    	Block bloc = event.getBlock();
    	World world = bloc.getWorld();
    	if(this.main.scenarios.isCutclean()) {
	    	if(bloc.getType() == Material.IRON_ORE) {
	    		Location clone = new Location(bloc.getWorld(), bloc.getLocation().getBlockX() + 0.5, (double)bloc.getLocation().getBlockY(), bloc.getLocation().getBlockZ() + 0.5);
	    		event.setCancelled(true);
	    		bloc.setType(Material.AIR);
	    		bloc.getState().update();
	    		bloc.getWorld().dropItemNaturally(clone, new ItemStack(Material.IRON_INGOT));
	            ((ExperienceOrb)bloc.getWorld().spawn(clone, (Class)ExperienceOrb.class)).setExperience(2);
	    	}
	    	if(bloc.getType() == Material.GOLD_ORE) {
	    		Location clone = new Location(bloc.getWorld(), bloc.getLocation().getBlockX() + 0.5, (double)bloc.getLocation().getBlockY(), bloc.getLocation().getBlockZ() + 0.5);
	    		event.setCancelled(true);
	    		bloc.setType(Material.AIR);
	    		bloc.getState().update();
	    		world.dropItemNaturally(clone, new ItemStack(Material.GOLD_INGOT));
	    		 ExperienceOrb orb = world.spawn(clone, ExperienceOrb.class);
	             orb.setExperience(4);
	    	}
    	}
    	// double xp
    	if(bloc.getType() == Material.DIAMOND_ORE) {
    		Location clone = new Location(bloc.getWorld(), bloc.getLocation().getBlockX() + 0.5, (double)bloc.getLocation().getBlockY(), bloc.getLocation().getBlockZ() + 0.5);
    		 ExperienceOrb orb = world.spawn(clone, ExperienceOrb.class);
             orb.setExperience(7);
    	}
    	
    	if(bloc.getType() == Material.EMERALD_ORE) {
    		Location clone = new Location(bloc.getWorld(), bloc.getLocation().getBlockX() + 0.5, (double)bloc.getLocation().getBlockY(), bloc.getLocation().getBlockZ() + 0.5);
    		event.setExpToDrop(5);
    	}
    	
    	if(bloc.getType() == Material.REDSTONE_ORE || bloc.getType() == Material.GLOWING_REDSTONE_ORE) {
    		Location clone = new Location(bloc.getWorld(), bloc.getLocation().getBlockX() + 0.5, (double)bloc.getLocation().getBlockY(), bloc.getLocation().getBlockZ() + 0.5);
    		event.setExpToDrop(5);
    	}
    	
    	if(bloc.getType() == Material.LAPIS_ORE) {
    		Location clone = new Location(bloc.getWorld(), bloc.getLocation().getBlockX() + 0.5, (double)bloc.getLocation().getBlockY(), bloc.getLocation().getBlockZ() + 0.5);
    		event.setExpToDrop(5);
    	}
    	// taux flint pommes
    	if(bloc.getType() == Material.GRAVEL) {
    		Location clone = new Location(bloc.getWorld(), bloc.getLocation().getBlockX() + 0.5, (double)bloc.getLocation().getBlockY(), bloc.getLocation().getBlockZ() + 0.5);
    		event.setCancelled(true);
    		bloc.setType(Material.AIR);
    		bloc.getState().update();
    		world.dropItemNaturally(clone, new ItemStack(Material.FLINT));
    	}
    	
    	if(bloc.getType() == Material.LEAVES || bloc.getType() == Material.LEAVES_2) {
    		Location clone = new Location(bloc.getWorld(), bloc.getLocation().getBlockX() + 0.5, (double)bloc.getLocation().getBlockY(), bloc.getLocation().getBlockZ() + 0.5);
    		event.setCancelled(true);
    		bloc.setType(Material.AIR);
    		bloc.getState().update();
    		int random = (int) (1+Math.random() * 100);
    		if(random < 8) world.dropItemNaturally(clone, new ItemStack(Material.APPLE));
    	}
    	
    	// allCobble
    	if(this.main.scenarios.isAllcobble()) {
    		if(bloc.getType().equals(Material.STONE)) {
    			if(bloc.getData() == 1 || bloc.getData() == 3 || bloc.getData() == 5) {
    				Location clone = new Location(bloc.getWorld(), bloc.getLocation().getBlockX() + 0.5, (double)bloc.getLocation().getBlockY(), bloc.getLocation().getBlockZ() + 0.5);
    	    		event.setCancelled(true);
    	    		bloc.setType(Material.AIR);
    	    		bloc.getState().update();
    	    		bloc.getWorld().dropItemNaturally(clone, new ItemStack(Material.COBBLESTONE));
    			}
    		}
    	}
    	
    }
    
// Hastey Boys
    
    @EventHandler
    public void onCraft(final PrepareItemCraftEvent event) {
    	if(this.main.scenarios.isHasteyboys()) {
	    	ItemStack item = event.getRecipe().getResult();
	    	ArrayList<Material> outils = new ArrayList<>();
	    	outils.add(Material.WOOD_PICKAXE); outils.add(Material.STONE_PICKAXE); outils.add(Material.IRON_PICKAXE); outils.add(Material.DIAMOND_PICKAXE); 
	    	outils.add(Material.WOOD_AXE); outils.add(Material.STONE_AXE); outils.add(Material.IRON_AXE); outils.add(Material.DIAMOND_AXE);
	    	outils.add(Material.WOOD_SPADE); outils.add(Material.STONE_SPADE); outils.add(Material.IRON_SPADE); outils.add(Material.DIAMOND_SPADE);
	    	if(outils.contains(item.getType())) {
	    		ItemMeta meta = item.getItemMeta();
	    		meta.addEnchant(Enchantment.DIG_SPEED, 3, true);
	    		meta.addEnchant(Enchantment.DURABILITY, 3, true);
	    		item.setItemMeta(meta);
	    		event.getInventory().setResult(item);
	    	}
    	}
    }
    
    @EventHandler
    public void onMobDeath(EntityDeathEvent event) {
    	// betazombie :
    	if(this.main.scenarios.isBetazombie()) {
    		if(event.getEntity() instanceof Zombie) {
    			event.getDrops().add(new ItemStack(Material.FEATHER, 1));
    		}
    	}
    }
    
 // Empecher la pluie etc
    
    @EventHandler
    public void onPortal(PlayerPortalEvent event) {
    	event.setCancelled(true);
    }
    
    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        
        boolean rain = event.toWeatherState();
        if(rain)
            event.setCancelled(true);
    }
    
    @EventHandler
    public void onThunderChange(ThunderChangeEvent event) {
     
        boolean storm = event.toThunderState();
        if(storm)
            event.setCancelled(true);
    }
 
	 @EventHandler
	 public void onAchievement(PlayerAchievementAwardedEvent event) {
		 event.setCancelled(true);
	 }
	 
	 // chat mute + rumeurs
	 
	 @EventHandler
	 public void onChat(PlayerChatEvent event) {
		 if(this.main.config.isRumeurs()) {
			 Player p = event.getPlayer();
			 if(!this.main.config.getRumeursListe().containsKey(p.getUniqueId())) {
				 this.main.config.getRumeursListe().put(p.getUniqueId(), event.getMessage());
				 event.setCancelled(true);
			 } else {
				 p.sendMessage("§cErreur : Vous ne pouvez envoyer qu'une rumeur");
				 event.setCancelled(true);
			 }
		 }
	 }
	 
	 @EventHandler
	 public void onLoot(InventoryPickupItemEvent event) {
		
		 if(event.getItem().getType().equals(Material.ENDER_PEARL)) {
			 System.out.println("trigger");
			 int nb = 0;
			 for (ItemStack it : event.getInventory().getContents()) {
				if(it.getType().equals(Material.ENDER_PEARL)) nb += it.getAmount();
			}
			 System.out.println(nb);
			 if(nb == this.main.config.getPearlsmax()) {
				 event.setCancelled(true);
				 Player p = ((OfflinePlayer) event).getPlayer();
				 p.sendMessage("§cVous ne pouvez avoir que " + nb + " pearls maximum dans votre inventaire");
			 }
		 }
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
    
    
    public Inventory createMenuFleches(ItemStack it, int first, int second, String etat) {
    	Inventory inv = Bukkit.createInventory((InventoryHolder)null, 27, "§8Edit " + it.getItemMeta().getDisplayName());
    	ItemStack rouge = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
    	ItemStack verte = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
    	ItemStack marron = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 12);
    	for(int i = 0; i < 11; i++) inv.setItem(i, marron);
    	
		inv.setItem(11, this.createItemStain(Material.STAINED_GLASS_PANE, "-" + second, (short) 14));
		inv.setItem(12, this.createItemStain(Material.STAINED_GLASS_PANE, "-" + first, (short) 14));
		
		inv.setItem(13, it);
		
		inv.setItem(14, this.createItemStain(Material.STAINED_GLASS_PANE, "+" + first, (short) 13));
		inv.setItem(15, this.createItemStain(Material.STAINED_GLASS_PANE, "+" + second, (short) 13));
		for(int i = 16; i < 22; i++) inv.setItem(i, marron);

		inv.setItem(22, this.createIt(Material.PAPER, etat));
		for(int i = 23; i < 26; i++) inv.setItem(i, marron);
		inv.setItem(26, this.createIt(Material.CACTUS, "Retour"));
		
		return inv;
    }
    
    public void mortFinale(LgPlayer ap, Player p, ArrayList<ItemStack> stuff, ItemStack casque, ItemStack plastron, ItemStack jambes, ItemStack bottes, Location loc) {
    	this.main.getPlayers().remove(p.getUniqueId());
    	// get tueur pour ange voleur / nb kills
		if(this.main.getDamagers().containsKey(p.getUniqueId())) {
			LgPlayer tueur = this.main.getPlayers().get(this.main.getDamagers().get(p.getUniqueId()));
			tueur.addKill();
			if(tueur.getRole().getNom().equals("Voleur")) {
				if(tueur.getRole().getNbUses() > 0) {
					if(tueur.getPlayer() != null) {
						tueur.getRole().use();
						Player tueurp = tueur.getPlayer();
						tueurp.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
						tueurp.setMaxHealth(p.getMaxHealth());
						tueur.setRole(ap.getRole());
						tueur.setVoleur(true);
						for (PotionEffect pe : tueur.getRole().getEffetsPerma()) {
							tueurp.addPotionEffect(pe);
						}
						
						tueurp.sendMessage("§7Vous venez de tuer " + p.getDisplayName() + " et récupérez donc son rôle");
						tueurp.sendMessage("Vous êtes " + tueur.getRole().getNom() + ", vous devez gagner avec les " + tueur.getRole().getCamp() + " bla bla bla ");
					}
					
				}
			}
			
			if(tueur.getRole().getNom().equals("Ange") && tueur.getRole().getTarget().equals(p.getUniqueId()) && tueur.getRole().getType() == 1) {
				if(tueur.getPlayer() != null) {
					tueur.getPlayer().sendMessage("§7Vous venez de tuer votre cible, vous passez donc à §415♥§7 permanents.");
						tueur.getPlayer().setMaxHealth(30);
				}
			}
			
		}
		
		// si kaboom : BOOM (changer rayon selon config)
		if(ap.getRole().getNom().equals("Loup-Garou Kaboom")) p.getWorld().createExplosion(loc, this.main.config.getRayonKaboom());
		for (ItemStack item : stuff) {
			Bukkit.getWorld("world").dropItemNaturally(loc, item);
		}
		if(casque != null) Bukkit.getWorld("world").dropItemNaturally(loc, casque);
		if(plastron != null) Bukkit.getWorld("world").dropItemNaturally(loc, plastron);
		if(jambes != null) Bukkit.getWorld("world").dropItemNaturally(loc, jambes);
		if(bottes != null) Bukkit.getWorld("world").dropItemNaturally(loc, bottes);
		if(this.main.config.isGapplekill()) Bukkit.getWorld("world").dropItemNaturally(loc, new ItemStack(Material.GOLDEN_APPLE, 1));
		if(p != null) p.setGameMode(GameMode.SPECTATOR);
		boolean isGrim = false;
		if(!main.getPlayers().get(p.getUniqueId()).getRole().getNom().equals("Ermite") && !main.getPlayers().get(p.getUniqueId()).getRole().getNom().equals("Loup-Garou Craintif")) {
			if(this.main.getDamagers().containsKey(p.getUniqueId())) {
				LgPlayer tueur = this.main.getPlayers().get(this.main.getDamagers().get(p.getUniqueId()));
				if(tueur.getRole().getNom().equals("Loup-Garou Grimeur") && tueur.getRole().getNbUses() > 0) {
					tueur.getRole().use();
					isGrim = true;
					Bukkit.broadcastMessage("§7================§4 ♠ §7================");
	    			Bukkit.broadcastMessage("§7Le village a perdu l'un de ses membres: §f" + p.getDisplayName() + "§7, qui était §fLoup-Garou");
	    			Bukkit.broadcastMessage("§7================================");
				}
			}
			if(!isGrim) {
				String role = (this.main.getEvents().containsKey("NotAllLoups") && main.getPlayers().get(p.getUniqueId()).getRole().getCamp().equals("§4Loup-Garou§f")) ? "Loup-Garou": main.getPlayers().get(p.getUniqueId()).getRole().getNom();
    			String sivoleur = main.getPlayers().get(p.getUniqueId()).isVoleur() ? "(Voleur) " : "";
    			Bukkit.broadcastMessage("§7================§4 ♠ §7================");
    			Bukkit.broadcastMessage("§7Le village a perdu l'un de ses membres: §f" + p.getDisplayName() + "§7, qui était §f" + sivoleur + role);
    			Bukkit.broadcastMessage("§7================================");
			}
		}
		
		// si couple : 
		if(ap.getCouple() != null) {
			if(this.main.getPlayers().containsKey(ap.getUuid())) {
				Player p2 = Bukkit.getPlayer(ap.getCouple());
				ArrayList<ItemStack> stuff2 = new ArrayList<ItemStack>();
		    	ItemStack casque2 = p2.getInventory().getHelmet();
		    	ItemStack plastron2 = p2.getInventory().getChestplate();
		    	ItemStack jambes2 = p2.getInventory().getLeggings();
		    	ItemStack bottes2 = p2.getInventory().getBoots();
		    	
				for (ItemStack it : p2.getInventory()) {
					if(it != null) stuff2.add(it);
				}
				LgPlayer ap2 = this.main.getPlayers().get(p2.getUniqueId());
				ap2.setCasque(casque);
				ap2.setPlastron(plastron);
				ap2.setJambes(jambes);
				ap2.setBottes(bottes);
				ap2.setStuff(stuff);
				
				Location loc2 = p2.getLocation();
				this.mortFinale(ap2, p2, stuff2, casque2, plastron2, jambes2, bottes2, loc2);
			}
		}
		
		
		// check modèle es, cible ange, couple, kill voleur, mystique, event solitaire
		// es 
		if(ap.isModeleEs()) {
			for (UUID uuid : this.main.config.getPlayersByRole("Enfant Sauvage")) {
				Player es = Bukkit.getPlayer(uuid);
				LgPlayer esp = this.main.getPlayers().get(uuid);
				if(esp.getRole().getTarget().equals(ap.getUuid())) {
					esp.getRole().setCamp("§4Loup-Garou§f");
					if(es != null) {
						ArrayList<LgPlayer> loups = this.main.config.getPlayersCamp("§4Loup-Garou§f"); 
						String listelg = "§c";
						// affiche liste loup
						// prévenir les loups
						for (LgPlayer lgp : loups) {
							Player lg = lgp.getPlayer();
							Player lgoffline = (Player) Bukkit.getOfflinePlayer(lgp.getUuid());
							listelg += lgoffline.getDisplayName() + ", ";
							if(lg != null && (!lg.getUniqueId().equals(es.getUniqueId()))) lg.sendMessage("§4Un joueur a rejoint votre camp. Faites /lg role pour découvrir son pseudo");
						}
						es.sendMessage("Blabla votre modèle est mort vous passez loup");
						es.sendMessage("§4Voici la liste des loups-garou :");
						es.sendMessage(listelg);
					}
				}
				
			}
		}
		
		// mystique
		if(ap.getRole().getCamp().equals("§4Loup-Garou§f")) {
			for (UUID uuid : this.main.config.getPlayersByRole("Loup-Garou Mystique")) {
				Player lgmplayer = Bukkit.getPlayer(uuid);
				LoupGarouMystique lgmrole = (LoupGarouMystique) this.main.getPlayers().get(uuid).getRole();
				
				// on met dans un list les rôles pour while après
				ArrayList<LgPlayer> alive = new ArrayList<>();
				for (Map.Entry mapentry : this.main.getPlayers().entrySet()) {
					alive.add((LgPlayer) mapentry.getValue());
				}
				
				int i = 0;
				while(i < alive.size()) {
					if(!alive.get(i).getRole().getCamp().equals("§4Loup-Garou§f") && !lgmrole.getRolesEu().contains(alive.get(i))) {
						break;
					} else  {
						i++;
					}
				}
				if(i == alive.size()) {
					if(lgmplayer != null) lgmplayer.sendMessage("Vous connaissez déjà tout les rôles de la partie. Sacré clairvoyant !");
				} else {
					if(lgmplayer != null) lgmplayer.sendMessage("§7Un membre de votre camp est mort. Par conséquent, vous découvrez que §f" + alive.get(i).getPlayer().getDisplayName() + " §7est §f" + alive.get(i).getRole().getNom());
					lgmrole.addRolesEu(alive.get(i));
				}
			}
		}
		
		if(ap.isCibleAnge()) {
			ArrayList<UUID> anges = this.main.config.getPlayersByRole("Ange");
			for (UUID ang : anges) {
				LgRole ange = this.main.getPlayers().get(ang).getRole();
				if(ange.getTarget().equals(p.getUniqueId()) && ange.getType() == 0) {
					if(Bukkit.getPlayer(ang) != null) {
						Bukkit.getPlayer(ang).sendMessage("§7Votre protégé est mort. Vous avez désormais 12 §4♥§7 permanents et devez gagner seul.");
						Bukkit.getPlayer(ang).setMaxHealth(24);
					}
				}
			}
		}
	}
    
}
