package use_case.view_favorite;

public interface ViewFavoriteOutputBoundary {
    void prepareSuccessView(ViewFavoriteOutputData outputData);
    
    void prepareEmptyView();
}