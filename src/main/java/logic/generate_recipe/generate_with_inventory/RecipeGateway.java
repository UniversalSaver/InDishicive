package logic.generate_recipe.generate_with_inventory;

import entity.Recipe;

import java.util.List;

public interface RecipeGateway {

    List<Recipe> findByInventory(java.util.Set<String> have);

}
