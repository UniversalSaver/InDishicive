package adapters.inventory.add_ingredient;

import logic.inventory.add_ingredient.AddIngredientInputBoundary;

/**
 * Controller for adding ingredients to inventory.
 */
public class AddIngredientController {

    private final AddIngredientInputBoundary addIngredientUseCaseInteractor;

    public AddIngredientController(AddIngredientInputBoundary addIngredientUseCaseInteractor) {
        this.addIngredientUseCaseInteractor = addIngredientUseCaseInteractor;
    }

    /**
     * Executes the add ingredient use case.
     * @param ingredientName the name of the ingredient
     * @param amount the amount of the ingredient
     */
    public void execute(String ingredientName, String amount) {
        addIngredientUseCaseInteractor.execute(ingredientName, amount);
    }
}
