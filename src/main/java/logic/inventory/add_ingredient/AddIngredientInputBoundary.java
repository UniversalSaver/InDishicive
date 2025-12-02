package logic.inventory.add_ingredient;

/**
 * Input boundary for adding ingredients.
 */
public interface AddIngredientInputBoundary {

    /**
     * Executes the add ingredient use case.
     * @param ingredientName the name of the ingredient
     * @param amount the amount of the ingredient
     */
    void execute(String ingredientName, String amount);
}
