package de.digitaldevs.core.inventory;

import de.digitaldevs.core.inventory.utils.InventoryUtils;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents an inventory having a border around it.
 *
 * @author MerryChrismas
 * @author <a href='https://digitaldevs.de'>DigitalDevs.de</a>
 * @version 1.0.0
 * @see AbstractInventory
 */
public class BorderedInventory extends AbstractInventory {

    /**
     * Instantiates a new {@code BorderedInventory}.
     *
     * @param rows       Amount of how many rows the inventory should have.
     * @param title      The title the inventory should have. Cannot be null.
     * @param borderItem The item the border should be from. Cannot be null.
     */
    public BorderedInventory(@NotNull String title, int rows, @NotNull ItemStack borderItem) {
        super(title, rows, borderItem);
        this.createBorder();
    }

    private void createBorder() {
        InventoryUtils.fill(this.inventory, (this.inventory.getSize() / 9) - 1, this.item);
    }

}
