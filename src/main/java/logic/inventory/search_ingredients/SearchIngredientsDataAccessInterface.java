package logic.inventory.search_ingredients;

import java.util.List;

import databases.inventory.IngredientDataAccessException;

/**
 * Interface for accessing ingredient data from an external source.
 */
public interface SearchIngredientsDataAccessInterface {

    /**
     * Retrieves all available ingredients from the data source.
     * @return a list of ingredient names
     * @throws IngredientDataAccessException if there is a failure reading the data
     */
    List<String> getAllIngredients() throws IngredientDataAccessException;
}
