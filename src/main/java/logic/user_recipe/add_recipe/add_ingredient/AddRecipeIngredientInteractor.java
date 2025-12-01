package logic.user_recipe.add_recipe.add_ingredient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import entity.Ingredient;

/**
 * An implementation of the respective interface.
 */
public class AddRecipeIngredientInteractor implements AddRecipeIngredientInputBoundary {

	private final AddRecipeIngredientDataAccessInterface addIngredientDataAccess;
	private final AddRecipeIngredientOutputBoundary addIngredientPresenter;

	public AddRecipeIngredientInteractor(AddRecipeIngredientDataAccessInterface addIngredientDataAccess,
                                         AddRecipeIngredientOutputBoundary addIngredientPresenter) {
		this.addIngredientDataAccess = addIngredientDataAccess;
		this.addIngredientPresenter = addIngredientPresenter;
	}
	
	@Override
	public void execute() {
		final List<Ingredient> listOfIngredients = addIngredientDataAccess.listPossibleIngredients();

		if (listOfIngredients == null) {
			addIngredientPresenter.presentFailView();
		} else {
            listOfIngredients.sort(Comparator.comparing(Ingredient::getName));
			addIngredientPresenter.presentSuccessView(getOutputData(listOfIngredients));
		}
	}

	@NotNull
	private static AddRecipeIngredientOutputData getOutputData(List<Ingredient> listOfIngredients) {
		return new AddRecipeIngredientOutputData(getIngredientNames(listOfIngredients));
	}

	@NotNull
	private static List<String> getIngredientNames(List<Ingredient> listOfIngredients) {
		final List<String> ingredientNames = new ArrayList<>();
		for (Ingredient ingredient : listOfIngredients) {
			ingredientNames.add(ingredient.getName());
		}
		return ingredientNames;
	}
}
