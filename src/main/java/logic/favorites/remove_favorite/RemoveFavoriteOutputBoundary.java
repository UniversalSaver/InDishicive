package logic.favorites.remove_favorite;

/**
 * output boundary for the remove favorite use case.
 */
public interface RemoveFavoriteOutputBoundary {

    void prepareSuccessView(RemoveFavoriteOutputData outputData);

    void prepareFailureView(String errorMessage);
}
