package utils.scoreboard;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IScoreboardCriteria.EnumScoreboardHealthDisplay;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardObjective;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardScore;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardScore.EnumScoreboardAction;
import net.minecraft.server.v1_8_R3.PlayerConnection;





/**
 * Utilitaire permettant de gerer le sidebar de facon individuelle, independamment du {@link org.bukkit.scoreboard.Scoreboard}, avec des chaines de caracteres plus longues et integrant un systeme anti-clignotement
 * 
 * <p>Permet une gestion Nom vers Score du sidebar</p>
 * 
 * @author Unfou
 */
public class Sidebar {

    private Player player;
    private HashMap<String, Integer> linesA;
    private HashMap<String, Integer> linesB;
    private String name = "";
    private String bufferUnique = String.valueOf(new Random().nextLong()%1000000000l);
    
    private Boolean a = true;
    private String getBuffer() {
    	return a?(bufferUnique+"A"):(bufferUnique+"B");
    }
    private HashMap<String, Integer> linesBuffer() {
    	return a?linesA:linesB;
    }
    private HashMap<String, Integer> linesDisplayed() {
    	return (!a)?linesA:linesB;
    }
    private void swapBuffer() {
    	a = !a;
    }

    /**
     * Initialise le sidebar pour un joueur
     * 
     * @param p Le joueur pour qui il faut initialiser le sidebar
     */
    public Sidebar(Player p) {
    	
        this.player = p;
        this.linesA = new HashMap<>();
        this.linesB = new HashMap<>();
        
        PacketPlayOutScoreboardObjective packetA = new PacketPlayOutScoreboardObjective();
        setField(packetA, "a", this.bufferUnique + "A");
        setField(packetA, "b", "");
        setField(packetA, "d", 0);
        setField(packetA, "c", EnumScoreboardHealthDisplay.INTEGER);
    	
        PacketPlayOutScoreboardObjective packetB = new PacketPlayOutScoreboardObjective();
        setField(packetB, "a", this.bufferUnique + "B");
        setField(packetB, "b", "");
        setField(packetB, "d", 0);
        setField(packetB, "c", EnumScoreboardHealthDisplay.INTEGER);
    	
        getPlayer().sendPacket(packetA);
        getPlayer().sendPacket(packetB);
    }
    
    /**
     * Envoie les modifications apportees au sidebar au joueur
     */
    public synchronized void send() {
    	
    	PacketPlayOutScoreboardDisplayObjective packet = new PacketPlayOutScoreboardDisplayObjective();
        setField(packet, "a", 1);
        setField(packet, "b", this.getBuffer());
    	
        swapBuffer();
        
        getPlayer().sendPacket(packet);
        
        for (String text : linesDisplayed().keySet()) {
        	if (linesBuffer().containsKey(text)) {
        		if (linesBuffer().get(text) == linesDisplayed().get(text)) {
        			continue;
        		}
        	}
        	
        	setLine(text, linesDisplayed().get(text));
        }
        for (String text : new ArrayList<String>(linesBuffer().keySet())) {
        	if (!linesDisplayed().containsKey(text)) {
        		removeLine(text);
        	}
        }
    }

    /**
     * Permet de detruire le sidebar du joueur
     */
    public synchronized void remove() {
    	PacketPlayOutScoreboardObjective packetA = new PacketPlayOutScoreboardObjective();
        setField(packetA, "a", this.bufferUnique + "A");
        setField(packetA, "b", "");
        setField(packetA, "d", 1);
        PacketPlayOutScoreboardObjective packetB = new PacketPlayOutScoreboardObjective();
        setField(packetB, "a", this.bufferUnique + "B");
        setField(packetB, "b", "");
        setField(packetB, "d", 1);
        
        getPlayer().sendPacket(packetA);
        getPlayer().sendPacket(packetB);
    }
    
    /**
     * Permet de vider le sidebar
     */
    public synchronized void clear() {
    	for (String text : new ArrayList<>(linesBuffer().keySet())) {
    		removeLine(text);
    	}
    }
    
    /**
     * Permet d'assigner un score a un certain texte
     * 
     * @param text Le texte (<i>max. <b>40</b> caracteres</i>)
     * @param line Le score a assigner au texte
     */
    public synchronized void setLine(String text, Integer line) {
    	if (text == null) return;
    	
    	if (text.length() > 40)
            text = text.substring(0, 40);
    	
        if (linesBuffer().containsKey(text))
            removeLine(text);
        
        PacketPlayOutScoreboardScore packet = new PacketPlayOutScoreboardScore(text);
        setField(packet, "b", this.getBuffer());
        setField(packet, "c", line);
        setField(packet, "d", EnumScoreboardAction.CHANGE);
        
        getPlayer().sendPacket(packet);
        linesBuffer().put(text, line);
    }

    /**
     * Permet de retirer un certain texte du sidebar
     * 
     * @param text Le texte a retirer
     */
    public synchronized void removeLine(String text) {
        
    	if (text.length() > 40)
            text = text.substring(0, 40);
    	
    	if (!linesBuffer().containsKey(text))
    		return;
    	
    	Integer line = linesBuffer().get(text);
    	
    	PacketPlayOutScoreboardScore packet = new PacketPlayOutScoreboardScore(text);
        setField(packet, "b", this.getBuffer());
        setField(packet, "c", line);
        setField(packet, "d", EnumScoreboardAction.REMOVE);
        
        getPlayer().sendPacket(packet);
        linesBuffer().remove(text);
    }

    /**
     * Permet de modifier le titre du sidebar
     * 
     * <p>Cette modification s'effectue instantanement et ne necessite pas un appel de {@link #send()}</p>
     * 
     * @param displayName Le nouveau titre (<i>max. <b>32</b> caracteres</i>)
     */
    public void setName(String displayName) {
    	
    	if(displayName.length() > 32)
    		displayName = displayName.substring(0, 32);
    	
    	if (displayName.equals(name)) return;
    	name = displayName;
    	
    	PacketPlayOutScoreboardObjective packetA = new PacketPlayOutScoreboardObjective();
        setField(packetA, "a", this.bufferUnique + "A");
        setField(packetA, "b", displayName);
        setField(packetA, "d", 2);
        setField(packetA, "c", EnumScoreboardHealthDisplay.INTEGER);
        
        PacketPlayOutScoreboardObjective packetB = new PacketPlayOutScoreboardObjective();
        setField(packetB, "a", this.bufferUnique + "B");
        setField(packetB, "b", displayName);
        setField(packetB, "d", 2);
        setField(packetB, "c", EnumScoreboardHealthDisplay.INTEGER);
        
        getPlayer().sendPacket(packetA);
        getPlayer().sendPacket(packetB);
    }

    private void setField(Object edit, String fieldName, Object value) {
        try {
            Field field = edit.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(edit, value);
        } catch(IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
    
    private PlayerConnection getPlayer() {
        return ((CraftPlayer)this.player.getPlayer()).getHandle().playerConnection;
    }
}