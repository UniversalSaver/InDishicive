package adapters.user_recipe.view_recipes;

import java.util.List;

/**
 * A recipe state, which is used by the view model to send information about current view.
 */
public class ViewRecipesState {

    private final List<RecipeSummary> recipeSummaries;
    private final int numberOfRecipes;

    private final String errorMessage;

    public ViewRecipesState(List<RecipeSummary> recipeSummaries, int numberOfRecipes, String errorMessage) {
        this.recipeSummaries = recipeSummaries;
        this.numberOfRecipes = numberOfRecipes;
        this.errorMessage = errorMessage;
    }

    public List<RecipeSummary> getRecipeSummaries() {
        return recipeSummaries;
    }

    public int getNumberOfRecipes() {
        return numberOfRecipes;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
