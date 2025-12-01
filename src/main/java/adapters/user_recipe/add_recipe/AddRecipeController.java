package adapters.user_recipe.add_recipe;

import java.util.List;

import logic.user_recipe.add_recipe.AddRecipeInputBoundary;
import logic.user_recipe.add_recipe.AddRecipeInputData;

/**
 * Controls the program to add a recipe to database.
 */
public class AddRecipeController {
	private final AddRecipeInputBoundary addRecipeInteractor;

	public AddRecipeController(AddRecipeInputBoundary addRecipeInteractor) {
		this.addRecipeInteractor = addRecipeInteractor;
	}

    /**
     * Given inputted data from user, runs program to add recipe to database.
     * @param ingredientNames name of ingredients
     * @param ingredientAmounts amount of ingredients (Assumed to follow same order as names)
     * @param title name of recipe
     * @param description a short description of recipe
     * @param steps steps required to make recipe
     */
    public void execute(List<String> ingredientNames, List<String> ingredientAmounts, String title,
						String description, String steps) {
		addRecipeInteractor.execute(new AddRecipeInputData(ingredientNames, ingredientAmounts,
				title, description, steps));
	}
}
