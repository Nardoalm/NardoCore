package fusionmc.nardodev.utils.bungee.commands.uTils;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.CommandSender;
import fusionmc.nardodev.utils.bungee.commands.Commands;

public class OnlineCommand extends Commands {
   public OnlineCommand() {
      super("online");
   }

   public void perform(CommandSender sender, String[] args) {
      sender.sendMessage("§eTotal de jogadores online: §7" + ProxyServer.getInstance().getPlayers().size());
   }
}
