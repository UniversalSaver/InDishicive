package logic.generate_recipe.generate_with_inventory;

import java.util.List;
import java.util.Set;

import entity.Recipe;

/**
 * Gateway interface for recipe operations.
 */
public interface RecipeGateway {

    /**
     * Finds recipes that can be made with the given ingredients.
     * @param have the set of ingredient names available
     * @return a list of matching recipes
     */
    List<Recipe> findByInventory(Set<String> have);

}
