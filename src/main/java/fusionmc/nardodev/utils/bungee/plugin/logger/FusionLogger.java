package fusionmc.nardodev.utils.bungee.plugin.logger;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginLogger;

public class FusionLogger extends PluginLogger {
   private String prefix;
   private Plugin plugin;
   private CommandSender sender;

   public FusionLogger(Plugin plugin) {
      super(plugin);
      this.plugin = plugin;
      this.prefix = "[" + plugin.getDescription().getName() + "] ";
      this.sender = ProxyServer.getInstance().getConsole();
   }

   public FusionLogger(FusionLogger parent, String prefix) {
      super(parent.plugin);
      this.plugin = parent.plugin;
      this.prefix = parent.prefix + prefix;
      this.sender = ProxyServer.getInstance().getConsole();
   }

   public void run(Level level, String method, Runnable runnable) {
      try {
         runnable.run();
      } catch (Exception var5) {
         this.log(level, method.replace("${n}", this.plugin.getDescription().getName()).replace("${v}", this.plugin.getDescription().getVersion()), var5);
      }

   }

   public void log(LogRecord logRecord) {
      FusionLogger.LLevel level = FusionLogger.LLevel.fromName(logRecord.getLevel().getName());
      if (level != null) {
         String message = logRecord.getMessage();
         if (!message.equals("Default system encoding may have misread config.yml from plugin jar")) {
            StringBuilder result = new StringBuilder(this.prefix + message);
            if (logRecord.getThrown() != null) {
               result.append("\n").append(logRecord.getThrown().getLocalizedMessage());
               StackTraceElement[] var5 = logRecord.getThrown().getStackTrace();
               int var6 = var5.length;

               for(int var7 = 0; var7 < var6; ++var7) {
                  StackTraceElement ste = var5[var7];
                  result.append("\n").append(ste.toString());
               }
            }

            this.sender.sendMessage(TextComponent.fromLegacyText(level.format(result.toString())));
         }
      }
   }

   public FusionLogger getModule(String module) {
      return new FusionLogger(this, module + ": ");
   }

   private static enum LLevel {
      INFO("§a"),
      WARNING("§e"),
      SEVERE("§c");

      private String color;

      private LLevel(String color) {
         this.color = color;
      }

      public String format(String message) {
         return this.color + message;
      }

      public static FusionLogger.LLevel fromName(String name) {
         FusionLogger.LLevel[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            FusionLogger.LLevel level = var1[var3];
            if (level.name().equalsIgnoreCase(name)) {
               return level;
            }
         }

         return null;
      }
   }
}
