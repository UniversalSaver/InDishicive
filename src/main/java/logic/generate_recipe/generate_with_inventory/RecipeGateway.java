package logic.generate_recipe.generate_with_inventory;

import java.util.List;
import java.util.Set;

import entity.Recipe;

/**
 * Gateway for finding recipes based on available inventory items.
 */
public interface RecipeGateway {
    /**
     * Finds recipes that can be made using the given inventory items.
     *
     * @param have the set of available ingredient names
     * @return a list of matching recipes
     */
    List<Recipe> findByInventory(Set<String> have);
}
