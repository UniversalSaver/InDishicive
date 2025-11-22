package interface_adapter.add_recipe;

import use_case.add_recipe.AddIngredientOutputBoundary;
import use_case.add_recipe.AddIngredientOutputData;

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
