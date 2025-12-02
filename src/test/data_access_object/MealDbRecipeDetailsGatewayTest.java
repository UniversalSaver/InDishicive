package data_access_object;

import databases.generate_recipe.MealDbRecipeDetailsGateway;
import entity.Ingredient;
import entity.Recipe;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MealDbRecipeDetailsGatewayTest {

    @Test
    void existingRecipeTest() {
        MealDbRecipeDetailsGateway gateway = new MealDbRecipeDetailsGateway();

        Recipe recipe = gateway.findByTitle("Arrabiata");

        assertNotNull(recipe);
        assertEquals("Spicy Arrabiata Penne", recipe.getTitle());
    }

    @Test
    void noRecipeTest() {
        MealDbRecipeDetailsGateway gateway = new MealDbRecipeDetailsGateway();

        Recipe recipe = gateway.findByTitle("ThisRecipeDoesNotExist12345");

        assertNull(recipe);
    }

    @Test
    void ingredientsLoadedTest() {
        MealDbRecipeDetailsGateway gateway = new MealDbRecipeDetailsGateway();

        Recipe recipe = gateway.findByTitle("Arrabiata");

        assertNotNull(recipe);

        List<Ingredient> ingredients = recipe.getIngredients();
        assertNotNull(ingredients);
        assertFalse(ingredients.isEmpty());

        for (Ingredient ingredient : ingredients) {
            assertNotNull(ingredient.getName());
            assertFalse(ingredient.getName().isBlank());
        }

        assertNotNull(recipe.getSteps());
        assertFalse(recipe.getSteps().isBlank());
    }
}