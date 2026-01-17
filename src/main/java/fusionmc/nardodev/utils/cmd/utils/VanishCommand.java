package fusionmc.nardodev.utils.cmd.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import fusionmc.nardodev.utils.cmd.Commands;
import fusionmc.nardodev.utils.managers.Manager;

public class VanishCommand extends Commands {

   public VanishCommand() {
      super("vanish", "v");
   }

   @Override
   public void perform(CommandSender sender, String label, String[] args) {
      // 1. Verificação se é jogador (Cláusula de Guarda)
      if (!(sender instanceof Player)) {
         sender.sendMessage(ChatColor.RED + "Apenas jogadores podem utilizar este comando.");
         return; // Para a execução aqui
      }

      Player player = (Player) sender;

      // 2. Verificação de permissão (Cláusula de Guarda)
      if (!player.hasPermission("nardocore.admin")) {
         player.sendMessage(ChatColor.RED + "Você não possui permissão para executar este comando.");
         return; // Para a execução aqui
      }

      // 3. Lógica do Vanish (Toggle)
      toggleVanish(player);
   }

   private void toggleVanish(Player player) {
      if (Manager.isVanished(player)) {
         Manager.removeVanish(player);
         player.sendMessage(ChatColor.RED + "Modo invisível desativado.");
      } else {
         Manager.addVanish(player);
         player.sendMessage(ChatColor.GREEN + "Modo invisível ativado.");
      }
   }
}