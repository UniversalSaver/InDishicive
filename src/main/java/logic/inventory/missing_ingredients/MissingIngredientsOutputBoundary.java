package logic.inventory.missing_ingredients;

import java.util.List;

public interface MissingIngredientsOutputBoundary {
    void presentMissingIngredients(List<String> missingIngredients);
    void presentAllIngredientsAvailable();
}

