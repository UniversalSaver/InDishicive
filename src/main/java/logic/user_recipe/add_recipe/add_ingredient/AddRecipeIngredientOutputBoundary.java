package logic.user_recipe.add_recipe.add_ingredient;

/**
 * On failure, the database couldn't be accessed. On success, the database returned a list of ingredients, and this is
 * the parsed data.
 */
public interface AddRecipeIngredientOutputBoundary {
	void presentSuccessView(AddRecipeIngredientOutputData outputData);
	void presentFailView();
}
