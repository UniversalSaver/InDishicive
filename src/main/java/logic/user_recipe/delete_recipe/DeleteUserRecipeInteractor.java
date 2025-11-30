package logic.user_recipe.delete_recipe;

import logic.user_recipe.view_recipes.ViewRecipesInteractor;

/**
 * Concrete implementation of the input boundary.
 */
public class DeleteUserRecipeInteractor implements DeleteUserRecipeInputBoundary {
    private final ViewRecipesInteractor viewRecipesInteractor;
    private final DeleteUserRecipeDataAccessInterface database;

    public DeleteUserRecipeInteractor(ViewRecipesInteractor viewRecipesInteractor,
                                      DeleteUserRecipeDataAccessInterface database) {
        this.viewRecipesInteractor = viewRecipesInteractor;
        this.database = database;
    }

    @Override
    public void execute(DeleteUserRecipeInputData inputData) {
        final String deleted = database.deleteRecipe(inputData.getRecipeTitle());

        if (deleted.equals(DeleteUserRecipeDataAccessInterface.DELETED)) {
            viewRecipesInteractor.execute();
        } else {
            // Supposed to do something, but what, I'm not sure.
            // TODO
        }
    }
}
