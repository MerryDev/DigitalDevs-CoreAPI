package de.digitaldevs.core.builder;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class is used for creating items very easily and allows you to modify an existing item.
 *
 * @author MerryChrismas
 * @author <a href='https://digitaldevs.de'>DigitalDevs.de</a>
 * @version 1.0.0
 */
public class ItemBuilder {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    /**
     * Instantiates a new {@code ItemBuilder} with an material and default amount of 1 and the default subID of 0.
     *
     * @param material The material of which the item should be. Cannot be null.
     */
    public ItemBuilder(Material material) {
        this(material, 1);
    }

    /**
     * Instantiates a new {@code ItemBuilder} with an material, amount and the default subID of 0.
     *
     * @param material The material of which the item should be. Cannot be null.
     * @param amount   Defines how much items the {@code ItemStack} should contain.
     */
    public ItemBuilder(@NotNull Material material, int amount) {
        this(material, amount, 0);
    }

    /**
     * Instantiates a new {@code ItemBuilder} with an material, default amount of 1 and an subID.
     *
     * @param material The material of which the item should be. Cannot be null.
     * @param subID    The ID of an sub-material (e.g. {@code Material.WOOL} has 15 subIDs)
     */
    public ItemBuilder(@NotNull Material material, short subID) {
        this(material, 1, subID);
    }

    /**
     * Instantiates a new {@code ItemBuilder} with an material, amount and subID.
     *
     * @param material The material of which the item should be. Cannot be null.
     * @param amount   Defines how much items the {@code ItemStack} should contain.
     * @param subID    The ID of an sub-material (e.g. {@code Material.WOOL} has 15 subIDs)
     */
    public ItemBuilder(@NotNull Material material, int amount, int subID) {
        this.itemStack = new ItemStack(material, amount, (short) subID);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    /**
     * Instantiates a new {@code ItemBuilder} from an existing {@code ItemStack}.
     *
     * @param itemStack The existing item. Cannot be null.
     */
    public ItemBuilder(@NotNull ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = this.itemStack.getItemMeta();
    }

    /**
     * Set the displayed name of the item.
     *
     * @param name The name the items should have. Cannot be null.
     * @return the modified {@code ItemBuilder}
     */
    public ItemBuilder name(@NotNull String name) {
        this.itemMeta.setDisplayName(name);
        return this;
    }

    /**
     * Sets the amount how much items the {@code ItemStack} should contain.
     *
     * @param amount How much items the {@code ItemStack} should contain.
     * @return The modified {@code ItemBuilder}
     */
    public ItemBuilder amount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    /**
     * Applies a lore to the item.
     *
     * @param lore The lines of the lore. Each String represents a new line. Cannot be null.
     * @return The modified {@code ItemBuilder}
     */
    public ItemBuilder lore(@NotNull String... lore) {
        this.itemMeta.setLore(Arrays.asList(lore));
        return this;
    }

    /**
     * Applies an enchantment with a certain level to the {@code ItemStack}. <br>
     * Note that only the vanilla levels work.
     *
     * @param enchantment The enchantment which should be applied to the {@code ItemStack}. Cannot be null.
     * @param level       The vanilla level of the enchantment which should be applied.
     * @return The modified {@code ItemBuilder}
     */
    public ItemBuilder enchant(@NotNull Enchantment enchantment, int level) {
        this.itemStack.addEnchantment(enchantment, level);
        return this;
    }

    /**
     * Applies an enchantment with a certain level to the {@code ItemStack}. <br>
     *
     * @param enchantment The enchantment which should be applied to the {@code ItemStack}. Cannot be null.
     * @param level       The level of the enchantment which should be applied.
     * @return The modified {@code ItemBuilder}
     */
    public ItemBuilder enchantUnsafe(@NotNull Enchantment enchantment, int level) {
        this.itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    /**
     * Applies a map of enchantments and levels to the {@code ItemStack}. <br>
     * Note that the level must be one of the vanilla values.
     *
     * @param enchantments The map which contains the enchantments and their levels. None of the values can be null.
     * @return The modified {@code ItemBuilder}
     */
    public ItemBuilder addEnchantments(@NotNull Map<@NotNull Enchantment, @NotNull Integer> enchantments) {
        this.itemStack.addEnchantments(enchantments);
        return this;
    }

    /**
     * Applies a map of enchantments and levels to the {@code ItemStack}. <br>
     *
     * @param enchantments The map which contains the enchantments and their levels. None of the values can be null.
     * @return The modified {@code ItemBuilder}
     */
    public ItemBuilder addUnsafeEnchantments(@NotNull Map<@NotNull Enchantment, @NotNull Integer> enchantments) {
        this.itemStack.addUnsafeEnchantments(enchantments);
        return this;
    }

    /**
     * Applies one or more {@link ItemFlag}(s) to the item.
     *
     * @param flags The flag(s) which should be applied to the item. Cannot be null.
     * @return the modified {@code ItemBuilder}
     */
    public ItemBuilder flag(@NotNull ItemFlag... flags) {
        this.itemMeta.addItemFlags(flags);
        return this;
    }

    /**
     * Makes the item unbreakable.
     *
     * @return The modified {@code ItemBuilder}
     */
    public ItemBuilder unbreakable() {
        this.itemMeta.spigot().setUnbreakable(true);
        return this;
    }

    /**
     * Makes the item breakable.
     *
     * @return The modified {@code ItemBuilder}
     */
    public ItemBuilder breakable() {
        this.itemMeta.spigot().setUnbreakable(false);
        return this;
    }

    /**
     * Deletes the complete lore of the item.
     */
    public void removeLore() {
        this.itemMeta.setLore(null);
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
            this.itemMeta.setLore(lore);
        }
    }

    /**
     * Removes all currently applied enchantments from the item.
     */
    public void removeEnchantments() {
        this.getEnchantments().forEach((enchantment, integer) -> this.itemStack.removeEnchantment(enchantment));
    }

    /**
     * Removes a specific enchantment from the item if it is currently applied.
     *
     * @param enchantment The enchantment shich should be removed. Cannot be null.
     */
    public void removeEnchantment(@NotNull Enchantment enchantment) {
        if (this.itemMeta.hasEnchant(enchantment)) {
            this.itemStack.removeEnchantment(enchantment);
        }
    }

    /**
     * Removes a list of enchantments from the item if they are currently applied.
     *
     * @param enchantments The enchantments which should be removed from the item
     */
    public void removeEnchantments(Enchantment... enchantments) {
        Arrays.asList(enchantments).forEach(this::removeEnchantment);
    }

    /**
     * Removes all currently applied ItemFlags from the item.
     */
    public void removeFlags() {
        this.getFlags().forEach(this.itemMeta::removeItemFlags);
    }

    /**
     * Removes a specific ItemFlag if it is currently applied to the item.
     *
     * @param flag The ItemFlag which should be removed. Cannot be null.
     */
    public void removeFlag(@NotNull ItemFlag flag) {
        if (this.itemMeta.hasItemFlag(flag)) {
            this.itemMeta.removeItemFlags(flag);
        }
    }

    /**
     * Removes multiple ItemFlags from the item if they are currently applied to the item.
     *
     * @param flags The ItemFlags which should be removed from the item
     */
    public void removeFlags(ItemFlag... flags) {
        Arrays.asList(flags).forEach(this::removeFlag);
    }

    /**
     * Sets the durability of the item.
     *
     * @param durability Defines how much durability the item should have be left.
     * @return The modified {@code ItemBuilder}
     */
    public ItemBuilder durability(short durability) {
        this.itemStack.setDurability(durability);
        return this;
    }

    /**
     * Checks weather the item is unbreakable or not.
     *
     * @return A final boolean containing the unbreakable-state
     */
    public final boolean isUnbreakable() {
        return this.itemMeta.spigot().isUnbreakable();
    }

    /**
     * Gets all current applied ItemFlags of the item.
     *
     * @return A final Set containing all current applied ItemFlags
     */
    public final Set<ItemFlag> getFlags() {
        return this.itemMeta.getItemFlags();
    }

    /**
     * Gets all current applied enchantments and their level of the {@code ItemStack}.
     *
     * @return A final {@code Map<Enchantment, Integer>} holding all current applied enchantments with their level.
     */
    public final Map<Enchantment, Integer> getEnchantments() {
        return this.itemMeta.getEnchants();
    }

    /**
     * Gets the lore of the {@code ItemStack}.
     *
     * @return A final {@code List} with Strings. Each String represents a new line.
     */
    public final List<String> getLore() {
        return this.itemMeta.getLore();
    }

    /**
     * Applies the {@code ItemMeta} to the {@code ItemStack}.
     *
     * @return The item
     */
    public ItemStack build() {
        this.itemStack.setItemMeta(this.itemMeta);
        return this.itemStack;
    }

}
