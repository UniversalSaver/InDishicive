package use_case.add_recipe;

import entity.UserRecipe;

public interface AddRecipeDataAccessInterface {
	String ADDED_MESSAGE = "added";

    /**
     * Puts a recipe into the database.
     * @param recipe recipe to be entered
     * @return "Added" if recipe is successfully added. Otherwise, returns an error message depending on what went
	 * wrong
     */
    String addRecipe(UserRecipe recipe);
}
