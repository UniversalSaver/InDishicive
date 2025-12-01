package logic.generate_recipe.random_recipe;

import entity.Recipe;

/**
 * The expected methods in a presenter for the random recipe use case.
 */
public interface RandomRecipeOutputBoundary {
    /**
     * Opens a recipe details view given a recipe.
     * @param recipe recipe to be presented
     */
    void prepareSuccessView(Recipe recipe);

    /**
     * Gives a failure message for a recipe.
     * @param error the error that occurred
     */
    void prepareFailView(String error);
}