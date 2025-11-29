package logic.user_recipe.add_recipe.add_ingredient;

import entity.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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
		List<Ingredient> listOfIngredients = addIngredientDataAccess.listPossibleIngredients();

		if (listOfIngredients == null) {
			addIngredientPresenter.presentFailView();
		} else {
			addIngredientPresenter.presentSuccessView(getOutputData(listOfIngredients));
		}
	}

	@NotNull
	private static AddRecipeIngredientOutputData getOutputData(List<Ingredient> listOfIngredients) {
		return new AddRecipeIngredientOutputData(getIngredientNames(listOfIngredients));
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
