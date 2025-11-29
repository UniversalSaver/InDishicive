package adapters.favorites.remove_favorites;

import entity.Recipe;
import logic.favorites.remove_favorite.RemoveFavoriteInputBoundary;
import logic.favorites.remove_favorite.RemoveFavoriteInputData;

public class RemoveFavoriteController {

    private final RemoveFavoriteInputBoundary interactor;
    public RemoveFavoriteController(RemoveFavoriteInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(Recipe recipe) {
        RemoveFavoriteInputData inputData = new RemoveFavoriteInputData(recipe);
        interactor.execute(inputData);
    }
}
