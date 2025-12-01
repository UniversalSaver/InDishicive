package logic.user_recipe.view_recipes.view_detailed_recipe;

import entity.UserRecipe;

/**
 * Defines expected methods within a Data Access Object for the View User Recipe Details use case.
 */
public interface ViewUserRecipeDetailsDataAccessInterface {
    /**
     * Given a title, return a User Recipe, or null if it cannot be found.
     * @param recipeTitle title of searched recipe
     * @return user recipe, or null if not found
     */
    UserRecipe getRecipeByTitle(String recipeTitle);
}
