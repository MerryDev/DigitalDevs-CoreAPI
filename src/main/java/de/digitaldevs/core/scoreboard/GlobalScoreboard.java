package de.digitaldevs.core.scoreboard;

import de.digitaldevs.core.exception.LineTooLongException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * @author MerryChrismas
 * @author <a href='https://digitaldevs.de'>DigitalDevs.de</a>
 * @version 1.0.0
 */
public class GlobalScoreboard extends AbstractScoreboard {

    private Supplier<String> titleSupplier;
    private Supplier<List<String>> linesSupplier;
    private Scoreboard scoreboard;

    public GlobalScoreboard() {
    }

    public GlobalScoreboard(Supplier<String> titleSupplier, Supplier<List<String>> linesSupplier) {
        this.titleSupplier = titleSupplier;
        this.linesSupplier = linesSupplier;
    }

    /**
     * Update the scoreboard. Call this when the scoreboard contents should change in some way.
     *
     * @throws LineTooLongException Thrown if a line on the scoreboard is over 64 characters
     */
    public void updateScoreboard() throws LineTooLongException {
        this.createBukkitScoreboardIfNull();
        if (this.linesSupplier != null) updateScoreboard(toBukkitScoreboard(), this.linesSupplier.get());
    }

    /**
     * Add a player to the scoreboard
     *
     * @param player The player to add
     */
    @Override
    public void addPlayer(Player player) {
        super.addPlayer(player);
        this.createBukkitScoreboardIfNull();
        player.setScoreboard(this.scoreboard);
    }

    /**
     * Get the Bukkit Scoreboard that backs this GlobalScoreboard.
     *
     * @return The Bukkit Scoreboard
     */
    public Scoreboard toBukkitScoreboard() {
        return this.scoreboard;
    }

    /**
     * Creates the Bukkit Scoreboard for this scoreboard to use
     */
    private void createBukkitScoreboardIfNull() {
        if (this.scoreboard != null) return;

        ScoreboardManager scoreboardManager = Bukkit.getServer().getScoreboardManager();
        if (scoreboardManager == null) return;

        this.scoreboard = scoreboardManager.getNewScoreboard();
        for (UUID playerUUID : this.activePlayers) {
            Player player = Bukkit.getPlayer(playerUUID);
            if (player != null) player.setScoreboard(this.scoreboard);
        }
    }

    protected void setLinesSupplier(Supplier<List<String>> linesSupplier) {
        this.linesSupplier = linesSupplier;
    }

    protected void setTitleSupplier(Supplier<String> titleSupplier) {
        this.titleSupplier = titleSupplier;
    }

    @Override
    protected String getTitle(Scoreboard scoreboard) {
        return this.titleSupplier.get();
    }
}
