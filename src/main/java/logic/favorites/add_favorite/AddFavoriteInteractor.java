package logic.favorites.add_favorite;
import entity.Recipe;
import logic.favorites.favorite_recipes.FavoriteDataAccessInterface;
import logic.generate_recipe.view_recipe_details.RecipeDetailsGateway;

/**
 * Interactor for the Add Favorite use case.
 * Contains the logic for adding a recipe to favorites.
 */
public class AddFavoriteInteractor implements AddFavoriteInputBoundary{

    private final FavoriteDataAccessInterface favoriteDataAccess;
    private final AddFavoriteOutputBoundary presenter;
    private final RecipeDetailsGateway recipeDetailsGateway;

    public AddFavoriteInteractor(FavoriteDataAccessInterface favoriteDataAccess,
                                  AddFavoriteOutputBoundary presenter,
                                  RecipeDetailsGateway recipeDetailsGateway) {
        this.favoriteDataAccess = favoriteDataAccess;
        this.presenter = presenter;
        this.recipeDetailsGateway = recipeDetailsGateway;
    }

    @Override
    public void execute(AddFavoriteInputData inputData){
        Recipe recipe = inputData.getRecipe();
        if (favoriteDataAccess.isFavorite(recipe)){
            presenter.prepareFailView("Already in Favorites!");
            return;
        }

        favoriteDataAccess.saveFavorite(recipe);
        presenter.prepareSuccessView();
    }

    public void execute(String recipeTitle){
        Recipe recipe = recipeDetailsGateway.findByTitle(recipeTitle);

        if (recipe == null) {
            presenter.prepareFailView("Recipe not found!");
            return;
        }

        if (favoriteDataAccess.isFavorite(recipe)){
            presenter.prepareFailView("Already in Favorites!");
            return;
        }

        favoriteDataAccess.saveFavorite(recipe);
        presenter.prepareSuccessView();
    }

}