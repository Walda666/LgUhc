package roles;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class Voleur extends LgRole {
    public Voleur() {
    	this.nom = "Voleur";
        this.camp = "§fSolo§f";
        this.desc = "arab";
        this.effetsPerma.add(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999, 0, false, false));
        this.nbUses = 1;
    }
}