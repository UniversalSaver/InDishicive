package logic.favorites.view_favorite;

/**
 * Input boundary for the view favorite use case.
 * Defines the contract for executing the view favorites operation.
 */
public interface ViewFavoriteInputBoundary {
    /**
     * Executes the view favorite use case to retrieve all favorite recipes.
     */
    void execute();
}
