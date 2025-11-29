package databases.inventory;

import entity.Ingredient;
import entity.Inventory;
import logic.generate_recipe.generate_with_inventory.InventoryReader;

import java.util.HashSet;
import java.util.Set;

/**
 * Adapter: wraps the Inventory entity and exposes it as an InventoryReader
 * for UC1 GenerateWithInventory.
 */
public class InventoryReaderFromInventory implements InventoryReader {

    private final Inventory inventory;

    public InventoryReaderFromInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public Set<String> getAll() {
        Set<String> names = new HashSet<>();
        for (Ingredient ingredient : inventory.getIngredients()) {
            if (ingredient == null || ingredient.getName() == null) {
                continue;
            }
            String name = ingredient.getName().trim().toLowerCase();
            if (!name.isEmpty()) {
                names.add(name);
            }
        }
        return names;
    }
}