package logic.favorites.add_favorite;

/**
 * Input boundary for the Add Favorite use case.
 */
public interface AddFavoriteInputBoundary {

    /**
     * Executes the add favorite use case.
     * @param inputData the input data containing the recipe to favorite
     */
    void execute(AddFavoriteInputData inputData);

    /**
     * Executes the add favorite use case with a recipe title.
     * @param recipeTitle the title of the recipe to favorite
     */
    void execute(String recipeTitle);
}