package interface_adapter.add_recipe;

import interface_adapter.view_recipes.ViewRecipesController;
import use_case.add_recipe.AddRecipeOutputBoundary;

import java.util.ArrayList;
import java.util.List;

public class AddRecipePresenter implements AddRecipeOutputBoundary {

	private final ViewRecipesController viewRecipesController;
	private final AddRecipeViewModel addRecipeViewModel;

	public AddRecipePresenter(ViewRecipesController viewRecipesController, AddRecipeViewModel addRecipeViewModel) {
		this.viewRecipesController = viewRecipesController;
		this.addRecipeViewModel = addRecipeViewModel;
	}


	@Override
	public void prepareSuccessView() {
		viewRecipesController.execute();
	}

	@Override
	public void prepareFailureView(String message) {
		List<String> encapsulatedMessage = new ArrayList<>();
		encapsulatedMessage.add(message);
		addRecipeViewModel.setState(encapsulatedMessage);
		addRecipeViewModel.firePropertyChange(AddRecipeViewModel.ADD_RECIPE_FAIL);
	}
}
