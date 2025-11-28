package adapters.user_recipe.add_recipe;

import logic.user_recipe.add_recipe.view_recipe_creator.ViewCreatorInputBoundary;

public class SwitchViewController {

	private final ViewCreatorInputBoundary viewCreatorInteractor;

	public SwitchViewController(ViewCreatorInputBoundary viewCreatorInteractor) {
		this.viewCreatorInteractor = viewCreatorInteractor;
	}

	public void execute() {
		viewCreatorInteractor.execute();
	}
}
