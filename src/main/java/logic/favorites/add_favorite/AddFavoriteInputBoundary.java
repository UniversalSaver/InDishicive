package logic.favorites.add_favorite;

/**
 * Input boundary for the add favorite use case.
 * Defines the contract for executing the add favorite operation.
 */
public interface AddFavoriteInputBoundary {

    /**
     * Executes the add favorite use case with the provided input data.
     *
     * @param inputData the input data containing the recipe to add to favorites
     */
    void execute(AddFavoriteInputData inputData);

    /**
     * Executes the add favorite use case with a recipe title.
     * Fetches the recipe details and adds it to favorites.
     *
     * @param recipeTitle the title of the recipe to add to favorites
     */
    void execute(String recipeTitle);
}
