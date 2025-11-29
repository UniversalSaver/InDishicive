package logic.favorites.view_favorite;

import java.util.List;

import entity.Recipe;
import logic.favorites.favorite_recipes.FavoriteDataAccessInterface;

/**
 * Interactor for the view favorite use case.
 * Handles the business logic for retrieving and displaying the user's favorite recipes.
 */
public class ViewFavoriteInteractor implements ViewFavoriteInputBoundary {
    private final FavoriteDataAccessInterface favoriteDataAccess;
    private final ViewFavoriteOutputBoundary presenter;

    /**
     * Constructs a ViewFavoriteInteractor with the specified data access and presenter.
     *
     * @param favoriteDataAccess the data access interface for favorites operations
     * @param presenter the output boundary for presenting the results
     */
    public ViewFavoriteInteractor(FavoriteDataAccessInterface favoriteDataAccess,
                                ViewFavoriteOutputBoundary presenter) {
        this.favoriteDataAccess = favoriteDataAccess;
        this.presenter = presenter;
    }

    /**
     * Executes the view favorite use case.
     * Retrieves all favorite recipes and prepares the appropriate view based on whether
     * the list is empty or contains recipes.
     */
    @Override
    public void execute() {
        final List<Recipe> favorites = favoriteDataAccess.getFavorites();
        if (favorites.isEmpty()) {
            presenter.prepareEmptyView();
        }
        else {
            final ViewFavoriteOutputData outputData = new ViewFavoriteOutputData(favorites);
            presenter.prepareSuccessView(outputData);
        }
    }

}
