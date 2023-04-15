package roles;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

public class Chasseur extends LgRole {
    public Chasseur() {
    	this.nom = "Chasseur";
        this.camp = "§fVillageois§f";
        this.desc = "respawn";
        ItemStack arc = new ItemStack(Material.BOW, 1);
    	ItemMeta meta = arc.getItemMeta();
    	meta.addEnchant(Enchantment.ARROW_DAMAGE, 4, false);
    	arc.setItemMeta(meta);
    	items.add(arc);
    	items.add(new ItemStack(Material.ARROW, 128));
    }
}