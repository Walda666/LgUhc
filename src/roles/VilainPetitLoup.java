package roles;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class VilainPetitLoup extends LgRole {
    public VilainPetitLoup() {
    	this.nom = "Vilain Petit Loup";
		this.camp = "§4Loup-Garou§f";
		this.desc = "arab";
		
		this.effetsNuit.add(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999, 0, false, false));
		this.effetsPerma.add(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 0, false, false));
		this.effetsJour.add(new PotionEffect(PotionEffectType.SPEED, 999999, 0, false, false));
	}
}