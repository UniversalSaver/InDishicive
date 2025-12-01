package adapters.user_recipe.add_recipe.add_ingredient;

import logic.user_recipe.add_recipe.add_ingredient.AddRecipeIngredientInputBoundary;

/**
 * Controls the addition of another option for ingredients.
 */
public class AddRecipeIngredientController {
	private final AddRecipeIngredientInputBoundary addIngredientInteractor;

	public AddRecipeIngredientController(AddRecipeIngredientInputBoundary addIngredientInteractor) {
		this.addIngredientInteractor = addIngredientInteractor;
	}

    /**
     * Runs the add ingredient functionality.
     */
    public void execute() {
		addIngredientInteractor.execute();
	}
}
