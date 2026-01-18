package me.nardodev.utils;

import me.nardodev.utils.cmd.Commands;
import me.nardodev.utils.listeners.Listeners;
import me.nardodev.utils.listeners.world.WorldProtectionListener;
import me.nardodev.utils.managers.Manager;
import me.nardodev.utils.managers.types.ScoreboardManager;
import me.nardodev.utils.managers.types.TabListManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;

public class Main extends JavaPlugin implements PluginMessageListener {
   public static Main instance;
   private boolean validInit;

   public static Main getInstance() {
      return instance;
   }

   @Override
   public void onEnable() {
      instance = this;
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
         getLogger().log(Level.SEVERE, "§c§lERRO -> §cNão foi possível remover os comandos de reload:", ex);
      }

      this.saveDefaultConfig();

      Listeners.setupListeners();
      Manager.setupManager();
      Commands.setupCommands();
      ScoreboardManager.init();

      Bukkit.getPluginManager().registerEvents(new WorldProtectionListener(), this);

      this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
      this.getServer().getMessenger().registerOutgoingPluginChannel(this, "MESSAGE_UTILS");
      this.getServer().getMessenger().registerIncomingPluginChannel(this, "MESSAGE_UTILS", this);
      this.validInit = true;
      Bukkit.getConsoleSender().sendMessage("§a§lSUCESSO -> §aO plugin foi ativado.");
   }

   @Override
   public void onDisable() {
      Bukkit.getConsoleSender().sendMessage("§c§lTCHAU :( -> §cO plugin foi desativado.");
   }

   @Override
   public void onPluginMessageReceived(String channel, Player player, byte[] message) {
   }
}