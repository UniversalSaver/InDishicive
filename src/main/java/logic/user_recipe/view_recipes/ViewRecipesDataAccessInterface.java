package logic.user_recipe.view_recipes;

import java.util.List;

import entity.UserRecipe;

/**
 * An expectation of what the data object should be able to do.
 */
public interface ViewRecipesDataAccessInterface {

    /**
     * Returns a list of the user's recipes.
     * @return list of user recipes
     */
    List<UserRecipe> getUserRecipes();
}
