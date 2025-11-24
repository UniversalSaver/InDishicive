package interface_adapter.user_recipe.add_recipe;

import use_case.user_recipe.add_recipe.AddRecipeInputBoundary;
import use_case.user_recipe.add_recipe.AddRecipeInputData;

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
