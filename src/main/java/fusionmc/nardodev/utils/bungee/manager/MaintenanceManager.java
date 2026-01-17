package fusionmc.nardodev.utils.bungee.manager;

import java.util.ArrayList;
import java.util.List;
import fusionmc.nardodev.utils.bungee.plugin.config.FusionConfig;

public class MaintenanceManager {
   private static List<String> PLAYERS;
   private static List<String> SERVERS;
   public static final FusionConfig CONFIG = FusionConfig.getConfig("config");
   public static final FusionConfig WHITELIST = FusionConfig.getConfig("whitelisteds");

   public static void setupMaintenance() {
      PLAYERS = new ArrayList();
      SERVERS = new ArrayList();
      PLAYERS.addAll(WHITELIST.getStringList("playerNames"));
      SERVERS.addAll(CONFIG.getStringList("maintenance"));
   }

   public static void removePlayer(String player) {
      List<String> players = WHITELIST.getStringList("playerNames");
      players.remove(player);
      WHITELIST.set("playerNames", players);
      PLAYERS.remove(player);
   }

   public static void addPlayer(String player) {
      List<String> players = WHITELIST.getStringList("playerNames");
      players.add(player);
      WHITELIST.set("playerNames", players);
      PLAYERS.add(player);
   }

   public static List<String> getPlayers() {
      return PLAYERS;
   }

   public static List<String> getServers() {
      return SERVERS;
   }
}
