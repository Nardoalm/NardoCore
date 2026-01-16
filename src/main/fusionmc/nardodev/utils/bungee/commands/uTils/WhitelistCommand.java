package redehyzen.escolhendo.utils.bungee.commands.uTils;

import java.util.List;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import redehyzen.escolhendo.utils.bungee.commands.Commands;
import redehyzen.escolhendo.utils.bungee.manager.MaintenanceManager;
import redehyzen.escolhendo.utils.bungee.plugin.config.HyzenConfig;

public class WhitelistCommand extends Commands {
   public WhitelistCommand() {
      super("whitelist", "maintenance");
   }

   public void perform(CommandSender sender, String[] args) {
      if (!sender.hasPermission("whitelist.cmd")) {
         sender.sendMessage(TextComponent.fromLegacyText("§cYou don't have permission to execute this command."));
      } else if (args.length == 0) {
         sender.sendMessage(TextComponent.fromLegacyText(" §eAjuda"));
         sender.sendMessage(TextComponent.fromLegacyText(""));
         sender.sendMessage(TextComponent.fromLegacyText(" §6/whitelist add [servidor] §f- §7Adicionar servidor na manutenção"));
         sender.sendMessage(TextComponent.fromLegacyText(" §6/whitelist remove [servidor] §f- §7Remover servidor da manutenção"));
         sender.sendMessage(TextComponent.fromLegacyText(" §6/whitelist addplayer [jogador] §f- §7Adicionar um jogador à whitelist."));
         sender.sendMessage(TextComponent.fromLegacyText(" §6/whitelist removeplayer [jogador] §f- §7Remover um jogador da whitelist."));
         sender.sendMessage(TextComponent.fromLegacyText(""));
      } else {
         String option = args[0];
         String player;
         List players;
         if (option.equalsIgnoreCase("add")) {
            if (args.length < 2) {
               sender.sendMessage(TextComponent.fromLegacyText("§cUtilize /whitelist add [servidor]"));
               return;
            }

            player = args[1];
            MaintenanceManager.getServers().add(player);
            players = HyzenConfig.getConfig("config").getStringList("maintenance");
            players.add(player);
            HyzenConfig.getConfig("config").set("maintenance", players);
            sender.sendMessage(TextComponent.fromLegacyText("§aO servidor " + player + " foi adicionado à manutenção."));
         } else if (option.equalsIgnoreCase("remove")) {
            if (args.length < 2) {
               sender.sendMessage(TextComponent.fromLegacyText("§cUtilize /whitelist remove [servidor]"));
               return;
            }

            player = args[1];
            MaintenanceManager.getServers().remove(player);
            players = HyzenConfig.getConfig("config").getStringList("maintenance");
            players.remove(player);
            HyzenConfig.getConfig("config").set("maintenance", players);
            sender.sendMessage(TextComponent.fromLegacyText("§aO servidor " + player + " foi removido da manutenção."));
         } else if (option.equalsIgnoreCase("addplayer")) {
            if (args.length < 2) {
               sender.sendMessage(TextComponent.fromLegacyText("§cUtilize /whitelist addplayer [jogador]"));
               return;
            }

            player = args[1];
            MaintenanceManager.addPlayer(player);
            sender.sendMessage(TextComponent.fromLegacyText("§aO jogador " + player + " foi adicionado à manutenção."));
         } else if (option.equalsIgnoreCase("removeplayer")) {
            if (args.length < 2) {
               sender.sendMessage(TextComponent.fromLegacyText("§cUtilize /whitelist removeplayer [jogador]"));
               return;
            }

            player = args[1];
            MaintenanceManager.removePlayer(player);
            sender.sendMessage(TextComponent.fromLegacyText("§aO jogador " + player + " foi removido da manutenção."));
         } else {
            sender.sendMessage(TextComponent.fromLegacyText(" §eAjuda - Whitelist"));
            sender.sendMessage(TextComponent.fromLegacyText(""));
            sender.sendMessage(TextComponent.fromLegacyText(" §6/whitelist add [servidor] §f- §7Adicionar servidor na manutenção"));
            sender.sendMessage(TextComponent.fromLegacyText(" §6/whitelist remove [servidor] §f- §7Remover servidor da manutenção"));
            sender.sendMessage(TextComponent.fromLegacyText(" §6/whitelist addplayer [jogador] §f- §7Adicionar um jogador à whitelist."));
            sender.sendMessage(TextComponent.fromLegacyText(" §6/whitelist removeplayer [jogador] §f- §7Remover um jogador da whitelist."));
            sender.sendMessage(TextComponent.fromLegacyText(""));
         }

      }
   }
}
