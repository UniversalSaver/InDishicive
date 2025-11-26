package interface_adapter.remove_ingredient;

import use_case.remove_ingredient.RemoveIngredientInputBoundary;

/**
 * Controller for the Remove Ingredient use case.
 * Handles user input and delegates to the use case interactor.
 */
public class RemoveIngredientController {

    private final RemoveIngredientInputBoundary removeIngredientUseCaseInteractor;

    /**
     * Constructs a RemoveIngredientController.
     * @param removeIngredientUseCaseInteractor the use case interactor for removing ingredients
     */
    public RemoveIngredientController(RemoveIngredientInputBoundary removeIngredientUseCaseInteractor) {
        this.removeIngredientUseCaseInteractor = removeIngredientUseCaseInteractor;
    }

    /**
     * Executes the remove ingredient use case.
     * @param ingredientName the name of the ingredient to remove
     */
    public void execute(String ingredientName) {
        removeIngredientUseCaseInteractor.execute(ingredientName);
    }
}

