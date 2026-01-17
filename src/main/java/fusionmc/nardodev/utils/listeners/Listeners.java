package fusionmc.nardodev.utils.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import fusionmc.nardodev.utils.Main;
import fusionmc.nardodev.utils.listeners.player.PlayerJoinListener;
import fusionmc.nardodev.utils.listeners.player.PlayerRestListener;

public class Listeners {
   public static void setupListeners() {
      try {
         PluginManager pm = Bukkit.getPluginManager();

         // Removi a linha 15 que estava errada
         // Apenas registra os listeners que realmente implementam Listener
         pm.registerEvents(new PlayerRestListener(), Main.getInstance());
         pm.registerEvents(new PlayerJoinListener(), Main.getInstance());

      } catch (Exception var1) {
         var1.printStackTrace();
      }
   }
}