package fusionmc.nardodev.utils.cmd.utils;

import java.util.Arrays;
import java.util.stream.IntStream;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import fusionmc.nardodev.utils.cmd.Commands;

public class ClearChatCommand extends Commands {
   public ClearChatCommand() {
      super("cc", "clearchat");
   }

   @Override
   public void perform(CommandSender sender, String label, String[] args) {
      if (!(sender instanceof Player)) {
         sender.sendMessage("§cApenas jogadores podem executar este comando.");
      } else {
         Player player = (Player)sender;
         if (!player.hasPermission("nardocore.cmd.clearchat")) {
            player.sendMessage("§cVocê não tem permissão para executar este comando.");
         } else {
            IntStream.range(0, 200).forEach((i) -> {
               Bukkit.getOnlinePlayers().forEach((consumer) -> {
                  consumer.sendMessage("");
               });
            });
            TextComponent component = new TextComponent("");
            Arrays.stream(TextComponent.fromLegacyText("O chat foi limpo.")).forEach(component::addExtra);
            Bukkit.getOnlinePlayers().forEach((consumer) -> {
               consumer.spigot().sendMessage(component);
            });
         }
      }
   }
}
