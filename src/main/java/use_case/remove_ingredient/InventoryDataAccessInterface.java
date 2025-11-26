package use_case.remove_ingredient;

import entity.Ingredient;

/**
 * Interface for accessing and modifying inventory data.
 */
public interface InventoryDataAccessInterface {
    /**
     * Removes an ingredient from the inventory.
     * @param ingredient the ingredient to remove
     * @return true if the ingredient was removed, false if it was not found
     */
    boolean removeIngredient(Ingredient ingredient);
    
    /**
     * Finds an ingredient in the inventory by name.
     * @param name the name of the ingredient to find
     * @return the ingredient with the matching name, or null if not found
     */
    Ingredient findIngredientByName(String name);
}

