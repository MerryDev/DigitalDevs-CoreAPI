package de.digitaldevs.core.builder;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * This class is used for creating skulls easily by using the spigot-api's {@code SkullMeta}.
 *
 * @author MerryChrismas
 * @author <a href='https://digitaldevs.de'>DigitalDevs.de</a>
 * @version 1.0.0
 * @see SkullMeta
 */
public class SkullBuilder {

    private final ItemStack itemStack;
    private final SkullMeta skullMeta;

    /**
     * Instantiates a new {@code SkullBuilder}.
     */
    public SkullBuilder() {
        this.itemStack = new ItemBuilder(Material.SKULL_ITEM, (short) 3).build();
        this.skullMeta = (SkullMeta) this.itemStack.getItemMeta();
    }

    /**
     * Applies a player-based skull-texture to the skull.
     *
     * @param owner The name of the player holding the texture. Cannot be null.
     * @return The modified {@code SkullBuilder}
     */
    public SkullBuilder owner(@NotNull String owner) {
        this.skullMeta.setOwner(owner);
        return this;
    }

    /**
     * Set the displayed name of the item.
     *
     * @param name The name the items should have. Cannot be null.
     * @return the modified {@code SkullBuilder}
     */
    public SkullBuilder name(@NotNull String name) {
        this.skullMeta.setDisplayName(name);
        return this;
    }

    /**
     * Sets the amount how much items the {@code ItemStack} should contain.
     *
     * @param amount How much items the {@code ItemStack} should contain.
     * @return The modified {@code SkullBuilder}
     */
    public SkullBuilder amount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    /**
     * Applies a lore to the item.
     *
     * @param lore The lines of the lore. Each String represents a new line. Cannot be null.
     * @return The modified {@code SkullBuilder}
     */
    public SkullBuilder lore(@NotNull String... lore) {
        this.skullMeta.setLore(Arrays.asList(lore));
        return this;
    }


    /**
     * Deletes the complete lore of the item.
     */
    public void removeLore() {
        this.skullMeta.setLore(null);
    }

    /**
     * Deletes a specific line of the lore if it exists.
     *
     * @param index the index of the line which should be removed.
     */
    public void removeLore(int index) {
        final List<String> lore = this.getLore();
        if (lore.size() >= index) {
            lore.remove(index);
            this.removeLore();
            this.skullMeta.setLore(lore);
        }
    }

    /**
     * Gets the lore of the {@code ItemStack}.
     *
     * @return A final {@code List} with Strings. Each String represents a new line.
     */
    public final List<String> getLore() {
        return this.skullMeta.getLore();
    }

    /**
     * Applies the {@code ItemMeta} to the {@code ItemStack}.
     *
     * @return The item
     */
    public ItemStack build() {
        this.itemStack.setItemMeta(this.skullMeta);
        return this.itemStack;
    }

}
