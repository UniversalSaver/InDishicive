package interface_adapter.add_recipe;

import use_case.add_recipe.AddIngredientInputBoundary;

public class AddIngredientController {
	private final AddIngredientInputBoundary addIngredientInteractor;

	public AddIngredientController(AddIngredientInputBoundary addIngredientInteractor) {
		this.addIngredientInteractor = addIngredientInteractor;
	}

	public void execute() {
		addIngredientInteractor.execute();
	}
}
