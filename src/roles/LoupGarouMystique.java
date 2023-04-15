package roles;

import java.util.ArrayList;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import lg.LgPlayer;

public class LoupGarouMystique extends LgRole {
	private ArrayList<LgPlayer> rolesEu;
    public LoupGarouMystique() {
    	this.nom = "Loup-Garou Mystique";
		this.camp = "§4Loup-Garou§f";
		this.desc = "arab";
		
		this.effetsNuit.add(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999, 0, false, false));
		this.effetsPerma.add(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 0, false, false));
		this.rolesEu = new ArrayList<LgPlayer>();
	}
    
	public ArrayList<LgPlayer> getRolesEu() {
		return rolesEu;
	}
	public void addRolesEu(LgPlayer role) {
		this.rolesEu.add(role);
	}
	/*
	public boolean containsRolesEu(String role) {
		boolean bool = false;
		for (LgPlayer p : this.rolesEu) {
			
		}
	}*/
    
}