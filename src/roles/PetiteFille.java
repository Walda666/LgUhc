package roles;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PetiteFille extends LgRole {
    public PetiteFille() {
    	this.nom = "Petite Fille";
        this.camp = "§fVillageois§f";
        this.desc = "mineure";
        this.items.add(new ItemStack(Material.TNT, 5));
    }
    
}