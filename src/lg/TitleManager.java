package lg;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;

public class TitleManager {
    @SuppressWarnings("rawtypes")
    public static void sendTitle(Player player, String msgTitle, String msgSubTitle, int ticks) {
        IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a((String)("{\"text\": \"" + msgTitle + "\"}"));
        IChatBaseComponent chatSubTitle = IChatBaseComponent.ChatSerializer.a((String)("{\"text\": \"" + msgSubTitle + "\"}"));
        PacketPlayOutTitle p = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle);
        PacketPlayOutTitle p2 = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, chatSubTitle);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)p);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)p2);
        TitleManager.sendTime(player, ticks);
    }

    @SuppressWarnings("rawtypes")
    private static void sendTime(Player player, int ticks) {
        PacketPlayOutTitle p = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, 20, ticks, 20);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)p);
    }

    @SuppressWarnings("rawtypes")
    public static void sendActionBar(Player player, String message) {
        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a((String)("{\"text\": \"" + message + "\"}"));
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)ppoc);
    }
    
    public void sendClickableCommand(Player player, String message, String command) {
        // Make a new component (Bungee API).
        TextComponent component = new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', message)));
        // Add a click event to the component.
        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + command));

        // Send it!
        player.spigot().sendMessage(component);
    }
    
    public TextComponent getClickableCommand(Player player, String message, String command) {
        // Make a new component (Bungee API).
        TextComponent component = new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', message)));
        // Add a click event to the component.
        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + command));

        return component;
    }
    
}