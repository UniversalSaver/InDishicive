package logic.favorites.favorite_recipes;

import java.util.List;

import entity.Recipe;

/**
 * Data Access Interface for managing favorite recipes.
 */
public interface FavoriteDataAccessInterface {
    
    /**
     * Saves a recipe to the favorites list.
     * @param recipe the recipe to save
     */
    void saveFavorite(Recipe recipe);
    
    /**
     * Retrieves all favorite recipes.
     * @return list of all favorite recipes
     */
    List<Recipe> getFavorites();
    
    /**
     * Checks if a recipe is already favorited.
     * @param recipe the recipe to check
     * @return true if the recipe is in favorites, false otherwise
     */
    boolean isFavorite(Recipe recipe);

    void removeFavorite(Recipe recipe);
}

