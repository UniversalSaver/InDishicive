package logic.favorites.remove_favorite;

import entity.Recipe;
import logic.favorites.favorite_recipes.FavoriteDataAccessInterface;
import java.util.List;

/**
 * interactor for the remove favorite use case
 */
public class RemoveFavoriteInteractor implements RemoveFavoriteInputBoundary {
    private final FavoriteDataAccessInterface favoriteDataAccess;
    private final RemoveFavoriteOutputBoundary presenter;

    public RemoveFavoriteInteractor(FavoriteDataAccessInterface favoriteDataAccess,
                                    RemoveFavoriteOutputBoundary presenter) {
        this.favoriteDataAccess = favoriteDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(RemoveFavoriteInputData inputData) {
        Recipe recipe = inputData.getRecipe();

        // incase somehow it isnt
        if (!favoriteDataAccess.isFavorite(recipe)) {
            presenter.prepareFailureView("Recipe is not favorite!");
            return;
        }

        favoriteDataAccess.removeFavorite(recipe);

        List<Recipe> remainingFavorites = favoriteDataAccess.getFavorites();
        RemoveFavoriteOutputData outputData = new RemoveFavoriteOutputData(remainingFavorites);

        presenter.prepareSuccessView(outputData);
    }
}
