package logic.favorites.add_favorite;

import entity.Recipe;

/**
 * Input data for the add favorite use case.
 * Encapsulates the recipe to be added to favorites.
 */
public class AddFavoriteInputData {

    private final Recipe recipe;

    /**
     * Constructs AddFavoriteInputData with the specified recipe.
     *
     * @param recipe the recipe to add to favorites
     */
    public AddFavoriteInputData(Recipe recipe) {
        this.recipe = recipe;
    }

    /**
     * Gets the recipe to be added to favorites.
     *
     * @return the recipe to add
     */
    public Recipe getRecipe() {
        return recipe;
    }
}
