package commands;

import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.inventory.Inventory;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.util.UUIDTypeAdapter;

import java.util.Iterator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.block.BlockFace;
import org.bukkit.Material;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.GameMode;
import org.bukkit.Location;

import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import java.util.Map;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.command.CommandExecutor;

import lg.LgPlayer;
import lg.TitleManager;
import lg.main;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
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

public class CommandTest implements CommandExecutor
{
    private main main;
    
    public CommandTest(main main) {
        this.main = main;
    }
    
    public ItemStack createItem(Material material, String nom, int nb) {
        ItemStack it = new ItemStack(material, 1);
        ItemMeta metaIt = it.getItemMeta();
        metaIt.setDisplayName(nom);
        it.setAmount(nb);
        it.setItemMeta(metaIt);
        return it;
    }
    
    public boolean onCommand(final CommandSender sender, Command cmd, String msg, String[] arg) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player)sender;
        
        System.out.println("test");

        
     
        
       // Bukkit.broadcastMessage("§6>>§f§m--------------------------------------§6<<\n§fVous");
        
        /*
       TitleManager tm = new TitleManager();
      
       String nbRoles = String.valueOf(this.main.getCompo().size());
       Inventory inv = Bukkit.createInventory((InventoryHolder)null, 54, "§8Composition");
       
       inv.setItem(0, this.createItem(Material.DIRT, "Ancien", this.main.config.nbInCompo(new Ancien())));
       inv.setItem(1, this.createItem(Material.DIRT, "Ange", this.main.config.nbInCompo(new Ange())));
       inv.setItem(2, this.createItem(Material.DIRT, "Assassin", this.main.config.nbInCompo(new Assassin())));
       inv.setItem(3, this.createItem(Material.DIRT, "Berger", this.main.config.nbInCompo(new Berger())));
       inv.setItem(4, this.createItem(Material.DIRT, "Chasseur", this.main.config.nbInCompo(new Chasseur())));
       inv.setItem(5, this.createItem(Material.DIRT, "(X) Chien-Loup", this.main.config.nbInCompo(new ChienLoup())));
       inv.setItem(6, this.createItem(Material.DIRT, "Citoyen", this.main.config.nbInCompo(new Citoyen())));
       inv.setItem(7, this.createItem(Material.DIRT, "Confesseur", this.main.config.nbInCompo(new Confesseur())));
       inv.setItem(8, this.createItem(Material.DIRT, "Corbeau", this.main.config.nbInCompo(new Corbeau())));
       inv.setItem(9, this.createItem(Material.DIRT, "Cupidon", this.main.config.nbInCompo(new Cupidon())));
       inv.setItem(10, this.createItem(Material.DIRT, "Enfant Sauvage", this.main.config.nbInCompo(new EnfantSauvage())));
       inv.setItem(11, this.createItem(Material.DIRT, "Infect Père des Loups", this.main.config.nbInCompo(new InfectPereDesLoups())));
       inv.setItem(12, this.createItem(Material.DIRT, "Loup Fantôme", this.main.config.nbInCompo(new LoupFantome())));
       inv.setItem(13, this.createItem(Material.DIRT, "Loup-Garou", this.main.config.nbInCompo(new LoupGarou())));
       inv.setItem(14, this.createItem(Material.DIRT, "Loup-Garou Blanc", this.main.config.nbInCompo(new LoupGarouBlanc())));
       inv.setItem(15, this.createItem(Material.DIRT, "Loup-Garou Kaboom", this.main.config.nbInCompo(new LoupGarouKaboom())));
       inv.setItem(16, this.createItem(Material.DIRT, "Loup-Garou Mystique", this.main.config.nbInCompo(new LoupGarouMystique())));
       inv.setItem(17, this.createItem(Material.DIRT, "Mercenaire", this.main.config.nbInCompo(new Mercenaire())));
       inv.setItem(18, this.createItem(Material.DIRT, "Mineur", this.main.config.nbInCompo(new Mineur())));
       inv.setItem(19, this.createItem(Material.DIRT, "Montreur d'Ours", this.main.config.nbInCompo(new MontreurDours())));
       inv.setItem(20, this.createItem(Material.DIRT, "(X) Nezuko", this.main.config.nbInCompo(new Nezuko())));
       inv.setItem(21, this.createItem(Material.DIRT, "Petite Fille", this.main.config.nbInCompo(new PetiteFille())));
       inv.setItem(22, this.createItem(Material.DIRT, "Petit Garçon", this.main.config.nbInCompo(new PetitGarcon())));
       inv.setItem(23, this.createItem(Material.DIRT, "Renard", this.main.config.nbInCompo(new Renard())));
       inv.setItem(24, this.createItem(Material.DIRT, "Salvateur", this.main.config.nbInCompo(new Salvateur())));
       inv.setItem(25, this.createItem(Material.DIRT, "Simple Villageois", this.main.config.nbInCompo(new SimpleVillageois())));
       inv.setItem(26, this.createItem(Material.DIRT, "Soeur", this.main.config.nbInCompo(new Soeur())));
       inv.setItem(27, this.createItem(Material.DIRT, "Sorcière", this.main.config.nbInCompo(new Sorciere())));
       inv.setItem(28, this.createItem(Material.DIRT, "Vilain Petit Loup", this.main.config.nbInCompo(new VilainPetitLoup())));
       inv.setItem(29, this.createItem(Material.DIRT, "Villageois Villageois", this.main.config.nbInCompo(new VillageoisVillageois())));
       inv.setItem(30, this.createItem(Material.DIRT, "Voleur", this.main.config.nbInCompo(new Voleur())));
       inv.setItem(31, this.createItem(Material.DIRT, "Voyante", this.main.config.nbInCompo(new Voyante())));
       inv.setItem(32, this.createItem(Material.DIRT, "Voyante Bavarde", this.main.config.nbInCompo(new VoyanteBavarde())));
       
       
       
       inv.setItem(52, this.createItem(Material.BARRIER, "Clear compo", 1));
       inv.setItem(53, this.createItem(Material.GLASS, nbRoles, Integer.parseInt(nbRoles)));
       //player.openInventory(inv);
       
       
      //Bukkit.getWorld("world").getWorldBorder().setSize(110);
      //Bukkit.getWorld("world").getWorldBorder().setWarningDistance(0);
       
       
       ItemStack rouge = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
       
       player.getInventory().addItem(rouge);
       */
        /*
        this.main.getTask().setJour(0);
        this.main.getTask().setHeure(0);
        this.main.getTask().setMinute(0);
        this.main.getTask().setSeconde(0);
        */
       
        //DisguiseAPI.constructDisguise(player);
        
        //DisguiseAPI.disguiseEntity(player, new PlayerDisguise("TheGuill84"));
        MobDisguise mobDisguise = new MobDisguise(DisguiseType.VILLAGER, true, true);
        mobDisguise.setKeepDisguiseOnPlayerDeath(false);
        mobDisguise.setViewSelfDisguise(true);
        DisguiseAPI.disguiseToAll(player, mobDisguise);
        
        
        return true;
        
       
    
    
  
    }
}
