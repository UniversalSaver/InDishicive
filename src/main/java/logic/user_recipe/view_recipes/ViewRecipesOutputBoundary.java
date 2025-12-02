package logic.user_recipe.view_recipes;

import java.util.List;

/**
 * Outlines expectations of the presenter.
 */
public interface ViewRecipesOutputBoundary {

    /**
     * Given list of recipes, present them to the user.
     * @param recipeInformation list of recipes to be shown
     */
    void prepareSuccessView(List<ViewRecipesOutputData> recipeInformation);
}
