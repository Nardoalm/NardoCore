package fusionmc.nardodev.utils.bungee.listeners;

import escolhendo.apexstore.services.utils.StringUtils;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.ServerPing.PlayerInfo;
import net.md_5.bungee.api.ServerPing.Players;
import net.md_5.bungee.api.ServerPing.Protocol;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import fusionmc.nardodev.utils.bungee.Settings;
import fusionmc.nardodev.utils.bungee.manager.MaintenanceManager;
import fusionmc.nardodev.utils.bungee.plugin.config.FusionConfig;

public class Listeners implements Listener {
   public static final FusionConfig config = FusionConfig.getConfig("motd");

   @EventHandler
   public void onServerConnect(ServerConnectEvent evt) {
      if (MaintenanceManager.getServers().contains(evt.getTarget().getName())) {
         ProxiedPlayer player = evt.getPlayer();
         if (!MaintenanceManager.getPlayers().contains(player.getName())) {
            player.sendMessage(TextComponent.fromLegacyText(
                    "§cExpulso enquanto se conectava a " + evt.getTarget().getName() + ": " + Settings.maintenance$kick));
            evt.setCancelled(true);
         }
      }
   }

   @EventHandler
   public void onServerConnected(ServerConnectedEvent evt) {
      if (MaintenanceManager.getServers().contains("ALL")) {
         ProxiedPlayer player = evt.getPlayer();
         if (!MaintenanceManager.getPlayers().contains(player.getName())) {
            player.disconnect(TextComponent.fromLegacyText(Settings.maintenance$kick));
         }
      }
   }

   @EventHandler
   public void onProxyPing(ProxyPingEvent evt) {
      ServerPing ping = evt.getResponse();
      Players pingPlayers = ping.getPlayers();
      Protocol pingVersion = ping.getVersion();
      if (config.getBoolean("version_change_enabled")) {
         pingPlayers.setSample(new PlayerInfo[]{
                 new PlayerInfo(StringUtils.formatColors(config.getString("version_name")), UUID.randomUUID())
         });
         pingVersion.setName(StringUtils.formatColors(config.getString("version")));
         evt.getResponse().setVersion(new Protocol(StringUtils.formatColors(config.getString("version")), 2));
      }

      ping.setDescription(StringUtils.formatColors(config.getString("motd").replace("\\n", "\n")));
      evt.setResponse(ping);
   }

   @EventHandler(priority = -128)
   public void onChat(ChatEvent evt) {
      if (evt.getSender() instanceof ProxiedPlayer) {
         if (evt.isCommand()) {
            ProxiedPlayer player = (ProxiedPlayer) evt.getSender(); // <-- aqui mudamos para ProxiedPlayer
            String[] args = evt.getMessage().replace("/", "").split(" ");
            String command = args[0];

            List<String> blocked = Settings.blocked_commands$list.stream()
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());

            if (blocked.contains(command.toLowerCase())) {
               player.sendMessage(TextComponent.fromLegacyText(
                       Settings.blocked_commands$message.replace("{command}", command)
               ));

               ProxyServer.getInstance().getPlayers().stream()
                       .filter(pp -> pp.hasPermission("hyzenutils.exe.notification"))
                       .forEach(pp -> pp.sendMessage(TextComponent.fromLegacyText(
                               StringUtils.formatColors(
                                       Settings.blocked_commands$message_staff
                                               .replace("{player}", player.getName())
                                               .replace("{command}", "/" + command)
                               )
                       )));

               evt.setCancelled(true);
            }
         }
      }
   }
}
