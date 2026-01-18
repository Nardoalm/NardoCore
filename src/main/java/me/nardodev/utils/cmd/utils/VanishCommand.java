package me.nardodev.utils.cmd.utils;

import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.nardodev.utils.cmd.Commands;
import me.nardodev.utils.managers.Manager;

public class VanishCommand extends Commands {

   public VanishCommand() {
      super("vanish", "v");
   }

   private void toggleVanish(Player player) {
      if (Manager.isVanished(player)) {
         Manager.removeVanish(player);
         player.sendMessage("§a§lSUCESSO ➔ §aModo invisível desativado.");
         player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
      } else {
         Manager.addVanish(player);
         player.sendMessage( "§a§lSUCESSO ➔ §aModo invisível ativado.");
         player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
      }
   }

   @Override
   public void perform(CommandSender sender, String label, String[] args) {


      if (!(sender instanceof Player)) {
         sender.sendMessage("§c§lERRO ➔ §cApenas jogadores podem utilizar este comando.");
         Player senderPlayer = (Player) sender;
         senderPlayer.playSound(senderPlayer.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
         return;
      }


      if (!sender.hasPermission("nardocore.admin")) {
         sender.sendMessage("§c§lERRO ➔ §cVocê não possui permissão para executar este comando.");
         Player senderPlayer = (Player) sender;
         senderPlayer.playSound(senderPlayer.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
         return;
      }

      Player playerSender = (Player)sender;

      toggleVanish(playerSender);
   }

}