package adapters.user_recipe.add_recipe;

import java.util.ArrayList;
import java.util.List;

import adapters.user_recipe.view_recipes.ViewRecipesController;
import logic.user_recipe.add_recipe.AddRecipeOutputBoundary;

/**
 * Prepares the view to show any errors in their recipe, or the current recipe list if addition is successful.
 */
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
		final List<String> encapsulatedMessage = new ArrayList<>();
		encapsulatedMessage.add(message);
		addRecipeViewModel.setState(encapsulatedMessage);
		addRecipeViewModel.firePropertyChange(AddRecipeViewModel.ADD_RECIPE_FAIL);
	}
}
