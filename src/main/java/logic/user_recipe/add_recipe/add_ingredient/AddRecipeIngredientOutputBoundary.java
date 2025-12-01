package logic.user_recipe.add_recipe.add_ingredient;

/**
 * On failure, the database couldn't be accessed. On success, the database returned a list of ingredients, and this is
 * the parsed data.
 */
public interface AddRecipeIngredientOutputBoundary {
    /**
     * Creates a success view, with an additional ingredient for user to customize from options.
     * @param outputData options for ingredients
     */
    void presentSuccessView(AddRecipeIngredientOutputData outputData);

    /**
     * Presents a fail view to let user know their input was gotten, but something went wrong.
     */
    void presentFailView();
}
