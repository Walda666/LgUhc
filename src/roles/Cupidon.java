package roles;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Cupidon extends LgRole {
    public Cupidon() {
    	this.nom = "Cupidon";
        this.camp = "§fVillageois§f";
        this.desc = "respawn";
        ItemStack arc = new ItemStack(Material.BOW, 1);
    	ItemMeta meta = arc.getItemMeta();
    	meta.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, false);
    	arc.setItemMeta(meta);
    	this.items.add(arc);
    	this.items.add(new ItemStack(Material.ARROW, 64));
    	this.nbUses = 1;
    }
}