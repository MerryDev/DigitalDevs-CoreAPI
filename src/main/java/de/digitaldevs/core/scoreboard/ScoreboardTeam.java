package de.digitaldevs.core.scoreboard;

import de.digitaldevs.core.exception.TeamNameTooLongException;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author MerryChrismas
 * @author <a href='https://digitaldevs.de'>DigitalDevs.de</a>
 * @version 1.0.0
 */
public class ScoreboardTeam {

    @Getter private String name;
    @Getter private String displayName;
    @Getter private final List<UUID> entities = new ArrayList<>();
    @Getter private final AbstractScoreboard scoreboard;

    protected ScoreboardTeam(String name, String displayName,  AbstractScoreboard scoreboard) {
        this.name = name;
        this.displayName = displayName;
        this.scoreboard = scoreboard;
    }

    public void setName(String name) {
        if (name.length() > 16) {
            throw new TeamNameTooLongException("The name '" + name + "' is longer than 16 characters!");
        }
        this.name = name;
        refresh();
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
        this.refresh();
    }

    public void refresh() {
        if (this.scoreboard instanceof PersonalScoreboard) {
            for (UUID uuid : this.entities) {
                Player player = Bukkit.getPlayer(uuid);
                if (player != null) this.refresh(player.getScoreboard());
            }
        } else this.refresh(((GlobalScoreboard) this.scoreboard).toBukkitScoreboard());
    }

    public void refresh(Scoreboard scoreboard) {
        Team team = this.toBukkitTeam(scoreboard);
        if (team == null) return;
        for (UUID playerUUID : this.entities) {
            Player player = Bukkit.getPlayer(playerUUID);
            if ((player != null) && !team.hasEntry(player.getName())) team.addEntry(player.getName());
        }

        team.setPrefix(ChatColor.translateAlternateColorCodes('&', this.getDisplayName()));
    }

    public Team toBukkitTeam(Scoreboard bukkitScoreboard) {
        if (bukkitScoreboard == null) return null;

        Team team;
        if (bukkitScoreboard.getTeam(this.name) != null) team = bukkitScoreboard.getTeam(this.name);
        else team = bukkitScoreboard.registerNewTeam(this.name);

        return team;
    }

    public void addPlayer(Player player) {
        this.addEntity(player.getUniqueId());
    }

    public void addEntity(Entity entity) {
        this.addEntity(entity.getUniqueId());
    }

    public void addEntity(UUID uuid) {
        if (this.entities.contains(uuid)) return;
        this.entities.add(uuid);
        this.refresh();
    }

    public void removePlayer(Player player) {
        this.removeEntity(player.getUniqueId());
    }

    public void removeEntity(Entity entity) {
        this.removeEntity(entity.getUniqueId());
    }

    public void removeEntity(UUID uuid) {
        this.entities.remove(uuid);
        this.refresh();
    }

    protected void destroy() {
        if (this.scoreboard instanceof PersonalScoreboard) {
            for (UUID uuid : this.entities) {
                Player player = Bukkit.getPlayer(uuid);

                if (player != null) {
                    Team team = player.getScoreboard().getTeam(this.name);
                    if (team != null) team.unregister();
                }
            }
        } else {
            Scoreboard scoreboard = ((GlobalScoreboard) this.scoreboard).toBukkitScoreboard();
            Team team = scoreboard.getTeam(this.name);
            if (team != null) team.unregister();
        }
    }

    public boolean isOnTeam(UUID uuid) {
        return this.getEntities().contains(uuid);
    }
}
