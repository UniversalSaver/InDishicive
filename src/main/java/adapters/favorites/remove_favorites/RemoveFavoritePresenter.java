package adapters.favorites.remove_favorites;

import adapters.favorites.view_favorite.ViewFavoriteViewModel;
import logic.favorites.remove_favorite.RemoveFavoriteOutputBoundary;
import logic.favorites.remove_favorite.RemoveFavoriteOutputData;

public class RemoveFavoritePresenter implements RemoveFavoriteOutputBoundary {

    private final RemoveFavoriteViewModel removeFavoriteViewModel;
    private final ViewFavoriteViewModel viewFavoriteViewModel;

    public RemoveFavoritePresenter(RemoveFavoriteViewModel removeFavoriteViewModel,
                                   ViewFavoriteViewModel viewFavoriteViewModel) {
        this.removeFavoriteViewModel = removeFavoriteViewModel;
        this.viewFavoriteViewModel = viewFavoriteViewModel;
    }

    @Override
    public void prepareSuccessView(RemoveFavoriteOutputData outputData) {
        RemoveFavoriteState state = removeFavoriteViewModel.getState();
        state.setStatusMessage("Recipe removed from favorites!");
        state.setSuccess(true);
        removeFavoriteViewModel.setState(state);
        removeFavoriteViewModel.firePropertyChange(RemoveFavoriteViewModel.FAVORITE_REMOVED);

        viewFavoriteViewModel.setState(outputData.getRemainingFavorites());
        viewFavoriteViewModel.firePropertyChange(ViewFavoriteViewModel.FAVORITE_LOADED);
    }

    @Override
    public void prepareFailureView(String errorMessage) {
        RemoveFavoriteState state = removeFavoriteViewModel.getState();
        state.setStatusMessage(errorMessage);
        state.setSuccess(false);
        removeFavoriteViewModel.setState(state);
        removeFavoriteViewModel.firePropertyChange(RemoveFavoriteViewModel.FAVORITE_REMOVED_FAILED);
    }
}
