package interface_adapter.view_favorite;
import use_case.view_favorite.ViewFavoriteOutputBoundary;
import use_case.view_favorite.ViewFavoriteOutputData;
import java.util.ArrayList;

public class ViewFavoritePresenter implements ViewFavoriteOutputBoundary{
    private final ViewFavoriteViewModel viewModel;

    public ViewFavoritePresenter(ViewFavoriteViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(ViewFavoriteOutputData outputData){
        viewModel.setState(outputData.getFavoriteRecipes());
        viewModel.firePropertyChange(ViewFavoriteViewModel.FAV_LOADED);
    }

    @Override
    public void prepareEmptyView() {
        viewModel.setState(new ArrayList<>());
        viewModel.firePropertyChange(ViewFavoriteViewModel.FAV_LOADED);
    }
}