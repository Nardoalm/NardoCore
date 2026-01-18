package me.nardodev.utils.managers.types;

import me.nardodev.utils.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardManager {

    private static int index = 0;
    private static final String[] ANIMATION = {
            "§b§lFUSION",
            "§f§lFUSION",
            "§3§lFUSION",
            "§9§lFUSION",
            "§1§lFUSION",
            "§9§lFUSION",
            "§3§lFUSION",
            "§f§lFUSION"
    };

    public static void init() {
        new BukkitRunnable() {
            @Override
            public void run() {
                index++;
                if (index >= ANIMATION.length) {
                    index = 0;
                }
                for (Player player : Bukkit.getOnlinePlayers()) {
                    update(player);
                }
            }
        }.runTaskTimer(Main.getInstance(), 0L, 5L);
    }

    public static void update(Player player) {
        Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = sb.registerNewObjective("sidebar", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(ANIMATION[index]);

        obj.getScore("§1").setScore(7);
        obj.getScore("§fNick: §7" + player.getName()).setScore(6);
        obj.getScore("§2").setScore(5);
        obj.getScore("§fRank: §7Membro").setScore(4);
        obj.getScore("§3").setScore(3);
        obj.getScore("§fOnline: §a" + Bukkit.getOnlinePlayers().size()).setScore(2);
        obj.getScore("§4").setScore(1);
        obj.getScore("§ewww.fusionmc.com.br").setScore(0);

        player.setScoreboard(sb);
    }
}