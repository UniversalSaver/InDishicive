package logic.favorites.add_favorite;

/**
 * Output boundary for the Add Favorite use case.
 */
public interface AddFavoriteOutputBoundary {

    /**
     * Prepares the success view when a recipe is successfully added to favorites.
     */
    void prepareSuccessView();

    /**
     * Prepares the fail view when adding to favorites fails.
     * @param errorMessage the error message to display
     */
    void prepareFailView(String errorMessage);
}