package logic.generate_recipe.generate_with_inventory;

import java.util.Set;

/**
 * Reads all inventory items in the inventory.
 */
public interface InventoryReader {

    /**
     * Gets all inventory item names.
     *
     * @return a set containing all item names in the inventory
     */
    Set<String> getAll();
}
