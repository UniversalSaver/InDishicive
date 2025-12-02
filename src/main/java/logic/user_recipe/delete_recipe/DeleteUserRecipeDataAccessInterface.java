package logic.user_recipe.delete_recipe;

/**
 * Defines the expected features of a data access object for the delete user recipe use case.
 */
public interface DeleteUserRecipeDataAccessInterface {
    String DELETED = "deleted";

    /**
     * Deletes the recipe from the database, or if it's not found, returns as error message.
     * @param title title of recipe to be deleted
     * @return DELETED if deleted without issue, or error message
     */
    String deleteRecipe(String title);
}
