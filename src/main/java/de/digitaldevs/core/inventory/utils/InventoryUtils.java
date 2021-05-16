package de.digitaldevs.core.inventory.utils;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * This class contains all relevant methods to modify inventories.
 *
 * @author MerryChrismas
 * @author <a href='https://digitaldevs.de'>DigitalDevs.de</a>
 * @version 1.0.0
 */
public class InventoryUtils {

    /**
     * Fills an given inventory completely with an specific item.
     *
     * @param inventory The inventory which should be filled. Cannot be null.
     * @param itemStack The item which should fill the inventory.
     */
    public static void fill(@NotNull Inventory inventory, ItemStack itemStack) {
        for (int i = 0; i <= inventory.getSize(); i++) fill(inventory, 8, itemStack);
    }

    public static void fill(Inventory inventory, int toRow, ItemStack itemStack) {
        for (int i = 0; i <= toRow; i++) {
            for (int j = 0; j <= 8; j++) {
                if ((i != 0) && (i != toRow) && (j != 0) && (j != 8)) continue;
                setItem(inventory, i, j, itemStack);
            }
        }
    }

    private static void setItem(Inventory inventory, int row, int column, ItemStack itemStack) {
        inventory.setItem(9 * row + column, itemStack);
    }

}
