package logic.favorites.view_favorite;

/**
 * Output boundary for the view favorite use case.
 * Defines the contract for presenting the results of viewing favorites.
 */
public interface ViewFavoriteOutputBoundary {
    /**
     * Prepares the view for successfully retrieved favorite recipes.
     *
     * @param outputData the output data containing the list of favorite recipes
     */
    void prepareSuccessView(ViewFavoriteOutputData outputData);

    /**
     * Prepares the view when the favorites list is empty.
     */
    void prepareEmptyView();
}
