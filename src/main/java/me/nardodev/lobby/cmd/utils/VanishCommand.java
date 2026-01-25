package me.nardodev.lobby.cmd.utils;

import me.nardodev.lobby.utils.welcomeMessages.WelcomeMessage;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.nardodev.lobby.cmd.Commands;
import me.nardodev.lobby.managers.Manager;

public class VanishCommand extends Commands {

   public VanishCommand() {
      super("vanish", "v");
   }

   private void toggleVanish(Player player) {
      if (Manager.isVanished(player)) {
         Manager.removeVanish(player);
         player.sendMessage("§a§lSUCESSO ➔ §aModo invisível desativado.");
         player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
         WelcomeMessage.sendWelcomeMessage(player);
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


      if (!sender.hasPermission("nardocore.staff.vanish")) {
         sender.sendMessage("§c§lERRO ➔ §cVocê não possui permissão para executar este comando.");
         Player senderPlayer = (Player) sender;
         senderPlayer.playSound(senderPlayer.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
         return;
      }

      Player playerSender = (Player)sender;

      toggleVanish(playerSender);
   }

}