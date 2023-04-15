package roles;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PetitGarcon extends LgRole {
    public PetitGarcon() {
    	this.nom = "Petit Garçon";
        this.camp = "§fVillageois§f";
        this.desc = "respawn";
        this.items.add(new ItemStack(Material.TNT, 5));
     }
}