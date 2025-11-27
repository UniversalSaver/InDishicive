package adapters.favorites.view_favorite;
import logic.favorites.view_favorite.ViewFavoriteOutputBoundary;
import logic.favorites.view_favorite.ViewFavoriteOutputData;
import java.util.ArrayList;

public class ViewFavoritePresenter implements ViewFavoriteOutputBoundary{
    private final ViewFavoriteViewModel viewModel;

    public ViewFavoritePresenter(ViewFavoriteViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(ViewFavoriteOutputData outputData){
        viewModel.setState(outputData.getFavoriteRecipes());
        viewModel.firePropertyChange(ViewFavoriteViewModel.FAVORITE_LOADED);
        viewModel.firePropertyChange(ViewFavoriteViewModel.SET_VISIBLE);
    }

    @Override
    public void prepareEmptyView() {
        viewModel.setState(new ArrayList<>());
        viewModel.firePropertyChange(ViewFavoriteViewModel.FAVORITE_LOADED);
        viewModel.firePropertyChange(ViewFavoriteViewModel.SET_VISIBLE);
    }
}