package fusionmc.nardodev.utils.scoreboard;

import fusionmc.nardodev.utils.scoreboard.byMiko.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Team;

/**
 * Representa uma linha (team) customizada no scoreboard.
 * <p>
 * Autor original: Miko
 * Refatorado por: Java 🔨🤖🔧
 */
public class VirtualTeam {

    private static final String RESET_CODE = "§r";

    private FusionScoreboard instance;
    private String name;
    private String prefix = "";
    private String entry;
    private String suffix = "";
    private int line;

    protected VirtualTeam(FusionScoreboard instance, String name, int line) {
        this.instance = instance;
        this.name = name;
        this.line = line;
    }

    public void destroy() {
        if (instance == null || instance.getScoreboard() == null) return;

        instance.getScoreboard().resetScores(entry);
        Team team = instance.getScoreboard().getTeam(name);
        if (team != null) {
            team.unregister();
        }

        clearFields();
    }

    public void update() {
        if (instance == null || instance.getScoreboard() == null) return;

        Team team = getOrCreateTeam();
        if (team == null) return;

        team.setPrefix(prefix);
        if (!team.hasEntry(entry)) {
            team.addEntry(entry);
        }

        team.setSuffix(suffix);
        instance.getObjective().getScore(entry).setScore(line);
    }

    public void setValue(String text) {
        if (text == null) text = "";

        text = StringUtils.translateAlternateColorCodes('&', text);
        if (text.length() > 32) {
            text = text.substring(0, 29) + "...";
        }

        this.entry = ChatColor.values()[line - 1] + RESET_CODE;

        this.prefix = extractPrefix(text);
        this.suffix = extractSuffix(text, prefix);
    }

    private Team getOrCreateTeam() {
        Team team = instance.getScoreboard().getTeam(name);
        if (team != null) return team;

        try {
            return instance.getScoreboard().registerNewTeam(name);
        } catch (IllegalArgumentException ignored) {
            return instance.getScoreboard().getTeam(name);
        }
    }

    private String extractPrefix(String text) {
        String result = text.substring(0, Math.min(16, text.length()));
        if (result.endsWith("§") && result.length() == 16) {
            return result.substring(0, 15);
        }
        return result;
    }

    private String extractSuffix(String text, String prefix) {
        String remaining = text.substring(Math.min(text.length(), prefix.length()));
        String coloredSuffix = StringUtils.getLastColor(prefix) + remaining;
        String result = coloredSuffix.substring(0, Math.min(16, coloredSuffix.length()));

        if (result.endsWith("§")) {
            return result.substring(0, result.length() - 1);
        }

        return result;
    }

    private void clearFields() {
        this.instance = null;
        this.name = null;
        this.prefix = null;
        this.entry = null;
        this.suffix = null;
        this.line = -1;
    }
}