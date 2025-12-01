package logic.user_recipe.view_recipes.view_detailed_recipe;

/**
 * Defines the operation of a presenter.
 * Should show an error message if the data is odd, then reprocess the user recipe list.
 */
public interface ViewUserRecipeDetailsOutputBoundary {
    /**
     * Shows the recipe as a new window.
     * @param outputData necessary information about the recipe
     */
    void presentSuccessView(ViewUserRecipeOutputData outputData);

    /**
     * Shows an error message, and re-renders the recipe summaries.
     */
    void presentFailureView();
}
