package me.nardodev.utils.mikoImports.welcomeMessages;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class WelcomeMessage {
    private static final String coloredLine = "§c§m          §6§m          §e§m          §a§m          §b§m          ";
    private static final String middle = " §6entrou no lobby!";

    public static void sendWelcomeMessage(Player player) {
        if (player.hasPermission("nardocore.member.welcomeMessage")) {
            String formattedName = PlaceholderAPI.setPlaceholders(player, "%leaftags_tag_prefix%%player_displayname%");

            player.getServer().broadcastMessage(coloredLine);
            player.getServer().broadcastMessage("");
            player.getServer().broadcastMessage("§6⭐  " + formattedName + middle);
            player.getServer().broadcastMessage("");
            player.getServer().broadcastMessage(coloredLine);
        }
    }
}