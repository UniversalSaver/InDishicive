package interface_adapter.add_recipe;

import use_case.view_recipe_creator.ViewCreatorInputBoundary;

public class SwitchViewController {

	private final ViewCreatorInputBoundary viewCreatorInteractor;

	public SwitchViewController(ViewCreatorInputBoundary viewCreatorInteractor) {
		this.viewCreatorInteractor = viewCreatorInteractor;
	}

	public void execute() {
		viewCreatorInteractor.execute();
	}
}
