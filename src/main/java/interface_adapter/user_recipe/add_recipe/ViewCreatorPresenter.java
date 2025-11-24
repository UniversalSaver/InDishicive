package interface_adapter.user_recipe.add_recipe;

import interface_adapter.UserRecipesViewManagerModel;
import use_case.user_recipe.add_recipe.view_recipe_creator.ViewCreatorOutputBoundary;

public class ViewCreatorPresenter implements ViewCreatorOutputBoundary {
	private final UserRecipesViewManagerModel userRecipesViewManagerModel;
	private final AddRecipeViewModel addRecipeViewModel;

	public ViewCreatorPresenter(UserRecipesViewManagerModel userRecipesViewManagerModel,
								AddRecipeViewModel addRecipeViewModel) {
		this.userRecipesViewManagerModel = userRecipesViewManagerModel;
		this.addRecipeViewModel = addRecipeViewModel;
	}

	@Override
	public void prepareSuccessView() {

		this.userRecipesViewManagerModel.setState(AddRecipeViewModel.VIEW_NAME);
		this.userRecipesViewManagerModel.firePropertyChange(UserRecipesViewManagerModel.CHANGE_VIEW);

		this.addRecipeViewModel.firePropertyChange(AddRecipeViewModel.WIPE_VIEW);
	}
}
