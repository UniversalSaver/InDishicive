package logic.inventory.missing_ingredients;

import java.util.List;

import entity.Ingredient;

/**
 * Interface for reading inventory data.
 */
public interface InventoryReaderInterface {
    /**
     * Gets all ingredients from the inventory.
     * @return a list of ingredients
     */
    List<Ingredient> getAllIngredients();
}
