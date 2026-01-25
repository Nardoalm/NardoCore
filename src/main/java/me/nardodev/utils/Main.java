package me.nardodev.utils;

import me.nardodev.utils.cmd.Commands;
import me.nardodev.utils.listeners.Listeners;
import me.nardodev.utils.listeners.world.WorldProtectionListener;
import me.nardodev.utils.managers.Manager;
import me.nardodev.utils.managers.types.ScoreboardManager;
import me.nardodev.utils.mikoImports.welcomeMessages.WMConfig;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
   private static Main instance;

   public static Main getInstance() {
      return instance;
   }

   @Override
   public void onEnable() {
      WorldProtectionListener.setPvp(false);
      instance = this;
      saveDefaultConfig();

      WMConfig.init(this);

      Listeners.setupListeners();
      Manager.setupManager();
      Commands.setupCommands();
      ScoreboardManager.init();

      Bukkit.getPluginManager().registerEvents(new WorldProtectionListener(), this);

      getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

      Bukkit.getScheduler().runTask(this, () -> {
         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule doDaylightCycle false");
         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule doWeatherCycle false");

         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "time set 6000");
         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "weather clear");

         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule sendCommandFeedback false");

         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule doMobSpawning false");
         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kill @e[type=!player]");
      });

      Bukkit.getConsoleSender().sendMessage("§a§lSUCESSO -> §aO plugin foi ativado.");
   }

   @Override
   public void onDisable() {
      Bukkit.getConsoleSender().sendMessage("§c§lTCHAU :( -> §cO plugin foi desativado.");
   }
}