package adapters.favorites.remove_favorites;

import adapters.favorites.view_favorite.ViewFavoriteViewModel;
import logic.favorites.remove_favorite.RemoveFavoriteOutputBoundary;
import logic.favorites.remove_favorite.RemoveFavoriteOutputData;

/**
 * Presenter for the remove favorite use case.
 * Prepares and updates the view models based on the result of removing a favorite recipe.
 */
public class RemoveFavoritePresenter implements RemoveFavoriteOutputBoundary {

    private final RemoveFavoriteViewModel removeFavoriteViewModel;
    private final ViewFavoriteViewModel viewFavoriteViewModel;

    /**
     * Constructs a RemoveFavoritePresenter with the specified view models.
     *
     * @param removeFavoriteViewModel the view model for the remove favorite operation
     * @param viewFavoriteViewModel the view model for displaying the favorites list
     */
    public RemoveFavoritePresenter(RemoveFavoriteViewModel removeFavoriteViewModel,
                                   ViewFavoriteViewModel viewFavoriteViewModel) {
        this.removeFavoriteViewModel = removeFavoriteViewModel;
        this.viewFavoriteViewModel = viewFavoriteViewModel;
    }

    /**
     * Prepares the success view when a recipe is successfully removed from favorites.
     * Updates both the remove favorite state and the favorites list view.
     *
     * @param outputData the output data containing the remaining favorite recipes
     */
    @Override
    public void prepareSuccessView(RemoveFavoriteOutputData outputData) {
        final RemoveFavoriteState state = removeFavoriteViewModel.getState();
        state.setStatusMessage("Recipe removed from favorites!");
        state.setSuccess(true);
        removeFavoriteViewModel.setState(state);
        removeFavoriteViewModel.firePropertyChange(RemoveFavoriteViewModel.FAVORITE_REMOVED);

        viewFavoriteViewModel.setState(outputData.getRemainingFavorites());
        viewFavoriteViewModel.firePropertyChange(ViewFavoriteViewModel.FAVORITE_LOADED);
    }

    /**
     * Prepares the failure view when removing a recipe from favorites fails.
     *
     * @param errorMessage the error message describing why the operation failed
     */
    @Override
    public void prepareFailureView(String errorMessage) {
        final RemoveFavoriteState state = removeFavoriteViewModel.getState();
        state.setStatusMessage(errorMessage);
        state.setSuccess(false);
        removeFavoriteViewModel.setState(state);
        removeFavoriteViewModel.firePropertyChange(RemoveFavoriteViewModel.FAVORITE_REMOVED_FAILED);
    }
}
