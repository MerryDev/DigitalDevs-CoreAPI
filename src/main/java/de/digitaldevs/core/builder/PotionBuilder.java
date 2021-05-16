package de.digitaldevs.core.builder;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * This class is used for creating potions very easily. <br>
 * <strong>Note: </strong> Only minecraft vanilla potions and levels are supported by this class.
 *
 * @author MerryChrismas
 * @author <a href='https://digitaldevs.de'>DigitalDevs.de</a>
 * @version 1.0.0
 */
public class PotionBuilder {

    private final Potion potion;
    private final PotionMeta potionMeta;

    /**
     * Instantiates a new {@code PotionBuilder} with an default amount and level of 1.
     *
     * @param type The type the potion should be from. Cannot be null.
     */
    public PotionBuilder(PotionType type) {
        this(type, 1);
    }

    /**
     * Instantiates a new {@code PotionBuilder} with an default amount of 1.
     *
     * @param type  The type the potion should be from. Cannot be null.
     * @param level The level of the potion effect.
     */
    public PotionBuilder(PotionType type, int level) {
        this(type, level, 1);
    }

    /**
     * Instantiates a new {@code PotionBuilder}.
     *
     * @param type   The type the potion should be from. Cannot be null.
     * @param level  The level of the potion effect.
     * @param amount The amount how many potions should be on this stack.
     */
    public PotionBuilder(@NotNull PotionType type, int level, int amount) {
        this.potion = new Potion(type, level);
        this.potionMeta = (PotionMeta) this.potion.toItemStack(amount).getItemMeta();
    }

    /**
     * Set the displayed name of the item.
     *
     * @param name The name the items should have. Cannot be null.
     * @return the modified {@code PotionBuilder}
     */
    public PotionBuilder name(String name) {
        this.potionMeta.setDisplayName(name);
        return this;
    }

    /**
     * Applies a lore to the item.
     *
     * @param lore The lines of the lore. Each String represents a new line. Cannot be null.
     * @return The modified {@code PotionBuilder}
     */
    public PotionBuilder lore(@NotNull String... lore) {
        this.potionMeta.setLore(Arrays.asList(lore));
        return this;
    }

    /**
     * Applies the potion effect to an living entity.
     *
     * @param entity The entity to which the potion effect should be applied. Cannot be null.
     * @return The modified {@code PotionBuilder}
     */
    public PotionBuilder apply(@NotNull LivingEntity entity) {
        this.potion.apply(entity);
        return this;
    }

    /**
     * Deletes the complete lore of the item.
     */
    public void removeLore() {
        this.potionMeta.setLore(null);
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
            this.potionMeta.setLore(lore);
        }
    }

    /**
     * Gets the lore of the {@code ItemStack}.
     *
     * @return A final {@code List} with Strings. Each String represents a new line.
     */
    public final List<String> getLore() {
        return this.potionMeta.getLore();
    }

}
