package roles;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import lg.main;

import java.util.ArrayList;
import java.util.UUID;

public class LgRole
{
    protected String nom;
    protected String desc;
    protected String camp;
    protected ArrayList<PotionEffect> effetsJour = new ArrayList<>();
    protected ArrayList<PotionEffect> effetsNuit = new ArrayList<>();
    protected ArrayList<PotionEffect> effetsPerma = new ArrayList<>();
    protected ArrayList<ItemStack> items = new ArrayList<>();
    protected ArrayList<String> mates = new ArrayList<>();
    protected int nbUses = 0;
    protected int type = 0;
    protected String facade = null;
    protected UUID target = null;
    protected boolean canUse = false;
    
    public String getNom() {
        return this.nom;
    }
    
    public void setNom(final String nom) {
        this.nom = nom;
    }
    
    public String getDesc() {
        return this.desc;
    }
    
    public void setDesc(final String desc) {
        this.desc = desc;
    }

    public ArrayList<PotionEffect> getEffetsJour() {
        return this.effetsJour;
    }
    
    public void setEffetsJour(final ArrayList<PotionEffect> effets) {
        this.effetsJour = effets;
    }
    
    public ArrayList<PotionEffect> getEffetsNuit() {
        return this.effetsNuit;
    }
    
    public void setEffetsNuit(final ArrayList<PotionEffect> effets) {
        this.effetsNuit = effets;
    }
    
    public String getCamp() {
        return this.camp;
    }
    
    public void setCamp(final String camp) {
        this.camp = camp;
    }
    
    public ArrayList<ItemStack> getItems() {
        return this.items;
    }
    
    public void setItems(final ArrayList<ItemStack> items) {
        this.items = items;
    }
    
    public ArrayList<String> getMates() {
        return this.mates;
    }
    
    public void setMates(final ArrayList<String> mates) {
        this.mates = mates;
    }
    
    public int getNbUses() {
        return this.nbUses;
    }
    
    public void setNbUses(final int nbUses) {
        this.nbUses = nbUses;
    }
    
    public int getType() {
    	return this.type;
    }
    
    public void setType(int type) {
    	this.type = type;
    }

	public ArrayList<PotionEffect> getEffetsPerma() {
		return effetsPerma;
	}

	public void setEffetsPerma(ArrayList<PotionEffect> effetsPerma) {
		this.effetsPerma = effetsPerma;
	}

	public String getFacade() {
		return facade;
	}

	public void setFacade(String facade) {
		this.facade = facade;
	}

	public UUID getTarget() {
		return target;
	}

	public void setTarget(UUID target) {
		this.target = target;
	}
	
	public void use() {
		this.nbUses--;
	}

	public boolean isCanUse() {
		return canUse;
	}

	public void setCanUse(boolean canUse) {
		this.canUse = canUse;
	}
    
	
    
}
