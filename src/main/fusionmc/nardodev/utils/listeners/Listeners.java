package redehyzen.escolhendo.utils.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import redehyzen.escolhendo.utils.Main;
import redehyzen.escolhendo.utils.listeners.player.PlayerJoinListener;
import redehyzen.escolhendo.utils.listeners.player.PlayerRestListener;

public class Listeners {
   public static void setupListeners() {
      try {
         PluginManager pm = Bukkit.getPluginManager();
         pm.getClass().getDeclaredMethod("registerEvents", Listener.class, Plugin.class).invoke(pm, Main.getInstance());
         pm.getClass().getDeclaredMethod("registerEvents", Listener.class, Plugin.class).invoke(pm, new PlayerRestListener(), Main.getInstance());
         pm.getClass().getDeclaredMethod("registerEvents", Listener.class, Plugin.class).invoke(pm, new PlayerJoinListener(), Main.getInstance());
      } catch (Exception var1) {
         var1.printStackTrace();
      }

   }
}
