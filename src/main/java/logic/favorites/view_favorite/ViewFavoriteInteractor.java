package logic.favorites.view_favorite;

import java.util.List;

import entity.Recipe;
import logic.favorites.favorite_recipes.FavoriteReaderInterface;

/**
 * Interactor for the view favorite use case.
 * Handles the business logic for retrieving and displaying the user's favorite recipes.
 * Uses segregated interfaces following ISP - only depends on read operations.
 */
public class ViewFavoriteInteractor implements ViewFavoriteInputBoundary {
    private final FavoriteReaderInterface favoriteReader;
    private final ViewFavoriteOutputBoundary presenter;

    /**
     * Constructs a ViewFavoriteInteractor with the specified data access and presenter.
     *
     * @param favoriteReader the reader interface for retrieving favorites
     * @param presenter the output boundary for presenting the results
     */
    public ViewFavoriteInteractor(FavoriteReaderInterface favoriteReader,
                                ViewFavoriteOutputBoundary presenter) {
        this.favoriteReader = favoriteReader;
        this.presenter = presenter;
    }

    /**
     * Executes the view favorite use case.
     * Retrieves all favorite recipes and prepares the appropriate view based on whether
     * the list is empty or contains recipes.
     */
    @Override
    public void execute() {
        final List<Recipe> favorites = favoriteReader.getFavorites();
        if (favorites.isEmpty()) {
            presenter.prepareEmptyView();
        }
        else {
            final ViewFavoriteOutputData outputData = new ViewFavoriteOutputData(favorites);
            presenter.prepareSuccessView(outputData);
        }
    }

}
