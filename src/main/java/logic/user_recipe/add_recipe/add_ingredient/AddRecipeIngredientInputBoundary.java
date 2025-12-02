package logic.user_recipe.add_recipe.add_ingredient;

/**
 * Uses the database to get a list of ingredients, then prompts the presenter to present them as a choice to the user.
 */
public interface AddRecipeIngredientInputBoundary {
    /**
     * Queries database for a list of ingredients, then prompts presenter to
     * give user a select box with those ingredients.
     */
    void execute();
}
