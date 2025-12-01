package adapters.user_recipe.delete_recipe;

import logic.user_recipe.delete_recipe.DeleteUserRecipeInputBoundary;
import logic.user_recipe.delete_recipe.DeleteUserRecipeInputData;

/**
 * The controller for the delete user recipe use case
 */
public class DeleteUserRecipeController {
    private final DeleteUserRecipeInputBoundary deleteUserRecipeInteractor;

    public DeleteUserRecipeController(DeleteUserRecipeInputBoundary deleteUserRecipeInteractor) {
        this.deleteUserRecipeInteractor = deleteUserRecipeInteractor;
    }

    public void execute(String recipeTitle) {
        deleteUserRecipeInteractor.execute(new DeleteUserRecipeInputData(recipeTitle));
    }
}
