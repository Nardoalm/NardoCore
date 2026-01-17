package fusionmc.nardodev.utils.bungee.commands;

import escolhendo.apexstore.services.bungee.Bungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import fusionmc.nardodev.utils.bungee.commands.uTils.ChatVipCommand;
import fusionmc.nardodev.utils.bungee.commands.uTils.DivulgarCommand;
import fusionmc.nardodev.utils.bungee.commands.uTils.OnlineCommand;
import fusionmc.nardodev.utils.bungee.commands.uTils.ReplyCommand;
import fusionmc.nardodev.utils.bungee.commands.uTils.StaffChatCommand;
import fusionmc.nardodev.utils.bungee.commands.uTils.TellCommand;
import fusionmc.nardodev.utils.bungee.commands.uTils.WarningCommand;
import fusionmc.nardodev.utils.bungee.commands.uTils.WhitelistCommand;

public abstract class Commands extends Command {
   public Commands(String name, String... aliases) {
      super(name, (String)null, aliases);
      ProxyServer.getInstance().getPluginManager().registerCommand(Bungee.getInstance(), this);
   }

   public abstract void perform(CommandSender var1, String[] var2);

   public void execute(CommandSender sender, String[] args) {
      this.perform(sender, args);
   }

   public static void setupCommands() {
      new WarningCommand();
      new StaffChatCommand();
      new OnlineCommand();
      new TellCommand();
      new ReplyCommand();
      new WhitelistCommand();
      new ChatVipCommand();
      new DivulgarCommand();
   }
}
