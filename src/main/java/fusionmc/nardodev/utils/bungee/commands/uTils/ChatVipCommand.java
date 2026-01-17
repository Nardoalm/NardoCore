package fusionmc.nardodev.utils.bungee.commands.uTils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import escolhendo.apexstore.services.player.role.Role;
import escolhendo.apexstore.services.utils.StringUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import fusionmc.nardodev.utils.bungee.commands.Commands;

public class ChatVipCommand extends Commands {
   public ChatVipCommand() {
      super("cv", "chatvip");
   }

   public void perform(CommandSender sender, String[] args) {
      if (sender.hasPermission("hyzenutils.cmd.chatvip")) {
         if (args.length == 0) {
            sender.sendMessage(TextComponent.fromLegacyText("§cUtilize /cv [mensagem]"));
            return;
         }

         ProxiedPlayer playerr = null;
         if (sender instanceof ProxiedPlayer) {
            playerr = (ProxiedPlayer)sender;
         }

         // Tornar as variáveis final para uso na lambda
         final ProxiedPlayer finalPlayerr = playerr;

         String joined = StringUtils.join(args, " ");
         String server = playerr == null ? "Lobby" : StringUtils.capitalise(playerr.getServer().getInfo().getName().toUpperCase());

         final String finalServer = server;

         ProxyServer.getInstance().getPlayers().stream().filter((player) -> {
            return player.hasPermission("hyzenutils.cmd.chatvip");
         }).forEach((player) -> {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("VIP_SOUND");
            out.writeUTF(player.getName());
            player.getServer().sendData("MESSAGE_UTILS", out.toByteArray());
            player.sendMessage(TextComponent.fromLegacyText("§6§l[Chat Vip] §7[" + finalServer + "] " + (sender instanceof ProxiedPlayer ? Role.getPlayerRole(finalPlayerr).getPrefix() + finalPlayerr.getName() : "§4[Supremo] CONSOLE") + "§f: " + StringUtils.formatColors(joined)));
         });
      } else {
         sender.sendMessage(TextComponent.fromLegacyText("§cVocê não tem permissão para isto."));
      }

   }
}