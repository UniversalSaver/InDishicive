package main.java.use_case.favorite_recipes;
import entity.Recipe;
import netscape.javascript.JSObject;

/**
 * DAO interface for the Favorite Use Case.
 */
public interface FavoriteDataAccessInterface {

    /**
     * saves the recipe as a Favorite
     * @param recipe to save
     */
    void saveFavorite(Recipe recipe);

}
