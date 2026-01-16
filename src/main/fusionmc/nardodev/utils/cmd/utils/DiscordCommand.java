package redehyzen.escolhendo.utils.cmd.utils;

import java.util.Arrays;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import redehyzen.escolhendo.utils.Language;
import redehyzen.escolhendo.utils.cmd.Commands;

public class DiscordCommand extends Commands {
   public DiscordCommand() {
      super("discord", "dc");
   }

   public ChatColor getColor() {
      return ChatColor.valueOf(Language.discord$click.split(" : ")[0]);
   }

   public boolean getBold() {
      return Boolean.parseBoolean(Language.discord$click.split(" : ")[1].replace("bold>", ""));
   }

   public void perform(CommandSender sender, String label, String[] args) {
      if (!(sender instanceof Player)) {
         sender.sendMessage(Language.discord$message + "AQUI" + Language.discord$message2);
      } else {
         Player player = (Player)sender;
         if (args.length != 0) {
            String action = args[0];
            if (!player.hasPermission("hyzenutils.cmd.discord")) {
               player.chat("/discord");
               return;
            }

            if (action.equalsIgnoreCase("give")) {
               player.sendMessage("§aCabeça adicionada ao seu inventário.");
               return;
            }

            if (action.equalsIgnoreCase("ajuda")) {
               player.sendMessage("");
               player.sendMessage("§6/discord give §f- §7Pegar cabeças do discord.");
               player.sendMessage("");
               return;
            }
         }

         TextComponent component = new TextComponent("");
         Arrays.stream(TextComponent.fromLegacyText(Language.discord$message)).forEach(component::addExtra);
         TextComponent click = new TextComponent("AQUI");
         click.setColor(this.getColor());
         click.setBold(this.getBold());
         click.setClickEvent(new ClickEvent(Action.OPEN_URL, Language.discord$link));
         click.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§7Clique aqui para abrir o link de convite.")));
         component.addExtra(click);
         BaseComponent[] var7 = TextComponent.fromLegacyText(Language.discord$message2);
         int var8 = var7.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            BaseComponent components = var7[var9];
            component.addExtra(components);
         }

         player.spigot().sendMessage(component);
      }
   }
}
