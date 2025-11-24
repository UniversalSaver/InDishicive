package use_case.add_recipe;

import entity.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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
		List<Ingredient> listOfIngredients = addIngredientDataAccess.listPossibleIngredients();

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
		List<String> ingredientNames = new ArrayList<>();
		for (Ingredient ingredient : listOfIngredients) {
			ingredientNames.add(ingredient.getName());
		}
		return ingredientNames;
	}
}
