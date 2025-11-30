package logic.favorites.remove_favorite;
import entity.Recipe;

import java.util.List;

/**
 * Output data for the remove favorite use case.
 * contains the updated list of favorite recipes.
 */
public class RemoveFavoriteOutputData {
    private final List<Recipe> remainingFavorites;

    public RemoveFavoriteOutputData(List<Recipe> remainingFavorites) {
        this.remainingFavorites = remainingFavorites;
    }

    public List<Recipe> getRemainingFavorites() {
        return remainingFavorites;
    }

}
