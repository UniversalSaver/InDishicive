package adapters.favorites.view_favorite;

import java.util.ArrayList;

import logic.favorites.view_favorite.ViewFavoriteOutputBoundary;
import logic.favorites.view_favorite.ViewFavoriteOutputData;

/**
 * Presenter for the view favorite use case.
 * Prepares and updates the view model with the list of favorite recipes.
 */
public class ViewFavoritePresenter implements ViewFavoriteOutputBoundary {
    private final ViewFavoriteViewModel viewModel;

    /**
     * Constructs a ViewFavoritePresenter with the specified view model.
     *
     * @param viewModel the view model for displaying favorite recipes
     */
    public ViewFavoritePresenter(ViewFavoriteViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Prepares the success view when favorites are successfully retrieved.
     * Updates the view model with the list of favorite recipes.
     *
     * @param outputData the output data containing the favorite recipes
     */
    @Override
    public void prepareSuccessView(ViewFavoriteOutputData outputData) {
        viewModel.setState(outputData.getFavoriteRecipes());
        viewModel.firePropertyChange(ViewFavoriteViewModel.FAVORITE_LOADED);
        viewModel.firePropertyChange(ViewFavoriteViewModel.SET_VISIBLE);
    }

    /**
     * Prepares the view when the favorites list is empty.
     * Updates the view model with an empty list.
     */
    @Override
    public void prepareEmptyView() {
        viewModel.setState(new ArrayList<>());
        viewModel.firePropertyChange(ViewFavoriteViewModel.FAVORITE_LOADED);
        viewModel.firePropertyChange(ViewFavoriteViewModel.SET_VISIBLE);
    }
}
