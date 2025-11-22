package interface_adapter.add_ingredient;

import use_case.add_ingredient.AddIngredientInputBoundary;

public class AddIngredientController {

    private final AddIngredientInputBoundary addIngredientUseCaseInteractor;

    public AddIngredientController(AddIngredientInputBoundary addIngredientUseCaseInteractor) {
        this.addIngredientUseCaseInteractor = addIngredientUseCaseInteractor;
    }

    public void execute(String ingredientName, String amount) {
        addIngredientUseCaseInteractor.execute(ingredientName, amount);
    }
}

