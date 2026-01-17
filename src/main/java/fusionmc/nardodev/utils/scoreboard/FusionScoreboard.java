package fusionmc.nardodev.utils.scoreboard;

import fusionmc.nardodev.utils.scoreboard.scroller.ScoreboardScroller;
import fusionmc.nardodev.utils.scoreboard.byMiko.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

/**
 * Classe abstrata para gerenciamento de Scoreboards customizadas por jogador.
 * <p>
 * Autor original: Miko
 * Refatorado por: Java 🔨🤖🔧
 */
public abstract class FusionScoreboard {

    private final VirtualTeam[] teams = new VirtualTeam[15];
    private Player player;
    private Objective objective;
    private Scoreboard scoreboard;
    private ScoreboardScroller scroller;
    private String display;
    private boolean healthEnabled;
    private boolean healthTabEnabled;

    public FusionScoreboard() {
    }

    public void scroll() {
        if (scroller != null) {
            display(scroller.next());
        }
    }

    public void update() {
        // Método sobrescrito por subclasses
    }

    public void updateHealth() {
        if (!healthEnabled && !healthTabEnabled || scoreboard == null) return;

        for (Player target : Bukkit.getOnlinePlayers()) {
            int health = (int) target.getHealth();

            if (healthTabEnabled) {
                Objective healthTab = scoreboard.getObjective("healthPL");
                if (healthTab != null) {
                    healthTab.getScore(target.getName()).setScore(health);
                }
            }

            if (healthEnabled) {
                Objective healthBelow = scoreboard.getObjective("healthBN");
                if (healthBelow != null && healthBelow.getScore(target.getName()).getScore() == 0) {
                    healthBelow.getScore(target.getName()).setScore(health);
                }
            }
        }
    }

    public FusionScoreboard add(int line) {
        return add(line, "");
    }

    public FusionScoreboard add(int line, String name) {
        if (!isValidLine(line)) return this;

        VirtualTeam team = getOrCreate(line);
        team.setValue(name);
        if (scoreboard != null) team.update();

        return this;
    }

    public FusionScoreboard remove(int line) {
        if (!isValidLine(line)) return this;

        VirtualTeam team = teams[line - 1];
        if (team != null) {
            team.destroy();
            teams[line - 1] = null;
        }

        return this;
    }

    public FusionScoreboard to(Player player) {
        Player last = this.player;
        this.player = player;

        if (scoreboard != null) {
            if (last != null) last.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
            player.setScoreboard(scoreboard);
        }

        return this;
    }

    public FusionScoreboard display(String display) {
        this.display = StringUtils.translateAlternateColorCodes('&', display);
        if (objective != null) {
            objective.setDisplayName(this.display.substring(0, Math.min(this.display.length(), 32)));
        }

        return this;
    }

    public FusionScoreboard scroller(ScoreboardScroller scroller) {
        this.scroller = scroller;
        return this;
    }

    public FusionScoreboard health() {
        this.healthEnabled = !healthEnabled;
        toggleObjective("healthBN", DisplaySlot.BELOW_NAME, "health", "§c❤", healthEnabled);
        return this;
    }

    public FusionScoreboard healthTab() {
        this.healthTabEnabled = !healthTabEnabled;
        toggleObjective("healthPL", DisplaySlot.PLAYER_LIST, "dummy", null, healthTabEnabled);
        return this;
    }

    public FusionScoreboard build() {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective(getObjectiveName(), "dummy");
        this.objective.setDisplayName(display == null ? "" : display.substring(0, Math.min(display.length(), 32)));
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        if (player != null) player.setScoreboard(scoreboard);

        if (healthEnabled) {
            registerObjective("healthBN", "health", DisplaySlot.BELOW_NAME, "§c❤");
        }

        if (healthTabEnabled) {
            registerObjective("healthPL", "dummy", DisplaySlot.PLAYER_LIST, null);
        }

        for (VirtualTeam team : teams) {
            if (team != null) team.update();
        }

        return this;
    }

    public void destroy() {
        if (objective != null) objective.unregister();
        if (healthEnabled) removeObjective("healthBN");
        if (healthTabEnabled) removeObjective("healthPL");

        this.objective = null;
        this.scoreboard = null;
        this.player = null;
        this.display = null;
    }

    public VirtualTeam getTeam(int line) {
        return isValidLine(line) ? teams[line - 1] : null;
    }

    public VirtualTeam getOrCreate(int line) {
        if (!isValidLine(line)) return null;

        if (teams[line - 1] == null) {
            teams[line - 1] = new VirtualTeam(this, "score[" + line + "]", line);
        }

        return teams[line - 1];
    }

    public String getObjectiveName() {
        return "mScoreboard";
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public Objective getObjective() {
        return objective;
    }

    // ========== MÉTODOS PRIVADOS ==========

    private boolean isValidLine(int line) {
        return line >= 1 && line <= 15;
    }

    private void toggleObjective(String name, DisplaySlot slot, String criteria, String displayName, boolean enable) {
        if (scoreboard == null) return;

        if (!enable) {
            removeObjective(name);
        } else {
            registerObjective(name, criteria, slot, displayName);
        }
    }

    private void registerObjective(String name, String criteria, DisplaySlot slot, String displayName) {
        Objective obj = scoreboard.registerNewObjective(name, criteria);
        if (displayName != null) obj.setDisplayName(displayName);
        obj.setDisplaySlot(slot);
    }

    private void removeObjective(String name) {
        Objective obj = scoreboard.getObjective(name);
        if (obj != null) obj.unregister();
    }
}