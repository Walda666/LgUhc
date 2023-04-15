package roles;import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class LoupGarouCraintif extends LgRole {
	public LoupGarouCraintif() {
    	this.nom = "Loup-Garou Craintif";
		this.camp = "§4Loup-Garou§f";
		this.desc = "arab";
		this.effetsPerma.add(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 0, false, false));
	}
}