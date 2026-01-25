package me.nardodev.lobby;

import me.nardodev.lobby.cmd.Commands;
import me.nardodev.lobby.listeners.Listeners;
import me.nardodev.lobby.listeners.world.WorldProtectionListener;
import me.nardodev.lobby.managers.Manager;
import me.nardodev.lobby.managers.types.ScoreboardManager;
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