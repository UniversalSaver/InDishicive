package logic.favorites.view_favorite;

public interface ViewFavoriteOutputBoundary {
    void prepareSuccessView(ViewFavoriteOutputData outputData);
    
    void prepareEmptyView();
}