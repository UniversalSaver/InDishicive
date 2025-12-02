package logic.favorites.remove_favorite;

/**
 * Output boundary for the remove favorite use case.
 * Defines the contract for presenting the results of a remove favorite operation.
 */
public interface RemoveFavoriteOutputBoundary {

    /**
     * Prepares the view for a successful remove favorite operation.
     *
     * @param outputData the output data containing the remaining favorite recipes
     */
    void prepareSuccessView(RemoveFavoriteOutputData outputData);

    /**
     * Prepares the view for a failed remove favorite operation.
     *
     * @param errorMessage the error message describing why the operation failed
     */
    void prepareFailureView(String errorMessage);
}
