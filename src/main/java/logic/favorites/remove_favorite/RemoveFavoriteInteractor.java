package logic.favorites.remove_favorite;

import java.util.List;

import entity.Recipe;
import logic.favorites.favorite_recipes.FavoriteReaderInterface;
import logic.favorites.favorite_recipes.FavoriteRemoverInterface;

/**
 * Interactor for the remove favorite use case.
 * Handles the business logic for removing a recipe from the user's favorites list.
 */
public class RemoveFavoriteInteractor implements RemoveFavoriteInputBoundary {
    private final FavoriteReaderInterface favoriteReader;
    private final FavoriteRemoverInterface favoriteRemover;
    private final RemoveFavoriteOutputBoundary presenter;

    /**
     * Constructs a RemoveFavoriteInteractor with the specified data access and presenter.
     *
     * @param favoriteReader the reader interface for checking and retrieving favorites
     * @param favoriteRemover the remover interface for removing favorites
     * @param presenter the output boundary for presenting the result
     */
    public RemoveFavoriteInteractor(FavoriteReaderInterface favoriteReader,
                                    FavoriteRemoverInterface favoriteRemover,
                                    RemoveFavoriteOutputBoundary presenter) {
        this.favoriteReader = favoriteReader;
        this.favoriteRemover = favoriteRemover;
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
        if (!favoriteReader.isFavorite(recipe)) {
            presenter.prepareFailureView("Recipe is not in favorites!");
        }
        else {
            favoriteRemover.removeFavorite(recipe);
            final List<Recipe> remainingFavorites = favoriteReader.getFavorites();
            final RemoveFavoriteOutputData outputData = new RemoveFavoriteOutputData(remainingFavorites);

            presenter.prepareSuccessView(outputData);
        }
    }
}
