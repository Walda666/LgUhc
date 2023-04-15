package roles;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class Assassin extends LgRole {
    public Assassin() {
    	this.nom = "Assassin";
		this.camp = "§fSolo§f";
		this.desc = "arab";
		ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);
		EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();
		meta.addEnchant(Enchantment.DAMAGE_ALL, 3, false);
		
		ItemStack bookp = new ItemStack(Material.ENCHANTED_BOOK, 1);
		EnchantmentStorageMeta metap = (EnchantmentStorageMeta) bookp.getItemMeta();
		metap.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, false);
		
		ItemStack booko = new ItemStack(Material.ENCHANTED_BOOK, 1);
		EnchantmentStorageMeta metao = (EnchantmentStorageMeta) booko.getItemMeta();
		metao.addEnchant(Enchantment.ARROW_DAMAGE, 3, false);
		this.items.add(booko);
		this.items.add(bookp);
		this.items.add(book);	
		
		this.effetsJour.add(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999, 0, false, false));
	}
}