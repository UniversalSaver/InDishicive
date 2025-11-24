package interface_adapter.user_recipe.add_recipe;

import use_case.user_recipe.add_recipe.view_recipe_creator.ViewCreatorInputBoundary;

public class SwitchViewController {

	private final ViewCreatorInputBoundary viewCreatorInteractor;

	public SwitchViewController(ViewCreatorInputBoundary viewCreatorInteractor) {
		this.viewCreatorInteractor = viewCreatorInteractor;
	}

	public void execute() {
		viewCreatorInteractor.execute();
	}
}
