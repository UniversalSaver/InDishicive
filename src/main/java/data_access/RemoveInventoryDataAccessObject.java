package data_access;

import entity.Ingredient;
import entity.Inventory;
import use_case.remove_ingredient.InventoryDataAccessInterface;

/**
 * Data Access Object for removing ingredients from inventory.
 * This class wraps the Inventory entity to follow Clean Architecture principles.
 */
public class RemoveInventoryDataAccessObject implements InventoryDataAccessInterface {
    
    private final Inventory inventory;
    
    /**
     * Constructs a RemoveInventoryDataAccessObject with the given inventory.
     * @param inventory the inventory entity to manage
     */
    public RemoveInventoryDataAccessObject(Inventory inventory) {
        this.inventory = inventory;
    }
    
    @Override
    public boolean removeIngredient(Ingredient ingredient) {
        return inventory.removeIngredient(ingredient);
    }
    
    @Override
    public Ingredient findIngredientByName(String name) {
        return inventory.findIngredientByName(name);
    }
}

