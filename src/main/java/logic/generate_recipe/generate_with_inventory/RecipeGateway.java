package logic.generate_recipe.generate_with_inventory;

import java.util.List;
import java.util.Set;

import entity.Recipe;

/**
 * Gateway interface for accessing recipe data.
 */
public interface RecipeGateway {

    /**
     * Finds recipes that can be made with the given ingredients.
     * @param have the set of available ingredient names
     * @return a list of recipes
     */
    List<Recipe> findByInventory(Set<String> have);
}
