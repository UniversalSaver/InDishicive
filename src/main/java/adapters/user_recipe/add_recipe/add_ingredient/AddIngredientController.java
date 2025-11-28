package adapters.user_recipe.add_recipe.add_ingredient;

import logic.user_recipe.add_recipe.add_ingredient.AddIngredientInputBoundary;

public class AddIngredientController {
	private final AddIngredientInputBoundary addIngredientInteractor;

	public AddIngredientController(AddIngredientInputBoundary addIngredientInteractor) {
		this.addIngredientInteractor = addIngredientInteractor;
	}

	public void execute() {
		addIngredientInteractor.execute();
	}
}
