package logic.inventory.missing_ingredients;

import java.util.List;

public interface MissingIngredientsInputBoundary {
    void execute(List<String> recipeIngredients);
}

