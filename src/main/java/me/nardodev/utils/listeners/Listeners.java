package me.nardodev.utils.listeners;

import me.nardodev.utils.listeners.player.ProtectionListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import me.nardodev.utils.Main;
import me.nardodev.utils.listeners.player.PlayerJoinListener;

public class Listeners {
   public static void setupListeners() {
      try {
         PluginManager pm = Bukkit.getPluginManager();
         pm.registerEvents(new ProtectionListener(), Main.getInstance());
         pm.registerEvents(new PlayerJoinListener(), Main.getInstance());

      } catch (Exception var1) {
         var1.printStackTrace();
      }
   }
}