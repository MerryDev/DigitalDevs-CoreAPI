package de.digitaldevs.core.inventory;

import de.digitaldevs.core.inventory.utils.InventoryUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents an inventory which is completely filled with an specific item.
 *
 * @author MerryChrismas
 * @author <a href='https://digitaldevs.de'>DigitalDevs.de</a>
 * @version 1.0.0
 * @see de.digitaldevs.core.inventory.AbstractInventory
 */
public class FilledInventory extends AbstractInventory {

    /**
     * Instantiates a new {@code FilledInventory}.
     *
     * @param rows     Amount of how many rows the inventory should have.
     * @param title    The title the inventory should have. Cannot be null.
     * @param fillItem The item with which the inventory should be filled. Cannot be null.
     */
    public FilledInventory(@NotNull String title, int rows, @NotNull ItemStack fillItem) {
        super(title, rows, fillItem);
        this.fill();
    }

    private void fill() {
        InventoryUtils.fill(this.inventory, this.item);
    }

}
