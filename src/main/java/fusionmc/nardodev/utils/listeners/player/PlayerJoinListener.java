package fusionmc.nardodev.utils.listeners.player;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import fusionmc.nardodev.utils.upgrade.Upgrade;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerQuitEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerJoinListener implements Listener {
   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent evt) {
      Player player = evt.getPlayer();
      if (Upgrade.QUEUE_VIPS.containsKey(player.getName())) {
         ((Upgrade)Upgrade.QUEUE_VIPS.get(player.getName())).dispatchPack(player);
      }

      Bukkit.setDefaultGameMode(GameMode.SURVIVAL);
   }

   @EventHandler
   public void onJoin(PlayerJoinEvent event) {
      event.setJoinMessage(null);
   }

   @EventHandler
   public void onQuit(PlayerQuitEvent event) {
      event.setQuitMessage(null);
   }

   @EventHandler
   public void onPreLogin(AsyncPlayerPreLoginEvent e) {
      if (Bukkit.getBanList(BanList.Type.NAME).isBanned(e.getName())) {

         BanEntry ban = Bukkit.getBanList(BanList.Type.NAME).getBanEntry(e.getName());
         String reason = ban.getReason();
         Date expires = ban.getExpiration();

         SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

         String banMessage =
                 "&cVocê foi banido temporariamente.\n\n" +
                         "  &cMotivo: &7" + reason + "\n" +
                         "  &cExpira em: &7" + sdf.format(expires) + "\n" +
                         "  &eAdquira seu unban em: &bwww.fusionmc.com.br\n" +
                         "  &cBanido injustamente? Contate-nos via\n" +
                         "  &9&ndiscord.gg/fusionmc";

         e.disallow(
                 AsyncPlayerPreLoginEvent.Result.KICK_BANNED,
                 ChatColor.translateAlternateColorCodes('&', banMessage)
         );
      }
   }
}
