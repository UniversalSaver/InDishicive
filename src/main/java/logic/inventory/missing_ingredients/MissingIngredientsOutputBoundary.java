package logic.inventory.missing_ingredients;

import java.util.List;

/**
 * Output boundary for the missing ingredients use case.
 */
public interface MissingIngredientsOutputBoundary {
    /**
     * Presents the list of missing ingredients.
     * @param missingIngredients the list of missing ingredients
     */
    void presentMissingIngredients(List<String> missingIngredients);

    /**
     * Presents that all ingredients are available.
     */
    void presentAllIngredientsAvailable();
}
