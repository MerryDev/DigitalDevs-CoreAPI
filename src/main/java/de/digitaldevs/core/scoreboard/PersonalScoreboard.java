package de.digitaldevs.core.scoreboard;

import de.digitaldevs.core.exception.LineTooLongException;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.*;
import java.util.function.Function;

/**
 * @author MerryChrismas
 * @author <a href='https://digitaldevs.de'>DigitalDevs.de</a>
 * @version 1.0.0
 */
public class PersonalScoreboard extends AbstractScoreboard {

    private Function<Player, String> generateTitleFunction;
    private Function<Player, List<String>> generateLinesFunction;
    private final Map<UUID, Scoreboard> playerScoreboard = new HashMap<>();

    public PersonalScoreboard() {
    }

    public PersonalScoreboard(Function<Player, String> generateTitleFunction, Function<Player, List<String>> generateLinesFunction) {
        this.generateTitleFunction = generateTitleFunction;
        this.generateLinesFunction = generateLinesFunction;
    }

    /**
     * Call this when the contents of the scoreboard should change in some way.
     *
     * @throws LineTooLongException Thrown if the scoreboard contains a line over 64 characters
     */
    public void updateScoreboard() throws LineTooLongException {
        if (this.generateLinesFunction == null) return;
        for (UUID playerUUID : this.activePlayers) {
            Player player = Bukkit.getPlayer(playerUUID);
            if (player == null) continue;

            List<String> lines = this.generateLinesFunction.apply(player);
            if (lines == null) lines = new ArrayList<>();

            this.updateScoreboard(player.getScoreboard(), lines);
        }
    }

    /**
     * Add a player to the scoreboard.
     *
     * @param player The player to add
     */
    @Override
    public void addPlayer(Player player) {
        this.activePlayers.add(player.getUniqueId());
        Validate.notNull(Bukkit.getScoreboardManager());
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        player.setScoreboard(scoreboard);
        this.playerScoreboard.put(player.getUniqueId(), scoreboard);
        this.updateScoreboard();
    }

    /**
     * Remove a player from the scoreboard.
     *
     * @param player The player to remove
     */
    @Override
    public void removePlayer(Player player) {
        super.removePlayer(player);
        this.playerScoreboard.remove(player.getUniqueId());
    }

    /**
     * Get the Bukkit Scoreboard for a specific player
     * This will not change in between updates, but will change if the player is removed and re-added to the Scoreboard.
     *
     * @param player The player to get the scoreboard for
     * @return The Bukkit Scoreboard
     */
    public Scoreboard toBukkitScoreboard(Player player) {
        return this.playerScoreboard.get(player.getUniqueId());
    }

    @Override
    protected String getTitle(Scoreboard scoreboard) {
        return this.generateTitleFunction.apply(this.playerForScoreboard(scoreboard));
    }

    private Player playerForScoreboard(Scoreboard scoreboard) {
        if (!this.playerScoreboard.containsValue(scoreboard)) return null;
        Player player = null;
        for (Map.Entry<UUID, Scoreboard> entry : this.playerScoreboard.entrySet()) {
            if (entry.getValue().equals(scoreboard)) {
                player = Bukkit.getPlayer(entry.getKey());
                break;
            }
        }
        return player;
    }

    protected void setGenerateLinesFunction(Function<Player, List<String>> generateLinesFunction) {
        this.generateLinesFunction = generateLinesFunction;
    }

    protected void setGenerateTitleFunction(Function<Player, String> generateTitleFunction) {
        this.generateTitleFunction = generateTitleFunction;
    }

}
