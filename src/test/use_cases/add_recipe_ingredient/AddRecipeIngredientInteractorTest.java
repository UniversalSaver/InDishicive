package use_cases.add_recipe_ingredient;

import databases.test_DAO.FromMemoryMealRecipeDataAccessObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import logic.user_recipe.add_recipe.add_ingredient.AddRecipeIngredientInteractor;
import logic.user_recipe.add_recipe.add_ingredient.AddRecipeIngredientOutputBoundary;
import logic.user_recipe.add_recipe.add_ingredient.AddRecipeIngredientOutputData;

import java.util.ArrayList;
import java.util.List;

class AddRecipeIngredientInteractorTest {
	@Test
	void allIngredientsPresentTest() {
		AddRecipeIngredientInteractor addIngredientInteractor = new AddRecipeIngredientInteractor(
				new FromMemoryMealRecipeDataAccessObject(), new AddRecipeIngredientOutputBoundary() {
			@Override
			public void presentSuccessView(AddRecipeIngredientOutputData outputData) {
				List<String> ingredientNames = outputData.getPossibleIngredients();
				List<String> expectedIngredientNames =
						new ArrayList<>(List.of(new String[] {"Beef", "Cheese", "Dough", "Tomato Sauce"}));

				Assertions.assertEquals(expectedIngredientNames, ingredientNames);
			}

			@Override
			public void presentFailView() {
				Assertions.fail("Returned Database should not be null.");
			}
		}
		);
		addIngredientInteractor.execute();
	}

	@Test
	void databaseUnaccessibleTest() {
		AddRecipeIngredientInteractor addIngredientInteractor = new AddRecipeIngredientInteractor(
				() -> null, new AddRecipeIngredientOutputBoundary() {

			@Override
			public void presentSuccessView(AddRecipeIngredientOutputData outputData) {
				Assertions.fail();
			}

			@Override
			public void presentFailView() {
                // Empty, as this is the desired outcome
			}
		});
		addIngredientInteractor.execute();
	}
}
