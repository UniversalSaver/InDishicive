package adapters.inventory.missing_ingredients;

import java.util.List;

import logic.inventory.missing_ingredients.MissingIngredientsInputBoundary;

/**
 * Controller for the missing ingredients use case.
 */
public class MissingIngredientsController {

    private final MissingIngredientsInputBoundary interactor;

    public MissingIngredientsController(MissingIngredientsInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes the missing ingredients use case.
     * @param recipeIngredients the list of ingredients required for a recipe
     */
    public void execute(List<String> recipeIngredients) {
        interactor.execute(recipeIngredients);
    }
}
