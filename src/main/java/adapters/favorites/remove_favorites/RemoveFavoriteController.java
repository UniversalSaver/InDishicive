package adapters.favorites.remove_favorites;

import entity.Recipe;
import logic.favorites.remove_favorite.RemoveFavoriteInputBoundary;
import logic.favorites.remove_favorite.RemoveFavoriteInputData;

/**
 * Controller for the remove favorite use case.
 * Handles user requests to remove a recipe from their favorites list.
 */
public class RemoveFavoriteController {

    private final RemoveFavoriteInputBoundary interactor;

    /**
     * Constructs a RemoveFavoriteController with the specified interactor.
     *
     * @param interactor the interactor that handles the remove favorite business logic
     */
    public RemoveFavoriteController(RemoveFavoriteInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes the remove favorite use case for the given recipe.
     *
     * @param recipe the recipe to remove from favorites
     */
    public void execute(Recipe recipe) {
        final RemoveFavoriteInputData inputData = new RemoveFavoriteInputData(recipe);
        interactor.execute(inputData);
    }
}
