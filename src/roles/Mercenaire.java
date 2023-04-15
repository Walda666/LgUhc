package roles;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Mercenaire extends LgRole {
    public Mercenaire() {
    	this.nom = "Mercenaire";
        this.camp = "§fSolo§f";
        this.desc = "arab";
        this.effetsPerma.add(new PotionEffect(PotionEffectType.SPEED, 999999, 0, false, false));
    }
}