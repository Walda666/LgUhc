package roles;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Renard extends LgRole {
    public Renard() {
    	this.nom = "Renard";
        this.camp = "§fVillageois§f";
        this.desc = "respawn";
        PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 999999, 0, false, false);
    	effetsJour.add(speed);
    	this.nbUses = 3;
    }
}