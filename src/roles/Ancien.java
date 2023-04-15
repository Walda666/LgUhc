package roles;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import roles.LgRole;

public class Ancien extends LgRole {
	
	public Ancien() {
        this.nom = "Ancien";
        this.camp = "§fVillageois§f";
        this.desc = "respawn";
        this.effetsPerma = new ArrayList<PotionEffect>();
		effetsPerma.add(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 99999, 0, false, false));
		this.nbUses = 1;
    }	
}