package redehyzen.escolhendo.utils;

import escolhendo.apexstore.services.plugin.ApexPlugin;
import fusionmc.nardodevutils.cmd.Commands;
import fusionmc.nardodevutils.listeners.Listeners;
import fusionmc.nardodevutils.lobby.UpgradeNPC;
import fusionmc.nardodevutils.managers.Manager;
import fusionmc.nardodevutils.upgrade.Upgrade;

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
      this.saveDefaultConfig();
      Listeners.setupListeners();
      Upgrade.setupUpgrades();
      Manager.setupManager();
      Commands.setupCommands();
      Language.setupLanguage();
      UpgradeNPC.setupNPCs();
      this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
      this.getServer().getMessenger().registerOutgoingPluginChannel(this, "MESSAGE_UTILS");
      this.getServer().getMessenger().registerIncomingPluginChannel(this, "MESSAGE_UTILS", new PluginMessageListener());
      this.validInit = true;
      this.getLogger().info("O plugin foi ativado.");
   }

   public void disable() {
      if (this.validInit) {
         UpgradeNPC.listNPCs().forEach(UpgradeNPC::destroy);
      }

      this.getLogger().info("O plugin foi desativado.");
   }
}
