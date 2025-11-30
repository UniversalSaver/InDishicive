package logic.favorites.remove_favorite;

import java.util.List;

import entity.Recipe;

/**
 * Output data for the remove favorite use case.
 * Contains the updated list of favorite recipes after a removal operation.
 */
public class RemoveFavoriteOutputData {
    private final List<Recipe> remainingFavorites;

    /**
     * Constructs RemoveFavoriteOutputData with the remaining favorites list.
     *
     * @param remainingFavorites the list of recipes that remain in favorites after removal
     */
    public RemoveFavoriteOutputData(List<Recipe> remainingFavorites) {
        this.remainingFavorites = remainingFavorites;
    }

    /**
     * Gets the list of remaining favorite recipes.
     *
     * @return the list of recipes still in favorites
     */
    public List<Recipe> getRemainingFavorites() {
        return remainingFavorites;
    }

}
