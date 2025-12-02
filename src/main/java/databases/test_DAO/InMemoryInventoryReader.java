package databases.test_DAO;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import logic.generate_recipe.generate_with_inventory.InventoryReader;

// This is just used for implement usecase1 this time, once use cases related to inventory are completed.
// I will rewrite another one.
/**
 * In-memory implementation of InventoryReader for testing purposes.
 */
public class InMemoryInventoryReader implements InventoryReader {

    private final Set<String> ingredients = new HashSet<>();

    /**
     * Adds an ingredient to the in-memory inventory.
     * @param ingredientName the name of the ingredient to add
     */
    public void add(String ingredientName) {
        if (ingredientName != null) {
            ingredients.add(ingredientName.trim().toLowerCase());
        }
    }

    @Override
    public Set<String> getAll() {
        return Collections.unmodifiableSet(ingredients);
    }
}
