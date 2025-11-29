package logic.favorites.remove_favorite;

import java.util.List;

import entity.Recipe;
import logic.favorites.favorite_recipes.FavoriteDataAccessInterface;

/**
 * Interactor for the remove favorite use case.
 * Handles the business logic for removing a recipe from the user's favorites list.
 */
public class RemoveFavoriteInteractor implements RemoveFavoriteInputBoundary {
    private final FavoriteDataAccessInterface favoriteDataAccess;
    private final RemoveFavoriteOutputBoundary presenter;

    /**
     * Constructs a RemoveFavoriteInteractor with the specified data access and presenter.
     *
     * @param favoriteDataAccess the data access interface for favorites operations
     * @param presenter the output boundary for presenting the result
     */
    public RemoveFavoriteInteractor(FavoriteDataAccessInterface favoriteDataAccess,
                                    RemoveFavoriteOutputBoundary presenter) {
        this.favoriteDataAccess = favoriteDataAccess;
        this.presenter = presenter;
    }

    /**
     * Executes the remove favorite use case.
     * Validates that the recipe is in favorites, removes it, and prepares the appropriate view.
     *
     * @param inputData the input data containing the recipe to remove
     */
    @Override
    public void execute(RemoveFavoriteInputData inputData) {
        final Recipe recipe = inputData.getRecipe();

        // Validate that the recipe is actually in favorites
        if (!favoriteDataAccess.isFavorite(recipe)) {
            presenter.prepareFailureView("Recipe is not in favorites!");
            return;
        }

        favoriteDataAccess.removeFavorite(recipe);
        final List<Recipe> remainingFavorites = favoriteDataAccess.getFavorites();
        final RemoveFavoriteOutputData outputData = new RemoveFavoriteOutputData(remainingFavorites);

        presenter.prepareSuccessView(outputData);
    }
}
