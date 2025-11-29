package data_access;

import entity.Ingredient;
import entity.Inventory;
import use_case.add_ingredient.InventoryDataAccessInterface;

/**
 * Data Access Object for managing inventory operations.
 * This class wraps the Inventory entity to follow Clean Architecture principles.
 */
public class InventoryDataAccessObject implements InventoryDataAccessInterface {
    
    private final Inventory inventory;
    
    /**
     * Constructs an InventoryDataAccessObject with the given inventory.
     * @param inventory the inventory entity to manage
     */
    public InventoryDataAccessObject(Inventory inventory) {
        this.inventory = inventory;
    }
    
    @Override
    public void addIngredient(Ingredient ingredient) {
        inventory.addIngredient(ingredient);
    }
}

