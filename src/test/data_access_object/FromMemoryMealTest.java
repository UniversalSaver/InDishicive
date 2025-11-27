package data_access_object;

import data_access.test_DAO.FromMemoryMealRecipeDataAccessObject;
import entity.Ingredient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class FromMemoryMealTest {
    @Test
    void existRecipeTest() {
        FromMemoryMealRecipeDataAccessObject db = new FromMemoryMealRecipeDataAccessObject();

        assertEquals("Pizza", db.findByTitle("Pizza").getTitle());
    }

    @Test
    void noRecipeTest() {
        FromMemoryMealRecipeDataAccessObject db = new FromMemoryMealRecipeDataAccessObject();

        assertNull(db.findByTitle("Escargot"));
    }

	@Test
	void getIngredientsTest() {
		FromMemoryMealRecipeDataAccessObject db = new FromMemoryMealRecipeDataAccessObject();

		List<Ingredient> gottenIngredients = db.listPossibleIngredients();
		Assertions.assertNotNull(gottenIngredients);

		List<String> ingredientNames = new ArrayList<>();


		for (Ingredient ingredient : gottenIngredients) {
			ingredientNames.add(ingredient.getName());
		}

		List<String> sortedIngredientNames = ingredientNames.stream().sorted().toList();

		Assertions.assertEquals(sortedIngredientNames, ingredientNames);
	}
}
