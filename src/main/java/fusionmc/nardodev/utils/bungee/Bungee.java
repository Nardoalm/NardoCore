package fusionmc.nardodev.utils.bungee;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import fusionmc.nardodev.utils.bungee.commands.Commands;
import fusionmc.nardodev.utils.bungee.listeners.Listeners;
import fusionmc.nardodev.utils.bungee.manager.MaintenanceManager;
import fusionmc.nardodev.utils.bungee.plugin.logger.FusionLogger;

public class Bungee extends Plugin {
   public static Bungee instance;
   public static final Map<ProxiedPlayer, ProxiedPlayer> TELL = new HashMap();

   public Bungee() {
      try {
         Field field = Plugin.class.getDeclaredField("logger");
         field.setAccessible(true);
         field.set(this, new FusionLogger(this));
      } catch (Exception var2) {
         var2.printStackTrace();
      }

      instance = this;
   }

   public void onEnable() {
      this.getProxy().getPluginManager().registerListener(this, new Listeners());
      Settings.setupSettings();
      Commands.setupCommands();
      MaintenanceManager.setupMaintenance();
      this.getLogger().info("O plugin foi ativado.");
   }

   public void onDisable() {
      this.getLogger().info("O plugin foi desativado.");
   }

   public static Bungee getInstance() {
      return instance;
   }
}
