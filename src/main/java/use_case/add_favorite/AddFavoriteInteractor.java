package use_case.add_favorite;
import entity.Recipe;
import use_case.favorite_recipes.FavoriteDataAccessInterface;

/**
 * Interactor for the Add Favorite use case.
 * Contains the logic for adding a recipe to favorites.
 */
public class AddFavoriteInteractor implements AddFavoriteInputBoundary{

    private final FavoriteDataAccessInterface favoriteDataAccess;
    private final AddFavoriteOutputBoundary presenter;
    
    public AddFavoriteInteractor(FavoriteDataAccessInterface favoriteDataAccess,
                                  AddFavoriteOutputBoundary presenter) {
        this.favoriteDataAccess = favoriteDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(AddFavoriteInputBoundary, inputData){
        Recipe recipe = inputData.getRecipe();
        if (favoriteDataAccess.isFavorite(recipe)){
            presenter.prepareFailView("Already in Favorites!");
            return;
        }

        favoriteDataAccess.saveFavorite(recipe);
        presenter.prepareSuccessView();
    }



}