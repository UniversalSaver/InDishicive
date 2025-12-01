package databases.inventory;

import entity.Ingredient;
import entity.Inventory;
import logic.inventory.add_ingredient.InventoryDataAccessInterface;
import logic.inventory.remove_ingredient.RemoveIngredientDataAccessInterface;

/**
 * Data Access Object for managing inventory operations.
 * This class wraps the Inventory entity to follow Clean Architecture principles.
 */
public class InventoryDataAccessObject implements InventoryDataAccessInterface, RemoveIngredientDataAccessInterface {
    
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

    @Override
    public boolean removeIngredient(Ingredient ingredient) {
        return inventory.removeIngredient(ingredient);
    }

    @Override
    public Ingredient findIngredientByName(String name) {
        return inventory.findIngredientByName(name);
    }
}

