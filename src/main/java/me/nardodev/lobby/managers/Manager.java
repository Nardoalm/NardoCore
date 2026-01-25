package me.nardodev.lobby.managers;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import me.nardodev.lobby.Main;
import org.bukkit.plugin.java.JavaPlugin;

public class Manager {

   public static List<Player> USING_VANISH = new ArrayList<>();
   private static String version = "";

   public static boolean isVanished(Player player) {
      return USING_VANISH.contains(player);
   }

   public static void addVanish(Player player) {
      USING_VANISH.add(player);
      updateVanishState(player);
   }

   public static void removeVanish(Player player) {
      USING_VANISH.remove(player);
      for (Player online : Bukkit.getOnlinePlayers()) {
         online.showPlayer(player);
      }
   }

   public static void setupManager() {
      try {
         version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
      } catch (Exception e) {
         version = "v1_8_R3";
      }

      new BukkitRunnable() {
         @Override
         public void run() {
            for (Player player : USING_VANISH) {
               sendActionBar(player, "§a§lVANISH ➔ §aVocê está invisível!");

               updateVanishState(player);
            }
         }
      }.runTaskTimer(JavaPlugin.getPlugin(Main.class), 0L, 20L);
   }

   private static void updateVanishState(Player player) {
      for (Player online : Bukkit.getOnlinePlayers()) {
         if (!online.hasPermission("nardocore.staff.vanish")) {
            online.hidePlayer(player);
         }
      }
   }


   private static void sendActionBar(Player player, String message) {
      try {
         Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
         Class<?> packetPlayOutChatClass = Class.forName("net.minecraft.server." + version + ".PacketPlayOutChat");
         Class<?> iChatBaseComponentClass = Class.forName("net.minecraft.server." + version + ".IChatBaseComponent");
         Class<?> chatComponentTextClass = Class.forName("net.minecraft.server." + version + ".ChatComponentText");

         Object craftPlayer = craftPlayerClass.cast(player);
         Object handle = craftPlayerClass.getMethod("getHandle").invoke(craftPlayer);
         Object playerConnection = handle.getClass().getField("playerConnection").get(handle);

         Object chatComponent = chatComponentTextClass.getConstructor(String.class).newInstance(message);

         Constructor<?> packetConstructor = packetPlayOutChatClass.getConstructor(iChatBaseComponentClass, byte.class);
         Object packet = packetConstructor.newInstance(chatComponent, (byte) 2);

         Method sendPacket = playerConnection.getClass().getMethod("sendPacket", Class.forName("net.minecraft.server." + version + ".Packet"));
         sendPacket.invoke(playerConnection, packet);

      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}