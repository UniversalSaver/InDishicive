package entity;

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
}
