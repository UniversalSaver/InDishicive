package logic.favorites.view_favorite;

import java.util.List;

import entity.Recipe;

/**
 * Output data for the view favorite use case.
 * Contains the list of favorite recipes retrieved from storage.
 */
public class ViewFavoriteOutputData {

    private final List<Recipe> favoriteRecipes;

    /**
     * Constructs ViewFavoriteOutputData with the list of favorite recipes.
     *
     * @param favoriteRecipes the list of recipes marked as favorites
     */
    public ViewFavoriteOutputData(List<Recipe> favoriteRecipes) {
        this.favoriteRecipes = favoriteRecipes;
    }

    /**
     * Gets the list of favorite recipes.
     *
     * @return the list of favorite recipes
     */
    public List<Recipe> getFavoriteRecipes() {
        return favoriteRecipes;
    }
}
