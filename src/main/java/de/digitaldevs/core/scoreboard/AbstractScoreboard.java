package de.digitaldevs.core.scoreboard;

import de.digitaldevs.core.exception.DuplicateTeamException;
import de.digitaldevs.core.exception.LineTooLongException;
import de.digitaldevs.core.exception.TeamNameTooLongException;
import lombok.Getter;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

/**
 * @author MerryChrismas
 * @author <a href='https://digitaldevs.de'>DigitalDevs.de</a>
 * @version 1.0.0
 */
public abstract class AbstractScoreboard {

    @Getter
    private final List<ScoreboardTeam> teams = new ArrayList<>();
    protected List<UUID> activePlayers = new ArrayList<>();
    private final Map<Scoreboard, List<String>> previousLines = new HashMap<>();

    /**
     * Add a player to the scoreboard
     *
     * @param player The player to add
     */
    public void addPlayer(Player player) {
        this.activePlayers.add(player.getUniqueId());
    }

    /**
     * Remove the player from the Scoreboard, and remove any teams they may be a member of.
     * This will reset their scoreboard to the main scoreboard.
     *
     * @param player The player to remove
     */
    public void removePlayer(Player player) {
        this.activePlayers.remove(player.getUniqueId());
        Validate.notNull(Bukkit.getScoreboardManager());
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());

        teams.forEach(team -> {
            if (team.isOnTeam(player.getUniqueId())) team.removePlayer(player);
        });
    }

    /**
     * Find a team using a name
     *
     * @param name The name to search for. Color codes will be stripped from both the team name and this variable.
     * @return The JScoreboardPlayerTeam found, if any. Will return null if no team exists
     */
    public Optional<ScoreboardTeam> findTeam(String name) {
        return teams.stream()
                .filter(team -> ChatColor.stripColor(team.getName()).equalsIgnoreCase(ChatColor.stripColor(name)))
                .findAny();
    }

    /**
     * Create a team on the scoreboard.
     *
     * @param name        The name for the new team. This name cannot be longer than 16 characters
     * @param displayName The name wich should be displayed
     * @return The created JScoreboardPlayerTeam
     * @throws DuplicateTeamException   If a team with that name already exists
     * @throws TeamNameTooLongException If the team's name is longer than 16 characters
     */
    public ScoreboardTeam createTeam(String name, String displayName) throws DuplicateTeamException, TeamNameTooLongException {
        for (ScoreboardTeam team : this.teams) {
            if (ChatColor.stripColor(team.getName()).equalsIgnoreCase(ChatColor.stripColor(name))) {
                throw new DuplicateTeamException(name);
            }
        }

        if (name.length() > 16) throw new TeamNameTooLongException(name);

        ScoreboardTeam team = new ScoreboardTeam(name, displayName, this);
        team.refresh();
        this.teams.add(team);
        return team;
    }

    /**
     * Remove a team from the scoreboard
     *
     * @param team The team to remove from the scoreboard
     */
    public void removeTeam(ScoreboardTeam team) {
        if (team.getScoreboard() != this) return;
        team.destroy();
        this.teams.remove(team);
    }

    /**
     * Destroy the scoreboard. This will reset all players to the server's main scoreboard, and clear all teams.
     * This method should be called inside the {@code onDisable()}-Method.
     */
    public void destroy() {
        for (UUID playerUUID : activePlayers) {
            Player player = Bukkit.getPlayer(playerUUID);
            if (player != null) {
                Validate.notNull(Bukkit.getScoreboardManager());
                player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
            }
        }
        for (ScoreboardTeam team : teams) team.destroy();
        this.activePlayers.clear();
        this.teams.clear();
    }

    /**
     * Update a scoreboard with a list of lines
     * These lines must be in reverse order!
     *
     * @param scoreboard The scoreboard which should be updated.
     * @param lines      The lines which the scoreboard should display.
     * @throws LineTooLongException If a String within the lines array is over 64 characters, this exception is thrown.
     */
    protected void updateScoreboard(Scoreboard scoreboard, List<String> lines) throws LineTooLongException {
        Objective objective;

        if (scoreboard.getObjective("dummy") == null) objective = scoreboard.registerNewObjective("dummy", "dummy");
        else objective = scoreboard.getObjective("dummy");
        Validate.notNull(objective);

        String title = this.getTitle(scoreboard);
        if (title == null) title = "";
        objective.setDisplayName(this.color(title));

        if (this.previousLines.containsKey(scoreboard)) {
            if (this.previousLines.get(scoreboard).equals(lines)) {
                this.updateTeams(scoreboard);
                return;
            }

            if (this.previousLines.get(scoreboard).size() != lines.size()) {
                scoreboard.clearSlot(DisplaySlot.SIDEBAR);
                scoreboard.getEntries().forEach(scoreboard::resetScores);
                scoreboard.getTeams().forEach(team -> {
                    if (team.getName().contains("line")) team.unregister();
                });
            }
        }

        this.previousLines.put(scoreboard, lines);

        List<String> reversedLines = new ArrayList<>(lines);
        Collections.reverse(reversedLines);

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        List<String> colorCodeOptions = this.colorOptions(reversedLines.size());
        int score = 1;
        for (String entry : reversedLines) {
            if (entry.length() > 64) {
                throw new LineTooLongException("The line is too long! Only 64 characters are supported!");
            }
            Team team = scoreboard.getTeam("line" + score);
            if (team != null) {
                team.setPrefix(this.color(entry));
                team.getEntries().forEach(team::removeEntry);
                team.addEntry(colorCodeOptions.get(score));
            } else {
                team = scoreboard.registerNewTeam("line" + score);
                team.addEntry(colorCodeOptions.get(score));
                team.setPrefix(this.color(entry));
                objective.getScore(colorCodeOptions.get(score)).setScore(score);
            }
            score++;
        }

        this.updateTeams(scoreboard);
    }

    /**
     * Update the teams on the scoreboard. Loops over all teams and calls refresh(Scoreboard)
     *
     * @param scoreboard The Bukkit scoreboard to use
     */
    private void updateTeams(Scoreboard scoreboard) {
        this.teams.forEach(team -> team.refresh(scoreboard));
    }

    /**
     * Generate a list of unique color code combinations to use as scoreboard entries.
     * This is done to ensure that...
     * 1. Duplicate lines can be created
     * 2. The content of a scoreboard line is stored in the team prefix + suffix, rather than the entry itself
     *
     * @param amountOfLines The amount of lines, and by proxy the amount of color combinations, to be generated
     * @return The list of unique color combinations
     */
    private List<String> colorOptions(int amountOfLines) {
        List<String> colorCodeOptions = new ArrayList<>();
        for (ChatColor color : ChatColor.values()) {
            if (color.isFormat()) continue;
            for (ChatColor secondColor : ChatColor.values()) {
                if (secondColor.isFormat()) continue;
                String option = color + " " + secondColor;
                if (color != secondColor && !colorCodeOptions.contains(option)) {
                    colorCodeOptions.add(option);
                    if (colorCodeOptions.size() == amountOfLines) break;
                }
            }
        }
        return colorCodeOptions;
    }

    /**
     * Translate a String into a color formatted String. Uses the ampersand character as the special old char.
     *
     * @param string The string to translate
     * @return The formatted String
     */
    protected String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    /**
     * Get the title for a particular Scoreboard
     *
     * @param scoreboard The Bukkit scoreboard that requires a title
     * @return The title String
     */
    protected abstract String getTitle(Scoreboard scoreboard);

}
