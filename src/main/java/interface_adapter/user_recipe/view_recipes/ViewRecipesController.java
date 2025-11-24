package interface_adapter.user_recipe.view_recipes;

import use_case.user_recipe.view_recipes.ViewRecipesInputBoundary;

public class ViewRecipesController {

    private final ViewRecipesInputBoundary viewRecipeUseCaseInteractor;

    public ViewRecipesController(ViewRecipesInputBoundary loginUseCaseInteractor) {
        viewRecipeUseCaseInteractor = loginUseCaseInteractor;
    }

    public void execute() {
        viewRecipeUseCaseInteractor.execute();
    }
}
