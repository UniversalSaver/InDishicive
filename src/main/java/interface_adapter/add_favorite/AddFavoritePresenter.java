package interface_adapter.add_favorite;
import use_case.add_favorite.AddFavoriteOutputBoundary;

public class AddFavoritePresenter implements AddFavoriteOutputBoundary{
     private final AddFavoriteViewModel viewModel;
     
     public AddFavoritePresenter(AddFavoriteViewModel viewModel){
        this.viewModel = viewModel;
     }

     @Override
     public void prepareSuccessView() {
        viewModel.firePropertyChange(AddFavoriteViewModel.FAVORITE_ADDED);
    }

    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.firePropertyChange(AddFavoriteViewModel.FAVORITE_FAILED);
    }
} 