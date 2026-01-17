package fusionmc.nardodev.utils.cmd.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import fusionmc.nardodev.utils.cmd.Commands;
import fusionmc.nardodev.utils.managers.Manager;

public class VanishCommand extends Commands {
   public VanishCommand() {
      super("vanish", "v");
   }

   public void perform(CommandSender sender, String label, String[] args) {
      if (!(sender instanceof Player)) {
         sender.sendMessage("§cApenas jogadores podem utilizar este comando.");
      } else {
         Player player = (Player)sender;
         if (!player.hasPermission("nardocore.admin")) {
            player.sendMessage("§cVocê não possui permissão para executar este comando.");
         } else {
            if (Manager.isVanished(player)) {
               Manager.removeVanish(player);
               player.sendMessage("§cModo invisível desativado.");
            } else if (!Manager.isVanished(player)) {
               Manager.addVanish(player);
               player.sendMessage("§aModo invisível ativado.");
            }

         }
      }
   }
}
