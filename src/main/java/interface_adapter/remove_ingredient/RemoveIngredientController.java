package interface_adapter.remove_ingredient;

import use_case.remove_ingredient.RemoveIngredientInputBoundary;

public class RemoveIngredientController {

    private final RemoveIngredientInputBoundary removeIngredientUseCaseInteractor;

    public RemoveIngredientController(RemoveIngredientInputBoundary removeIngredientUseCaseInteractor) {
        this.removeIngredientUseCaseInteractor = removeIngredientUseCaseInteractor;
    }

    public void execute(String ingredientName) {
        removeIngredientUseCaseInteractor.execute(ingredientName);
    }
}

