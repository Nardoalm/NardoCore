package redehyzen.escolhendo.utils.cmd;

import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import redehyzen.escolhendo.utils.cmd.utils.ClearChatCommand;
import redehyzen.escolhendo.utils.cmd.utils.CommandList;
import redehyzen.escolhendo.utils.cmd.utils.DiscordCommand;
import redehyzen.escolhendo.utils.cmd.utils.GamemodeCommand;
import redehyzen.escolhendo.utils.cmd.utils.NPCUpgradeCommand;
import redehyzen.escolhendo.utils.cmd.utils.PingCommand;
import redehyzen.escolhendo.utils.cmd.utils.TeleportCommand;
import redehyzen.escolhendo.utils.cmd.utils.VanishCommand;

public abstract class Commands extends Command {
   public Commands(String name, String... aliases) {
      super(name);
      this.setAliases(Arrays.asList(aliases));

      try {
         SimpleCommandMap simpleCommandMap = (SimpleCommandMap)Bukkit.getServer().getClass().getDeclaredMethod("getCommandMap").invoke(Bukkit.getServer());
         simpleCommandMap.register(this.getName(), "hyzenutils", this);
      } catch (ReflectiveOperationException var4) {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Nao foi possivel iniciar os comandos.");
      }

   }

   public static void setupCommands() {
      new GamemodeCommand();
      new ClearChatCommand();
      new VanishCommand();
      new NPCUpgradeCommand();
      new DiscordCommand();
      new PingCommand();
      new CommandList();
      new TeleportCommand();
   }

   public abstract void perform(CommandSender var1, String var2, String[] var3);

   public boolean execute(CommandSender sender, String commandLabel, String[] args) {
      this.perform(sender, commandLabel, args);
      return true;
   }
}
