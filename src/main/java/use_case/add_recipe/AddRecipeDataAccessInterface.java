package use_case.add_recipe;

import entity.UserRecipe;

public interface AddRecipeDataAccessInterface {
    /**
     * Puts a recipe into the database.
     * @param recipe recipe to be entered
     * @return True if recipe successfully added. False if recipe already exists
     */
    Boolean addRecipe(UserRecipe recipe);
}
