package fusionmc.nardodev.utils.bungee.commands.uTils;

import escolhendo.apexstore.services.player.role.Role;
import escolhendo.apexstore.services.utils.StringUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import fusionmc.nardodev.utils.bungee.Bungee;
import fusionmc.nardodev.utils.bungee.commands.Commands;

public class ReplyCommand extends Commands {
   public ReplyCommand() {
      super("r");
   }

   public void perform(CommandSender sender, String[] args) {
      if (sender instanceof ProxiedPlayer) {
         if (args.length == 0) {
            sender.sendMessage(TextComponent.fromLegacyText("§cUtilize /r [mensagem]"));
         } else {
            ProxiedPlayer player = (ProxiedPlayer)sender;
            ProxiedPlayer target = (ProxiedPlayer)Bungee.TELL.get(player);
            if (target != null && target.isConnected()) {
               String join = StringUtils.join(args, " ");
               if (player.hasPermission("hyzenutils.tell.color")) {
                  join = StringUtils.formatColors(join);
               }

               Bungee.TELL.put(target, player);
               Bungee.TELL.put(player, target);
               target.sendMessage(TextComponent.fromLegacyText("§8Mensagem de: " + Role.getPlayerRole(player).getPrefix() + player.getName() + "§8: §6" + join));
               player.sendMessage(TextComponent.fromLegacyText("§8Mensagem para: " + Role.getPlayerRole(target).getPrefix() + target.getName() + "§8: §6" + join));
            } else {
               player.sendMessage(TextComponent.fromLegacyText("§cJogador offline."));
            }
         }
      }
   }
}
