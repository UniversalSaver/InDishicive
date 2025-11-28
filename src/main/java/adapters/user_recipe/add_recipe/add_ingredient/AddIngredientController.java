package adapters.user_recipe.add_recipe.add_ingredient;

import logic.user_recipe.add_recipe.add_ingredient.AddIngredientInputBoundary;

/**
 * Controls the addition of another option for ingredients.
 */
public class AddIngredientController {
	private final AddIngredientInputBoundary addIngredientInteractor;

	public AddIngredientController(AddIngredientInputBoundary addIngredientInteractor) {
		this.addIngredientInteractor = addIngredientInteractor;
	}

    /**
     * Executes program to give user another ingredient to customize.
     */
    public void execute() {
		addIngredientInteractor.execute();
	}
}
