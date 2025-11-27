package interface_adapter.favorites.view_favorite;

import use_case.favorites.view_favorite.ViewFavoriteInputBoundary;

public class ViewFavoriteController {
    private final ViewFavoriteInputBoundary interactor;
    public ViewFavoriteController(ViewFavoriteInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(){
        interactor.execute();
    }
}
