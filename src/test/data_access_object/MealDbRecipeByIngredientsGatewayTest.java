package data_access_object;

import databases.generate_recipe.MealDbRecipeByIngredientsGateway;
import entity.Recipe;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Simple integration-style tests for MealDbRecipeByIngredientsGateway.
 */
class MealDbRecipeByIngredientsGatewayTest {

    @Test
    void returnsRecipesForValidIngredient() {
        MealDbRecipeByIngredientsGateway gateway = new MealDbRecipeByIngredientsGateway();

        List<String> ingredients = new ArrayList<>();
        ingredients.add("chicken");

        List<Recipe> recipes = gateway.findByIngredients(ingredients);

        assertNotNull(recipes);
        assertFalse(recipes.isEmpty());

        Recipe first = recipes.get(0);
        assertNotNull(first.getTitle());
        assertFalse(first.getTitle().isBlank());
    }

    @Test
    void returnsEmptyForNonExistingIngredient() {
        MealDbRecipeByIngredientsGateway gateway = new MealDbRecipeByIngredientsGateway();

        List<String> ingredients = new ArrayList<>();
        ingredients.add("thisIngredientDoesNotExist");

        List<Recipe> recipes = gateway.findByIngredients(ingredients);

        assertNotNull(recipes);
        assertTrue(recipes.isEmpty());
    }

    @Test
    void returnsEmptyForNullOrOnlyBlankInputs() {
        MealDbRecipeByIngredientsGateway gateway = new MealDbRecipeByIngredientsGateway();

        List<Recipe> resultNull = gateway.findByIngredients(null);
        assertNotNull(resultNull);
        assertTrue(resultNull.isEmpty());

        List<String> dirty = new ArrayList<>();
        dirty.add(null);
        dirty.add("   ");

        List<Recipe> resultDirty = gateway.findByIngredients(dirty);
        assertNotNull(resultDirty);
        assertTrue(resultDirty.isEmpty());
    }
}