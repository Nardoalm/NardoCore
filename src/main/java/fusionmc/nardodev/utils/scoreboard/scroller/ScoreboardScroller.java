package fusionmc.nardodev.utils.scoreboard.scroller;

import java.util.Collections;
import java.util.List;

/**
 * Classe que gerencia a rotação de strings (frames) para animação de Scoreboard.
 * Ideal para mensagens animadas que ciclam ao longo do tempo.
 */
public class ScoreboardScroller {

    private final List<String> frames;
    private int index;

    public ScoreboardScroller(List<String> frames) {
        if (frames == null || frames.isEmpty()) {
            throw new IllegalArgumentException("A lista de frames não pode ser nula ou vazia.");
        }

        this.frames = Collections.unmodifiableList(frames);
        this.index = -1;
    }

    public String next() {
        index = (index + 1) % frames.size();
        return frames.get(index);
    }

    public String current() {
        if (index == -1) return frames.get(0);
        return frames.get(index);
    }

    public void reset() {
        this.index = -1;
    }

    public int size() {
        return frames.size();
    }
}