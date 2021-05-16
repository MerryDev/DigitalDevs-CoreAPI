package de.digitaldevs.core.reflection.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author MerryChrismas
 * @author <a href='https://digitaldevs.de'>DigitalDevs.de</a>
 * @version 1.0.0
 */
@AllArgsConstructor
public enum PackageType {

    BLOCK("block"),
    COMMAND("command"),
    CONFIGURATION("configuration"),
    CONVERSATION("conversations"),
    ENCHANTMENT("enchantments"),
    ENTITY("entity"),
    EVENT("event"),
    GENERATOR("generator"),
    HELP("help"),
    INVENTORY("inventory"),
    MAP("map"),
    MATERIAL("material"),
    METADATA("metadata"),
    PERMISSION("permission"),
    POTION("potion"),
    PROJECTILE("projectile"),
    SCHEDULER("scheduler"),
    SCOREBOARD("scoreboard"),
    UTIL("util");

    @Getter private final String key;

}
