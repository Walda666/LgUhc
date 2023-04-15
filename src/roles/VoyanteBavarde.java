package roles;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class VoyanteBavarde extends LgRole {
    public VoyanteBavarde() {
        this.nom = "Voyante Bavarde";
        this.camp = "§fVillageois§f";
        this.desc = "respawn";
        this.effetsPerma = new ArrayList<PotionEffect>();
        this.items = new ArrayList<ItemStack>();
    }
	
    public ArrayList<ItemStack> getItems() {
    	ItemStack item1 = new ItemStack(Material.OBSIDIAN, 4);
    	ItemStack item2 = new ItemStack(Material.BOOKSHELF, 4);
    	items.add(item1);
    	items.add(item2);
    	return items;
    }
}