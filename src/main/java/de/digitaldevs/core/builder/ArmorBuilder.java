package de.digitaldevs.core.builder;

import de.digitaldevs.core.exception.InvalidMaterialException;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class is used for creating and modify leather armor very easily. <br>
 * <strong>Note: </strong> This utility works only for leather armor!
 *
 * @author MerryChrismas
 * @author <a href='https://digitaldevs.de'>DigitalDevs.de</a>
 * @version 1.0.0
 */
public class ArmorBuilder {

    private final ItemStack itemStack;
    private final LeatherArmorMeta armorMeta;

    /**
     * Instantiates a new {@code ArmorBuilder}.
     *
     * @param material The material the armor should be from. Cannot be null.
     * @throws InvalidMaterialException exception Thrown when the material is not of the leather armor type.
     */
    public ArmorBuilder(@NotNull Material material) throws InvalidMaterialException {
        if (!this.isAValidMaterial(material)) throw new InvalidMaterialException("The material must be leather armor!");
        this.itemStack = new ItemBuilder(material).build();
        this.armorMeta = (LeatherArmorMeta) this.itemStack.getItemMeta();
    }

    /**
     * Colorizes the armor with the given {@code Color}.
     *
     * @param color The color the armor should have. Cannot be null.
     * @return The modified {@code ArmorBuilder}
     */
    public ArmorBuilder color(@NotNull Color color) {
        this.armorMeta.setColor(color);
        return this;
    }

    /**
     * Colorizes the armor with an color created by rgb-values.
     *
     * @param r The red value.
     * @param g The green value.
     * @param b The blue value.
     * @return The modified {@code ArmorBuilder}
     */
    public ArmorBuilder color(int r, int g, int b) {
        this.armorMeta.setColor(Color.fromRGB(r, g, b));
        return this;
    }

    /**
     * Set the displayed name of the item.
     *
     * @param name The name the items should have. Cannot be null.
     * @return The modified {@code ArmorBuilder}
     */
    public ArmorBuilder name(@NotNull String name) {
        this.armorMeta.setDisplayName(name);
        return this;
    }

    /**
     * Sets the amount how much items the {@code ItemStack} should contain.
     *
     * @param amount How much items the {@code ItemStack} should contain.
     * @return The modified {@code ArmorBuilder}
     */
    public ArmorBuilder amount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    /**
     * Applies a lore to the item.
     *
     * @param lore The lines of the lore. Each String represents a new line. Cannot be null.
     * @return The modified {@code ArmorBuilder}
     */
    public ArmorBuilder lore(@NotNull String... lore) {
        this.armorMeta.setLore(Arrays.asList(lore));
        return this;
    }

    /**
     * Applies an enchantment with a certain level to the {@code ItemStack}. <br>
     * Note that only the vanilla levels work.
     *
     * @param enchantment The enchantment which should be applied to the {@code ItemStack}. Cannot be null.
     * @param level       The vanilla level of the enchantment which should be applied.
     * @return The modified {@code ArmorBuilder}
     */
    public ArmorBuilder enchant(@NotNull Enchantment enchantment, int level) {
        this.itemStack.addEnchantment(enchantment, level);
        return this;
    }

    /**
     * Applies an enchantment with a certain level to the {@code ItemStack}. <br>
     *
     * @param enchantment The enchantment which should be applied to the {@code ItemStack}. Cannot be null.
     * @param level       The level of the enchantment which should be applied.
     * @return The modified {@code ArmorBuilder}
     */
    public ArmorBuilder enchantUnsafe(@NotNull Enchantment enchantment, int level) {
        this.itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    /**
     * Applies a map of enchantments and levels to the {@code ItemStack}. <br>
     * Note that the level must be one of the vanilla values.
     *
     * @param enchantments The map which contains the enchantments and their levels. None of the values can be null.
     * @return The modified {@code ArmorBuilder}
     */
    public ArmorBuilder addEnchantments(@NotNull Map<@NotNull Enchantment, @NotNull Integer> enchantments) {
        this.itemStack.addEnchantments(enchantments);
        return this;
    }

    /**
     * Applies a map of enchantments and levels to the {@code ItemStack}. <br>
     *
     * @param enchantments The map which contains the enchantments and their levels. None of the values can be null.
     * @return The modified {@code ArmorBuilder}
     */
    public ArmorBuilder addUnsafeEnchantments(@NotNull Map<@NotNull Enchantment, @NotNull Integer> enchantments) {
        this.itemStack.addUnsafeEnchantments(enchantments);
        return this;
    }

    /**
     * Applies one or more {@link ItemFlag}(s) to the item.
     *
     * @param flags The flag(s) which should be applied to the item. Cannot be null.
     * @return the modified {@code ArmorBuilder}
     */
    public ArmorBuilder flag(@NotNull ItemFlag... flags) {
        this.armorMeta.addItemFlags(flags);
        return this;
    }

    /**
     * Makes the item unbreakable.
     *
     * @return The modified {@code ArmorBuilder}
     */
    public ArmorBuilder unbreakable() {
        this.armorMeta.spigot().setUnbreakable(true);
        return this;
    }

    /**
     * Makes the item breakable.
     *
     * @return The modified {@code ArmorBuilder}
     */
    public ArmorBuilder breakable() {
        this.armorMeta.spigot().setUnbreakable(false);
        return this;
    }

    /**
     * Deletes the complete lore of the item.
     */
    public void removeLore() {
        this.armorMeta.setLore(null);
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
            this.armorMeta.setLore(lore);
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
        if (this.armorMeta.hasEnchant(enchantment)) {
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
        this.getFlags().forEach(this.armorMeta::removeItemFlags);
    }

    /**
     * Removes a specific ItemFlag if it is currently applied to the item.
     *
     * @param flag The ItemFlag which should be removed. Cannot be null.
     */
    public void removeFlag(@NotNull ItemFlag flag) {
        if (this.armorMeta.hasItemFlag(flag)) {
            this.armorMeta.removeItemFlags(flag);
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
     * @return The modified {@code ArmorBuilder}
     */
    public ArmorBuilder durability(short durability) {
        this.itemStack.setDurability(durability);
        return this;
    }

    /**
     * Checks weather the item is unbreakable or not.
     *
     * @return A final boolean containing the unbreakable-state
     */
    public final boolean isUnbreakable() {
        return this.armorMeta.spigot().isUnbreakable();
    }

    /**
     * Gets all current applied ItemFlags of the item.
     *
     * @return A final Set containing all current applied ItemFlags
     */
    public final Set<ItemFlag> getFlags() {
        return this.armorMeta.getItemFlags();
    }

    /**
     * Gets all current applied enchantments and their level of the {@code ItemStack}.
     *
     * @return A final {@code Map<Enchantment, Integer>} holding all current applied enchantments with their level.
     */
    public final Map<Enchantment, Integer> getEnchantments() {
        return this.armorMeta.getEnchants();
    }

    /**
     * Gets the lore of the {@code ItemStack}.
     *
     * @return A final {@code List} with Strings. Each String represents a new line.
     */
    public final List<String> getLore() {
        return this.armorMeta.getLore();
    }

    /**
     * Applies the {@code LeatherArmorMeta} to the {@code ItemStack}.
     *
     * @return The item
     */
    public ItemStack build() {
        this.itemStack.setItemMeta(this.armorMeta);
        return this.itemStack;
    }

    private boolean isAValidMaterial(Material material) {
        return ((material == Material.LEATHER_BOOTS) || (material == Material.LEATHER_CHESTPLATE)
                || (material == Material.LEATHER_HELMET) || (material == Material.LEATHER_LEGGINGS));
    }

}
