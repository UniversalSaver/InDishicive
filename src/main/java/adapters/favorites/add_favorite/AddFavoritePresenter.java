package adapters.favorites.add_favorite;

import logic.favorites.add_favorite.AddFavoriteOutputBoundary;

/**
 * Presenter for the add favorite use case.
 * Prepares and updates the view model based on the result of adding a recipe to favorites.
 */
public class AddFavoritePresenter implements AddFavoriteOutputBoundary {

    private final AddFavoriteViewModel viewModel;

    /**
     * Constructs an AddFavoritePresenter with the specified view model.
     *
     * @param viewModel the view model for the add favorite operation
     */
    public AddFavoritePresenter(AddFavoriteViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Prepares the success view when a recipe is successfully added to favorites.
     */
    @Override
    public void prepareSuccessView() {
        final AddFavoriteState state = viewModel.getState();
        state.setStatusMessage("Recipe added to favorites!");
        state.setSuccess(true);
        viewModel.setState(state);
        viewModel.firePropertyChange(AddFavoriteViewModel.FAVORITE_ADDED);
    }

    /**
     * Prepares the failure view when adding a recipe to favorites fails.
     *
     * @param errorMessage the error message describing why the operation failed
     */
    @Override
    public void prepareFailView(String errorMessage) {
        final AddFavoriteState state = viewModel.getState();
        state.setStatusMessage(errorMessage);
        state.setSuccess(false);
        viewModel.setState(state);
        viewModel.firePropertyChange(AddFavoriteViewModel.FAVORITE_FAILED);
    }
}
