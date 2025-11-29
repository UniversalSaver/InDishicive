package logic.favorites.add_favorite;

import entity.Recipe;
import logic.favorites.favorite_recipes.FavoriteDataAccessInterface;
import logic.generate_recipe.view_recipe_details.RecipeDetailsGateway;

/**
 * Interactor for the add favorite use case.
 * Handles the business logic for adding a recipe to the user's favorites list.
 */
public class AddFavoriteInteractor implements AddFavoriteInputBoundary {

    private final FavoriteDataAccessInterface favoriteDataAccess;
    private final AddFavoriteOutputBoundary presenter;
    private final RecipeDetailsGateway recipeDetailsGateway;

    /**
     * Constructs an AddFavoriteInteractor with the specified dependencies.
     *
     * @param favoriteDataAccess the data access interface for favorites operations
     * @param presenter the output boundary for presenting the result
     * @param recipeDetailsGateway the gateway for fetching recipe details by title
     */
    public AddFavoriteInteractor(FavoriteDataAccessInterface favoriteDataAccess,
                                  AddFavoriteOutputBoundary presenter,
                                  RecipeDetailsGateway recipeDetailsGateway) {
        this.favoriteDataAccess = favoriteDataAccess;
        this.presenter = presenter;
        this.recipeDetailsGateway = recipeDetailsGateway;
    }

    /**
     * Executes the add favorite use case with the provided input data.
     * Validates that the recipe is not already a favorite before adding it.
     *
     * @param inputData the input data containing the recipe to add
     */
    @Override
    public void execute(AddFavoriteInputData inputData) {
        final Recipe recipe = inputData.getRecipe();
        if (favoriteDataAccess.isFavorite(recipe)) {
            presenter.prepareFailView("Already in Favorites!");
            return;
        }

        favoriteDataAccess.saveFavorite(recipe);
        presenter.prepareSuccessView();
    }

    /**
     * Executes the add favorite use case using a recipe title.
     * Fetches the recipe details, validates it exists and is not already a favorite,
     * then adds it to favorites.
     *
     * @param recipeTitle the title of the recipe to add to favorites
     */
    public void execute(String recipeTitle) {
        final Recipe recipe = recipeDetailsGateway.findByTitle(recipeTitle);

        if (recipe == null) {
            presenter.prepareFailView("Recipe not found!");
            return;
        }

        if (favoriteDataAccess.isFavorite(recipe)) {
            presenter.prepareFailView("Already in Favorites!");
            return;
        }

        favoriteDataAccess.saveFavorite(recipe);
        presenter.prepareSuccessView();
    }

}
