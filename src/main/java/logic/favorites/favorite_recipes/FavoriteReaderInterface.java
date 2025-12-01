package logic.favorites.favorite_recipes;

import java.util.List;

import entity.Recipe;

/**
 * Interface for reading favorite recipes.
 * Follows the Interface Segregation Principle by providing only read operations.
 */
public interface FavoriteReaderInterface {
    /**
     * Gets all favorite recipes.
     *
     * @return list of favorite recipes
     */
    List<Recipe> getFavorites();

    /**
     * Checks if a recipe is in favorites.
     *
     * @param recipe the recipe to check
     * @return true if the recipe is in favorites, false otherwise
     */
    boolean isFavorite(Recipe recipe);
}
