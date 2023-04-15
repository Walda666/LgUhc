package lg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.inventory.ItemStack;

import roles.LgRole;

public class LgConfig {
	private boolean vote, autorez, resisoeur, chatMute, angenormal, compocache, gapplekill, coupleAleatoire, solitaire, rumeurs;
	private int timerRoles, timerMeetup, daycycle, jourVote, jourMo, rayonKaboom, pearlsmax,dlimit, borderdepart, speedup;
	private String partieState;
	private HashMap<UUID, UUID> votes;
	private main main;
	private ArrayList<ItemStack> itemsStart;
	private HashMap<UUID, String> rumeursListe;

	public LgConfig(main main) {
		this.vote = false;
		this.autorez = true;
		this.resisoeur = true;
		this.chatMute = false;
		this.timerRoles = 2;
		this.daycycle = 5;
		this.partieState = "WAITING";
		this.votes = new HashMap<>();
		this.main = main;
		this.angenormal = false;
		this.compocache = false;
		this.jourMo = 2;
		this.jourVote = 3;
		this.rayonKaboom = 7;
		this.timerMeetup = 80;
		this.pearlsmax = 1;
		this.dlimit = 17;
		this.borderdepart = 1000;
		this.gapplekill = true;
		this.itemsStart = new ArrayList<>();
		this.speedup = 50;
		this.coupleAleatoire = false;
		this.solitaire = false;
		this.rumeursListe = new HashMap<UUID, String>();
	}
	
	
	
	public int getSpeedup() {
		return speedup;
	}



	public void setSpeedup(int speedup) {
		this.speedup = speedup;
	}



	public boolean isGapplekill() {
		return gapplekill;
	}

	public void setGapplekill(boolean gapplekill) {
		this.gapplekill = gapplekill;
	}

	public ArrayList<ItemStack> getItemsStart() {
		return itemsStart;
	}

	public void setItemsStart(ArrayList<ItemStack> itemsStart) {
		this.itemsStart = itemsStart;
	}

	public int getBorderdepart() {
		return borderdepart;
	}

	public void setBorderdepart(int borderdepart) {
		this.borderdepart = borderdepart;
	}

	public int getDlimit() {
		return dlimit;
	}
	
	public void setDlimit(int dlimit) {
		this.dlimit = dlimit;
	}
	
	public boolean isVote() {
		return vote;
	}

	public void setVote(boolean vote) {
		this.vote = vote;
	}

	public boolean isAutorez() {
		return autorez;
	}

	public void setAutorez(boolean autorez) {
		this.autorez = autorez;
	}

	public boolean isResisoeur() {
		return resisoeur;
	}

	public void setResisoeur(boolean resisoeur) {
		this.resisoeur = resisoeur;
	}
	public boolean isChatMute() {
		return chatMute;
	}
	public void setChatMute(boolean chatMute) {
		this.chatMute = chatMute;
	}
	public int getTimerRoles() {
		return timerRoles;
	}
	public void setTimerRoles(int timerRoles) {
		this.timerRoles = timerRoles;
	}
	public String getPartieState() {
		return partieState;
	}
	public void setPartieState(String partieState) {
		this.partieState = partieState;
	}
	public HashMap<UUID, UUID> getVotes() {
		return votes;
	}
	public void addVote(UUID joueur, UUID target) {
		this.votes.put(joueur, target);
	}
	
	public boolean containsVote(UUID joueur) {
		return this.votes.containsKey(joueur);
	}
	
	public boolean isCompocache() {
		return compocache;
	}
	public void setCompocache(boolean compocache) {
		this.compocache = compocache;
	}
	
	public boolean isAngenormal() {
		return angenormal;
	}
	
	public void setAngenormal(boolean angenormal) {
		this.angenormal = angenormal;
	}
		
	public int getJourVote() {
		return jourVote;
	}
	
	public void setJourVote(int jourVote) {
		this.jourVote = jourVote;
	}
	
	public int getJourMo() {
		return jourMo;
	}
	
	public void setJourMo(int jourMo) {
		this.jourMo = jourMo;
	}
	
	public int getRayonKaboom() {
		return rayonKaboom;
	}
	
	public void setRayonKaboom(int rayonKaboom) {
		this.rayonKaboom = rayonKaboom;
	}
	
	public int getTimerMeetup() {
		return timerMeetup;
	}
	
	public void setTimerMeetup(int timerMeetup) {
		this.timerMeetup = timerMeetup;
	}
	
	public int getPearlsmax() {
		return pearlsmax;
	}
	
	public void setPearlsmax(int pearlsmax) {
		this.pearlsmax = pearlsmax;
	}
	
	public ArrayList<UUID> getPlayersByRole(String role) {
		ArrayList<UUID> gars = new ArrayList<>();
		for (Map.Entry mapentry : this.main.getPlayers().entrySet()) {
             if(((LgPlayer) mapentry.getValue()).getRole().getNom().equals(role)) {
            	 gars.add(((LgPlayer) mapentry.getValue()).getUuid());
             }
		}
		return gars;
	}
	
	public ArrayList<UUID> getPlayersCouple() {
		ArrayList<UUID> gars = new ArrayList<>();
		for (Map.Entry mapentry : this.main.getPlayers().entrySet()) {
             if(((LgPlayer) mapentry.getValue()).getCouple() != null) {
            	 gars.add(((LgPlayer) mapentry.getValue()).getUuid());
             }
		}
		return gars;
	}
	
	public ArrayList<LgPlayer> getPlayersCamp(String camp) {
		ArrayList<LgPlayer> gars = new ArrayList<>();
		for (Map.Entry mapentry : this.main.getPlayers().entrySet()) {
			if(((LgPlayer) mapentry.getValue()).getRole().getCamp().equals(camp)) {
            	 gars.add(((LgPlayer) mapentry.getValue()));
             }
		}
		return gars;
	}
	public int getDaycycle() {
		return daycycle;
	}
	public void setDaycycle(int daycycle) {
		this.daycycle = daycycle;
	}
	
	public int nbInCompo(LgRole role) {
    	int compteur = 0;
    	for(LgRole rol : this.main.compo) {
    		if(rol.getNom().equals(role.getNom())) compteur++;
    	}
    	return compteur;
    }
	
	public void removeCompo(LgRole rolerm) {
    	int compteur = 0;
    	boolean rm = true;
    	while(rm && compteur < this.main.compo.size()) {
    		LgRole rol = this.main.compo.get(compteur);
    		if(rol.getNom().equals(rolerm.getNom())) {
    			rm = false;
    			this.main.compo.remove(compteur);
    		}
    		compteur++;
    	}
    }



	public boolean isCoupleAleatoire() {
		return coupleAleatoire;
	}



	public void setCoupleAleatoire(boolean coupleAleatoire) {
		this.coupleAleatoire = coupleAleatoire;
	}



	public boolean isSolitaire() {
		return solitaire;
	}



	public void setSolitaire(boolean solitaire) {
		this.solitaire = solitaire;
	}



	public boolean isRumeurs() {
		return rumeurs;
	}



	public void setRumeurs(boolean rumeurs) {
		this.rumeurs = rumeurs;
	}



	public HashMap<UUID, String> getRumeursListe() {
		return rumeursListe;
	}
    

}
