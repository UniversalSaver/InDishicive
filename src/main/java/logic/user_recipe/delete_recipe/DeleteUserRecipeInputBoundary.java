package logic.user_recipe.delete_recipe;

/**
 * Input interface for deleting a recipe.
 */
public interface DeleteUserRecipeInputBoundary {
    /**
     * Given a title of a recipe to delete, deletes it, or gives an error message as to why it couldn't be.
     * @param inputData title of recipe deleted
     */
    void execute(DeleteUserRecipeInputData inputData);
}
