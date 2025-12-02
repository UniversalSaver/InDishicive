package data_access_object;

import databases.generate_recipe.MealDbRecipeGateway;
import entity.Recipe;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Simple integration-style tests for MealDbRecipeGateway.
 * Requires an active Internet connection to TheMealDB.
 */
class MealDbRecipeGatewayTest {

    @Test
    void emptyInventoryReturnsEmptyList() {
        MealDbRecipeGateway gateway = new MealDbRecipeGateway();

        List<Recipe> resultNull = gateway.findByInventory(null);
        assertNotNull(resultNull);
        assertTrue(resultNull.isEmpty());

        List<Recipe> resultEmpty = gateway.findByInventory(Set.of());
        assertNotNull(resultEmpty);
        assertTrue(resultEmpty.isEmpty());
    }

    @Test
    void preloadAllRecipesCanBeCalledMultipleTimes() {
        MealDbRecipeGateway gateway = new MealDbRecipeGateway();

        gateway.preloadAllRecipes();
        gateway.preloadAllRecipes();
        gateway.preloadAllRecipes();
    }

    @Test
    void findByInventoryWithCommonIngredientsDoesNotCrash() {
        MealDbRecipeGateway gateway = new MealDbRecipeGateway();

        Set<String> inventory = new HashSet<>();
        inventory.add("chicken");
        inventory.add("salt");
        inventory.add("onion");

        List<Recipe> recipes = gateway.findByInventory(inventory);

        assertNotNull(recipes);
        if (!recipes.isEmpty()) {
            Recipe first = recipes.get(0);
            assertNotNull(first.getTitle());
            assertFalse(first.getTitle().isBlank());
        }
    }
}