package logic.favorites.favorite_recipes;

import entity.Recipe;

/**
 * Interface for removing favorite recipes.
 * Follows the Interface Segregation Principle by providing only remove operations.
 */
public interface FavoriteRemoverInterface {
    /**
     * Removes a recipe from favorites.
     *
     * @param recipe the recipe to remove
     */
    void removeFavorite(Recipe recipe);
}
