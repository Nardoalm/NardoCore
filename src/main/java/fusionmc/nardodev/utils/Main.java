package fusionmc.nardodev.utils;

import escolhendo.apexstore.services.libraries.MinecraftVersion;
import escolhendo.apexstore.services.listeners.PluginMessageListener;
import escolhendo.apexstore.services.nms.NMS;
import escolhendo.apexstore.services.plugin.ApexPlugin;
import fusionmc.nardodev.utils.cmd.Commands;
import fusionmc.nardodev.utils.listeners.Listeners;
import fusionmc.nardodev.utils.managers.Manager;
import fusionmc.nardodev.utils.upgrade.Upgrade;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;


public class Main extends ApexPlugin {
   public static Main instance;
   private boolean validInit;

   public static Main getInstance() {
      return instance;
   }

   public void start() {
      instance = this;
   }

   public void load() {
   }

   public void enable() {
      if (!NMS.setupNMS()) {
         setEnabled(false);
         getLogger().warning("O plugin apenas funciona na versão 1_8_R3 (Atual: " +
                 MinecraftVersion.getCurrentVersion().getVersion() + ")");
         return;
      }

      try {
         SimpleCommandMap commandMap = (SimpleCommandMap)
                 Bukkit.getServer().getClass().getDeclaredMethod("getCommandMap").invoke(Bukkit.getServer());
         Field field = commandMap.getClass().getDeclaredField("knownCommands");
         field.setAccessible(true);
         Map<String, Command> knownCommands = (Map<String, Command>) field.get(commandMap);
         for (String cmd : Arrays.asList("rl", "reload", "bukkit:rl", "bukkit:reload")) {
            knownCommands.remove(cmd);
         }
      } catch (ReflectiveOperationException ex) {
         getLogger().log(Level.SEVERE, "Não foi possível remover os comandos de reload:", ex);
      }

      this.saveDefaultConfig();
      Listeners.setupListeners();
      Upgrade.setupUpgrades();
      Manager.setupManager();
      Commands.setupCommands();
      Language.setupLanguage();
      this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
      this.getServer().getMessenger().registerOutgoingPluginChannel(this, "MESSAGE_UTILS");
      this.getServer().getMessenger().registerIncomingPluginChannel(this, "MESSAGE_UTILS", new PluginMessageListener());
      this.validInit = true;
      this.getLogger().info(ChatColor.GREEN + "O plugin foi ativado.");
   }

   public void disable() {
      this.getLogger().info(ChatColor.RED +"O plugin foi desativado.");
   }
}
