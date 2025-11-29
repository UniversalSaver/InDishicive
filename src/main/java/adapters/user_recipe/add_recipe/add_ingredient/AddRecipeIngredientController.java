package adapters.user_recipe.add_recipe.add_ingredient;

import logic.user_recipe.add_recipe.add_ingredient.AddRecipeIngredientInputBoundary;

public class AddRecipeIngredientController {
	private final AddRecipeIngredientInputBoundary addIngredientInteractor;

	public AddRecipeIngredientController(AddRecipeIngredientInputBoundary addIngredientInteractor) {
		this.addIngredientInteractor = addIngredientInteractor;
	}

	public void execute() {
		addIngredientInteractor.execute();
	}
}
