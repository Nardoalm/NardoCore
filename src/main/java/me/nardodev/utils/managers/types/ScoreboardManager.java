package me.nardodev.utils.managers.types;

import me.clip.placeholderapi.PlaceholderAPI;
import me.nardodev.utils.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreboardManager {

    private static final Map<UUID, Scoreboard> BOARDS = new HashMap<>();
    private static int index = 0;
    private static final String[] ANIMATION = {
            "§b§lFUSION", "§f§lFUSION", "§3§lFUSION", "§9§lFUSION",
            "§1§lFUSION", "§9§lFUSION", "§3§lFUSION", "§f§lFUSION"
    };

    public static void init() {
        new BukkitRunnable() {
            @Override
            public void run() {
                index++;
                if (index >= ANIMATION.length) index = 0;

                for (Player player : Bukkit.getOnlinePlayers()) {
                    update(player);
                }
            }
        }.runTaskTimer(Main.getInstance(), 0L, 3L);
    }

    public static void update(Player player) {
        Scoreboard sb = BOARDS.get(player.getUniqueId());
        Objective obj;

        if (sb == null) {
            sb = Bukkit.getScoreboardManager().getNewScoreboard();
            obj = sb.registerNewObjective("sidebar", "dummy");
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);
            setupTeams(sb, obj);
            BOARDS.put(player.getUniqueId(), sb);
        } else {
            obj = sb.getObjective("sidebar");
        }

        if (player.getScoreboard() != sb) {
            player.setScoreboard(sb);
        }

        if (obj != null) {
            obj.setDisplayName(ANIMATION[index]);
            String tag = PlaceholderAPI.setPlaceholders(
                    player,
                    "%leaftags_tag_prefix% "
            );
            updateLine(sb, "line6", "§fNick: §7", player.getName());
            updateLine(sb, "line4", "§fRank: §7", tag); // Ideal puxar do PermissionsEx/LuckPerms
            updateLine(sb, "line2", "§fOnline: §a", String.valueOf(Bukkit.getOnlinePlayers().size()));
        }
    }

    private static void setupTeams(Scoreboard sb, Objective obj) {
        createTeamLine(sb, obj, "line7", "§1", 7); // Espaço
        createTeamLine(sb, obj, "line6", "§2", 6); // Nick
        createTeamLine(sb, obj, "line5", "§3", 5); // Espaço
        createTeamLine(sb, obj, "line4", "§4", 4); // Rank
        createTeamLine(sb, obj, "line3", "§5", 3); // Espaço
        createTeamLine(sb, obj, "line2", "§6", 2); // Online
        createTeamLine(sb, obj, "line1", "§7", 1); // Espaço

        obj.getScore("§afusionmc.com.br").setScore(0);
    }

    private static void createTeamLine(Scoreboard sb, Objective obj, String teamName, String entry, int score) {
        Team t = sb.registerNewTeam(teamName);
        t.addEntry(entry);
        obj.getScore(entry).setScore(score);
    }

    private static void updateLine(Scoreboard sb, String teamName, String prefix, String suffix) {
        Team t = sb.getTeam(teamName);
        if (t == null) return;

        if (prefix.length() > 16) prefix = prefix.substring(0, 16);
        if (suffix.length() > 16) suffix = suffix.substring(0, 16);

        if (!t.getPrefix().equals(prefix)) t.setPrefix(prefix);
        if (!t.getSuffix().equals(suffix)) t.setSuffix(suffix);
    }

    public static void remove(Player player) {
        BOARDS.remove(player.getUniqueId());
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
    }
}