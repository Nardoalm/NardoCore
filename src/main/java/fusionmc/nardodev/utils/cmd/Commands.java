package fusionmc.nardodev.utils.cmd;

import java.util.Arrays;

import fusionmc.nardodev.utils.cmd.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;

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
      new DiscordCommand();
      new PingCommand();
      new CommandList();
      new TeleportCommand();
      new CbanCommand();
      new UnbanCommand();
   }

   public abstract void perform(CommandSender sender, String label, String[] args);

   public boolean execute(CommandSender sender, String commandLabel, String[] args) {
      this.perform(sender, commandLabel, args);
      return true;
   }
}
