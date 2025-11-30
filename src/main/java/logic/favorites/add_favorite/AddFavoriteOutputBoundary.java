package logic.favorites.add_favorite;

/**
 * Output boundary for the add favorite use case.
 * Defines the contract for presenting the results of an add favorite operation.
 */
public interface AddFavoriteOutputBoundary {

    /**
     * Prepares the success view when a recipe is successfully added to favorites.
     */
    void prepareSuccessView();

    /**
     * Prepares the failure view when adding a recipe to favorites fails.
     *
     * @param errorMessage the error message describing why the operation failed
     */
    void prepareFailView(String errorMessage);
}
