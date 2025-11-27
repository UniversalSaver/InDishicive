package use_cases.add_ingredient;

import data_access.FromMemoryMealRecipeDataAccessObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import use_case.add_recipe.AddIngredientInteractor;
import use_case.add_recipe.AddIngredientOutputBoundary;
import use_case.add_recipe.AddIngredientOutputData;

import java.util.ArrayList;
import java.util.List;

public class AddIngredientInteractorTest {
	@Test
	void allIngredientsPresentTest() {
		AddIngredientInteractor addIngredientInteractor = new AddIngredientInteractor(
				new FromMemoryMealRecipeDataAccessObject(), new AddIngredientOutputBoundary() {
			@Override
			public void presentSuccessView(AddIngredientOutputData outputData) {
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
		AddIngredientInteractor addIngredientInteractor = new AddIngredientInteractor(
				() -> null, new AddIngredientOutputBoundary() {

			@Override
			public void presentSuccessView(AddIngredientOutputData outputData) {
				Assertions.fail();
			}

			@Override
			public void presentFailView() {
			}
		});
		addIngredientInteractor.execute();
	}
}
