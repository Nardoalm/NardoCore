package fusionmc.nardodev.utils.cmd.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import fusionmc.nardodev.utils.cmd.Commands;

public class PingCommand extends Commands {
   public PingCommand() {
      super("ping", "p");
   }

   public void perform(CommandSender sender, String label, String[] args) {
      sender.sendMessage("§aSeu ping é de " + (sender instanceof Player ? ((CraftPlayer)sender).getHandle().ping : 0) + " §ams!");
   }
}
