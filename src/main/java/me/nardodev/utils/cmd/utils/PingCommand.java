package me.nardodev.utils.cmd.utils;

import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import me.nardodev.utils.cmd.Commands;

public class PingCommand extends Commands {
   public PingCommand() {
      super("ping", "p");
   }

   public void perform(CommandSender sender, String label, String[] args) {
      sender.sendMessage("§a§lSUCESSO ➔ §aSeu ping é de " + (sender instanceof Player ? ((CraftPlayer)sender).getHandle().ping : 0) + " §ams!");
      Player senderPlayer = (Player) sender;
      senderPlayer.playSound(senderPlayer.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
   }
}
