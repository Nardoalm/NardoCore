package redehyzen.escolhendo.utils.bungee.commands.uTils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import escolhendo.apexstore.services.player.role.Role;
import escolhendo.apexstore.services.utils.StringUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import redehyzen.escolhendo.utils.bungee.commands.Commands;

public class StaffChatCommand extends Commands {
   public StaffChatCommand() {
      super("sc", "staffchat");
   }

   public void perform(CommandSender sender, String[] args) {
      if (sender.hasPermission("hyzenutils.cmd.staffchat")) {
         if (args.length == 0) {
            sender.sendMessage(TextComponent.fromLegacyText("§cUtilize /sc [mensagem]"));
            return;
         }

         ProxiedPlayer playerr = null;
         if (sender instanceof ProxiedPlayer) {
            playerr = (ProxiedPlayer)sender;
         }

         String joined = StringUtils.join(args, " ");
         String server = playerr == null ? "Lobby" : StringUtils.capitalise(playerr.getServer().getInfo().getName().toUpperCase());
         ProxyServer.getInstance().getPlayers().stream().filter((player) -> {
            return player.hasPermission("hyzenutils.cmd.staffchat");
         }).forEach((player) -> {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("STAFF_SOUND");
            out.writeUTF(player.getName());
            player.getServer().sendData("MESSAGE_UTILS", out.toByteArray());
            player.sendMessage(TextComponent.fromLegacyText("§d§l[Staff] §7[" + server + "] " + (sender instanceof ProxiedPlayer ? Role.getPlayerRole(playerr).getPrefix() + playerr.getName() : "§6[Master] CONSOLE") + "§f: " + StringUtils.formatColors(joined)));
         });
      } else {
         sender.sendMessage(TextComponent.fromLegacyText("§cVocê não tem permissão para isto."));
      }

   }
}
