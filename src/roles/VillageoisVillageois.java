package roles;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class VillageoisVillageois extends LgRole {
    public VillageoisVillageois() {
    	this.nom = "Villageois Villageois";
        this.camp = "§fVillageois§f";
        this.desc = "respawn";
        this.effetsPerma = new ArrayList<PotionEffect>();
        this.items = new ArrayList<ItemStack>();
    }
}