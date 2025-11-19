package interface_adapter.add_favorite;
import entity.Recipe;
import use_case.add_favorite.AddFavoriteInputBoundary;
import use_case.add_favorite.AddFavoriteInputData;

public class AddFavoriteController{
    private final AddFavoriteInputBoundary interactor;
    public AddFavoriteController(AddFavoriteInputBoundary interactor){
        this.interactor = interactor;

    }

    public void execute(Recipe recipe){
        AddFavoriteInputData inputData = new AddFavoriteInputData(recipe);
        interactor.execute(inputData);
    }
}