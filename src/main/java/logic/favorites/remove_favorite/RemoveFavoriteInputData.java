package logic.favorites.remove_favorite;

import entity.Recipe;

/**
 * Input data for the remove favorite use case.
 * Encapsulates the recipe to be removed from favorites.
 */
public class RemoveFavoriteInputData {
    private final Recipe recipe;

    /**
     * Constructs RemoveFavoriteInputData with the specified recipe.
     *
     * @param recipe the recipe to remove from favorites
     */
    public RemoveFavoriteInputData(Recipe recipe) {
        this.recipe = recipe;
    }

    /**
     * Gets the recipe to be removed from favorites.
     *
     * @return the recipe to remove
     */
    public Recipe getRecipe() {
        return recipe;
    }
}
