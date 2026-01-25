package me.nardodev.lobby.listeners;

import me.nardodev.lobby.listeners.entity.EntityListener;
import me.nardodev.lobby.listeners.player.ChatListener;
import me.nardodev.lobby.listeners.player.ProtectionListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import me.nardodev.lobby.Main;
import me.nardodev.lobby.listeners.player.PlayerJoinListener;

public class Listeners {
   public static void setupListeners() {
      try {
         PluginManager pm = Bukkit.getPluginManager();
         pm.registerEvents(new ProtectionListener(), Main.getInstance());
         pm.registerEvents(new PlayerJoinListener(), Main.getInstance());
         pm.registerEvents(new EntityListener(), Main.getInstance());
         pm.registerEvents(new ChatListener(), Main.getInstance());
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}