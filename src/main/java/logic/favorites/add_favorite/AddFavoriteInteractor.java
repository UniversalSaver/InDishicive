package logic.favorites.add_favorite;

import entity.Recipe;
import logic.favorites.favorite_recipes.FavoriteReaderInterface;
import logic.favorites.favorite_recipes.FavoriteWriterInterface;
import logic.generate_recipe.view_recipe_details.RecipeDetailsGateway;

/**
 * Interactor for the add favorite use case.
 */
public class AddFavoriteInteractor implements AddFavoriteInputBoundary {

    private final FavoriteReaderInterface favoriteReader;
    private final FavoriteWriterInterface favoriteWriter;
    private final AddFavoriteOutputBoundary presenter;
    private final RecipeDetailsGateway recipeDetailsGateway;

    /**
     * Constructs an AddFavoriteInteractor with the specified dependencies.
     *
     * @param favoriteReader the reader interface for checking favorites
     * @param favoriteWriter the writer interface for adding favorites
     * @param presenter the output boundary for presenting the result
     * @param recipeDetailsGateway the gateway for fetching recipe details by title
     */
    public AddFavoriteInteractor(FavoriteReaderInterface favoriteReader,
                                  FavoriteWriterInterface favoriteWriter,
                                  AddFavoriteOutputBoundary presenter,
                                  RecipeDetailsGateway recipeDetailsGateway) {
        this.favoriteReader = favoriteReader;
        this.favoriteWriter = favoriteWriter;
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
        if (favoriteReader.isFavorite(recipe)) {
            presenter.prepareFailView("Already in Favorites!");
        }
        else {
            favoriteWriter.saveFavorite(recipe);
            presenter.prepareSuccessView();
        }
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
        }
        else if (favoriteReader.isFavorite(recipe)) {
            presenter.prepareFailView("Already in Favorites!");
        }
        else {
            favoriteWriter.saveFavorite(recipe);
            presenter.prepareSuccessView();
        }
    }

}
