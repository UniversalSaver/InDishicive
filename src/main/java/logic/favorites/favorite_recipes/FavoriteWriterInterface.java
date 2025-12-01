package logic.favorites.favorite_recipes;

import entity.Recipe;

/**
 * Interface for writing favorite recipes.
 * Follows the Interface Segregation Principle by providing only write operations.
 */
public interface FavoriteWriterInterface {
    /**
     * Saves a recipe to favorites.
     *
     * @param recipe the recipe to save
     */
    void saveFavorite(Recipe recipe);
}
