package lg;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import roles.LgRole;

import java.util.ArrayList;
import java.util.UUID;

public class LgPlayer
{
    private UUID uuid;
    private LgRole role;
    private boolean kit = false;
    private int kill = 0;
    private boolean salvate = false;
    private boolean modeleEs = false;
    private boolean rez = false;
    private ArrayList<ItemStack> stuff = new ArrayList<ItemStack>();
    private ItemStack casque = null;
    private ItemStack plastron = null;
    private ItemStack jambes= null;
    private ItemStack bottes = null;
    private UUID couple = null;
    private boolean cibleAnge = false;
    private boolean voleur = false;
    
    
    public LgPlayer(Player p) {
        this.uuid = p.getUniqueId();
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
    
    public Player getPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }
    
    public LgRole getRole() {
        return this.role;
    }
    
    public void setRole(LgRole role) {
        this.role = role;
    }
    
    public boolean hasRole() {
        return this.role != null;
    }

	public boolean getKit() {
		return kit;
	}

	public void setKit(boolean kit) {
		this.kit = kit;
	}

	public int getKill() {
		return kill;
	}

	public void setKill(int kill) {
		this.kill = kill;
	}
	
	public void addKill() {
		this.kill++;
	}

	public boolean isSalvate() {
		return salvate;
	}

	public void setSalvate(boolean salvate) {
		this.salvate = salvate;
	}

	public boolean isModeleEs() {
		return modeleEs;
	}

	public void setModeleEs(boolean modeleEs) {
		this.modeleEs = modeleEs;
	}

	public boolean isRez() {
		return rez;
	}

	public void setRez(boolean rez) {
		this.rez = rez;
	}

	public ArrayList<ItemStack> getStuff() {
		return stuff;
	}

	public void setStuff(ArrayList<ItemStack> stuff) {
		this.stuff = stuff;
	}

	public ItemStack getCasque() {
		return casque;
	}

	public void setCasque(ItemStack casque) {
		this.casque = casque;
	}

	public ItemStack getPlastron() {
		return plastron;
	}

	public void setPlastron(ItemStack plastron) {
		this.plastron = plastron;
	}

	public ItemStack getJambes() {
		return jambes;
	}

	public void setJambes(ItemStack jambes) {
		this.jambes = jambes;
	}

	public ItemStack getBottes() {
		return bottes;
	}

	public void setBottes(ItemStack bottes) {
		this.bottes = bottes;
	}

	public UUID getCouple() {
		return couple;
	}

	public void setCouple(UUID couple) {
		this.couple = couple;
	}

	public boolean isCibleAnge() {
		return cibleAnge;
	}

	public void setCibleAnge(boolean cibleAnge) {
		this.cibleAnge = cibleAnge;
	}

	public boolean isVoleur() {
		return voleur;
	}

	public void setVoleur(boolean voleur) {
		this.voleur = voleur;
	}
	
	
	
    
    
}
