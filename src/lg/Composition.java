package lg;

import java.util.ArrayList;
import java.util.Collections;


public class Composition
{
    private ArrayList<LgRole> compo;
    
    public Composition() {
        this.compo = new ArrayList<LgRole>();
    }
    
    public ArrayList<LgRole> getCompo() {
        return this.compo;
    }
    
    public void setCompo(ArrayList<LgRole> compo) {
        this.compo = compo;
    }
    
    public ArrayList<String> getCompoTriee() {
    	ArrayList<LgRole> clone = (ArrayList<LgRole>) this.compo.clone();
    	ArrayList<String> finale = new ArrayList<>();
    	ArrayList<String> explorateurs = new ArrayList<>();
    	ArrayList<String> animaux = new ArrayList<>();
    	ArrayList<String> braconniers = new ArrayList<>();
    	ArrayList<String> solos = new ArrayList<>();
    	for(LgRole role : clone) {
    		String camp = role.getCamp();
    		if(camp.equals("�aExplorateurs�f")) explorateurs.add(role.getNom());
    		if(camp.equals("�1Animaux�f") || camp.equals("�1Animaux �f") || camp.equals("�1Animaux  �f")) animaux.add(role.getNom());
    		if(camp.equals("�4Braconniers �f") || camp.equals("�4Braconniers�f")) braconniers.add(role.getNom());
    		if(camp.equals("�6Solo�f")) solos.add(role.getNom());
    		if(camp.equals("�6Solo   �f") || camp.equals("�aExplorateurs   �f") || camp.equals("�1Animaux   �f") || camp.equals("�4Braconniers   �f")) solos.add("�6Touriste�f");
    	}
    	Collections.sort(explorateurs);
    	Collections.sort(animaux);
    	Collections.sort(braconniers);
    	Collections.sort(solos);
    
    	for(String role: explorateurs) {
    		finale.add(role);
    	}
    	for(String role: animaux) {
    		finale.add(role);
    	}
    	for(String role: braconniers) {
    		finale.add(role);
    	}
    	for(String role: solos) {
    		finale.add(role);
    	}
    	return finale;
    }
}
