package logic.favorites.view_favorite;
import entity.Recipe;
import logic.favorites.favorite_recipes.FavoriteDataAccessInterface;
import java.util.List;

public class ViewFavoriteInteractor implements ViewFavoriteInputBoundary{
    private final FavoriteDataAccessInterface favoriteDataAccess;
    private final ViewFavoriteOutputBoundary presenter;

    public ViewFavoriteInteractor(FavoriteDataAccessInterface favoriteDataAccess,
                                ViewFavoriteOutputBoundary presenter){
        this.favoriteDataAccess = favoriteDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(){
        List<Recipe> favorites = favoriteDataAccess.getFavorites();
        if(favorites.isEmpty()){
            presenter.prepareEmptyView();
        } else {
            ViewFavoriteOutputData outputData = new  ViewFavoriteOutputData(favorites);
            presenter.prepareSuccessView(outputData);
        }
    }

}