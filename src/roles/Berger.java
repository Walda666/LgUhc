package roles;

public class Berger extends LgRole {
	private String mouton1, mouton2;
	public Berger() {
		this.nom = "Berger";
        this.camp = "§fVillageois§f";
        this.desc = "respawn";
        this.nbUses = 2;
        this.mouton1 = "pret";
        this.mouton2 = "pret";
    }
	public String getMouton1() {
		return mouton1;
	}
	public void setMouton1(String mouton1) {
		this.mouton1 = mouton1;
	}
	public String getMouton2() {
		return mouton2;
	}
	public void setMouton2(String mouton2) {
		this.mouton2 = mouton2;
	}
	
	
}