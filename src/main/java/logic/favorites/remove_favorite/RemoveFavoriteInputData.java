package logic.favorites.remove_favorite;
import entity.Recipe;


/**
 * Input Data for the remove favorite use ase
 */
public class RemoveFavoriteInputData {
    private final Recipe recipe;

    public RemoveFavoriteInputData(Recipe recipe) {
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }
}
