package adapters.user_recipe.delete_recipe;

import com.sun.source.doctree.EndElementTree;
import logic.user_recipe.delete_recipe.DeleteUserRecipeInputBoundary;
import logic.user_recipe.delete_recipe.DeleteUserRecipeInputData;

public class DeleteUserRecipeController {
    private final DeleteUserRecipeInputBoundary deleteUserRecipeInteractor;

    public DeleteUserRecipeController(DeleteUserRecipeInputBoundary deleteUserRecipeInteractor) {
        this.deleteUserRecipeInteractor = deleteUserRecipeInteractor;
    }

    public void execute(String recipeTitle) {
        deleteUserRecipeInteractor.execute(new DeleteUserRecipeInputData(recipeTitle));
    }
}
