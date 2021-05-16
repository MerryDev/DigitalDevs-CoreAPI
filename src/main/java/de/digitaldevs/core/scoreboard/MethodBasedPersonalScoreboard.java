package de.digitaldevs.core.scoreboard;

import org.bukkit.entity.Player;

import java.util.*;

/**
 * @author MerryChrismas
 * @author <a href='https://digitaldevs.de'>DigitalDevs.de</a>
 * @version 1.0.0
 */
public class MethodBasedPersonalScoreboard extends PersonalScoreboard {

    private final Map<UUID, String> playerToTitle = new HashMap<>();
    private final Map<UUID, List<String>> playerToLines = new HashMap<>();

    public MethodBasedPersonalScoreboard() {
        this.setGenerateTitleFunction(this::getTitle);
        this.setGenerateLinesFunction(this::getLines);
    }

    private String getTitle(Player player) {
        return this.playerToTitle.getOrDefault(player.getUniqueId(), "");
    }

    public void setTitle(Player player, String title) {
        this.playerToTitle.put(player.getUniqueId(), title);
        this.updateScoreboard();
    }

    private List<String> getLines(Player player) {
        return this.playerToLines.get(player.getUniqueId());
    }

    public void setLines(Player player, List<String> lines) {
        this.playerToLines.put(player.getUniqueId(), lines);
        this.updateScoreboard();
    }

    public void setLines(Player player, String... lines) {
        this.playerToLines.put(player.getUniqueId(), Arrays.asList(lines));
        this.updateScoreboard();
    }

}
