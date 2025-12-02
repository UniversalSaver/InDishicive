package logic.generate_recipe.generate_with_inventory;

import java.util.Set;

// Used to get the ingredients from inventory.
// It will change when use cases about inventory are completed.

/**
 * Interface for reading inventory data.
 */
public interface InventoryReader {

    /**
     * Gets all ingredients in the inventory.
     * @return a set of ingredient names
     */
    Set<String> getAll();
}
