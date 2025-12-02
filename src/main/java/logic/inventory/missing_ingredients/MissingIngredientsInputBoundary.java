package logic.inventory.missing_ingredients;

import java.util.List;

/**
 * Input boundary for the missing ingredients use case.
 */
public interface MissingIngredientsInputBoundary {
    /**
     * Executes the missing ingredients use case.
     * @param recipeIngredients the list of ingredients required for a recipe
     */
    void execute(List<String> recipeIngredients);
}
