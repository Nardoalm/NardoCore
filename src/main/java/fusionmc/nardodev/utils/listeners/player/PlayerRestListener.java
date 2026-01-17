package fusionmc.nardodev.utils.listeners.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import fusionmc.nardodev.utils.Language;

public class PlayerRestListener implements Listener {
   @EventHandler
   public void onServerListPing(ServerListPingEvent evt) {
      if (Language.motd$enabled) {
         evt.setMotd(Language.motd$text);
      }

   }
}
