package adapters.user_recipe.view_recipes;

import logic.user_recipe.view_recipes.ViewRecipesInputBoundary;

public class ViewRecipesController {

    private final ViewRecipesInputBoundary viewRecipeUseCaseInteractor;

    public ViewRecipesController(ViewRecipesInputBoundary loginUseCaseInteractor) {
        viewRecipeUseCaseInteractor = loginUseCaseInteractor;
    }

    public void execute() {
        viewRecipeUseCaseInteractor.execute();
    }
}
