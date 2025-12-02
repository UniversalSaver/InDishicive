package adapters.user_recipe.view_recipes;

import logic.user_recipe.view_recipes.ViewRecipesInputBoundary;

/**
 * A controller for switching the view to user recipes.
 */
public class ViewRecipesController {

    private final ViewRecipesInputBoundary viewRecipeUseCaseInteractor;

    public ViewRecipesController(ViewRecipesInputBoundary viewRecipesUseCaseInteractor) {
        viewRecipeUseCaseInteractor = viewRecipesUseCaseInteractor;
    }

    /**
     * Runs program to switch view to made user recipes.
     */
    public void execute() {
        viewRecipeUseCaseInteractor.execute();
    }
}
