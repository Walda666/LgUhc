package roles;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Mineur extends LgRole {
	public Mineur() {
		this.nom = "Mineur";
        this.camp = "§fVillageois§f";
        this.desc = "respawn";
        ItemStack pioche = new ItemStack(Material.DIAMOND_PICKAXE, 1);
    	ItemMeta meta = pioche.getItemMeta();
    	meta.addEnchant(Enchantment.DIG_SPEED, 4, false);
    	meta.spigot().setUnbreakable(true);
    	pioche.setItemMeta(meta);
    	items.add(pioche);
    	effetsPerma.add( new PotionEffect(PotionEffectType.FAST_DIGGING, 999999, 0, false, false));
    }
}