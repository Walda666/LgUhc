package utils.scoreboard;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.ChatColor;

/**
 * Utilitaire permettant de gerer le sidebar ligne par ligne en utilisant {@link Sidebar}
 * 
 * <p>Permet une gestion Score vers Nom du sidebar</p>
 * 
 * <p>A noter que cette classe ne prend en charge que la gestion du contenu du sidebar, par exemple le titre ne peut etre modifie que par la methode {@link Sidebar#setName(String)}</p>
 * 
 * @author Unfou
 */
public class Sideline {
	
	Sidebar sb;
	
	HashMap<Integer,String> old = new HashMap<>();
	Deque<String> buffer = new ArrayDeque<>();
	
	/**
     * Initialise le sidebar pour un joueur
     * 
     * @param sb L'objet {@link Sidebar} de controle du sidebar
     */
	public Sideline(Sidebar sb) {
		this.sb = sb;
	}
	
	/**
     * Permet de vider le sidebar
     */
	public synchronized void clear() {
		sb.clear();
		old.clear();
	}
	
	/**
     * Permet d'assigner un texte a une certaine ligne, qui peut être plusieurs fois le même ou vide
     * 
     * @param i Le numero de ligne
     * @param str Le texte (<i>max. <b>40</b> caracteres</i>)
     */
	public synchronized void set(Integer i, String str) {
		if (old.containsKey(i)) {
			sb.removeLine(old.get(i));
		}
		
		if (str.equals(""))
			str = " ";
		
		str = makeUnique(str);
		
		old.put(i, str);
		sb.setLine(str, i);
	}
	
	private String makeUnique(String str) {
		while (old.containsValue(str)) {
			for (int j=0; j<ChatColor.values().length; j++) {
				if (!old.containsValue(str + ChatColor.values()[j])) {
					str = str + ChatColor.values()[j];
					return str;
				}
			}
			str = str + ChatColor.RESET;
		}
		return str;
	}
	
	/**
	 * Permet d'ajouter une ligne contenant un certain texte, pouvant être plusieurs fois le même ou vide
	 * 
	 * <p>Une fois tous les ajouts effectues, il faut appeler {@link #flush()}</p>
	 * 
	 * @param str Le texte (<i>max. <b>40</b> caracteres</i>)
	 */
	public synchronized void add(String str) {
		buffer.add(str);
	}
	
	/**
     * Envoie les lignes ajoutees
     */
	public synchronized void flush() {
		clear();
		Integer i = 0;
		Iterator<String> it = buffer.descendingIterator();
		while (it.hasNext()) {
			String line = it.next();
			i++;
			set(i, line);
		}
		buffer.clear();
		
		sb.send();
	}
	
	/**
     * Envoie les modifications apportees au sidebar au joueur
     */
	public void send() {
		sb.send();
	}
	
	/**
	 * @return Le nombre de lignes disponibles restantes dans le scoreboard
	 */
	public Integer getRemainingSize() {
		return 15 - buffer.size();
	}
}