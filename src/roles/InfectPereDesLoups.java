package roles;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class InfectPereDesLoups extends LgRole {
    public InfectPereDesLoups() {
    	this.nom = "Infect Père des Loups";
		this.camp = "§4Loup-Garou§f";
		this.desc = "arab";
		
		this.effetsNuit.add(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999, 0, false, false));
		this.effetsPerma.add(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 0, false, false));
		this.nbUses = 1;
	}
}