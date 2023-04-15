package task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import lg.LgPlayer;
import lg.TitleManager;
import lg.main;
import roles.Berger;
import roles.LgRole;
import roles.MontreurDours;

import org.bukkit.scheduler.BukkitRunnable;

import commands.CmdAnge;
import commands.CmdLove;
import commands.CmdModele;
import commands.CommandAssign;
import commands.CommandStart;



public class TimerTask extends BukkitRunnable
{
    private main main;
    private int seconde;
    private int minute;
    private int heure;
    private int jour;
    private int nbApprenti;
    private TitleManager tm;
    private int tick;
    private boolean day;
    private double border;
    
    public TimerTask(main main) {
    	this.tick = 0;
        this.seconde = 0;
        this.minute = 0;
        this.heure = 0;
        this.jour = 1;
        this.main = main;
        this.nbApprenti = 0;
        this.tm = new TitleManager();
        this.day = false;
        this.border = 1000;
    }
    
    public void run() {
        if (this.seconde == 59) {
            this.seconde = 0;
            ++this.minute;
        }
        if (this.minute == 59 && this.seconde == 59) {
            this.minute = 0;
            this.seconde = 0;
            ++this.heure;
        }
        if (this.minute != 0 && this.minute % 20 == 0 && this.seconde == 0) {
            ++this.jour;
            Bukkit.broadcastMessage("§7§m----------  §b  Fin Épisode " +  (this.jour-1) + "  §7§m----------");
            if(this.main.config.getPartieState() == "PLAYING") {
	            // nb use partout + grr + vote + berger
            	// vote start (à mettre à partir de l'ép X)
            	if(this.main.config.getJourVote() <= this.jour) {
	            	Bukkit.broadcastMessage("§bVous disposez d'une minute pour voter avec la commande /lg vote [pseudo].");
	            	Bukkit.broadcastMessage("§bLa personne ayant reçu le plus de votes verra sa vie réduite de moitié, et la regagnera au fur et à mesure de l'épisode.");
	            	this.main.config.getVotes().clear();
	            	this.main.config.setVote(true);
            	}
        	// enlève salvation :
        	
        		for (UUID uuid : this.main.config.getPlayersByRole("Salvateur")) {
        			UUID target = this.main.getPlayers().get(uuid).getRole().getTarget();
        			if(target != null) this.main.getPlayers().get(target).setSalvate(false);
        		}
        	
        	// mouton 
        	 ArrayList<UUID> bergers = this.main.config.getPlayersByRole("Berger");
             for (UUID berger: bergers) {
            	 Berger ap = (Berger) this.main.getPlayers().get(berger).getRole();
            	 if(ap.getMouton1() != "devore" && ap.getMouton1() != "pret") {
            		 Player targetPseudo = (Player) Bukkit.getOfflinePlayer(ap.getMouton1());
            		 LgRole target = this.main.getPlayers().get(targetPseudo.getUniqueId()).getRole();
            		 if(target.getCamp().equals("§4Loup-Garou§f") || target.getNom().equals("Renard")) {
            			 // mouton mangé
            			 ap.setMouton1("devore");
            			 Player bergax = Bukkit.getPlayer(berger);
            			 if(bergax != null) {
            				 bergax.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 5*60*20, 0, false, false));
            				 bergax.sendMessage("Un de vos mouton vient d'être dévoré.. Dans un accès de colère vous obtenez l'effet Speed pendant 5 minutes");
            			 }
            		 }
            	 }
            	 
            	 if(ap.getMouton2() != "devore" && ap.getMouton2() != "pret") {
            		 Player targetPseudo = (Player) Bukkit.getOfflinePlayer(ap.getMouton2());
            		 LgRole target = this.main.getPlayers().get(targetPseudo.getUniqueId()).getRole();
            		 if(target.getCamp().equals("§4Loup-Garou§f") || target.getNom().equals("Renard")) {
            			 // mouton mangé
            			 ap.setMouton2("devore");
            			 Player bergax = Bukkit.getPlayer(berger);
            			 if(bergax != null) {
            				 bergax.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 5*60*20, 0, false, false));
            				 bergax.sendMessage("§4Un de vos mouton vient d'être dévoré.. Dans un accès de colère vous obtenez l'effet Speed pendant 5 minutes");
            			 }
            		 }
            	 }
             }       
            
	            for (Map.Entry mapentry : this.main.getPlayers().entrySet()) {
	            	LgPlayer ap = (LgPlayer) mapentry.getValue();
	            	if(ap.getRole().getNom().equals("Salvateur")) {
	            		ap.getRole().setNbUses(1);
	            		if(ap.getPlayer() != null) ap.getPlayer().sendMessage("§7Vous avez 1 minute pour salvater un joueur avec la commande /lg salvater [pseudo]");
	            	}
	            	if(ap.getRole().getNom().equals("Voyante")) {
	            		ap.getRole().setNbUses(1);
	            		if(ap.getPlayer() != null) ap.getPlayer().sendMessage("§7Vous avez 1 minute pour espionner un joueur avec la commande /lg voir [pseudo]");
	            	}
	            	if(ap.getRole().getNom().equals("Voyante Bavarde")) {
	            		ap.getRole().setNbUses(1);
	            		if(ap.getPlayer() != null) ap.getPlayer().sendMessage("§7Vous avez 1 minute pour espionner un joueur avec la commande /lg voir [pseudo]");
	            	}
	            	if(ap.getRole().getNom().equals("Détective")) {
	            		ap.getRole().setNbUses(1);
	            		if(ap.getPlayer() != null) ap.getPlayer().sendMessage("§7Vous avez 1 minute pour comparer deux joueurs avec la commande /lg comparer[pseudo] [pseudo]");
	            	}
	            	
	            	if(ap.getRole().getNom().equals("Loup-Garou Feutré")) {
	            		ArrayList<String> rolesSafeAlive = new ArrayList<>();
	            		for (Map.Entry map : this.main.getPlayers().entrySet()) {
	            			LgPlayer lp = (LgPlayer) map.getValue();
	            			if(!lp.getRole().getCamp().equals("§4Loup-Garou§f")) {
	            				rolesSafeAlive.add(lp.getRole().getNom());
	            			}
	            		}
	            		String facade = "";
	            		
	            		
	            		if(rolesSafeAlive.size() == 0) facade = "Simple Villageois";
	            		else {
	            			int rnd = new Random().nextInt(rolesSafeAlive.size());
	            			facade = rolesSafeAlive.get(rnd);
	            		}
	            		ap.getRole().setFacade(facade);
	            		if(ap.getPlayer() != null) ap.getPlayer().sendMessage("§7Votre rôle de façade pour cet épisode est : §f" + facade);
	            	}
	            	
	            }
	            if(this.main.config.getJourMo() <= this.jour) {
	        		for (UUID uuid : this.main.config.getPlayersByRole("Montreur d'Ours")) {
						Player p = Bukkit.getPlayer(uuid);
						if(p != null) {
							ArrayList<Player> playersNear = new ArrayList<>();
	        	    		List<Entity> nearList = (List<Entity>)p.getNearbyEntities(40.0, 120.0, 40.0);
	        	    		nearList.add(p);
	        	        	for (Entity entity : nearList) {
	        	        		if (entity instanceof Player) {
	        	        			if(this.main.getPlayers().containsKey(entity.getUniqueId())) {
	        	        				LgPlayer proche = this.main.getPlayers().get(entity.getUniqueId());
	        	        				if(proche.getRole().getCamp().equals("§4Loup-Garou§f") && proche.getRole().getFacade() == null) {
	        	        					playersNear.add(proche.getPlayer());
	        	        				}
	        	        			}
	        	        		}
	        	        	}
	        	        	int nbgrr = playersNear.size();
	        	        	if(this.main.getEventsActives().contains("OursMalLéché")) {
	        	        		if(Math.random() < 0.5) nbgrr+= 1;
	        	        		else nbgrr-= 1;
	        	        	}
	        	        	if(nbgrr < 0) nbgrr = 0;
	        	        	
	        	        	for(int i = 0; i < nbgrr; i++) Bukkit.broadcastMessage("§6Grrr");
	        	        	System.out.println("Grognes : " + playersNear);
							
						}
	                }
	            }
        		
        		Bukkit.getScheduler().runTaskLater(main, () -> {
        			this.main.config.setVote(false);
        			for (Map.Entry mapentry : this.main.getPlayers().entrySet()) {
    	            	LgPlayer ap = (LgPlayer) mapentry.getValue();
    	            	if(ap.getRole().getNom().equals("Salvateur")) {
    	            		ap.getRole().setNbUses(0);
    	            	}
    	            	if(ap.getRole().getNom().equals("Voyante")) {
    	            		ap.getRole().setNbUses(0);
    	            	}
    	            	if(ap.getRole().getNom().equals("Voyante Bavarde")) {
    	            		ap.getRole().setNbUses(0);
    	            	}
    	            	if(ap.getRole().getNom().equals("Détective")) {
    	            		ap.getRole().setNbUses(0);
    	            	}
    	            	
    	            	if(ap.getRole().getNom().equals("Citoyen") && ap.getRole().getNbUses() > 0) {
    	            		ap.getRole().setCanUse(true);
    	            		if(ap.getPlayer() != null) {
    	            			ap.getPlayer().sendMessage("§bLe vote a été cloturé. Vous avez la possibilité de consulter encore " + ap.getRole().getNbUses() + " fois les votes des joueurs. Attention, vous n'avez que 15 secondes pour les consulters.");
    	            			tm.sendClickableCommand(ap.getPlayer(), "§3[CONSULTER LES RESULTATS]", "lg citoyen");
    	            		}
    	            	}
    	            	
    	            }
        			// citoyen peut plus utiliser + affiche vote
        			Bukkit.getScheduler().runTaskLater(main, () -> {
        				if(this.main.config.getJourVote() <= this.jour) {
	        				ArrayList<UUID> citoyens = this.main.config.getPlayersByRole("Citoyen");
	        				for (UUID citoyen : citoyens) {
	        					this.main.getPlayers().get(citoyen).getRole().setCanUse(false);
	        				}
	        				
	        				HashMap<UUID, Integer> nbvotes = new HashMap<>();
	        	        	for (Map.Entry mapentry : this.main.config.getVotes().entrySet()) {
	        	        		int count = nbvotes.containsKey(mapentry.getValue()) ? nbvotes.get(mapentry.getValue()) : 0;
	        	        		// changement du nombre de votes selon corbeau craintif ermite
	        	        		Player player = (Player) Bukkit.getOfflinePlayer((UUID) mapentry.getKey());
	        	        		int tauxvote = 1;
	        	        		if(this.main.getPlayers().get(player.getUniqueId()).getRole().getNom().equals("Corbeau")) tauxvote = 3;
	        					if(this.main.getPlayers().get(player.getUniqueId()).getRole().getNom().equals("Ermite")) tauxvote = 0;
	        					if(this.main.getPlayers().get(player.getUniqueId()).getRole().getNom().equals("Loup-Garou Craintif")) tauxvote = 0;
	        	                nbvotes.put((UUID) mapentry.getValue(), count + tauxvote);
	        	        	}
	        	        	
	        	        	// regarde top votes (avec random si besoin)
	        	        	UUID topvoteUuid = null;
	        	        	int topvoteNb = 0;
	        	        	for (Map.Entry mapentry : nbvotes.entrySet()) {
	        	        		int nb = (int) mapentry.getValue();
	        	        		if(nb > topvoteNb) {
	        	        			topvoteNb = nb;
	        	        			topvoteUuid = (UUID) mapentry.getKey();
	        	        		}
	        	        		if(nb == topvoteNb) {
	        	        			 if(Math.random() < 0.5) {
	        	        				topvoteNb = nb;
	        	             			topvoteUuid = (UUID) mapentry.getKey();
	        	        			 }
	        	        		}
	        	        	}
	        	        	
	        	        	if(topvoteNb == 0) {
	        	        		Bukkit.broadcastMessage("Personne n'a été voté");
	        	        	} else {
	        	        		Player topvote = Bukkit.getPlayer(topvoteUuid);
	        	        		Bukkit.broadcastMessage("§7RESULTAT DU VOTE :");
	        	        		Bukkit.broadcastMessage("§7Le joueur §c" + topvote.getDisplayName() + " §7a reçu le plus grand nombre de votes : §b" + topvoteNb + "§7. Il voit sa vie réduite de moitié jusqu'au milieu de l'épisode");
	        	        		if(topvote != null) {
	        	        			topvote.setMaxHealth(topvote.getMaxHealth() / 2);
	        	        			Bukkit.getScheduler().runTaskLater(main, () -> { topvote.setMaxHealth(topvote.getMaxHealth() * 2); }, 1200L); // 1200 = 10mn
	        	        		}
	        	        	}
        	        	}
        			}, 300L);
        		}, 120L);
            }
        }
        
        if(this.minute == this.main.config.getTimerRoles() -1 && this.seconde == 0 && this.heure == 0) Bukkit.broadcastMessage("§6Distribution des rôles dans 1 minute.");
        
        if(this.minute == this.main.config.getTimerRoles() -1 && this.seconde == 30 && this.heure == 0) Bukkit.broadcastMessage("§6Distribution des rôles dans 30 secondes.");
        
        
        if(this.minute == this.main.config.getTimerRoles() -1 && this.seconde == 50 && this.heure == 0) Bukkit.broadcastMessage("§6Distribution des rôles dans 10 secondes.");
        
        if(this.minute == this.main.config.getTimerRoles() -1 && this.seconde > 55 && this.heure == 0) {
        	Bukkit.broadcastMessage("§6Distribution des rôles dans " + (60 - this.seconde) + " secondes.");
        }
        
        PotionEffect invi = new PotionEffect(PotionEffectType.INVISIBILITY, 60, 0, false, false);
        PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 60, 0, false, false);
        PotionEffect weakness = new PotionEffect(PotionEffectType.WEAKNESS, 60, 0, false, false);
        PotionEffect force = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 60, 0, false, false);
        PotionEffect resistance = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 0, false, false);
        
        
        // Jour h24 > effets + PG
        if(day) {
        	if(this.main.config.getPartieState() == "PLAYING") {
        		for (UUID uuid : this.main.config.getPlayersByRole("Petit Garçon")) {
        			Player p = Bukkit.getPlayer(uuid);
    				if(p != null) {
    					PlayerInventory inv = p.getInventory();
    					if(inv.getHelmet() == null && inv.getChestplate() == null && inv.getLeggings() == null && inv.getBoots() == null) {
    						p.removePotionEffect(PotionEffectType.INVISIBILITY);
    						p.removePotionEffect(PotionEffectType.SPEED);
    						p.removePotionEffect(PotionEffectType.WEAKNESS);
    						p.addPotionEffect(invi);
    						p.addPotionEffect(speed);
    						p.addPotionEffect(weakness);
    						
    					}
    				}
        		}
        		
        		for (Map.Entry mapentry : this.main.getPlayers().entrySet()) {
                    LgPlayer ap = (LgPlayer) mapentry.getValue();
                    Player p = ap.getPlayer();
                    // supprime effets nuit puis add jour
                    for(int i = 0; i < ap.getRole().getEffetsNuit().size(); i++) {
                    	p.removePotionEffect(ap.getRole().getEffetsNuit().get(i).getType());
                    }
                    
                    for(int i = 0; i < ap.getRole().getEffetsJour().size(); i++) {
                    	p.addPotionEffect(ap.getRole().getEffetsJour().get(i));
                    }
            	}
        		
        		ArrayList<UUID> ermites = this.main.config.getPlayersByRole("Ermite");
                ArrayList<UUID> craintifs = this.main.config.getPlayersByRole("Loup-Garou Craintif");
                for (UUID erm : ermites) {
                	Player ermite = Bukkit.getPlayer(erm);
                	if(ermite != null) {
                		ArrayList<Player> proches = new ArrayList<>();
                		List<Entity> nearList = (List<Entity>)ermite.getNearbyEntities(20.0, 120.0, 20.0);
        	        	for (Entity entity : nearList) {
        	        		if (entity instanceof Player) {
        	        			if(this.main.getPlayers().containsKey(entity.getUniqueId())) {
        	        				proches.add((Player) entity);
    	        				}
    	        			}
        	        	}
        	        	if(proches.size() == 0) {
        	        		ermite.removePotionEffect(PotionEffectType.SPEED);
        	        		ermite.addPotionEffect(speed);
        	        	}
        	        	if(proches.size() < 4) {
        	        		ermite.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
        	        		ermite.addPotionEffect(force);
        	        	} else {
        	        		ermite.removePotionEffect(PotionEffectType.WEAKNESS);
        	        		ermite.addPotionEffect(weakness);
        	        	}
                	}
                }
                
                for (UUID crain : craintifs) {
                	Player craintif = Bukkit.getPlayer(crain);
                	if(craintif != null) {
                		ArrayList<Player> proches = new ArrayList<>();
                		List<Entity> nearList = (List<Entity>)craintif.getNearbyEntities(20.0, 120.0, 20.0);
        	        	for (Entity entity : nearList) {
        	        		if (entity instanceof Player) {
        	        			if(this.main.getPlayers().containsKey(entity.getUniqueId())) {
        	        				if(this.main.getPlayers().get(entity.getUniqueId()).getRole().getCamp().equals("§4Loup-Garou§f")) {
        	        					proches.add((Player) entity);
        	        				}
    	        				}
    	        			}
        	        	}
        	        	if(proches.size() == 0) {
        	        		craintif.removePotionEffect(PotionEffectType.SPEED);
        	        		craintif.addPotionEffect(speed);
        	        	}
        	        	if(proches.size() < 3) {
        	        		craintif.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        	        		craintif.addPotionEffect(resistance);
        	        	} else {
        	        		craintif.removePotionEffect(PotionEffectType.WEAKNESS);
        	        		craintif.addPotionEffect(weakness);
        	        	}
                	}
                }
            }
        }
        
        // nuit h24 > effets nuit + PF / LGF
        
        if(!day) {
        	if(this.main.config.getPartieState() == "PLAYING") {
        		for (UUID uuid : this.main.config.getPlayersByRole("Petite Fille")) {
        			Player p = Bukkit.getPlayer(uuid);
    				if(p != null) {
    					PlayerInventory inv = p.getInventory();
    					if(inv.getHelmet() == null && inv.getChestplate() == null && inv.getLeggings() == null && inv.getBoots() == null) {
    						p.removePotionEffect(PotionEffectType.INVISIBILITY);
    						p.removePotionEffect(PotionEffectType.SPEED);
    						p.removePotionEffect(PotionEffectType.WEAKNESS);
    						p.addPotionEffect(invi);
    						p.addPotionEffect(speed);
    						p.addPotionEffect(weakness);
    						
    					}
    				}
        		}
        		
        		for (UUID uuid : this.main.config.getPlayersByRole("Loup-Garou Fantôme")) {
        			Player p = Bukkit.getPlayer(uuid);
    				if(p != null) {
    					PlayerInventory inv = p.getInventory();
    					if(inv.getHelmet() == null && inv.getChestplate() == null && inv.getLeggings() == null && inv.getBoots() == null) {
    						p.removePotionEffect(PotionEffectType.INVISIBILITY);
    						p.removePotionEffect(PotionEffectType.SPEED);
    						p.removePotionEffect(PotionEffectType.WEAKNESS);
    						p.addPotionEffect(invi);
    						p.addPotionEffect(speed);
    						p.addPotionEffect(weakness);
    						
    					}
    				}
        		}
        		
        		for (Map.Entry mapentry : this.main.getPlayers().entrySet()) {
                    LgPlayer ap = (LgPlayer) mapentry.getValue();
                    Player p = ap.getPlayer();
                    // supprime effets jour puis add nuit
                    for(int i = 0; i < ap.getRole().getEffetsJour().size(); i++) {
                    	p.removePotionEffect(ap.getRole().getEffetsJour().get(i).getType());
                    }
                    
                    for(int i = 0; i < ap.getRole().getEffetsNuit().size(); i++) {
                    	p.addPotionEffect(ap.getRole().getEffetsNuit().get(i));
                    }
            	}
        		
        		ArrayList<UUID> ermites = this.main.config.getPlayersByRole("Ermite");
                ArrayList<UUID> craintifs = this.main.config.getPlayersByRole("Loup-Garou Craintif");
                for (UUID erm : ermites) {
                	Player ermite = Bukkit.getPlayer(erm);
                	if(ermite != null) {
                		ArrayList<Player> proches = new ArrayList<>();
                		List<Entity> nearList = (List<Entity>)ermite.getNearbyEntities(20.0, 120.0, 20.0);
        	        	for (Entity entity : nearList) {
        	        		if (entity instanceof Player) {
        	        			if(this.main.getPlayers().containsKey(entity.getUniqueId())) {
        	        				proches.add((Player) entity);
    	        				}
    	        			}
        	        	}
        	        	if(proches.size() == 0) {
        	        		ermite.removePotionEffect(PotionEffectType.SPEED);
        	        		ermite.addPotionEffect(speed);
        	        	}
        	        	if(proches.size() < 4) {
        	        		ermite.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        	        		ermite.addPotionEffect(resistance);
        	        	} else {
        	        		ermite.removePotionEffect(PotionEffectType.WEAKNESS);
        	        		ermite.addPotionEffect(weakness);
        	        	}
                	}
                }
                
                for (UUID crain : craintifs) {
                	Player craintif = Bukkit.getPlayer(crain);
                	if(craintif != null) {
                		ArrayList<Player> proches = new ArrayList<>();
                		List<Entity> nearList = (List<Entity>)craintif.getNearbyEntities(20.0, 120.0, 20.0);
        	        	for (Entity entity : nearList) {
        	        		if (entity instanceof Player) {
        	        			if(this.main.getPlayers().containsKey(entity.getUniqueId())) {
        	        				if(this.main.getPlayers().get(entity.getUniqueId()).getRole().getCamp().equals("§4Loup-Garou§f")) {
        	        					proches.add((Player) entity);
        	        				}
    	        				}
    	        			}
        	        	}
        	        	if(proches.size() == 0) {
        	        		craintif.removePotionEffect(PotionEffectType.SPEED);
        	        		craintif.addPotionEffect(speed);
        	        	}
        	        	if(proches.size() < 3) {
        	        		craintif.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
        	        		craintif.addPotionEffect(force);
        	        	} else {
        	        		craintif.removePotionEffect(PotionEffectType.WEAKNESS);
        	        		craintif.addPotionEffect(weakness);
        	        	}
                	}
                }
            }
        }
        
        // pearls max par inv
        
        for (Map.Entry mapentry : this.main.getPlayers().entrySet()) {
        	Player p = Bukkit.getPlayer((UUID) mapentry.getKey());
        	if(p != null) {
        		int nb = 0;
   			 for (ItemStack it : p.getInventory().getContents()) {
   				 if(it != null) {
   					 if(it.getType().equals(Material.ENDER_PEARL)) nb += it.getAmount();
   				 }
   			}
   			 if(nb > this.main.config.getPearlsmax()) {
   				 p.sendMessage("§cVous ne pouvez avoir que " + this.main.config.getPearlsmax() + " pearl(s) maximum dans votre inventaire");
   				 p.getWorld().dropItemNaturally(p.getLocation(), new ItemStack(Material.ENDER_PEARL, (nb - this.main.config.getPearlsmax())));
   				 p.getInventory().removeItem(new ItemStack(Material.ENDER_PEARL, (nb - this.main.config.getPearlsmax())));

   			 }
        	}
        }
        /*
         * 
         *  int nb = 0;
			 for (ItemStack it : event.getInventory().getContents()) {
				if(it.getType().equals(Material.ENDER_PEARL)) nb += it.getAmount();
			}
			 System.out.println(nb);
			 if(nb == this.main.config.getPearlsmax()) {
				 event.setCancelled(true);
				 Player p = ((OfflinePlayer) event).getPlayer();
				 p.sendMessage("§cVous ne pouvez avoir que " + nb + " pearls maximum dans votre inventaire");
			 }
         */
        
        
        // passage jour / nuit selon daycycle
        	if(this.seconde == 0 && this.minute%this.main.config.getDaycycle() == 0) {
        		if(this.day) {
        			this.day = false;
        			Bukkit.broadcastMessage("");
        			Bukkit.broadcastMessage("§l☽ §1§l NUIT " + this.jour + " §f§l☽");
        			Bukkit.broadcastMessage("");
        			if(this.main.config.getPartieState() == "PLAYING") {
                		for (UUID uuid : this.main.config.getPlayersByRole("Petite Fille")) {
        					Player p = Bukkit.getPlayer(uuid);
        					if(p != null) {
        						String desc = "";
                	    		List<Entity> nearList = (List<Entity>)p.getNearbyEntities(50.0, 120.0, 50.0);
                	        	for (Entity entity : nearList) {
                	        		if (entity instanceof Player) {
                	        			if(this.main.getPlayers().containsKey(entity.getUniqueId())) {
                	        				desc += ((Player) entity).getDisplayName() + " ";
            	        				}
            	        			}
            	        		}
            	        	p.sendMessage("Liste des joueurs dans un rayon de 100 blocs autour de vous : ");
            	        	p.sendMessage(desc);
        					}
        				}
                		
                		for (UUID uuid : this.main.config.getPlayersByRole("Soeur")) {
                			this.main.getPlayers().get(uuid).getRole().setCanUse(false);
                		}
                	}
        		} else {
        			this.day = true;
        			Bukkit.broadcastMessage("");
        			Bukkit.broadcastMessage("§l☼ §e§l JOUR " + this.jour + " §f§l☼");
        			Bukkit.broadcastMessage("");
        			if(this.main.config.getPartieState() == "PLAYING") {
                		for (UUID uuid : this.main.config.getPlayersByRole("Petit Garçon")) {
        					Player p = Bukkit.getPlayer(uuid);
        					if(p != null) {
        						String desc = "";
                	    		List<Entity> nearList = (List<Entity>)p.getNearbyEntities(50.0, 120.0, 50.0);
                	        	for (Entity entity : nearList) {
                	        		if (entity instanceof Player) {
                	        			if(this.main.getPlayers().containsKey(entity.getUniqueId())) {
                	        				desc += ((Player) entity).getDisplayName() + " ";
            	        				}
            	        			}
            	        		}
            	        	p.sendMessage("Liste des joueurs dans un rayon de 100 blocs autour de vous : ");
            	        	p.sendMessage(desc);
        					}
        				}
                		
                		for (UUID uuid : this.main.config.getPlayersByRole("Soeur")) {
                			this.main.getPlayers().get(uuid).getRole().setCanUse(true);
                		}
                	}
        		}
        	}
        
        
        
        // Change scoreboard :
        
        /*
        if(this.seconde == 15) this.main.sm.alternateScoreboard2();
        if(this.seconde == 30) this.main.sm.alternateScoreboard();
        if(this.seconde == 45) this.main.sm.alternateScoreboard2();
        if(this.seconde == 1) this.main.sm.alternateScoreboard();
        */
        
        if(this.seconde == 0 && this.minute == this.main.config.getTimerRoles() && this.heure == 0) {
        	CommandAssign ca = new CommandAssign(main);
        	CommandStart cs = new CommandStart(main);
        	ca.onCommand(null, null, null, null);
        	cs.onCommand(null, null, null, null);
        	Bukkit.getScheduler().runTaskLater(main, () -> {
        		ArrayList<UUID> vivi = this.main.config.getPlayersByRole("Villageois Villageois");
        		for (UUID uuid : vivi) {
					String name = Bukkit.getOfflinePlayer(uuid).getName();
					Bukkit.broadcastMessage("§7Le joueur §f" + name + " §7est Villageois Villageois !");
				}
        		
        		ArrayList<UUID> anges = this.main.config.getPlayersByRole("Ange");
        		for (UUID uuid : anges) {
        			LgRole role = this.main.getPlayers().get(uuid).getRole();
        			if(role.getType() == 5) {
        				if(Math.random() < 0.5) {
        					String[] args = new String[1];
        					args[0] = "dechu";
        					CmdAnge cmd = new CmdAnge((Player) Bukkit.getOfflinePlayer(uuid), args, main);
        					cmd.run();
        				} else {
        					String[] args = new String[1];
        					args[0] = "gardien";
        					CmdAnge cmd = new CmdAnge((Player) Bukkit.getOfflinePlayer(uuid), args, main);
        					cmd.run();
        				}
        				
        			}
				}
        		ArrayList<UUID> es = this.main.config.getPlayersByRole("Enfant Sauvage");
        		for (UUID uuid : es) {
        			LgRole role = this.main.getPlayers().get(uuid).getRole();
        			if(role.getTarget() == null) {
        				ArrayList<UUID> alive = new ArrayList<>();
        				for (Map.Entry mapentry : this.main.getPlayers().entrySet()) {
        					if(!mapentry.getKey().equals(uuid)) alive.add((UUID) mapentry.getKey());
        				}
        				int rnd = new Random().nextInt(alive.size());
            			UUID cible = alive.get(rnd);
            			String[] args = new String[1];
    					args[0] = Bukkit.getOfflinePlayer(cible).getName();
    					CmdModele cmd = new CmdModele((Player) Bukkit.getOfflinePlayer(uuid), args, main);
    					cmd.run();
        			}
				}
        		
        		ArrayList<UUID> cupi = this.main.config.getPlayersByRole("Cupidon");
        		UUID cupidon = cupi.size() == 1 ? cupi.get(0) : null;
        		if(this.main.config.getPlayersCouple().size() == 0) {
        			// pas de couple => si cupi ou non
        			if(cupidon != null) {
        				ArrayList<UUID> alive = new ArrayList<>();
        				for (Map.Entry mapentry : this.main.getPlayers().entrySet()) {
        					if(!mapentry.getKey().equals(cupidon)) alive.add((UUID) mapentry.getKey());
        				}
        				int rnd = new Random().nextInt(alive.size());
            			UUID premier = alive.remove(rnd);
            			rnd = new Random().nextInt(alive.size());
            			UUID deuxieme = alive.remove(rnd);
            			String[] args = new String[2];
            			args[0] = Bukkit.getOfflinePlayer(premier).getName();
            			args[1] = Bukkit.getOfflinePlayer(deuxieme).getName();
    					
            			CmdLove cmd = new CmdLove((Player) Bukkit.getOfflinePlayer(cupidon), args, main);
    					cmd.run();
        			} else {
        				ArrayList<UUID> alive = new ArrayList<>();
        				for (Map.Entry mapentry : this.main.getPlayers().entrySet()) {
        					alive.add((UUID) mapentry.getKey());
        				}
        				int rnd = new Random().nextInt(alive.size());
            			UUID premier = alive.remove(rnd);
            			rnd = new Random().nextInt(alive.size());
            			UUID deuxieme = alive.remove(rnd);
            			Player target = Bukkit.getPlayer(premier);
            			Player target2 = Bukkit.getPlayer(deuxieme);
            			target.sendMessage("§dVous êtes amoureux de " + target2.getDisplayName() + " ! §1Si l'un d'entre vous meurt, l'aure ne pourra supporter cette souffrance et se suicidera immédiatement...");
						target.sendMessage("§1Vous possédez une boussole pointant constamment vers votre amoureux");
						target.getInventory().addItem(new ItemStack(Material.COMPASS, 1));
						main.getPlayers().get(target.getUniqueId()).setCouple(target2.getUniqueId());
						
						target2.sendMessage("§dVous êtes amoureux de " + target.getDisplayName() + " ! §1Si l'un d'entre vous meurt, l'aure ne pourra supporter cette souffrance et se suicidera immédiatement...");
						target2.sendMessage("§1Vous possédez une boussole pointant constamment vers votre amoureux");
						target2.getInventory().addItem(new ItemStack(Material.COMPASS, 1));
						main.getPlayers().get(target2.getUniqueId()).setCouple(target.getUniqueId());
            			
        			}
        		}
        		
			}, 60L); //6000
        }
        
        
      
        
        
        if(this.main.config.getPartieState() == "PLAYING") {
        // toutes les 2sec : boussole couple
        if(seconde%2 == 0) {
			ArrayList<UUID> couple =  this.main.config.getPlayersCouple();
			// si couple en vie + deux membres connectés : update boussoles (edit si trouple)
			if(couple.size() == 2) {
				Player c1 = Bukkit.getPlayer(couple.get(0));
				Player c2 = Bukkit.getPlayer(couple.get(1));
				if(c1 != null && c2 != null) {
					c1.setCompassTarget(c2.getLocation());
					c2.setCompassTarget(c1.getLocation());
				}
		    }
        }
        
        // tout le temps : resi soeurs
        ArrayList<UUID> soeurs = this.main.config.getPlayersByRole("Soeur");
        for (UUID s : soeurs) {
        	if(this.main.config.isResisoeur() && !this.main.getPlayers().get(s).isSalvate()) {
	        	Player soeur = Bukkit.getPlayer(s);
	        	if(soeur != null) {
	        		ArrayList<Player> playersNear = new ArrayList<>();
    	    		List<Entity> nearList = (List<Entity>)soeur.getNearbyEntities(15.0, 120.0, 15.0);
    	        	for (Entity entity : nearList) {
    	        		if (entity instanceof Player) {
    	        			if(this.main.getPlayers().containsKey(entity.getUniqueId())) {
    	        				LgPlayer proche = this.main.getPlayers().get(entity.getUniqueId());
    	        				if(proche.getRole().getNom().equals("Soeur")) {
    	        					soeur.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
    	        					soeur.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 0, false, false));
    	        				}
	        				}
	        			}
	        		}
	        	}
        	}
        }
    }
        /*
        Player moi = Bukkit.getPlayer("Walda666");
        String title = moi.getOpenInventory().getTitle();
        if(title.equals("§8Edit Diamond Limit") || title.equals("§8Edit Border initiale") || title.equals("§8Edit Temps Rôles") || title.equals("§8Edit Temps Meetup") || title.equals("§8Edit Pearls Max/Inventaire") || title.equals("§8Edit OursMalLéché") || title.equals("§8Edit Exposed") || title.equals("§8Edit Rumeurs") || title.equals("§8Edit Bénédiction") || title.equals("§8Edit Maire") || title.equals("§8Edit NotAllLoups") || title.equals("§8Config Uhc") || title.equals("§8Scenarios") || title.equals("§8Events aleatoires") || title.equals("§8Config Lg") || title.equals("§8Config") || title.equals("§8Composition")) {
        	moi.updateInventory();
        	//System.out.println("rl");
        }
        //WorldBorder border = Bukkit.getWorld("world").getWorldBorder();
        //border.setSize(this.border = border.getSize()-0.6);
        */
        ++this.seconde;
            
        
        // exposed
        if (this.heure == 0 && this.seconde == 1 && minute == 4) { //70
        	if(this.main.getEventsActives().contains("Exposed")) {
        		ArrayList<LgPlayer> players = new ArrayList<>();
        		for (Map.Entry mapentry : this.main.getPlayers().entrySet()) {
                    LgPlayer ap = (LgPlayer) mapentry.getValue();
                    players.add(ap);
        		}
        		Collections.shuffle(players);
        		Collections.shuffle(players);
        		Collections.shuffle(players);
        		Collections.shuffle(players);
        		Collections.shuffle(players);
        		LgPlayer cible = players.remove(0);
        		ArrayList<String> result = new ArrayList<>();
        		result.add(cible.getRole().getNom());
        		String campcible = cible.getRole().getCamp();
        		
        		if(campcible.equals("§fVillageois§f")) {
	        		boolean good = false;
	                int compteur = 0;
	                for (LgPlayer lp : players) {
	        			if(lp.getRole().getCamp().equals("§fVillageois§f") && !good) {
	        				result.add(lp.getRole().getNom());
	        				good = true;
	        			} else if(compteur < 2 && !campcible.equals("§fVillageois§f")) {
	        				result.add(lp.getRole().getNom());
	        				compteur++;
	        			}
	        		}
	        	} else {
	        		boolean good = false;
	                int compteur = 0;
	                for (LgPlayer lp : players) {
	        			if(!lp.getRole().getCamp().equals("§fVillageois§f") && !good) {
	        				result.add(lp.getRole().getNom());
	        				good = true;
	        			} else if(compteur < 2 && campcible.equals("§fVillageois§f")) {
	        				result.add(lp.getRole().getNom());
	        				compteur++;
	        			}
	        		}
	        	}
        		String desc = "§3[Exposed] » Le rôle de " + cible.getPlayer().getDisplayName() + " se trouve entre les suivants : ";
        		for (String st : result) {
					desc += st + ", ";
				}
        		Bukkit.broadcastMessage(desc.substring(0, desc.length()-2));
        	}
        }
    
    if (this.heure == 0 && this.seconde == 1 && minute == 105) {
    	if(this.main.getEventsActives().contains("Exposed")) {
    		if(Math.random() < 0.8) {
    			ArrayList<LgPlayer> players = new ArrayList<>();
        		for (Map.Entry mapentry : this.main.getPlayers().entrySet()) {
                    LgPlayer ap = (LgPlayer) mapentry.getValue();
                    players.add(ap);
        		}
        		Collections.shuffle(players);
        		Collections.shuffle(players);
        		Collections.shuffle(players);
        		Collections.shuffle(players);
        		Collections.shuffle(players);
        		LgPlayer cible = players.remove(0);
        		ArrayList<String> result = new ArrayList<>();
        		result.add(cible.getRole().getNom());
        		String campcible = cible.getRole().getCamp();
        		
        		if(campcible.equals("§fVillageois§f")) {
	        		boolean good = false;
	                int compteur = 0;
	                for (LgPlayer lp : players) {
	        			if(lp.getRole().getCamp().equals("§fVillageois§f") && !good) {
	        				result.add(lp.getRole().getNom());
	        				good = true;
	        			} else if(compteur < 2 && !campcible.equals("§fVillageois§f")) {
	        				result.add(lp.getRole().getNom());
	        				compteur++;
	        			}
	        		}
	        	} else {
	        		boolean good = false;
	                int compteur = 0;
	                for (LgPlayer lp : players) {
	        			if(!lp.getRole().getCamp().equals("§fVillageois§f") && !good) {
	        				result.add(lp.getRole().getNom());
	        				good = true;
	        			} else if(compteur < 2 && campcible.equals("§fVillageois§f")) {
	        				result.add(lp.getRole().getNom());
	        				compteur++;
	        			}
	        		}
	        	}
        		String desc = "§3[Exposed] » Le rôle de " + cible.getPlayer().getDisplayName() + " se trouve entre les suivants : §5";
        		for (String st : result) {
					desc += st + ", ";
				}
        		Bukkit.broadcastMessage(desc.substring(0, desc.length()-2));
    		}
    		
    	}
	}
    
    // rumeurs
    if (this.heure == 0 && this.seconde == 1 && minute == 96) { //96
    	if(this.main.getEventsActives().contains("Rumeurs")) {
    		Bukkit.broadcastMessage("§3[Rumeurs] » Vous disposez d'une minute pour écrire une rumeur qui sera ensuite dévoilé anonymement dans le chat !");
    		this.main.config.setRumeurs(true);
    		Bukkit.getScheduler().runTaskLater(main, () -> {
    			this.main.config.setRumeurs(false);
    			Bukkit.broadcastMessage("§3[Rumeurs] » C'est fini ! Voyons ce que vous avez à nous raconter !");
    			for (Map.Entry mapentry : this.main.config.getRumeursListe().entrySet()) {
                    String msg = (String) mapentry.getValue();
                    Bukkit.broadcastMessage("§7[Rumeurs] » §f" + msg);
    			}
    		}, 1200L);
    	}
    }
    
    
    }
    
    public int getJour() {
    	return this.jour;
    }
    
    public int getHeure() {
    	return this.heure;
    }
    public int getMinute() {
    	return this.minute;
    }
    public int getSeconde() {
    	return this.seconde;
    }

	public double getBorder() {
		return border;
	}

	public boolean isDay() {
		return day;
	}

	public void setDay(boolean day) {
		this.day = day;
	}

	public void setSeconde(int seconde) {
		this.seconde = seconde;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public void setHeure(int heure) {
		this.heure = heure;
	}

	public void setJour(int jour) {
		this.jour = jour;
	}    
	
	
    
    
}
