package use_case.favorites.add_favorite;
import entity.Recipe;

/**
 * Input data for the Add Favorite use case.
 */
public class AddFavoriteInputData {

    private final Recipe recipe;

    public AddFavoriteInputData(Recipe recipe) {
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }
}