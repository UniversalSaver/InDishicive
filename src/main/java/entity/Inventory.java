package entity;

import java.util.Collections;
import java.util.List;

/**
 * An entity representing the inventory, and all the ingredients it currently has
 */
public class Inventory {
    private final List<Ingredient> ingredients;

    /**
     * Creates a new inventory based on the given ingredients
     * @param ingredients a list of the ingredients currently in the inventory
     */
    public Inventory(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Adds an ingredient to the inventory
     * @param ingredient the ingredient to add
     */
    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    /**
     * Removes an ingredient from the inventory
     * @param ingredient the ingredient to remove
     * @return true if the ingredient was removed, false if it was not found
     */
    public boolean removeIngredient(Ingredient ingredient) {
        return ingredients.remove(ingredient);
    }

    /**
     * Finds an ingredient in the inventory by name.
     * @param name the name of the ingredient to find
     * @return the ingredient with the matching name, or null if not found
     */
    public Ingredient findIngredientByName(String name) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getName().equals(name)) {
                return ingredient;
            }
        }
        return null;
    }

    /**
     * Gets all ingredients currently in the inventory.
     * Returns an unmodifiable view to prevent external mutation.
     * @return an unmodifiable list of ingredients in the inventory
     */
    public List<Ingredient> getIngredients() {
        return Collections.unmodifiableList(ingredients);
    }
}
