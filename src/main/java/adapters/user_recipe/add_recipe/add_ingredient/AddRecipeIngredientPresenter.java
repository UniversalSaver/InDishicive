package adapters.user_recipe.add_recipe.add_ingredient;

import adapters.user_recipe.add_recipe.AddRecipeViewModel;
import logic.user_recipe.add_recipe.add_ingredient.AddRecipeIngredientOutputBoundary;
import logic.user_recipe.add_recipe.add_ingredient.AddRecipeIngredientOutputData;

public class AddRecipeIngredientPresenter implements AddRecipeIngredientOutputBoundary {
/**
 * Gives the user a new ingredient option to add to their recipe,
 * and upon failure, tells user that something went wrong.
 */
public class AddIngredientPresenter implements AddIngredientOutputBoundary {

	private AddRecipeViewModel viewModel;

	public AddRecipeIngredientPresenter(AddRecipeViewModel viewModel) {
		this.viewModel = viewModel;
	}

	@Override
	public void presentSuccessView(AddRecipeIngredientOutputData outputData) {
		viewModel.setState(outputData.getPossibleIngredients());
		viewModel.firePropertyChange(AddRecipeViewModel.ADD_INGREDIENT);
	}

	@Override
	public void presentFailView() {
		viewModel.firePropertyChange(AddRecipeViewModel.DATABASE_NOT_FOUND);
	}
}
