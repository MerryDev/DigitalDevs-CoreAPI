package de.digitaldevs.core.inventory;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * @author MerryChrismas
 * @author <a href='https://digitaldevs.de'>DigitalDevs.de</a>
 * @version 1.0.0
 */
public class AbstractInventory {

    @Getter private final String title;
    @Getter private final int rows;
    @Getter protected final ItemStack item;
    protected Inventory inventory;

    public AbstractInventory(String title, int rows, ItemStack item) {
        this.title = title;
        this.rows = rows;
        this.item = item;
        this.inventory = Bukkit.createInventory(null, this.rows * 9, this.title);
    }

    public Inventory wrap() {
        return this.inventory;
    }

}
