package adapters.user_recipe.view_recipes.view_detailed_recipe;

import logic.user_recipe.view_recipes.view_detailed_recipe.ViewUserRecipeDetailsInputBoundary;
import logic.user_recipe.view_recipes.view_detailed_recipe.ViewUserRecipeDetailsInputData;

/**
 * A controller for the view detailed recipe use case.
 */
public class ViewUserRecipeDetailsController {
    private final ViewUserRecipeDetailsInputBoundary viewUserRecipeDetailsInteractor;

    public ViewUserRecipeDetailsController(ViewUserRecipeDetailsInputBoundary viewUserRecipeDetailsInteractor) {
        this.viewUserRecipeDetailsInteractor = viewUserRecipeDetailsInteractor;
    }

    /**
     * Given a title to execute on, creates a window for the detailed recipe version, or gives user an error.
     * @param inputData title of recipe desired
     */
    public void execute(ViewUserRecipeDetailsInputData inputData) {
        viewUserRecipeDetailsInteractor.execute(inputData);
    }
}
