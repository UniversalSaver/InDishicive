package use_case.add_recipe;

import entity.Ingredient;
import entity.UserRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AddRecipeInteractor implements AddRecipeInputBoundary {
	private final AddRecipeDataAccessInterface addRecipeDataAccess;
	private final AddRecipeOutputBoundary addRecipeOutputBoundary;

	public AddRecipeInteractor(AddRecipeDataAccessInterface addRecipeDataAccess,
							   AddRecipeOutputBoundary addRecipeOutputBoundary) {
		this.addRecipeDataAccess = addRecipeDataAccess;
		this.addRecipeOutputBoundary = addRecipeOutputBoundary;
	}

    @Override
    public void execute(AddRecipeInputData inputData) {

		// Try to create a recipe, if one of the tests doesn't pass, return, as a presentFailureView was called
		UserRecipe gottenRecipe = createUserRecipe(inputData);
		if (gottenRecipe == null) return;

		String DAOOutput = addRecipeDataAccess.addRecipe(gottenRecipe);

		if (DAOOutput.equals(AddRecipeDataAccessInterface.ADDED_MESSAGE)) {
			addRecipeOutputBoundary.prepareSuccessView();
		} else {
			addRecipeOutputBoundary.prepareFailureView(DAOOutput);
		}
	}

	@Nullable
	private UserRecipe createUserRecipe(AddRecipeInputData inputData) {
		List<Ingredient> ingredients = createIngredients(inputData);
		String name = createName(inputData);
		String description = createDescription(inputData);
		String steps = createSteps(inputData);

		if (ingredients == null || name == null || description == null || steps == null) return null;

		return new UserRecipe(name, ingredients, steps, description);
	}

	@Nullable
	private String createSteps(AddRecipeInputData inputData) {
		String steps = inputData.getSteps();
		if (emptyString(steps)) {
			addRecipeOutputBoundary.prepareFailureView("Steps are empty.");
			return null;
		}
		return steps;
	}

	@Nullable
	private String createDescription(AddRecipeInputData inputData) {
		String description = inputData.getDescription();
		if (emptyString(description)) {
			addRecipeOutputBoundary.prepareFailureView("Description is empty.");
			return null;
		}
		return description;
	}

	@Nullable
	private String createName(AddRecipeInputData inputData) {
		String name = inputData.getTitle();
		if (emptyString(name)) {
			addRecipeOutputBoundary.prepareFailureView("Title is empty.");
			return null;
		}
		return name;
	}

	@Nullable
	private List<Ingredient> createIngredients(AddRecipeInputData inputData) {
		if (stringListNotFull(inputData.getIngredientNames())) {
			addRecipeOutputBoundary.prepareFailureView("Not all ingredients are named.");
			return null;
		}
		if (stringListNotFull(inputData.getIngredientAmounts())) {
			addRecipeOutputBoundary.prepareFailureView("Not all ingredient amounts are named.");
			return null;
		}

		List<Ingredient> ingredients = createIngredients(inputData.getIngredientNames(),
				inputData.getIngredientAmounts());

		if (ingredients == null) {
			addRecipeOutputBoundary.prepareFailureView("Ingredients did not align with amounts.");
			return null;
		}
		return ingredients;
	}

	private Boolean stringListNotFull(List<String> list) {
		for (String string : list) {
			if (emptyString(string)) return true;
		}
		return list.isEmpty();
	}

	private static boolean emptyString(String string) {
		return string == null || string.isBlank();
	}

	private List<Ingredient> createIngredients(List<String> names, List<String> amounts) {
		if (names.size() != amounts.size()) {
			return null;
		}

		List<Ingredient> result = new ArrayList<>();

		for (int i = 0; i < names.size(); i++) {
			Ingredient ingredient = new Ingredient(
					names.get(i),
					amounts.get(i)
			);
			result.add(ingredient);
		}

		return result;
	}
}
