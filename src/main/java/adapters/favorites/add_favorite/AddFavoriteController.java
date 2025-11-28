package adapters.favorites.add_favorite;
import entity.Recipe;
import logic.favorites.add_favorite.AddFavoriteInputBoundary;
import logic.favorites.add_favorite.AddFavoriteInputData;

public class AddFavoriteController{
    private final AddFavoriteInputBoundary interactor;
    public AddFavoriteController(AddFavoriteInputBoundary interactor){
        this.interactor = interactor;

    }

    public void execute(Recipe recipe){
        AddFavoriteInputData inputData = new AddFavoriteInputData(recipe);
        interactor.execute(inputData);
    }

    public void execute(String recipeTitle){
        interactor.execute(recipeTitle);
    }
}