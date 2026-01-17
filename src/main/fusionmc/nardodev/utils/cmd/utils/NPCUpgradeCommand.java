package redehyzen.escolhendo.utils.cmd.utils;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import redehyzen.escolhendo.utils.cmd.Commands;
import redehyzen.escolhendo.utils.lobby.UpgradeNPC;

public class NPCUpgradeCommand extends Commands {
   public NPCUpgradeCommand() {
      super("npcuparvip");
   }

   public void perform(CommandSender sender, String label, String[] args) {
      if (!(sender instanceof Player)) {
         sender.sendMessage("§cApenas jogadores podem executar este comando.");
      } else {
         Player player = (Player)sender;
         if (!player.hasPermission("hyzenutils.cmd.npcuparvip")) {
            player.sendMessage("§cVocê não tem permissão para executar este comando.");
         } else if (args.length == 0) {
            player.sendMessage(" \n§eAjuda\n \n§6/npcuparvip adicionar [id] §f- §7Adicionar NPC.\n§6/npcuparvip remover [id] §f- §7Remover NPC.\n ");
         } else {
            String action = args[0];
            String id;
            if (action.equalsIgnoreCase("adicionar")) {
               if (args.length <= 1) {
                  player.sendMessage("§cUtilize /npcuparvip adicionar [id]");
                  return;
               }

               id = args[1];
               if (UpgradeNPC.getById(id) != null) {
                  player.sendMessage("§cJá existe um NPC de Upar Vip utilizando \"" + id + "\" como ID.");
                  return;
               }

               Location location = player.getLocation().getBlock().getLocation().add(0.5D, 0.0D, 0.5D);
               location.setYaw(player.getLocation().getYaw());
               location.setPitch(player.getLocation().getPitch());
               UpgradeNPC.add(id, location);
               player.sendMessage("§aNPC de Upar Vip adicionado com sucesso.");
            } else if (action.equalsIgnoreCase("remover")) {
               if (args.length <= 1) {
                  player.sendMessage("§cUtilize /npcuparvip remover [id]");
                  return;
               }

               id = args[1];
               UpgradeNPC npc = UpgradeNPC.getById(id);
               if (npc == null) {
                  player.sendMessage("§cNão existe um NPC de Upar Vip utilizando \"" + id + "\" como ID.");
                  return;
               }

               UpgradeNPC.remove(npc);
               player.sendMessage("§cNPC de Upar Vip removido com sucesso.");
            } else {
               player.sendMessage(" \n§eAjuda\n \n§6/npcuparvip adicionar [id] §f- §7Adicionar NPC.\n§6/npcuparvip remover [id] §f- §7Remover NPC.\n ");
            }

         }
      }
   }
}
