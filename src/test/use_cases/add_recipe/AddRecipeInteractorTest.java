package use_cases.add_recipe;

import org.junit.jupiter.api.Test;
import use_case.add_recipe.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AddRecipeInteractorTest {

	/**
	 * A test where everything is given as expected.
	 */
	@Test
	void successTest() {
		List<String> ingredientNames = Arrays.stream(new String[] {"Milk", "Unsweetened cocoa powder",
				"Granulated sugar", "Chocolate chips", "Vanilla Extract"}).toList();
		List<String> ingredientAmounts = Arrays.stream(new String[] {"4 cups", "1/4 cup", "1/4 cup", "1/2 cup",
				"1/4 tsp"}).toList();
		AddRecipeInputData inputData = new AddRecipeInputData(ingredientNames, ingredientAmounts, "Hot Chocolate",
				"A cool hot chocolate recipe", "Place milk, cocoa powder and sugar in a small saucepan. Heat over medium/medium-low heat, whisking frequently, until warm (but not boiling). Add chocolate chips and whisk constantly until the chocolate chips melt and distribute evenly into the milk. Whisk in vanilla extract and a pinch of salt (if desired), serve immediately. ");
		AddRecipeOutputBoundary outputBoundary = new AddRecipeOutputBoundary() {
			@Override
			public void prepareSuccessView() {

			}

			@Override
			public void prepareFailureView(String message) {
				fail(message);
			}
		};
		AddRecipeDataAccessInterface dataAccess = recipe -> AddRecipeDataAccessInterface.ADDED_MESSAGE;

		AddRecipeInteractor addRecipeInteractor = new AddRecipeInteractor(dataAccess, outputBoundary);

		addRecipeInteractor.execute(inputData);
	}

	/**
	 * A test where the DAO decided something is wrong with the recipe.
	 */
	@Test
	void DAOFailureTest() {
		List<String> ingredientNames = Arrays.stream(new String[] {"Milk", "Unsweetened cocoa powder",
				"Granulated sugar", "Chocolate chips", "Vanilla Extract"}).toList();
		List<String> ingredientAmounts = Arrays.stream(new String[] {"4 cups", "1/4 cup", "1/4 cup", "1/2 cup",
				"1/4 tsp"}).toList();
		AddRecipeInputData inputData = new AddRecipeInputData(ingredientNames, ingredientAmounts, "Hot Chocolate",
				"A cool hot chocolate recipe", "Place milk, cocoa powder and sugar in a small saucepan. Heat over medium/medium-low heat, whisking frequently, until warm (but not boiling). Add chocolate chips and whisk constantly until the chocolate chips melt and distribute evenly into the milk. Whisk in vanilla extract and a pinch of salt (if desired), serve immediately. ");
		AddRecipeOutputBoundary outputBoundary = new AddRecipeOutputBoundary() {
			@Override
			public void prepareSuccessView() {
				fail("Should not have succeeded.");
			}

			@Override
			public void prepareFailureView(String message) {
				assertEquals("Something went wrong.", message);
			}
		};
		AddRecipeDataAccessInterface dataAccess = recipe -> "Something went wrong.";

		AddRecipeInteractor addRecipeInteractor = new AddRecipeInteractor(dataAccess, outputBoundary);

		addRecipeInteractor.execute(inputData);
	}

	/**
	 * A test where there is one less measurement than name for ingredients.
	 */
	@Test
	void ingredientAmountMeasureMismatchFailTest() {
		List<String> ingredientNames = Arrays.stream(new String[] {"Milk", "Unsweetened cocoa powder",
				"Granulated sugar", "Chocolate chips", "Vanilla Extract"}).toList();
		List<String> ingredientAmounts = Arrays.stream(new String[] {"4 cups", "1/4 cup", "1/4 cup",
				"1/2 cup"}).toList();
		AddRecipeInputData inputData = new AddRecipeInputData(ingredientNames, ingredientAmounts, "Hot Chocolate",
				"A cool hot chocolate recipe", "Place milk, cocoa powder and sugar in a small saucepan. Heat over medium/medium-low heat, whisking frequently, until warm (but not boiling). Add chocolate chips and whisk constantly until the chocolate chips melt and distribute evenly into the milk. Whisk in vanilla extract and a pinch of salt (if desired), serve immediately. ");

		AddRecipeOutputBoundary outputBoundary = new AddRecipeOutputBoundary() {
			@Override
			public void prepareSuccessView() {
				fail("Should not have succeeded");
			}

			@Override
			public void prepareFailureView(String message) {
				assertEquals("Ingredients did not align with amounts.", message);
			}
		};
		AddRecipeDataAccessInterface dataAccess = recipe -> AddRecipeDataAccessInterface.ADDED_MESSAGE;

		AddRecipeInteractor addRecipeInteractor = new AddRecipeInteractor(dataAccess, outputBoundary);

		addRecipeInteractor.execute(inputData);
	}

	/**
	 * A test where there is one less name than measurement for ingredients.
	 */
	@Test
	void ingredientAmountNameMismatchFailTest() {
		List<String> ingredientNames = Arrays.stream(new String[] {"Unsweetened cocoa powder", "Granulated sugar",
				"Chocolate chips", "Vanilla Extract"}).toList();
		List<String> ingredientAmounts = Arrays.stream(new String[] {"4 cups", "1/4 cup", "1/4 cup",
				"1/2 cup", "1/4 tsp"}).toList();
		AddRecipeInputData inputData = new AddRecipeInputData(ingredientNames, ingredientAmounts, "Hot Chocolate",
				"A cool hot chocolate recipe", "Place milk, cocoa powder and sugar in a small saucepan. Heat over medium/medium-low heat, whisking frequently, until warm (but not boiling). Add chocolate chips and whisk constantly until the chocolate chips melt and distribute evenly into the milk. Whisk in vanilla extract and a pinch of salt (if desired), serve immediately. ");
		AddRecipeOutputBoundary outputBoundary = new AddRecipeOutputBoundary() {
			@Override
			public void prepareSuccessView() {
				fail("Should not have succeeded");
			}

			@Override
			public void prepareFailureView(String message) {
				assertEquals("Ingredients did not align with amounts.", message);
			}
		};
		AddRecipeDataAccessInterface dataAccess = recipe -> AddRecipeDataAccessInterface.ADDED_MESSAGE;

		AddRecipeInteractor addRecipeInteractor = new AddRecipeInteractor(dataAccess, outputBoundary);

		addRecipeInteractor.execute(inputData);
	}

	/**
	 * A test where an ingredient is unnamed.
	 */
	@Test
	void ingredientNameNotGivenFailTest() {
		List<String> ingredientNames = Arrays.stream(new String[] {"Milk", "Unsweetened cocoa powder",
				"Granulated sugar", "Chocolate chips", ""}).toList();
		List<String> ingredientAmounts = Arrays.stream(new String[] {"4 cups", "1/4 cup", "1/4 cup",
				"1/2 cup", "1/4 tsp"}).toList();
		AddRecipeInputData inputData = new AddRecipeInputData(ingredientNames, ingredientAmounts, "Hot Chocolate",
				"A cool hot chocolate recipe", "Place milk, cocoa powder and sugar in a small saucepan. Heat over medium/medium-low heat, whisking frequently, until warm (but not boiling). Add chocolate chips and whisk constantly until the chocolate chips melt and distribute evenly into the milk. Whisk in vanilla extract and a pinch of salt (if desired), serve immediately. ");
		AddRecipeOutputBoundary outputBoundary = new AddRecipeOutputBoundary() {
			@Override
			public void prepareSuccessView() {
				fail("Should not have succeeded");
			}

			@Override
			public void prepareFailureView(String message) {
				assertEquals("Not all ingredients are named.", message);
			}
		};
		AddRecipeDataAccessInterface dataAccess = recipe -> AddRecipeDataAccessInterface.ADDED_MESSAGE;

		AddRecipeInteractor addRecipeInteractor = new AddRecipeInteractor(dataAccess, outputBoundary);

		addRecipeInteractor.execute(inputData);
	}

	/**
	 * A test where a measurement is unnamed.
	 */
	@Test
	void ingredientAmountNotGivenFailTest() {
		List<String> ingredientNames = Arrays.stream(new String[] {"Milk", "Unsweetened cocoa powder",
				"Granulated sugar", "Chocolate chips", "Vanilla Extract"}).toList();
		List<String> ingredientAmounts = Arrays.stream(new String[] {"4 cups", "", "1/4 cup", "1/2 cup",
				"1/4 tsp"}).toList();
		AddRecipeInputData inputData = new AddRecipeInputData(ingredientNames, ingredientAmounts, "Hot Chocolate",
				"A cool hot chocolate recipe", "Place milk, cocoa powder and sugar in a small saucepan. Heat over medium/medium-low heat, whisking frequently, until warm (but not boiling). Add chocolate chips and whisk constantly until the chocolate chips melt and distribute evenly into the milk. Whisk in vanilla extract and a pinch of salt (if desired), serve immediately. ");
		AddRecipeOutputBoundary outputBoundary = new AddRecipeOutputBoundary() {
			@Override
			public void prepareSuccessView() {
				fail("Shouldn't have succeeded.");
			}

			@Override
			public void prepareFailureView(String message) {
				assertEquals("Not all ingredient amounts are named.", message);
			}
		};
		AddRecipeDataAccessInterface dataAccess = recipe -> AddRecipeDataAccessInterface.ADDED_MESSAGE;

		AddRecipeInteractor addRecipeInteractor = new AddRecipeInteractor(dataAccess, outputBoundary);

		addRecipeInteractor.execute(inputData);

	}

	/**
	 * A test where the name isn't given.
	 */
	@Test
	void recipeNotNamedFailTest() {
		List<String> ingredientNames = Arrays.stream(new String[] {"Milk", "Unsweetened cocoa powder",
				"Granulated sugar", "Chocolate chips", "Vanilla Extract"}).toList();
		List<String> ingredientAmounts = Arrays.stream(new String[] {"4 cups", "1/4 cup", "1/4 cup", "1/2 cup",
				"1/4 tsp"}).toList();
		AddRecipeInputData inputData = new AddRecipeInputData(ingredientNames, ingredientAmounts, "   ",
				"A cool hot chocolate recipe", "Place milk, cocoa powder and sugar in a small saucepan. Heat over medium/medium-low heat, whisking frequently, until warm (but not boiling). Add chocolate chips and whisk constantly until the chocolate chips melt and distribute evenly into the milk. Whisk in vanilla extract and a pinch of salt (if desired), serve immediately. ");
		AddRecipeOutputBoundary outputBoundary = new AddRecipeOutputBoundary() {
			@Override
			public void prepareSuccessView() {
				fail("Shouldn't have succeeded.");
			}

			@Override
			public void prepareFailureView(String message) {
				assertEquals("Title is empty.", message);
			}
		};
		AddRecipeDataAccessInterface dataAccess = recipe -> AddRecipeDataAccessInterface.ADDED_MESSAGE;

		AddRecipeInteractor addRecipeInteractor = new AddRecipeInteractor(dataAccess, outputBoundary);

		addRecipeInteractor.execute(inputData);
	}

	/**
	 * A test where the steps are not given.
	 */
	@Test
	void stepsNotGivenFailTest() {
		List<String> ingredientNames = Arrays.stream(new String[] {"Milk", "Unsweetened cocoa powder",
				"Granulated sugar", "Chocolate chips", "Vanilla Extract"}).toList();
		List<String> ingredientAmounts = Arrays.stream(new String[] {"4 cups", "1/4 cup", "1/4 cup", "1/2 cup",
				"1/4 tsp"}).toList();
		AddRecipeInputData inputData = new AddRecipeInputData(ingredientNames, ingredientAmounts, "Hot Chocolate",
				"A cool hot chocolate recipe", null);
		AddRecipeOutputBoundary outputBoundary = new AddRecipeOutputBoundary() {
			@Override
			public void prepareSuccessView() {
				fail("Shouldn't have succeeded.");
			}

			@Override
			public void prepareFailureView(String message) {
				assertEquals("Steps are empty.", message);
			}
		};
		AddRecipeDataAccessInterface dataAccess = recipe -> AddRecipeDataAccessInterface.ADDED_MESSAGE;

		AddRecipeInteractor addRecipeInteractor = new AddRecipeInteractor(dataAccess, outputBoundary);

		addRecipeInteractor.execute(inputData);
	}

	/**
	 * A test where the description is not given.
	 */
	@Test
	void descriptionNotGivenFailTest() {
		List<String> ingredientNames = Arrays.stream(new String[] {"Milk", "Unsweetened cocoa powder",
				"Granulated sugar", "Chocolate chips", "Vanilla Extract"}).toList();
		List<String> ingredientAmounts = Arrays.stream(new String[] {"4 cups", "1/4 cup", "1/4 cup", "1/2 cup",
				"1/4 tsp"}).toList();
		AddRecipeInputData inputData = new AddRecipeInputData(ingredientNames, ingredientAmounts, "Hot Chocolate",
				"		", "Place milk, cocoa powder and sugar in a small saucepan. Heat over medium/medium-low heat, whisking frequently, until warm (but not boiling). Add chocolate chips and whisk constantly until the chocolate chips melt and distribute evenly into the milk. Whisk in vanilla extract and a pinch of salt (if desired), serve immediately. ");
		AddRecipeOutputBoundary outputBoundary = new AddRecipeOutputBoundary() {
			@Override
			public void prepareSuccessView() {
				fail("Shouldn't have succeeded.");
			}

			@Override
			public void prepareFailureView(String message) {
				assertEquals("Description is empty.", message);
			}
		};
		AddRecipeDataAccessInterface dataAccess = recipe -> AddRecipeDataAccessInterface.ADDED_MESSAGE;

		AddRecipeInteractor addRecipeInteractor = new AddRecipeInteractor(dataAccess, outputBoundary);

		addRecipeInteractor.execute(inputData);
	}
}
