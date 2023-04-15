package roles;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class Sorciere extends LgRole {
    public Sorciere() {
    	this.nom = "Sorcière";
        this.camp = "§fVillageois§f";
        this.desc = "respawn";
        this.effetsPerma = new ArrayList<PotionEffect>();
        this.items = new ArrayList<ItemStack>();
        Potion poth = new Potion(PotionType.INSTANT_HEAL, 1);
		poth.setSplash(true);
		items.add(poth.toItemStack(3));
		Potion potd = new Potion(PotionType.INSTANT_DAMAGE, 1);
		potd.setSplash(true);
		items.add(potd.toItemStack(3));
		Potion potr = new Potion(PotionType.REGEN, 1);
		potr.setSplash(true);
		items.add(potr.toItemStack(1));
		this.nbUses = 1;
    }
}