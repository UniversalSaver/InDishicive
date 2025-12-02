package adapters.user_recipe.add_recipe;

import logic.user_recipe.add_recipe.view_recipe_creator.ViewCreatorInputBoundary;

/**
 * Switched the view to the recipe creation panel.
 */
public class SwitchViewController {

	private final ViewCreatorInputBoundary viewCreatorInteractor;

	public SwitchViewController(ViewCreatorInputBoundary viewCreatorInteractor) {
		this.viewCreatorInteractor = viewCreatorInteractor;
	}

    /**
     * If called, executes program to show the recipe creator view.
     */
    public void execute() {
		viewCreatorInteractor.execute();
	}
}
