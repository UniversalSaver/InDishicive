package adapters.favorites.add_favorite;

import entity.Recipe;
import logic.favorites.add_favorite.AddFavoriteInputBoundary;
import logic.favorites.add_favorite.AddFavoriteInputData;

/**
 * Controller for the add favorite use case.
 * Handles user requests to add a recipe to their favorites list.
 */
public class AddFavoriteController {
    private final AddFavoriteInputBoundary interactor;

    /**
     * Constructs an AddFavoriteController with the specified interactor.
     *
     * @param interactor the interactor that handles the add favorite business logic
     */
    public AddFavoriteController(AddFavoriteInputBoundary interactor) {
        this.interactor = interactor;

    }

    /**
     * Executes the add favorite use case for the given recipe entity.
     *
     * @param recipe the recipe to add to favorites
     */
    public void execute(Recipe recipe) {
        final AddFavoriteInputData inputData = new AddFavoriteInputData(recipe);
        interactor.execute(inputData);
    }

    /**
     * Executes the add favorite use case for a recipe identified by its title.
     * This method fetches the recipe details before adding to favorites.
     *
     * @param recipeTitle the title of the recipe to add to favorites
     */
    public void execute(String recipeTitle) {
        interactor.execute(recipeTitle);
    }
}
