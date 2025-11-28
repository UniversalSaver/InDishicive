package logic.favorites.view_favorite;
import entity.Recipe;
import java.util.List;

public class ViewFavoriteOutputData {

    private final List<Recipe> favoriteRecipes;

    public ViewFavoriteOutputData(List<Recipe> favoriteRecipes) {
        this.favoriteRecipes = favoriteRecipes;
    }

    public List<Recipe> getFavoriteRecipes() {
        return favoriteRecipes;
    }
}