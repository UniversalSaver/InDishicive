package logic.inventory.add_ingredient;

import entity.Ingredient;

/**
 * Interface for accessing and modifying inventory data.
 */
public interface InventoryDataAccessInterface {
    /**
     * Adds an ingredient to the inventory.
     * @param ingredient the ingredient to add
     */
    void addIngredient(Ingredient ingredient);
}

