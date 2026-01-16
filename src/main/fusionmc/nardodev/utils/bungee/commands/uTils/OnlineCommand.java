package redehyzen.escolhendo.utils.bungee.commands.uTils;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import redehyzen.escolhendo.utils.bungee.commands.Commands;

public class OnlineCommand extends Commands {
   public OnlineCommand() {
      super("online");
   }

   public void perform(CommandSender sender, String[] args) {
      sender.sendMessage("§eTotal de jogadores online: §7" + BungeeCord.getInstance().getPlayers().size());
   }
}
