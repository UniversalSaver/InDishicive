package logic.user_recipe.add_recipe.add_ingredient;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import entity.Ingredient;

/**
 * An implementation of the respective interface.
 */
public class AddIngredientInteractor implements AddIngredientInputBoundary {

	private final AddIngredientDataAccessInterface addIngredientDataAccess;
	private final AddIngredientOutputBoundary addIngredientPresenter;

	public AddIngredientInteractor(AddIngredientDataAccessInterface addIngredientDataAccess,
								   AddIngredientOutputBoundary addIngredientPresenter) {
		this.addIngredientDataAccess = addIngredientDataAccess;
		this.addIngredientPresenter = addIngredientPresenter;
	}
	
	@Override
	public void execute() {
		final List<Ingredient> listOfIngredients = addIngredientDataAccess.listPossibleIngredients();

		if (listOfIngredients == null) {
			addIngredientPresenter.presentFailView();
		} else {
			addIngredientPresenter.presentSuccessView(getOutputData(listOfIngredients));
		}
	}

	@NotNull
	private static AddIngredientOutputData getOutputData(List<Ingredient> listOfIngredients) {
		return new AddIngredientOutputData(getIngredientNames(listOfIngredients));
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
