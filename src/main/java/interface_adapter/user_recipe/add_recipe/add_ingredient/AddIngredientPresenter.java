package interface_adapter.user_recipe.add_recipe.add_ingredient;

import interface_adapter.user_recipe.add_recipe.AddRecipeViewModel;
import use_case.user_recipe.add_recipe.add_ingredient.AddIngredientOutputBoundary;
import use_case.user_recipe.add_recipe.add_ingredient.AddIngredientOutputData;

public class AddIngredientPresenter implements AddIngredientOutputBoundary {

	AddRecipeViewModel viewModel;

	public AddIngredientPresenter(AddRecipeViewModel viewModel) {
		this.viewModel = viewModel;
	}

	@Override
	public void presentSuccessView(AddIngredientOutputData outputData) {
		viewModel.setState(outputData.getPossibleIngredients());
		viewModel.firePropertyChange(AddRecipeViewModel.ADD_INGREDIENT);
	}

	@Override
	public void presentFailView() {
		viewModel.firePropertyChange(AddRecipeViewModel.DATABASE_NOT_FOUND);
	}
}
