package use_case.favorites.view_favorite;

public interface ViewFavoriteOutputBoundary {
    void prepareSuccessView(ViewFavoriteOutputData outputData);
    
    void prepareEmptyView();
}