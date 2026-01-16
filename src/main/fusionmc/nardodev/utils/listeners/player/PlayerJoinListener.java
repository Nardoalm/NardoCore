package redehyzen.escolhendo.utils.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import redehyzen.escolhendo.utils.upgrade.Upgrade;

public class PlayerJoinListener implements Listener {
   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent evt) {
      Player player = evt.getPlayer();
      if (Upgrade.QUEUE_VIPS.containsKey(player.getName())) {
         ((Upgrade)Upgrade.QUEUE_VIPS.get(player.getName())).dispatchPack(player);
      }

   }
}
