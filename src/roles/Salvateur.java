package roles;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class Salvateur extends LgRole {
    public Salvateur() {
    	this.nom = "Salvateur";
        this.camp = "§fVillageois§f";
        this.desc = "respawn";
        this.effetsPerma = new ArrayList<PotionEffect>();
        this.items = new ArrayList<ItemStack>();
        Potion poth = new Potion(PotionType.INSTANT_HEAL, 1);
		poth.setSplash(true);
		items.add(poth.toItemStack(2));
    }
}