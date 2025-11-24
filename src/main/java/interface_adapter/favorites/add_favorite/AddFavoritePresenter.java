package interface_adapter.favorites.add_favorite;
import use_case.favorites.add_favorite.AddFavoriteOutputBoundary;


public class AddFavoritePresenter implements AddFavoriteOutputBoundary {

    private final AddFavoriteViewModel viewModel;

    public AddFavoritePresenter(AddFavoriteViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView() {
        AddFavoriteState state = viewModel.getState();
        state.setStatusMessage("Recipe added to favorites!");
        state.setSuccess(true);
        viewModel.setState(state);
        viewModel.firePropertyChange(AddFavoriteViewModel.FAVORITE_ADDED);
    }

    @Override
    public void prepareFailView(String errorMessage) {
        AddFavoriteState state = viewModel.getState();
        state.setStatusMessage(errorMessage);
        state.setSuccess(false);
        viewModel.setState(state);
        viewModel.firePropertyChange(AddFavoriteViewModel.FAVORITE_FAILED);
    }
} 