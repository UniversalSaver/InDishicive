package adapters.favorites.view_favorite;

import logic.favorites.view_favorite.ViewFavoriteInputBoundary;

public class ViewFavoriteController {
    private final ViewFavoriteInputBoundary interactor;
    public ViewFavoriteController(ViewFavoriteInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(){
        interactor.execute();
    }
}
