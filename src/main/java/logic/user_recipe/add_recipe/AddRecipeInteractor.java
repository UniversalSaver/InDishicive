package logic.user_recipe.add_recipe;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import entity.Ingredient;
import entity.UserRecipe;

/**
 * An implementation of the AddRecipeInputBoundary.
 */
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
		final UserRecipe gottenRecipe = createUserRecipe(inputData);
		if (gottenRecipe != null) {
            final String databaseMessage = addRecipeDataAccess.addRecipe(gottenRecipe);

            if (databaseMessage.equals(AddRecipeDataAccessInterface.ADDED_MESSAGE)) {
                addRecipeOutputBoundary.prepareSuccessView();
            } else {
                addRecipeOutputBoundary.prepareFailureView(databaseMessage);
            }
        }
	}

	@Nullable
	private UserRecipe createUserRecipe(AddRecipeInputData inputData) {
		final List<Ingredient> ingredients = createIngredients(inputData);
		final String name = createName(inputData);
		final String description = createDescription(inputData);
		final String steps = createSteps(inputData);

		if (ingredients == null || name == null || description == null || steps == null) {
            return null;
        }

		return new UserRecipe(name, ingredients, steps, description);
	}

	@Nullable
	private String createSteps(AddRecipeInputData inputData) {
		final String steps = inputData.getSteps();
		if (emptyString(steps)) {
			addRecipeOutputBoundary.prepareFailureView("Steps are empty.");
			return null;
		}
		return steps;
	}

	@Nullable
	private String createDescription(AddRecipeInputData inputData) {
		final String description = inputData.getDescription();
		if (emptyString(description)) {
			addRecipeOutputBoundary.prepareFailureView("Description is empty.");
			return null;
		}
		return description;
	}

	@Nullable
	private String createName(AddRecipeInputData inputData) {
		final String name = inputData.getTitle();
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

		final List<Ingredient> ingredients = createIngredients(inputData.getIngredientNames(),
				inputData.getIngredientAmounts());

		if (ingredients.isEmpty()) {
			addRecipeOutputBoundary.prepareFailureView("Ingredients did not align with amounts.");
			return null;
		}
		return ingredients;
	}

    private List<Ingredient> createIngredients(List<String> names, List<String> amounts) {
        if (names.size() != amounts.size()) {
            return new ArrayList<>();
        }

        final List<Ingredient> result = new ArrayList<>();

        for (int i = 0; i < names.size(); i++) {
            final Ingredient ingredient = new Ingredient(
                    names.get(i),
                    amounts.get(i)
            );
            result.add(ingredient);
        }

        return result;
    }

	private boolean stringListNotFull(List<String> list) {
		boolean emptyList = false;
        for (String string : list) {
			if (emptyString(string)) {
                emptyList = true;
                break;
            }
		}
		return emptyList;
	}

	private static boolean emptyString(String string) {
		return string == null || string.isBlank();
	}
}
