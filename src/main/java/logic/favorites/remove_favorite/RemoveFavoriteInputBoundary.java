package logic.favorites.remove_favorite;

/**
 * Input boundary for the remove favorite use case.
 * Defines the contract for executing the remove favorite operation.
 */
public interface RemoveFavoriteInputBoundary {
    /**
     * Executes the remove favorite use case with the provided input data.
     *
     * @param inputData the input data containing the recipe to remove from favorites
     */
    void execute(RemoveFavoriteInputData inputData);
}
