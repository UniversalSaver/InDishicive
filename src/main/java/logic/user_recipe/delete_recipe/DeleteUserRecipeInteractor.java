package logic.user_recipe.delete_recipe;

import logic.user_recipe.view_recipes.ViewRecipesInteractor;
import logic.user_recipe.view_recipes.view_detailed_recipe.ViewUserRecipeDetailsOutputBoundary;

/**
 * Concrete implementation of the input boundary.
 */
public class DeleteUserRecipeInteractor implements DeleteUserRecipeInputBoundary {
    private final ViewRecipesInteractor viewRecipesInteractor;
    private final DeleteUserRecipeDataAccessInterface database;
    private final ViewUserRecipeDetailsOutputBoundary viewRecipeDetailsPresenter;

    public DeleteUserRecipeInteractor(ViewRecipesInteractor viewRecipesInteractor,
                                      DeleteUserRecipeDataAccessInterface database,
                                      ViewUserRecipeDetailsOutputBoundary viewRecipeDetailsPresenter) {
        this.viewRecipesInteractor = viewRecipesInteractor;
        this.database = database;
        this.viewRecipeDetailsPresenter = viewRecipeDetailsPresenter;
    }

    @Override
    public void execute(DeleteUserRecipeInputData inputData) {
        final String deleted = database.deleteRecipe(inputData.getRecipeTitle());

        if (deleted.equals(DeleteUserRecipeDataAccessInterface.DELETED)) {
            viewRecipesInteractor.execute();
        } else {
            viewRecipeDetailsPresenter.presentFailureView();
        }
    }
}
