package adapters.user_recipe.add_recipe;

import logic.user_recipe.add_recipe.AddRecipeInputBoundary;
import logic.user_recipe.add_recipe.AddRecipeInputData;

import java.util.List;

public class AddRecipeController {
	private final AddRecipeInputBoundary addRecipeInteractor;

	public AddRecipeController(AddRecipeInputBoundary addRecipeInteractor) {
		this.addRecipeInteractor = addRecipeInteractor;
	}

	public void execute(List<String> ingredientNames, List<String> ingredientAmounts, String title,
						String description, String steps) {
		addRecipeInteractor.execute(new AddRecipeInputData(ingredientNames, ingredientAmounts,
				title, description, steps));
	}
}
